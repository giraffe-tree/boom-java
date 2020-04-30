package me.giraffetree.java.boomjava.concurrent.problem.reordering;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author GiraffeTree
 * @date 2020/4/29
 */
public class CompileReorderingTest2 {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {

        test();
        EXECUTOR_SERVICE.shutdown();

    }


    private static void test() {
        int loop = 100_000;
        int[] result = new int[4];
        while (loop-- > 0) {
            CountDownLatch startLatch = new CountDownLatch(2);
            CountDownLatch endLatch = new CountDownLatch(2);
            Computer3 computer = new Computer3();
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignore) {
                }
                computer.m1();
                endLatch.countDown();
            });
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignore) {
                }
                computer.m2();
                endLatch.countDown();
            });
            try {
                endLatch.await();
            } catch (InterruptedException ignore) {
            }
            result[computer.x + computer.y]++;

        }
        System.out.println(Arrays.toString(result));

    }

    private static boolean checkResult(int[] result) {
//        for (int i : result) {
//            if (i == 0) {
//                return true;
//            }
//        }
//        return false;
        return result[0] == 0;
    }

    private static class Computer implements M {
        int a, b, x, y;

        @Override
        public void m1() {
            x = b;
            a = 2;
        }

        @Override
        public void m2() {
            y = a;
            b = 1;
        }
    }

    private static class Computer2 implements M {
        int a, b, x, y;

        @Override
        public void m1() {
            a = 2;
            x = b;
        }

        @Override
        public void m2() {
            y = a;
            b = 1;
        }
    }

    private static class Computer3 implements M {
        int a, b, x, y, z;

        @Override
        public void m1() {
            a = 2;
            x = b;
        }

        @Override
        public void m2() {
            b = 1;
            y = a;
        }
    }


    interface M {
        void m1();

        void m2();
    }


}
