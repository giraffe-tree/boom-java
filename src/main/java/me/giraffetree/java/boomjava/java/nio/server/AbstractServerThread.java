package me.giraffetree.java.boomjava.java.nio.server;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author GiraffeTree
 * @date 2020/7/22
 */
public abstract class AbstractServerThread implements Runnable {

    private final CountDownLatch startLatch = new CountDownLatch(1);

    private CountDownLatch shutdownLatch = new CountDownLatch(0);
    private AtomicBoolean alive = new AtomicBoolean(true);

    abstract void wakeup();

    /**
     * 优雅地停止
     */
    public void initiateShutdown() {
        if (alive.getAndSet(false)) {
            wakeup();
        }
    }

    public void awaitShutdown() throws InterruptedException {
        shutdownLatch.await();
    }

    public boolean isStarted() {
        return startLatch.getCount() == 0;
    }

    public void awaitStartup() throws InterruptedException {
        startLatch.await();
    }

    protected void startupComplete() {
        shutdownLatch = new CountDownLatch(1);
        startLatch.countDown();
    }

    protected void shutdownComplete() {
        shutdownLatch.countDown();
    }

    protected boolean isRunning() {
        return alive.get();
    }
}
