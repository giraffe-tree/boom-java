package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.atomic;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author GiraffeTree
 * @date 2020/4/26
 */
public class AtomicFieldUpdaterTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<Apple> updater = AtomicIntegerFieldUpdater.newUpdater(Apple.class, "size");
        Apple apple = new Apple(0);
        int loop = 10_000_000;
        int threadCount = 2;
        for (int i = 0; i < threadCount; i++) {
            EXECUTOR_SERVICE.execute(() -> {
                int c = loop;
                while (c-- > 0) {
                    updater.getAndIncrement(apple);
                }
            });
        }
        EXECUTOR_SERVICE.shutdown();

        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(apple.size);
        System.out.println(apple.size == loop * threadCount);

    }

    private static class Apple {
        // 没有 volatile 修饰会导致 IllegalArgumentException: Must be volatile type
        volatile int size;

        public Apple(int size) {
            this.size = size;
        }
    }
}
