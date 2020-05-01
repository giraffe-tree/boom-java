package me.giraffetree.java.boomjava.concurrent.thread.state;

/**
 * 本程序主要用来探究线程状态的变化
 * 使用 jvisualvm 可以清晰的看到线程状态的变化
 * <p>
 * 另外推荐在 jvisualvm 上安装 visual gc 的插件, 检查堆占用变化, 一级棒!!!
 *
 * @author GiraffeTree
 * @date 2020-05-01
 */
public class StateTest {

    private static Object obj = new Object();

    public static void main(String[] args) {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        test();
    }

    private static void test() {
        Thread threadNew = new Thread("thread-new");
        System.out.println(threadNew.getState());
        Thread threadSleep = new Thread(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
            }
        }, "thread-sleep");
        threadSleep.start();
        Thread threadWaiting = new Thread(() -> {
            synchronized (obj) {
                try {
                    // 这里只为了演示, 实际上要包裹在一个 while 中
                    obj.wait();
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread-waiting");
        threadWaiting.start();
        Thread threadTimedWaiting = new Thread(() -> {
            synchronized (obj) {
                try {
                    obj.wait(10000L);
                    Thread.sleep(10000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread-timed-waiting");
        threadTimedWaiting.start();
        Thread threadRunnable = new Thread(() -> {
            synchronized (obj) {
                long l = System.currentTimeMillis();
                while (System.currentTimeMillis() - l < 5000L) {

                }
            }
        }, "thread-runnable");
        threadRunnable.start();

        Thread threadBlocking = new Thread(() -> {
            synchronized (obj) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread-blocking");
        threadBlocking.start();

        Thread thread = new Thread(() -> {
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState());

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
