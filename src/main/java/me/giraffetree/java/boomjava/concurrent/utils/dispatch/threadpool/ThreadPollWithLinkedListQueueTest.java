package me.giraffetree.java.boomjava.concurrent.utils.dispatch.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 容易造成错误的示例, 应当使用有界队列
 * 指定最大的 capacity, 否则会执行不到 拒绝策略, 且会造成 OOM
 *
 * @author GiraffeTree
 * @date 2020-05-05
 */
public class ThreadPollWithLinkedListQueueTest {

    public static void main(String[] args) {
        test(4, 10, 1000);
    }

    private static void test(int core, int max, int loop) {
        // 这里使用了 linked queue 作为任务队列, LinkedBlockingQueue 需要指定 capacity
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                core, max, 100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(20), new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(String.format("thread-%d", count.getAndIncrement()));
                return thread;
            }
        }, new ThreadPoolExecutor.AbortPolicy()
        );
        // 如果这里使用 Executors.newFixedThreadPool, 它内部使用的 linkedBlockingQueue 的 capacity 为 2^31 -1
        // 很有可能导致撑满内存, 导致 OOM
//        ExecutorService pool = Executors.newFixedThreadPool(4);

        while (loop-- > 0) {
            final int c = loop;
            pool.execute(() -> {
                System.out.println(String.format("%s running ... count:%d", Thread.currentThread().getName(), c));
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
            });
        }
        pool.shutdown();
    }

}
