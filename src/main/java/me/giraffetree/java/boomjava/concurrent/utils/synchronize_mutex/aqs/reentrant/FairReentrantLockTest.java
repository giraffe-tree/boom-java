package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.aqs.reentrant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁保证了 FIFO 原则, 但是代价是可能造成大量的线程切换
 * @author GiraffeTree
 * @date 2020-05-04
 */
public class FairReentrantLockTest {

    public static void main(String[] args) throws InterruptedException {
//        ReentrantLock lock = new ReentrantLock(true);
        FairReentrantLock lock = new FairReentrantLock();
//        LockTestUtils.testLockAndUnLock(lock, 4, 2, 1000L);
        Thread t1 = new Thread(() -> {
            lock.lock();
            System.out.println(String.format("%s %s get lock",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), Thread.currentThread().getName()));
            printThread(lock,"t1 lock 1");

            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            printThread(lock,"t1 lock 2");
            System.out.println(String.format("%s %s get lock",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), Thread.currentThread().getName()));
            lock.unlock();
            lock.unlock();
        }, "t1");
        Thread t2 = new Thread(() -> {
            lock.lock();
            System.out.println(String.format("%s %s get lock",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), Thread.currentThread().getName()));
            printThread(lock,"t2 lock 1");
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            printThread(lock,"t2 lock 2");
            System.out.println(String.format("%s %s get lock",
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), Thread.currentThread().getName()));
            lock.unlock();
            lock.unlock();
        }, "t2");
        t1.start();
        t2.start();

    }

    private static void printThread(FairReentrantLock lock, String prefix) {
        Collection<Thread> threads = lock.getThreads();
        Optional<String> reduce = threads.stream().map(x -> x.getName() + ":" + x.getState()).reduce((x, y) -> x + "," + y);
        System.out.println(prefix + " - " + reduce.orElse(""));
    }
}
