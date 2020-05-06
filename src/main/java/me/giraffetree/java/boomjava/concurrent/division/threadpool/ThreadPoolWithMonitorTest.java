package me.giraffetree.java.boomjava.concurrent.division.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试继承 ThreadPoolExecutor 重写一些方法, 达到监控 thread pool 的目的
 *
 * @author GiraffeTree
 * @date 2020-05-05
 */
public class ThreadPoolWithMonitorTest {

    public static void main(String[] args) {

        test(30);

    }

    private static void test(int loop) {
        ThreadPoolWithMonitor pool = new ThreadPoolWithMonitor(4, 10, 100L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(20), new ThreadFactory() {
            private AtomicInteger c = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(String.format("thread-%d", c.incrementAndGet()));
                return thread;
            }
        }, new ThreadPoolExecutor.AbortPolicy());

        while (loop-- > 0) {
            final int c = loop;
            pool.execute(() -> {
                System.out.println(String.format("%s running ... count:%d", Thread.currentThread().getName(), c));
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        long taskCount = pool.getCompletedTaskCount();
        System.out.println("getCompletedTaskCount:" + taskCount);
        pool.shutdown();
    }

    private static class ThreadPoolWithMonitor extends ThreadPoolExecutor {


        public ThreadPoolWithMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println(String.format("%s before execute", t.getName()));
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("after execute... count:" + getCompletedTaskCount());
        }

        @Override
        protected void terminated() {
            System.out.println("pool terminated...");
        }
    }


}
