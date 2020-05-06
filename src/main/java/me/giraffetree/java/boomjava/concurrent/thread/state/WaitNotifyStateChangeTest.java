package me.giraffetree.java.boomjava.concurrent.thread.state;

/**
 * 主要用来测试 wait notify 时, 线程状态的变化
 * 测试结果
 * A state:WAITING   // A 在等待队列中
 * B try to notify all
 * B try to sleep 2s
 * A state:BLOCKED   // A 为阻塞态, 在进入 synchronized 时被阻塞, 在进入monitor前的同步队列中
 * A get lock after check flag
 *
 * @author GiraffeTree
 * @date 2020-05-01
 */
public class WaitNotifyStateChangeTest {

    private static Object lock = new Object();
    private static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        test();

    }

    private static void test() throws InterruptedException {
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                while (!flag) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("A get lock after check flag");
            }
        }, "A");

        Thread threadB = new Thread(() -> {
            synchronized (lock) {
                System.out.println("A state:" + threadA.getState());
                flag = true;
                System.out.println("B try to notify all");
                lock.notifyAll();
                System.out.println("B try to sleep 2s");
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A state:" + threadA.getState());
            }
        }, "B");
        threadA.start();
        Thread.sleep(1000L);

        threadB.start();

    }
}
