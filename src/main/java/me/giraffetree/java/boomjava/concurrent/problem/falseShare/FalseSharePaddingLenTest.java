package me.giraffetree.java.boomjava.concurrent.problem.falseShare;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * @author GiraffeTree
 * @date 2020/4/28
 */
public class FalseSharePaddingLenTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
        FalseShareUtils.printObjectLayout(new Interval0());
        FalseShareUtils.printObjectLayout(new Interval8());
        FalseShareUtils.printObjectLayout(new Interval16());
        FalseShareUtils.printObjectLayout(new Interval32());
        FalseShareUtils.printObjectLayout(new Interval48());
        FalseShareUtils.printObjectLayout(new Interval64());
        FalseShareUtils.printObjectLayout(new Interval80());

        int count = 5_000_000;
        int retry = 10;
        long[] record;
//        int[] arr = {0, 8, 16, 24, 32, 48, 64, 80};
        int[] arr = {80, 64, 48, 32, 24, 16, 8, 0};
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
