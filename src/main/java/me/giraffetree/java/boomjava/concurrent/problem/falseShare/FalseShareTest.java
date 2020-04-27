package me.giraffetree.java.boomjava.concurrent.problem.falseShare;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;
import sun.misc.Contended;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 这里的测试了两个 volatile 的写操作由于 false sharing 带来的性能影响
 * 分成了以下3种情况
 * 1. 对照组, 仅仅两个 volatile 变量
 * 2. 使用 @Contented 避免了 false sharing
 * 3. 使用手动的 padding 填充, 来避免 false sharing
 * <p>
 * 终于测试成功了 TT
 * !!!!!!!!!!!!!!!! 注意 !!!!!!!!!!!!!!!!!!!!!
 * 我使用 java 8 需要加上虚拟机选项 -XX:-RestrictContended
 * 如果你在 Java 9 以上版本试验的话，在使用 javac 编译时需要添加 --add-exports java.base/jdk.internal.vm.annotation=ALL-UNNAME
 * 参考:
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
 * 测试时候不准确的地方:
 * 两个线程执行时, 其实不是同时开始的, 所以在运行的前一段时间内, 竞争可能较小
 *
 * @author GiraffeTree
 * @date 2020/4/26
 */
public class FalseShareTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(2, 2);
//    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        int count = 10_000_000;
        int retry = 10;
        testNormalVolatile(count, retry);
        testContentedVolatile(count, retry);
        testPaddingVolatile(count, retry);
        EXECUTOR_SERVICE.shutdown();
    }

    private static void testNormalVolatile(final int count, final int retry) {
        long all = 0L;
        int r = retry;
        long[] record = new long[retry];
        while (r-- > 0) {
            Foo foo = new Foo();
            CountDownLatch endLatch = new CountDownLatch(2);
            long l1 = System.currentTimeMillis();
            EXECUTOR_SERVICE.execute(() -> {
                int loop = count;
                while (loop-- > 0) {
                    foo.a = loop;
                }
                endLatch.countDown();
            });
            EXECUTOR_SERVICE.execute(() -> {
                int loop = count;
                while (loop-- > 0) {
                    foo.b = loop;
                }
                endLatch.countDown();
            });

            try {
                endLatch.await();
                long l2 = System.currentTimeMillis();
                all += (l2 - l1);
                record[retry - 1 - r] = l2 - l1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("normal volatile average:%dms %s", all / retry, Arrays.toString(record)));
    }

    private static void testContentedVolatile(final int count, int retry) {
        long all = 0L;
        int r = retry;
        long[] record = new long[retry];
        while (r-- > 0) {

            FooWithContented fooWithContented = new FooWithContented();
            CountDownLatch endLatch = new CountDownLatch(2);

            long l1 = System.currentTimeMillis();
            EXECUTOR_SERVICE.execute(() -> {
                int loop = count;

                while (loop-- > 0) {
                    fooWithContented.a = loop;
                }
                endLatch.countDown();
            });
            EXECUTOR_SERVICE.execute(() -> {
                int loop = count;
                while (loop-- > 0) {
                    fooWithContented.b = loop;
                }
                endLatch.countDown();
            });

            try {
                endLatch.await();
                long l2 = System.currentTimeMillis();
                all += (l2 - l1);
                record[retry - 1 - r] = l2 - l1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("contended volatile average:%dms %s", all / retry, Arrays.toString(record)));
    }

    private static void testPaddingVolatile(final int count, int retry) {
        long all = 0L;
        int r = retry;
        long[] record = new long[retry];
        while (r-- > 0) {

            FooWithPadding fooWithPadding = new FooWithPadding();
            CountDownLatch endLatch = new CountDownLatch(2);

            long l1 = System.currentTimeMillis();
            EXECUTOR_SERVICE.execute(() -> {

                int loop = count;
                while (loop-- > 0) {
                    fooWithPadding.a = loop;
                }
                endLatch.countDown();
            });
            EXECUTOR_SERVICE.execute(() -> {
                int loop = count;
                while (loop-- > 0) {
                    fooWithPadding.b = loop;
                }
                endLatch.countDown();
            });

            try {
                endLatch.await();
                long l2 = System.currentTimeMillis();
                all += (l2 - l1);
                record[retry - 1 - r] = l2 - l1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("padding volatile average:%dms %s", all / retry, Arrays.toString(record)));
    }


    /**
     * 注意字段重排列
     */
    private static class FooWithPadding {
        volatile int a;
        long p1;
        long p2;
        long p3;
        long p4;
        long p5;
        long p6;
        volatile int b;
        long p11;
        long p12;
        long p13;
        long p14;
        long p15;
        long p16;
        long p17;
    }

    private static class Foo {
        volatile int a;
        volatile int b;
    }

    private static class FooWithContented {

        @Contended
        volatile int a;

        @Contended
        volatile int b;
    }

}
