package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.atomic;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author GiraffeTree
 * @date 2020/4/26
 */
public class AtomicNumberTest {
    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
        AtomicInteger num = new AtomicInteger(0);
        int loop = 10_000_000;
        EXECUTOR_SERVICE.execute(() -> {
            int c = loop;
            while (c-- > 0) {
                num.incrementAndGet();
            }
        });
        EXECUTOR_SERVICE.execute(() -> {
            int c = loop;
            while (c-- > 0) {
                num.incrementAndGet();
            }
        });
        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num.get());

    }
}
