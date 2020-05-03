package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 源于: java 并发编程的艺术 第5章 P133
 *
 * @author GiraffeTree
 * @date 2020-05-03
 */
public class TwinsLock implements Lock {

    private final Sync sync = new Sync(2);

    @Override
    public void lock() {
        sync.tryAcquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.tryReleaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private static class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count <=0 ");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            while (true) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int returnCount) {
            while (true) {
                int cur = getState();
                int newCount = cur + returnCount;
                if (compareAndSetState(cur, newCount)) {
                    return true;
                }
            }
        }
    }


}
