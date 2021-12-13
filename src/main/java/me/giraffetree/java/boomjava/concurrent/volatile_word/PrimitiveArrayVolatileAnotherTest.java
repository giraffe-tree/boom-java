package me.giraffetree.java.boomjava.concurrent.volatile_word;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 原始类型数组的可见性问题
 * <p>
 * 案例来源
 * https://stackoverflow.com/questions/53753792/java-volatile-array-my-test-results-do-not-match-the-expectations
 *
 * @author giraffetree
 */
public class PrimitiveArrayVolatileAnotherTest {
    /**
     * Make this non-volatile now
     */
    private static long[] arr = new long[20];

    /**
     * Make another volatile variable
     */
    private static volatile long vol = 0;

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        final int fieldIndex = 1;
        final int fieldValue = 10;
        executorService.submit(
                getTaskSetArrField(fieldIndex, fieldValue)
        );
        // 如果 writeVolatile = false, 会输出 task timeout
        // 如果 writeVolatile = true, 则程序会很快执行结束
        executorService.submit(getTaskCheckArr(false, fieldIndex, fieldValue));

        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
        if (!terminated) {
            System.out.printf("%s task timeout ...\n", LocalDateTime.now());
            executorService.shutdownNow();
        }
    }

    private static Runnable getTaskSetArrField(final int fieldIndex, final int fieldValue) {
        return () -> {
            System.out.printf("%s %s start getTaskSetArrField \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
                arr[fieldIndex] = fieldValue;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s %s end set arr[19] = 2 \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }

    private static Runnable getTaskCheckArr(final boolean writeVolatile, final int fieldIndex, final int expectValue) {
        return () -> {
            System.out.printf("%s %s start getTaskCheckArr \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            while (true) {
                if (writeVolatile) {
                    long i = vol;
                }
                if (arr[fieldIndex] == expectValue) {
                    break;
                }
            }
            System.out.printf("%s %s Jump out of the loop!\n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }

}