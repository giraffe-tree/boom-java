package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.aqs.utils;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author GiraffeTree
 * @date 2020-05-04
 */
public class LockTestUtils {

    public static void testLockAndUnLock(Lock lock, int threadCount, int curThreadLockCount, long sleepMills) {
        testLockAndUnLock(threadCount, threadCount, lock, threadCount, curThreadLockCount, sleepMills, sleepMills * threadCount * 2);
    }


    /**
     * 测试 加锁/解锁 可重入
     *
     * @param poolCoreSize       线程池核心数
     * @param poolMaxSize        线程池最大数
     * @param lock               锁
     * @param threadCount        需要同时执行多少线程
     * @param curThreadLockCount 每个线程重复获取一把锁的次数
     * @param sleepMills         线程在获取完所有锁之后, 休眠的毫秒数
     * @param poolAwaitMills     线程池等待完成的时间
     */
    public static void testLockAndUnLock(int poolCoreSize, int poolMaxSize, Lock lock, int threadCount, int curThreadLockCount, long sleepMills, long poolAwaitMills) {
        ExecutorService executorService = ExecutorUtils.getExecutorService(poolCoreSize, poolMaxSize);
        CountDownLatch startLatch = new CountDownLatch(threadCount);
        while (threadCount-- > 0) {
            executorService.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignored) {
                }
                int lockCount = curThreadLockCount;
                while (lockCount-- > 0) {
                    lock(lock, sleepMills);
                }
                try {
                    Thread.sleep(sleepMills);
                } catch (InterruptedException ignored) {
                }
                int unlockCount = curThreadLockCount;
                while (unlockCount-- > 0) {
                    unlock(lock);
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(poolAwaitMills, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
        }
    }

    private static void lock(Lock lock, long sleepMills) {
        lock.lock();
        System.out.println(String.format("%s %s get lock",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), Thread.currentThread().getName()));
    }

    private static void unlock(Lock lock) {
        lock.unlock();
        System.out.println(String.format("%s %s unlock",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), Thread.currentThread().getName()));
    }

}
