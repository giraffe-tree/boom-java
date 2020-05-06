package me.giraffetree.java.boomjava.concurrent.problem.dcl;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 安全单例
 *
 * @author GiraffeTree
 * @date 2020/4/30
 */
public class DCLTest {

    private static final ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(10, 10);

    public static void main(String[] args) {
        testUnsafeInstance();
        EXECUTOR_SERVICE.shutdown();
    }

    private static void testUnsafeInstance() {
        final int threadCount = 10;
        int c = threadCount;
        CountDownLatch startLatch = new CountDownLatch(threadCount);
        CountDownLatch endLatch = new CountDownLatch(threadCount);
        while (c-- > 0) {
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException e) {
                }
                if (Cap.getInstance() == null) {
                    System.out.println("get null instance!!");
                }
                endLatch.countDown();
            });
        }

        try {
            endLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * unsafe
     */
    private static class Cap {
        private static Cap cap;

        public static Cap getInstance() {
            if (cap != null) {
                return cap;
            }
            return new Cap();
        }
    }

    /**
     * unsafe
     */
    private static class CapUnsafeDcl {
        private static CapUnsafeDcl capUnsafeDcl;

        public static CapUnsafeDcl getInstance() {
            if (capUnsafeDcl == null) {
                synchronized (capUnsafeDcl) {
                    capUnsafeDcl = new CapUnsafeDcl();
                    return capUnsafeDcl;
                }
            }
            return capUnsafeDcl;
        }
    }

    /**
     * 正确的 double check lock
     * 禁用了对象中属性初始化 与 设置指针 的重排序
     * 优点: 除了能对静态字段初始化, 也可以对实例字段初始化
     */
    private static class CapSafeDcl {
        // after jdk 1.5
        private volatile static CapSafeDcl capSafeDcl;

        public static CapSafeDcl getInstance() {
            if (capSafeDcl == null) {
                synchronized (capSafeDcl) {
                    capSafeDcl = new CapSafeDcl();
                    return capSafeDcl;
                }
            }
            return capSafeDcl;
        }
    }

    /**
     * 基于类初始化
     * class 被加载后, 在被线程使用前, 会执行类的初始化
     * jvm 在初始化过程中, 会去获取一个锁, 以同步多线程对一个类的初始化
     * 优点: 简洁
     */
    private static class CapBaseOnClassInit {
        private static class Inner {
            private static CapBaseOnClassInit capBaseOnClassInit = new CapBaseOnClassInit();
        }

        public static CapBaseOnClassInit getInstance() {
            return Inner.capBaseOnClassInit;
        }

    }

}
