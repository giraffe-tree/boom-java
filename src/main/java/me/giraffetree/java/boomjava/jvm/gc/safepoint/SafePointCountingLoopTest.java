package me.giraffetree.java.boomjava.jvm.gc.safepoint;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;

/**
 * 用于探究计数循环对垃圾回收, 到达安全点的影响
 * 预测: 无安全点检测的计数循环导致该线程一直不会进入安全区, 可能带来的 GC 长暂停
 *
 * <p>
 * -XX:+PrintGC -XX:+PrintSafepointStatistics -XX:+PrintGCApplicationStoppedTime
 * <p>
 * -XX:+UseCountedLoopSafepoints
 *
 * @author GiraffeTree
 * @date 2020/5/25
 */
public class SafePointCountingLoopTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(2, 2);

    public static void main(String[] args) {
            foo();
    }

    private static void test() {
        int loop = 100_000_000;
        for (int i = 0; i < loop; i++) {
            new Object().hashCode();
        }

    }

    public static void foo() {
        System.out.println("start foo...");
        int sum = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum += Math.sqrt(i);
        }
    }

    public static void bar() {
        for (int i = 0; i < 50_000_000; i++) {
            new Object().hashCode();
        }
    }
}

