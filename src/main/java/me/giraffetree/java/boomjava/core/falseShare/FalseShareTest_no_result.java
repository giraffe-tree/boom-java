package me.giraffetree.java.boomjava.core.falseShare;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 虽然测试了很多次, 但还是没有测试出来差异?
 * 没有预期的结果
 * false sharing
 * https://www.cnblogs.com/cyfonly/p/5800758.html
 * <p>
 * 高速缓存行的大小
 * Intel i7 64 Byte
 * https://stackoverflow.com/questions/14707803/line-size-of-l1-and-l2-caches
 * <p>
 * 如何查看 cpu L1, L2 cache line size?
 * https://www.iteye.com/blog/coderplay-1486649
 * https://www.iteye.com/blog/coderplay-1485760
 * windows: cpuz 查看缓存行大小
 * linux: cat /sys/devices/system/cpu/cpu0/cache/index0/coherency_line_size
 */
public class FalseShareTest_no_result {

    private final static int NUM_THREADS = 8;

    //    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(NUM_THREADS, 16, 1000L,
//            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setDaemon(true).build());
    // 使用的是无界队列!
    private static ExecutorService executor = Executors.newFixedThreadPool(16);

    public static void main(final String[] args) {

        testBox();

        executor.shutdown();
    }

    private static void testBoxLong() {
        int base = 50 * 1000 * 1000;

        testBoxLong(4, 4, base);
        testBoxLong(64, 4, base);
        testBoxLong(64 * 4, 4, base);
        testBoxLong(64 * 8, 4, base);

        testBoxLong(4, 4, base);
        testBoxLong(64, 4, base);
        testBoxLong(64 * 4, 4, base);
        testBoxLong(64 * 8, 4, base);
    }


    private static void testBoxLong(int arrayLen, int threadCount, int cycle) {
        BoxLong boxLong = new BoxLong(arrayLen, threadCount, cycle);
        long cost = boxLong.concurrentModify(executor);
        System.out.println(String.format("len:%d thread:%d cycle:%d cost:%dms",
                arrayLen, threadCount, cycle, cost));
    }

    private static void testBox() {

        int base = 1 * 1000 * 1000;

        System.out.println("132:");
        testBox(4, 4, base, Volatile132.class);
        testBox(8, 4, base, Volatile132.class);
        testBox(16, 4, base, Volatile132.class);
        testBox(32, 4, base, Volatile132.class);

        System.out.println("128:");
        testBox(4, 4, base, Volatile128.class);
        testBox(8, 4, base, Volatile128.class);
        testBox(16, 4, base, Volatile128.class);
        testBox(32, 4, base, Volatile128.class);

        System.out.println("68:");
        testBox(4, 4, base, Volatile68.class);
        testBox(8, 4, base, Volatile68.class);
        testBox(16, 4, base, Volatile68.class);
        testBox(32, 4, base, Volatile68.class);

        System.out.println("64:");
        testBox(4, 4, base, Volatile64.class);
        testBox(8, 4, base, Volatile64.class);
        testBox(16, 4, base, Volatile64.class);
        testBox(32, 4, base, Volatile64.class);

        System.out.println("36:");
        testBox(4, 4, base, Volatile36.class);
        testBox(8, 4, base, Volatile36.class);
        testBox(16, 4, base, Volatile36.class);
        testBox(32, 4, base, Volatile36.class);

        System.out.println("32:");
        testBox(4, 4, base, Volatile32.class);
        testBox(8, 4, base, Volatile32.class);
        testBox(16, 4, base, Volatile32.class);
        testBox(32, 4, base, Volatile32.class);

        System.out.println("20:");
        testBox(4, 4, base, VolatileLong.class);
        testBox(8, 4, base, VolatileLong.class);
        testBox(16, 4, base, VolatileLong.class);
        testBox(32, 4, base, VolatileLong.class);
    }

    private static void testBox(int arrayLen, int threadCount, int cycle, Class c) {
        Box box = new Box(arrayLen, threadCount, cycle, c);
        long cost = box.concurrentModify(executor);
        System.out.println(String.format("len:%d thread:%d cycle:%d class:%s cost:%dms",
                arrayLen, threadCount, cycle, c.getSimpleName(), cost));
    }


    private static class Box<T extends VolatileLong> {
        private Object[] share;
        private int threadCount;
        /**
         * value 累加次数
         */
        private int cycle;
        private CountDownLatch countDownLatch;
        private Class<T> clz;
        /**
         * 取平均时间时, 所需要的循环次数
         */
        private int count;

        public Box(int arrayLength, int threadCount, int cycle, Class<T> c) {
            if (arrayLength < threadCount) {
                throw new RuntimeException("error: thread count > share array length ");
            }
            this.share = new Object[arrayLength];

            this.threadCount = threadCount;
            this.cycle = cycle;
            this.countDownLatch = new CountDownLatch(threadCount);
            this.clz = c;
            this.count = 10;
        }

        private void init() {
            for (int i = 0; i < share.length; i++) {
                try {
                    share[i] = clz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 多个线程同时修改一个缓存行中的内容
         *
         * @param executorService 线程池
         * @return long 平均耗时
         */
        public long concurrentModify(ExecutorService executorService) {
            init();
            long sum = 0L;
            for (int c = 0; c < count; c++) {
                long l1 = System.currentTimeMillis();
                for (int i = 0; i < threadCount; i++) {
                    final int arrIndex = i * (share.length / threadCount);
                    executorService.execute(() -> modify(arrIndex));
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long l2 = System.currentTimeMillis();
                sum += l2 - l1;
            }
            return sum / count;
        }

        private void modify(int arrIndex) {
            long i = cycle;
            while (i-- > 0) {
                long tmp = ((T) share[arrIndex]).value;
                ((T) share[arrIndex]).value = tmp + i;
            }
            countDownLatch.countDown();
        }

    }

    private static class BoxLong {
        private long[] share;
        private int threadCount;
        /**
         * value 累加次数
         */
        private int cycle;
        private CountDownLatch countDownLatch;
        /**
         * 取平均时间时, 所需要的循环次数
         */
        private int count;

        public BoxLong(int arrayLength, int threadCount, int cycle) {
            if (arrayLength < threadCount) {
                throw new RuntimeException("error: thread count > share array length ");
            }
            this.share = new long[arrayLength];

            this.threadCount = threadCount;
            this.cycle = cycle;
            this.countDownLatch = new CountDownLatch(threadCount);
            this.count = 10;
        }

        /**
         * 多个线程同时修改一个缓存行中的内容
         *
         * @param executorService 线程池
         * @return long 平均耗时
         */
        public long concurrentModify(ExecutorService executorService) {
            long sum = 0L;
            for (int c = 0; c < count; c++) {
                long l1 = System.currentTimeMillis();
                for (int i = 0; i < threadCount; i++) {
                    final int arrIndex = i * (share.length / threadCount);
                    executorService.execute(() -> modify(arrIndex));
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long l2 = System.currentTimeMillis();
                sum += l2 - l1;
            }
            return sum / count;
        }

        private void modify(int arrIndex) {
            long i = cycle;
            while (i-- > 0) {
                share[arrIndex]++;
            }
            countDownLatch.countDown();
        }

    }


    /**
     * obj header 12 字节(class指针压缩)
     */
    public static class VolatileLong {
        public volatile long value = 0L;
    }

    public final static class Volatile32 extends VolatileLong {
        private long p1;
        private int i1;

        public long get() {
            return p1 + i1;
        }
    }

    public final static class Volatile36 extends VolatileLong {
        private long p1, p2;

        public long get() {
            return p1 + p2;
        }
    }

    public final static class Volatile64 extends VolatileLong {
        private long p1, p2, p3, p4, p5;
        private int i1;
    }

    public final static class Volatile68 extends VolatileLong {
        private long p1, p2, p3, p4, p5, p6;
    }

    public final static class Volatile128 extends VolatileLong {
        private long p1, p2, p3, p4, p5, p6, p7,
                p11, p12, p13, p14, p15, p16;
        private int i1;
    }

    public final static class Volatile132 extends VolatileLong {
        private long p1, p2, p3, p4, p5, p6, p7,
                p11, p12, p13, p14, p15, p16, p17;
    }


}