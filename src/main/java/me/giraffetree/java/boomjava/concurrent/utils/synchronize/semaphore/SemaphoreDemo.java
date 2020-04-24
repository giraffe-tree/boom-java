package me.giraffetree.java.boomjava.concurrent.utils.synchronize.semaphore;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 信号量 semaphore
 * semaphore 与 lock&condition 不同的一点是 Semaphore 可以允许多个线程访问一个临界区。
 *
 * @author GiraffeTree
 * @date 2020/4/23
 */
public class SemaphoreDemo {

    private final static ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(
            4, 10, 100L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1024),
            new ThreadFactory() {
                private AtomicInteger count = new AtomicInteger(0);

                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("executor - " + count.getAndIncrement());
                    return thread;
                }
            });

    private static int count = 0;
    private final static Semaphore semaphore = new Semaphore(1, false);

    public static void main(String[] args) {
        testSemaphore(3, 100000000);
        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("count:" + count);
    }

    private static void testSemaphore(int taskCount, int loop) {
        while (taskCount-- > 0) {
            execute(loop);
        }
    }

    private static void execute(final int loop) {
        EXECUTOR_SERVICE.execute(() -> {
            int cur = loop;
            while (cur-- > 0) {
                addOne();
            }
        });
    }

    private static void addOne() {
        try {
            semaphore.acquire();
            count++;
        } catch (InterruptedException ignored) {
        } finally {
            semaphore.release();
        }
    }

}
