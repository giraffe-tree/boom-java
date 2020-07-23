package me.giraffetree.java.boomjava.jvm.jol;

import me.giraffetree.java.boomjava.jvm.lock.biased.BiasedSynchronizedTest;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author GiraffeTree
 * @date 2020-06-15
 */
public class SyncJolTest {

    private final static Object lock = new Object();


    public static void main(String[] args) {

        biasedLockDelayOnTest();
    }

    /**
     * 使用 java 8 默认的配置运行
     * 不进行任何的 jvm 参数配置
     */
    private static void biasedLockDelayOnTest() {
        // 1. 获取 jvm 启动时间
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        long startTime = bean.getStartTime();
        // 2. 循环获取 header 信息
        long loop = 10;
        while (loop-- > 0) {
            long current = System.currentTimeMillis();
            Object x = new Object();
            JavaUtilsJolTest.print(x);
            System.out.println(String.format("lost %dms\n", current - startTime));
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                System.exit(1);
            }
        }
    }


    /**
     * 测试偏向锁开启时的 java 对象结构
     * -XX:+UnlockDiagnosticVMOptions -XX:+PrintBiasedLockingStatistics -XX:+TraceBiasedLocking
     * {@link BiasedSynchronizedTest}
     */
    private static void biasedLockOnTest() {
        //
        JavaUtilsJolTest.print(lock);
        synchronized (lock) {

        }
    }

}
