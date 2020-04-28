package me.giraffetree.java.boomjava.jvm.markword;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.ExecutorService;

/**
 * @author GiraffeTree
 * @date 2020/4/28
 */
public class MarkWordTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {

        testSynchronized();
        EXECUTOR_SERVICE.shutdown();
    }

    private static void testSynchronized() {
        Object obj = new Object();
        // 无锁
        printObjectLayout(obj);
        // 有锁
        final int loop = 100;
        int threadCount = 2;
        while (threadCount-- > -0) {
            EXECUTOR_SERVICE.execute(() -> {
                int c = loop;
                while (c-- > 0) {
                    synchronized (obj) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException e) {
                        }
                    }
                    if (c % 10 == 0) {
                        printObjectLayout(obj);
                    }
                }
            });
        }
    }

    private static void testBiasedLock() {

    }

    private static void printObjectLayout(Object o) {
        ClassLayout classLayout = ClassLayout.parseInstance(o);
        System.out.println(classLayout.toPrintable());
    }


}
