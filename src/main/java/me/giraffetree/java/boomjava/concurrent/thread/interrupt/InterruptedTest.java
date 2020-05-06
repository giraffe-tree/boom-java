package me.giraffetree.java.boomjava.concurrent.thread.interrupt;

/**
 * 用于测试线程中断标志位
 * 如果线程已经被终结, 则 isInterrupted() 方法是否被中断都会返回 false
 *
 * @author GiraffeTree
 * @date 2020-05-01
 */
public class InterruptedTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SleepRunner(), "sleep");
        t1.setDaemon(true);
        Thread t2 = new Thread(new BusyRunner(), "busy");
        t2.setDaemon(true);
        t1.start();
        t2.start();
        Thread.sleep(2000L);
        t1.interrupt();
        t2.interrupt();

        System.out.println("sleep isInterrupted:" + t1.isInterrupted());
        System.out.println("busy isInterrupted:" + t2.isInterrupted());

        Thread.sleep(2000L);
        // sleep 线程已经被终结, 则是否被中断都会返回 false
        System.out.println("sleep isInterrupted:" + t1.isInterrupted() + " - " + t1.getState());
        System.out.println("busy isInterrupted:" + t2.isInterrupted());

    }

    private static class SleepRunner implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                // 重新设置中断flag
                Thread.currentThread().interrupt();
                System.out.println("sleep in catch isInterrupted:" + Thread.currentThread().isInterrupted());
            }

        }
    }

    private static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                // do nothing
            }
        }
    }
}
