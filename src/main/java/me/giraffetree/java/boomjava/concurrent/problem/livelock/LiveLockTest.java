package me.giraffetree.java.boomjava.concurrent.problem.livelock;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用以模拟活锁问题
 * 我很喜欢的一个比喻是 两个机器人过独木桥
 * 如何解决活锁问题, 随机等待时间重试, 指数退避
 *
 * @author GiraffeTree
 * @date 2020/5/6
 */
public class LiveLockTest {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        ExecutorService executorService = ExecutorUtils.getExecutorService(2, 2);
        ReentrantLock lock = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();


        executorService.execute(() -> {
            while (true) {
                lock.lock();
                try {
                    Thread.sleep(1000L);
                    boolean success = lock2.tryLock();
                    if (!success) {
                        System.out.println(String.format("%s %s get lock fail, try to release lock", LocalDateTime.now(), Thread.currentThread().getName()));
                        continue;
                    } else {
                        System.out.println(String.format("%s %s get lock success", LocalDateTime.now(), Thread.currentThread().getName()));
                        lock2.unlock();
                        break;
                    }
                } catch (InterruptedException e) {
                } finally {
                    lock.unlock();
                }

            }
        });
        executorService.execute(() -> {
            while (true) {
                lock2.lock();
                try {
                    Thread.sleep(1000L);
                    boolean success = lock.tryLock();
                    if (!success) {
                        System.out.println(String.format("%s %s get lock fail, try to release lock", LocalDateTime.now(), Thread.currentThread().getName()));
                        continue;
                    } else {
                        System.out.println(String.format("%s %s get lock success", LocalDateTime.now(), Thread.currentThread().getName()));
                        lock.unlock();
                        break;
                    }
                } catch (InterruptedException e) {
                } finally {
                    lock2.unlock();
                }

            }

        });

        executorService.shutdown();
    }

}
