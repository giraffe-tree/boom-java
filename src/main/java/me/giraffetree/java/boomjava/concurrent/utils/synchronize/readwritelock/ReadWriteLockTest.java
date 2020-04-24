package me.giraffetree.java.boomjava.concurrent.utils.synchronize.readwritelock;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author GiraffeTree
 * @date 2020/4/24
 */
public class ReadWriteLockTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
//        test(100000000, 10000);

//        testUpgrade();
//        testDegrade();

        testReentrant();

        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testReentrant() {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        writeLock.lock();
        System.out.println("我获取了两次写锁");

        writeLock.unlock();
//        writeLock.unlock();

        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        readLock.lock();

        System.out.println("我获取了读锁");
    }

    /**
     * 锁的降级
     */
    private static void testDegrade() {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        writeLock.lock();
        // 在获取写锁的临界区, 获取读锁
        readLock.lock();
        writeLock.unlock();

        // 如果不释放读锁, 将无法再次获取写锁
        readLock.unlock();

        // 是否能够获取写锁
        writeLock.lock();
        System.out.println("oh, I get write lock!!!");
    }

    /**
     * 无法升级
     * 当前线程获取读锁之后, 再获取写锁时阻塞
     * 造成了死锁
     */
    private static void testUpgrade() {
        // 如何分析有没有 阻塞
        // jstack {pid}

        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        readLock.lock();
        writeLock.lock();

        System.out.println("oh, I'm not executed .... dead lock occurring ....");
        writeLock.unlock();

    }

    private static void test(final int readLoop, final int writeLoop) {
        PaperRwl paper = new PaperRwl("");


        EXECUTOR_SERVICE.execute(() -> {
            int loop = readLoop;
            while (loop-- > 0) {
                paper.getAndSet();
            }
        });
        EXECUTOR_SERVICE.execute(() -> {
            int loop = writeLoop;
            while (loop-- > 0) {
                paper.set("hello - " + loop);
            }
        });
    }

    static class PaperRwl {

        final private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        private String paper;

        final private Lock readLock = readWriteLock.readLock();

        final private Lock writeLock = readWriteLock.writeLock();

        public PaperRwl(String paper) {
            this.paper = paper;
        }

        public String getAndSet() {
            readLock.lock();
            try {
                if (!StringUtils.isEmpty(paper)) {
                    return paper;
                }
            } finally {
                readLock.unlock();
            }


            // 读写锁不允许 锁的升级(先是获取读锁，然后再升级为写锁)
            writeLock.lock();
            try {
                if (!StringUtils.isEmpty(paper)) {
                    // 锁降级
                    readLock.lock();
                    try {
                        return paper;
                    } finally {
                        readLock.unlock();
                    }
                } else {
                    paper = "init";
                    return paper;
                }

            } finally {
                writeLock.unlock();
            }

        }

        public String get() {
            readLock.lock();
            try {
                return paper;
            } finally {
                readLock.unlock();
            }
        }

        public void set(String paper) {
            writeLock.lock();
            try {
                this.paper = paper;
            } finally {
                writeLock.unlock();
            }
        }


    }


}
