package me.giraffetree.java.boomjava.concurrent.volatile_word;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 更新 对象中的 field 可见性问题
 * 修改 useVolatile 变量, 观察不同值产生的影响
 * <p>
 *
 * @author giraffetree
 */
public class NormalObjectFieldUpdateVolatileTest {

    private static volatile MultiFieldObject volatileObject = new MultiFieldObject();
    private static MultiFieldObject normalObject = new MultiFieldObject();

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        long f1 = 12131;
        long f16 = 232;

        // 如果 useVolatile = false, 会输出 task timeout
        // 如果 useVolatile = true, 则程序会很快执行结束
        final boolean useVolatile = false;

        System.out.println("use volatile object:" + useVolatile);

        executorService.submit(getTaskCheckObject(useVolatile, f1,f16));
        executorService.submit(getTaskCheckObject(useVolatile, f1,f16));

        executorService.submit(getTaskSetObjectField(useVolatile, f1,f16));

        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
        if (!terminated) {
            System.out.printf("%s task timeout ...\n", LocalDateTime.now());
            executorService.shutdownNow();
        }
    }

    private static Runnable getTaskSetObjectField(final boolean writeVolatile, final long f1, final long f16) {
        return () -> {
            System.out.printf("%s %s start getTaskSetKeyValue \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
                if (writeVolatile) {
                    volatileObject.setF01(f1);
                    volatileObject.setF16(f16);
                } else {
                    normalObject.setF01(f1);
                    normalObject.setF16(f16);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s %s end set obj \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }

    private static Runnable getTaskCheckObject(final boolean readVolatile, final long f1, final long f16) {
        return () -> {
            System.out.printf("%s %s start getTaskCheckKeyValue \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            // 死循环, 直到发现对象被修改
            while (true) {
                if (readVolatile) {
                    if (volatileObject.getF01() == f1 && volatileObject.getF16() == f16) {
                        break;
                    }
                } else {
                    if (normalObject.getF01() == f1 && normalObject.getF16() == f16) {
                        break;
                    }
                }
            }
            System.out.printf("%s %s Jump out of the loop!\n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }


}