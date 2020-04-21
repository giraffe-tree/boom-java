package me.giraffetree.java.boomjava.concurrent.problem.thread_communication;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程交替打印 奇偶数
 * https://stackoverflow.com/questions/25425130/loop-doesnt-see-value-changed-by-other-thread-without-a-print-statement
 *
 * @author GiraffeTree
 * @date 2020-04-09
 */
@Slf4j
public class InterThreadCommunication {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1000L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("thread-%d").build());
    private static AtomicInteger value = new AtomicInteger(0);
    private static volatile int m = 0;

    public static void main(String[] args) {
        // 1. 共享变量
//        testWithAtomicInteger();
//        testWithVolatile();
        testWithMain();
    }

    public static void testWithMain() {
        while (true) {
            if (m % 2 == 0) {
                System.out.println(String.format("%s v:%d", Thread.currentThread().getName(), m));
                m++;
            }
        }
    }


    public static void testWithVolatile() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(() -> {
            while (true) {
                if (value.get() % 2 == 0) {
                    System.out.println(String.format("%s v:%d", Thread.currentThread().getName(), m + 1));
                    m++;
                }
            }
        });
        executorService.submit(() -> {
            int x = m + 1;
            while (true) {
                if (value.get() % 2 == 1) {
                    int y = x - 1;
                    System.out.println(String.format("%s v:%d", Thread.currentThread().getName(), m + 1));
                    m++;
                }
            }
        });

    }

    public static void testWithAtomicInteger() {

        executor.execute(() -> {
            while (true) {

                if (value.get() % 2 == 0) {

                    System.out.println(String.format("%s v:%d", Thread.currentThread().getName(), value.get() + 1));
                    value.getAndIncrement();
                }
            }
        });
        executor.execute(() -> {
            while (true) {
                if (value.get() % 2 == 1) {
                    System.out.println(String.format("%s v:%d", Thread.currentThread().getName(), value.get() + 1));
                    value.getAndIncrement();
                }
            }
        });

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
