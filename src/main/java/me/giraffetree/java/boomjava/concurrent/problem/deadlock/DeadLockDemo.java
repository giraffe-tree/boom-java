package me.giraffetree.java.boomjava.concurrent.problem.deadlock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 死锁问题
 * t1, t2 为线程 , A,B 为资源
 * t1 get A
 * t2 get B
 * t1 get B && t2 get A 造成死锁
 *
 * @author GiraffeTree
 * @date 2020-04-12
 */
public class DeadLockDemo {

    @SuppressWarnings("AlibabaThreadPoolCreation")
    private final static ExecutorService executor = new ThreadPoolExecutor(2, 4,
            1000L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setDaemon(false).setNameFormat("dead lock - %d").build());

    public static void main(String[] args) {
        deadLock();
    }

    private static void deadLock() {
        Object o1 = new Object();
        Object o2 = new Object();

        executor.execute(() -> {
            while (true) {
                synchronized (o2) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (o1) {
                        System.out.println("hello world");

                    }
                }
            }
        });
        executor.execute(() -> {
            while (true) {
                synchronized (o1) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println("hello world");
                    }
                }
            }
        });

    }

}
