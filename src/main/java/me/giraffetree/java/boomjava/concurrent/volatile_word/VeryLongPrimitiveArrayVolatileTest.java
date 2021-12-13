package me.giraffetree.java.boomjava.concurrent.volatile_word;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 原始类型数组的可见性问题
 * 这里可以知道即使 volatile 数组很长, jvm 也能保证设置值之后, 其他线程可以在比较短的时间内读到最新的值
 *
 * @author giraffetree
 */
public class VeryLongPrimitiveArrayVolatileTest {

    private final static int length = 1000_000;
    /**
     * 定义两个数组
     */
    private static volatile long[] volatileArray = new long[length];
    private static long[] normalArray = new long[length];

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        final int fieldIndex = length - 1;
        final int fieldValue = 10;
        // 如果 useVolatile = false, 会输出 task timeout
        // 如果 useVolatile = true, 则程序会很快执行结束
        final boolean useVolatile = true;
        System.out.println("use volatile array:" + useVolatile);

        executorService.submit(getTaskSetArrField(useVolatile, fieldIndex, fieldValue));
        executorService.submit(getTaskCheckArr(useVolatile, fieldIndex, fieldValue));

        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
        if (!terminated) {
            System.out.printf("%s task timeout ...\n", LocalDateTime.now());
            executorService.shutdownNow();
        }
    }

    private static Runnable getTaskSetArrField(final boolean writeVolatileArray, final int fieldIndex, final int fieldValue) {
        return () -> {
            System.out.printf("%s %s start getTaskSetArrField \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
                if (writeVolatileArray) {
                    volatileArray[fieldIndex] = fieldValue;
                } else {
                    normalArray[fieldIndex] = fieldValue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s %s end set arr \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }

    private static Runnable getTaskCheckArr(final boolean readVolatileArray, final int fieldIndex, final int expectValue) {
        return () -> {
            System.out.printf("%s %s start getTaskCheckArr \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            // 死循环, 直到发现数组被修改
            while (true) {
                if (readVolatileArray) {
                    if (volatileArray[fieldIndex] == expectValue) {
                        break;
                    }
                } else {
                    if (normalArray[fieldIndex] == expectValue) {
                        break;
                    }
                }

            }
            System.out.printf("%s %s Jump out of the loop!\n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }

}