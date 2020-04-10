package me.giraffetree.java.boomjava.concurrent.countdownlatch;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程交替打印 0-100 奇偶数
 * https://blog.csdn.net/u011514810/article/details/77131296
 *
 * @author GiraffeTree
 * @date 2020/3/27
 */
@Slf4j
public class CountDownLatchTest {

    private static AtomicInteger x = new AtomicInteger(0);
    private static Integer lock = 1;

    public static void main(String[] args) throws InterruptedException {

//        test(() -> addByAtomicInteger());
        test(() -> addBySyncObject());

    }

    private static void test(Runnable runnable) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("pool-%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        executorService.execute(runnable);
        executorService.execute(runnable);
    }

    private static void addBySyncObject() {
        while (true) {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " - " + ++lock);
            }
        }
    }

    private static void addByAtomicInteger() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " - " + x.incrementAndGet());
        }

    }
}
