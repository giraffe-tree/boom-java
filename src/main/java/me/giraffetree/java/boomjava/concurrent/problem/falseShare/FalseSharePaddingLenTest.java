package me.giraffetree.java.boomjava.concurrent.problem.falseShare;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * 不同 padding 对竞争的影响
 * 下面的表是某次我测试的结果, 注意测试的顺序
 * 可以看出 两个 volatile 的 padding 越小, 竞争越激烈
 * <p>
 * {128}  -  average:22.80ms [15, 10, 25, 26, 25, 26, 25, 25, 25, 26]
 * {80}  -  average:26.90ms [39, 26, 25, 26, 25, 26, 25, 26, 26, 25]
 * {64}  -  average:170.40ms [190, 177, 160, 186, 186, 181, 151, 168, 182, 123]
 * {48}  -  average:191.00ms [193, 186, 139, 191, 128, 193, 247, 196, 197, 240]
 * {32}  -  average:192.50ms [134, 250, 153, 203, 253, 248, 247, 120, 190, 127]
 * {24}  -  average:198.40ms [189, 191, 135, 128, 253, 143, 191, 254, 248, 252]
 * {16}  -  average:237.70ms [252, 180, 245, 256, 250, 246, 218, 246, 237, 247]
 * {8}  -  average:245.00ms [259, 247, 256, 251, 242, 199, 246, 257, 256, 237]
 * {0}  -  average:230.00ms [247, 240, 247, 268, 248, 240, 190, 128, 243, 249]
 * <p>
 * 另外猜测 由于 cpu 缓存锁升级的关系, 后面对 volatile 的写可能会更慢
 * 所以在测试的时候, 建议分开在多个进程中测试, 可能会有更加符合实际的答案
 * 以下是单独测试的结果 (每次进程只测试一种大小的对象)
 * 虽然有些差异, 但大致难看出 padding 越小, 竞争越严重
 * {128}  -  average:32.70ms [84, 13, 40, 28, 30, 26, 28, 26, 26, 26]
 * {80}  -  average:31.80ms [80, 14, 27, 36, 27, 28, 28, 26, 26, 26]
 * {64}  -  average:31.20ms [80, 10, 34, 30, 28, 27, 26, 26, 26, 25]
 * // 48
 * {48}  -  average:43.30ms [91, 21, 35, 29, 51, 34, 27, 48, 45, 52]
 * {48}  -  average:37.60ms [77, 11, 35, 96, 26, 28, 25, 27, 25, 26]
 * {48}  -  average:33.10ms [93, 10, 26, 39, 30, 28, 25, 29, 25, 26]
 * // 32
 * {32}  -  average:53.10ms [75, 12, 92, 110, 105, 27, 27, 30, 27, 26]
 * {32}  -  average:57.00ms [90, 12, 54, 27, 103, 26, 26, 104, 101, 27]
 * {32}  -  average:62.20ms [84, 26, 29, 26, 26, 107, 108, 102, 28, 86]
 * // 24
 * {24}  -  average:60.30ms [76, 13, 95, 27, 109, 25, 105, 101, 26, 26]
 * {24}  -  average:71.00ms [85, 13, 101, 28, 27, 107, 26, 101, 103, 119]
 * {24}  -  average:73.90ms [96, 19, 97, 109, 109, 26, 28, 37, 102, 116]
 * // 16
 * {16}  -  average:62.40ms [76, 12, 26, 36, 101, 110, 107, 27, 26, 103]
 * {16}  -  average:80.70ms [89, 24, 75, 90, 111, 26, 103, 107, 119, 63]
 * {16}  -  average:73.40ms [75, 22, 90, 99, 111, 101, 26, 27, 108, 75]
 * // 8
 * {8}  -  average:73.40ms [97, 25, 93, 26, 110, 105, 102, 121, 29, 26]
 * {8}  -  average:82.60ms [77, 31, 99, 101, 26, 95, 26, 145, 87, 139]
 * {8}  -  average:75.60ms [88, 19, 97, 111, 26, 107, 100, 100, 39, 69]
 * // 0
 * {0}  -  average:79.10ms [90, 25, 105, 30, 26, 109, 101, 106, 120, 79]
 * {0}  -  average:84.80ms [78, 24, 104, 130, 82, 101, 93, 100, 26, 110]
 * {0}  -  average:101.90ms [163, 50, 122, 75, 89, 107, 114, 102, 109, 88]
 * {0}  -  average:85.50ms [76, 21, 78, 102, 115, 101, 111, 112, 26, 113]
 *
 * @author GiraffeTree
 * @date 2020/4/28
 */
public class FalseSharePaddingLenTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(16, 16);

    public static void main(String[] args) {
        // 这里的代码可以打印 对象的内存分布
//        FalseShareUtils.printObjectLayout(new Interval0());
//        FalseShareUtils.printObjectLayout(new Interval8());
//        FalseShareUtils.printObjectLayout(new Interval16());
//        FalseShareUtils.printObjectLayout(new Interval32());
//        FalseShareUtils.printObjectLayout(new Interval48());
//        FalseShareUtils.printObjectLayout(new Interval64());
//        FalseShareUtils.printObjectLayout(new Interval80());

        int count = 5_000_000;
        int retry = 10;
        long[] record;
        // 如果改变顺序, 你可以明显看到不同
        // 猜想 cpu 中的缓存lock 有升级机制
//        int[] arr = {0, 8, 16, 24, 32, 48, 64, 80};
        // 建议从大到小进行测试
        int[] arr = {128, 80, 64, 48, 32, 24, 16, 8, 0};
        // 每次仅仅测试一种大小的对象, 可能会得到更加接近真实的答案
//        int[] arr = {0};

        for (int interval : arr) {
            record = testVolatile(interval, count, retry);
            System.out.println(String.format("{%d}  -  average:%.2fms %s",
                    interval,
                    LongStream.of(record).average().orElse(-1.0), Arrays.toString(record)));
        }
        EXECUTOR_SERVICE.shutdown();

    }


    private static long[] testVolatile(int interval, final int count, final int retry) {

        long[] record = new long[retry];
        int r = retry;
        ThreadLocalRandom current = ThreadLocalRandom.current();
        while (r-- > 0) {
            CountDownLatch startLatch = new CountDownLatch(2);
            CountDownLatch endLatch = new CountDownLatch(2);
            int c = current.nextInt(0, 8);
            Interval0[] arr = new Interval0[c];
            while (c-- > 0) {
                // 减少对象在同一个缓存行的不同位置的影响
                // 由于java 内存中的对象是按照 8 字节对齐的, 并且缓存行为 64 字节
                // 我们随机模拟新生成的两个对象在任意的 8/16/24/32/40/48/56/64 的起始位置上
                // Interval0 为 24 字节
                Interval0 obj24 = new Interval0();
                arr[c] = obj24;
            }

            SetAll obj = generate(interval);

            long l1 = System.currentTimeMillis();
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignored) {
                }
                int loop = count;
                while (loop-- > 0) {
                    obj.setA(loop);
                }
                endLatch.countDown();
            });
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignored) {
                }
                int loop = count;
                while (loop-- > 0) {
                    obj.setB(loop);
                }
                endLatch.countDown();
            });

            try {
                endLatch.await();
                long l2 = System.currentTimeMillis();
                record[retry - 1 - r] = l2 - l1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return record;
    }

    private static SetAll generate(int interval) {
        switch (interval) {
            case 0:
                return new Interval0();
            case 8:
                return new Interval8();
            case 16:
                return new Interval16();
            case 24:
                return new Interval24();
            case 32:
                return new Interval32();
            case 48:
                return new Interval48();
            case 64:
                return new Interval64();
            case 80:
                return new Interval80();
            case 128:
                return new Interval128();
            default:
                throw new RuntimeException("not supported interval");
        }
    }

    private interface SetAll extends SetA, SetB {

    }

    private interface SetA {
        void setA(int a);
    }

    private interface SetB {
        void setB(int b);
    }


    private static class Interval0 implements SetAll {
        volatile int a;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

    private static class Interval8 implements SetAll {
        volatile int a;
        long p1;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

    private static class Interval16 implements SetAll {
        volatile int a;
        long p1, p2;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

    private static class Interval24 implements SetAll {
        volatile int a;
        long p1, p2, p3;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

    private static class Interval32 implements SetAll {
        volatile int a;
        long p1, p2, p3, p4;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

    private static class Interval48 implements SetAll {
        volatile int a;
        long p1, p2, p3, p4, p5, p6;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

    private static class Interval64 implements SetAll {
        volatile int a;
        long p1, p2, p3, p4, p5, p6, p7, p8;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

    private static class Interval80 implements SetAll {
        volatile int a;
        long p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

    private static class Interval128 implements SetAll {
        volatile int a;
        long p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16;
        volatile int b;

        @Override
        public void setA(int a) {
            this.a = a;
        }

        @Override
        public void setB(int b) {
            this.b = b;
        }
    }

}
