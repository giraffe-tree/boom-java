package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.stampedlock;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author GiraffeTree
 * @date 2020/4/24
 */
public class StampedLockTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();


    public static void main(String[] args) throws InterruptedException {

//        testGetAndSet();

        testReentrant();

        testReadAfterWrite();

        EXECUTOR_SERVICE.shutdown();
        EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);

    }

    private static void testReadAfterWrite() {
        StampedLock stampedLock = new StampedLock();
        long stamp = stampedLock.writeLock();
        System.out.println("我获取了写锁");
        // 此处被阻塞
        long stamp2 = stampedLock.tryReadLock();
        if (stamp2 == 0) {
            System.out.println("我没拿到读锁TT");
        } else {
            System.out.println("我获取了读锁");
        }

    }

    private static void testReentrant() {
        StampedLock stampedLock = new StampedLock();
        long stamp = stampedLock.writeLock();
        // 如果再次调用 stampedLock.writeLock() 此处会被阻塞
        long stamp2 = stampedLock.tryWriteLock();
        if (0 == stamp2) {
            System.out.println("我第二次没有拿到写锁");
        } else {
            System.out.println("我获取了两次写锁");
        }
    }

    private static void testGetAndSet() {
        Book book = new Book("hello");
        final int loop = 1000000;
        EXECUTOR_SERVICE.execute(() -> {
            int a = loop;
            while (a-- > 0) {
                book.get();
            }
        });
        EXECUTOR_SERVICE.execute(() -> {
            int a = loop;
            while (a-- > 0) {
                book.set(String.format("hello-%d", a));
            }
        });
    }

    private static class Book {

        private String book;

        private StampedLock stampedLock = new StampedLock();

        public Book(String book) {
            this.book = book;
        }

        public String get() {
            // 乐观读
            long stamp = stampedLock.tryOptimisticRead();
            String cur = book;
            // other  operation
            otherOp();

            if (!stampedLock.validate(stamp)) {
                // 悲观读
                stamp = stampedLock.readLock();
                try {
                    cur = book;
                    // other operation
                    otherOp();

                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            return cur;
        }

        public void set(String book) {
            long stamp = stampedLock.writeLock();
            try {
                this.book = book;
            } finally {
                stampedLock.unlockWrite(stamp);

            }
        }


    }

    private static void otherOp() {

    }

}
