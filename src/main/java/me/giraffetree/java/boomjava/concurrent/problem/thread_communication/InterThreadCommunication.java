package me.giraffetree.java.boomjava.concurrent.problem.thread_communication;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程交替打印 0-100 奇偶数
 * https://blog.csdn.net/u011514810/article/details/77131296
 * <p>
 * 两个线程交替打印 奇偶数
 * https://stackoverflow.com/questions/25425130/loop-doesnt-see-value-changed-by-other-thread-without-a-print-statement
 *
 * @author GiraffeTree
 * @date 2020-04-09
 */
public class InterThreadCommunication {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1000L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setDaemon(true).build());

    private static AtomicInteger value = new AtomicInteger(0);
    private static volatile int m = 0;

    public static void main(String[] args) {
        // 1. 共享变量
//        testWithAtomicInteger();
//        testWithVolatileError();
//        testWithVolatile();
//        testWithoutRead(100);
//        testWithRead(100);
        testWaitAndNotify();
//        testOnlySynchronized();
    }

    public static void testOnlySynchronized() {
        Object obj = new Object();
        int max = 100;
        executor.execute(() -> {
            while (true) {
                synchronized (obj) {
                    if (check(m, max)) {
                        if (m % 2 == 0) {
                            m++;
                            System.out.println(Thread.currentThread().getName() + " - " + m);
                        }
                    } else {
                        break;
                    }

                }
            }
        });
        executor.execute(() -> {
            while (true) {
                synchronized (obj) {
                    if (check(m, max)) {
                        if (m % 2 == 1) {
                            m++;
                            System.out.println(Thread.currentThread().getName() + " - " + m);
                        }
                    } else {
                        break;
                    }


                }
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void testWaitAndNotify() {
        Object obj = new Object();
        int max = 100;
        executor.execute(() -> {
            while (true) {
                synchronized (obj) {
                    // m % 2 == 0 不加也可以
                    if (m % 2 == 0 && check(m, max)) {
                        m++;
                        System.out.println(Thread.currentThread().getName() + " - " + m);
                        obj.notifyAll();
                    }

                    if (check(m, max)) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }

                }
            }
        });
        executor.execute(() -> {
            while (true) {
                synchronized (obj) {
                    // m % 2 == 1 不加也可以
                    if (m % 2 == 1 && check(m, max)) {
                        m++;
                        System.out.println(Thread.currentThread().getName() + " - " + m);
                        obj.notifyAll();
                    }

                    if (check(m, max)) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void testWithRead(int max) {
        executor.execute(() -> {
            int a = 0;
            while (check(m, max)) {
                if (m % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + " - " + m);
                    m = m + 1;
                    a++;
                }
            }
            System.out.println(Thread.currentThread().getName() + " final a=" + a);
        });
        executor.execute(() -> {
            int a = 0;
            while (check(m, max)) {
                if (m % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + " - " + m);
                    m = m + 1;
                    a++;
                }
            }
            System.out.println(Thread.currentThread().getName() + " final a= " + a);
        });
        executor.shutdown();
        try {
            executor.awaitTermination(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("final m :" + m);
    }

    public static void testWithoutRead(int max) {
        executor.execute(() -> {
            int a = 0;
            while (check(m, max)) {
                if (m % 2 == 0) {
                    a++;
                    System.out.println(Thread.currentThread().getName() + " - " + m);
                    m++;
                }
            }
            System.out.println(Thread.currentThread().getName() + " - " + a);
        });
        executor.execute(() -> {
            int a = 0;
            while (check(m, max)) {
                if (m % 2 == 1) {
                    a++;
                    System.out.println(Thread.currentThread().getName() + " - " + m);
                    m++;
                }
            }
            System.out.println(Thread.currentThread().getName() + " - " + a);
        });
        executor.shutdown();
        try {
            executor.awaitTermination(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("final m :" + m);
    }

    /**
     * 使用 volatile 可见性来进行 奇偶数打印
     */
    public static void testWithVolatile() {
        executor.execute(() -> {
            while (true) {
                if (m % 2 == 0 && check(m)) {
                    System.out.print(String.format("%s v:%d\n", Thread.currentThread().getName(), m));
                    m++;
                }
                if (!check(m)) {
                    System.out.println(Thread.currentThread().getName() + " break...");
                    break;
                }
            }
        });
        executor.execute(() -> {
            while (true) {
                if (m % 2 == 1) {
                    System.out.print(String.format("%s v:%d\n", Thread.currentThread().getName(), m));
                    m++;
                }
                if (!check(m)) {
                    System.out.println(Thread.currentThread().getName() + " break...");
                    break;
                }
            }
        });
        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static boolean check(int m) {
        return check(m, 10000);
    }

    private static boolean check(int m, int max) {
        return m < max;
    }

    /**
     * 错误示范!!!!!!!!
     * 由于 volatile 的变量 m 变化可见, 导致另一个线程可能先于前一个线程打印
     */
    public static void testWithVolatileError() {
        executor.execute(() -> {
            while (true) {
                if (m % 2 == 0) {
                    System.out.print(String.format("%s v:%d\n", Thread.currentThread().getName(), ++m));
                }
            }
        });
        executor.execute(() -> {
            while (true) {
                if (m % 2 == 1) {
                    System.out.print(String.format("%s v:%d\n", Thread.currentThread().getName(), ++m));
                }
            }
        });
    }


    /**
     * 这里其实也只试用了 value 的可见性
     */
    public static void testWithAtomicInteger() {

        executor.execute(() -> {
            while (true) {
                if (value.get() % 2 == 0) {
                    System.out.println(String.format("%s v:%d", Thread.currentThread().getName(), value.get() + 1));
                    value.getAndIncrement();
                }
            }
        });
        executor.execute(() -> {
            while (true) {
                if (value.get() % 2 == 1) {
                    System.out.println(String.format("%s v:%d", Thread.currentThread().getName(), value.get() + 1));
                    value.getAndIncrement();
                }
            }
        });

    }

}
