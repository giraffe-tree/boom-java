package me.giraffetree.java.boomjava.concurrent.problem.reordering;


import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 测试失败!!
 * 编译重排序
 * https://docs.oracle.com/javase/specs/jls/se10/html/jls-17.html#jls-17.4
 * 没有重现 x=1, y=2
 *
 * @author GiraffeTree
 * @date 2020-04-29
 */
public class CompileReorderingTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();
//    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        test();
        EXECUTOR_SERVICE.shutdown();
    }

    private static void test() {
        int loop = 100_000;
        int[] result = new int[4];
        for (int i = 0; i < loop; i++) {
            CountDownLatch startLatch = new CountDownLatch(2);
            CountDownLatch endLatch = new CountDownLatch(2);
            Phone phone = new Phone();
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignore) {
                }
                phone.change1();
                endLatch.countDown();
            });
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignore) {
                }
                phone.change2();
                endLatch.countDown();
            });
            try {
                endLatch.await();
            } catch (InterruptedException ignore) {
            }
            result[phone.x + phone.y]++;
        }
        System.out.println(Arrays.toString(result));

    }

    private static class Phone {

        int a, b;
        int x, y;

        void change1() {
            x = a;
            b = 2;
        }

        void change2() {
            y = b;
            a = 1;
        }
    }

}
