package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.aqs.reentrant;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author GiraffeTree
 * @date 2020-05-04
 */
public class FairReentrantLock implements Lock {

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public Collection<Thread> getThreads() {
        return sync.getThreads();
    }

    /**
     * state 计数器表示当前线程获取锁的次数
     * state 为 0 时, 说明没有线程占用这个锁
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            Thread cur = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                // 如果没有其他线程在队列中, 且 cas 成功, 则独占锁
                if (!hasQueuedPredecessors() && compareAndSetState(0, arg)) {
                    setExclusiveOwnerThread(cur);
                    return true;
                }
            } else if (cur == getExclusiveOwnerThread()) {
                // 同一个线程, 重复获取锁时
                int nextC = c + arg;
                if (nextC < 0) {
                    throw new Error("maximum lock count exceeded");
                }
                setState(nextC);
                return true;
            }
            return false;
        }

            @Override
            protected boolean tryRelease(int arg) {
                int c = getState() - arg;
                if (Thread.currentThread() != getExclusiveOwnerThread()) {
                    throw new IllegalMonitorStateException();
                }
                boolean free = false;
                if (c == 0) {
                    free = true;
                    setExclusiveOwnerThread(null);
                }
                setState(c);
            return free;
        }

        private Condition newCondition() {
            return new ConditionObject();
        }

        public Collection<Thread> getThreads() {
            return super.getQueuedThreads();
        }

    }


}
