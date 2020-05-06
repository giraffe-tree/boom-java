package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.aqs.share;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;

/**
 * @author GiraffeTree
 * @date 2020-05-03
 */
public class TwinsLockTest {
    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(10,10);

    public static void main(String[] args) {
        testTwinsLock();
    }


    private static void testTwinsLock() {
        TwinsLock twinsLock = new TwinsLock();
        int loop = 10;
        while (loop-- > 0) {
            EXECUTOR_SERVICE.execute(() -> {
                while (true) {
                    twinsLock.lock();
                    try {
                        System.out.println(String.format("%s %s get lock",
                                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), Thread.currentThread().getName()));
                        try {
                            Thread.sleep(10000L);
                        } catch (InterruptedException ignored) {
                        }
                    } finally {
                        twinsLock.unlock();
                        System.out.println(String.format("%s %s unlock",
                                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), Thread.currentThread().getName()));
                    }
                }
            });
        }
    }
}
