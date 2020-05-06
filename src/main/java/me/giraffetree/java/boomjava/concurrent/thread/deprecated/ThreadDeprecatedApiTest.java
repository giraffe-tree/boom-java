package me.giraffetree.java.boomjava.concurrent.thread.deprecated;

/**
 * suspend 会占用资源不会释放
 * stop 会导致资源释放不完全, 程序可能工作在不确定的情况下
 * resume
 *
 * @author GiraffeTree
 * @date 2020-05-01
 */
public class ThreadDeprecatedApiTest {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {
        test();
        Thread.sleep(5000L);
    }

    private static void test() throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                while (true) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        System.out.println("t1 start...");
        t1.start();
        Thread.sleep(1000L);
        System.out.println("t1 suspend");
        t1.suspend();
        Thread t2 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("i get lock 1");
            }
        });
        t2.start();
        System.out.println("t2 start...");

        Thread.sleep(2000L);

        System.out.println("t1 resume...");
        t1.resume();

        Thread.sleep(2000L);

        System.out.println("t1 stop");
        t1.stop();


    }
}
