package me.giraffetree.java.boomjava.concurrent.utils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author GiraffeTree
 * @date 2020/4/24
 */
public class ExecutorUtils {

    public static ExecutorService getExecutorService() {
        return getExecutorService(4, 10);
    }

    public static ExecutorService getExecutorService(int core, int max) {
        return getExecutorService(core, max, 1024, "thread-%d");
    }

    public static ExecutorService getExecutorService(int core, int max, int queueSize, String nameFormat) {
        return getExecutorService(core, max, queueSize, nameFormat, new ThreadPoolExecutor.AbortPolicy());
    }

    public static ExecutorService getExecutorService(int core, int max, int queueSize, String nameFormat, RejectedExecutionHandler handler) {
        return new ThreadPoolExecutor(
                core, max, 100L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize), new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(String.format(nameFormat, count.getAndIncrement()));
                return thread;
            }
        }, handler
        );
    }

}
