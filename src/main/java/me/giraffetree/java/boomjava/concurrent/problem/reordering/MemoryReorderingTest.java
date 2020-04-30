package me.giraffetree.java.boomjava.concurrent.problem.reordering;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * https://tech.meituan.com/2014/09/23/java-memory-reordering.html
 *
 * @author GiraffeTree
 * @date 2020-04-27
 */
public class MemoryReorderingTest {
    /**
     * 如果使用
     * ExecutorUtils.getExecutorService(1,2);
     * 竟然也会死锁??? => 探究一下?
     */
    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(2, 2);

    private static int x = 0, y = 0;
    private static int a = 0, b = 0;

    /**
     * 不同位置代表不同结果
     * index -> (x,y)
     * 0    ->  0,0
     * 1    ->  0,1
     * 2    ->  1,0
     * 3    ->  1,1
     */
    private static int[] result = new int[4];

    /**
     * 现在有两个线程 0, 1 ; 我们将 线程编号记为 x
     * 如果一个线程首先获得 lock 的访问权, 则会将 threadContend[x]++
     * <p>
     * 主要用来记录哪个线程先开始执行
     */
    private static int[] threadContend = new int[2];

    private static volatile int lock = -1;

    public static void main(String[] args) {
        test();
        EXECUTOR_SERVICE.shutdown();
    }

    private static void test() {
        int i = 0;
        for (; ; ) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            lock = -1;
            CountDownLatch startLatch = new CountDownLatch(2);
            CountDownLatch endLatch = new CountDownLatch(2);
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignore) {
                }
                lock = 0;
                a = 1;
                x = b;
                endLatch.countDown();
            });

            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignore) {
                }
                lock = 1;
                b = 1;
                y = a;
                endLatch.countDown();
            });
            try {
                endLatch.await();
            } catch (InterruptedException ignore) {
            }
            if (lock == 1) {
                threadContend[0]++;
            } else if (lock == 0) {
                threadContend[1]++;
            }

            if (x == 0 && y == 0) {
                result[0]++;
                System.out.println(String.format("第%d次 (0,0)", i));
                break;
            } else if (x == 0 && y == 1) {
                result[1]++;
            } else if (y == 0) {
                result[2]++;
            } else if (y == 1) {
                result[3]++;
            }


        }
        System.out.println("(x,y) 结果: " + Arrays.toString(result));
        System.out.println("线程执行的先后次数比较: " + Arrays.toString(threadContend));
    }

}
