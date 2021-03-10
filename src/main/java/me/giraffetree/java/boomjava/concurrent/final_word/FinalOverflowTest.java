package me.giraffetree.java.boomjava.concurrent.final_word;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author GiraffeTree
 * @date 2021-03-09
 */
public class FinalOverflowTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        EXECUTOR_SERVICE.execute(
                () -> {
                    boolean process = countDownAndAwait(countDownLatch);
                    if (!process) {
                        return;
                    }
                    long loop = 1_000_000_000;
                    while (loop-- > 0) {
                        Door.write();
                    }
                }
        );

        EXECUTOR_SERVICE.execute(() -> {
            boolean process = countDownAndAwait(countDownLatch);
            if (!process) {
                return;
            }
            long loop = 1_000_000_000;
            while (loop-- > 0) {
                int read = Door.read();
                if (read == 0) {
                    System.out.println("error get final field");
                    break;
                }
            }
        });

    }


    private static boolean countDownAndAwait(CountDownLatch cdl) {
        cdl.countDown();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
