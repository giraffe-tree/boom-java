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
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquireShared(1) >= 0;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
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

        final ConditionObject newCondition() {
            return new ConditionObject();
        }


    }


}
