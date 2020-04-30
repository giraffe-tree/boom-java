package me.giraffetree.java.boomjava.concurrent.problem.reordering.final_reorder;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * !!!没有测试出 构造函数中的普通变量没有完全初始化的情况
 *
 * @author GiraffeTree
 * @date 2020/4/30
 */
public class FinalReorderTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
        test();
        EXECUTOR_SERVICE.shutdown();
    }

    public static void test() {
        final int loop = 100_000_000;
        CountDownLatch startLatch = new CountDownLatch(3);
        CountDownLatch endLatch = new CountDownLatch(3);
        EXECUTOR_SERVICE.execute(() -> {
            startLatch.countDown();
            try {
                startLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int c = loop;
            while (c-- > 0) {
                Ble.t1();
            }
            endLatch.countDown();
        });
        EXECUTOR_SERVICE.execute(() -> {
            startLatch.countDown();
            try {
                startLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int c = loop;
            while (c-- > 0) {
                int a = Ble.t2();
                if (a == 0) {
                    System.out.println("a = 0");
                }
            }

            endLatch.countDown();
        });
        EXECUTOR_SERVICE.execute(() -> {
            startLatch.countDown();
            try {
                startLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int c = loop;
            while (c-- > 0) {
                int i = Ble.t3();
                if (i == 0) {
                    System.out.println("i = 0");
                }
            }
            endLatch.countDown();
        });

        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//            startBarrier.reset();


    }

    private static class Ble {
        static Ble ble = new Ble(1, 2);
        int a;
        int b;

        Ble(int a, int b) {
            this.a = a;
            this.b = b;
        }

        private static void t1() {
            ble = new Ble(1, 2);
        }

        private static int t2() {
            return ble.a;
        }

        private static int t3() {
            return ble.b;
        }
    }
}
