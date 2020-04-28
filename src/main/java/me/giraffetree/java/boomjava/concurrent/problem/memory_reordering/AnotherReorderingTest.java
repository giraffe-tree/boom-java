package me.giraffetree.java.boomjava.concurrent.problem.memory_reordering;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 来自 java 规范
 * https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3.1.4
 *
 * @author GiraffeTree
 * @date 2020/4/28
 */
public class AnotherReorderingTest {

    /**
     * 如果使用 volatile int i = 0, j = 0; 则 j 不可能大于 i
     * 如果使用  int i = 0, j = 0;  则由于重排序 j 可能大于 i
     */
    private static int i = 0, j = 0;
    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(2, 2);

    /**
     * 理论上这种统计并不准确
     * 三种情况
     * 0    ->  i,j 均为 1
     * 1    ->  i>j
     * 2    ->  j<i
     */
    private static volatile boolean[] result = new boolean[3];

    public static void main(String[] args) {
        test();
        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ignore) {
        }

        System.out.println(String.format("i=j %s", result[0] ? "存在" : "不存在"));
        System.out.println(String.format("i>j %s", result[1] ? "存在" : "不存在"));
        System.out.println(String.format("i<j %s", result[2] ? "存在" : "不存在"));

    }

    private static void test() {
        final int loop = 1_000_000;
        CountDownLatch endLatch = new CountDownLatch(2);
        EXECUTOR_SERVICE.execute(() -> {
            int c = loop;
            while (c-- > 0) {
                one();
            }
            endLatch.countDown();
        });
        EXECUTOR_SERVICE.execute(() -> {
            int c = loop;
            while (c-- > 0) {
                two();
            }
            endLatch.countDown();
        });
        try {
            endLatch.await();
        } catch (InterruptedException e) {
        }
        System.out.println(Arrays.toString(result));
    }


    private static void one() {
        i++;
        j++;
    }

    private static void two() {
        if (!result[0] && i == j) {
            result[0] = true;
        } else if (!result[1] && i > j) {
            result[1] = true;
        } else if (!result[2] && j > i) {
            result[2] = true;
        }
    }

}
