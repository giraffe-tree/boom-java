package me.giraffetree.java.boomjava.concurrent.volatile_word;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 更换 volatile 对象中引用对象的 field 可见性问题
 * 修改 useVolatile 变量, 观察不同值产生的影响
 * <p>
 *
 * @author giraffetree
 */
public class NormalObjectReferenceObjectVolatileTest {

    private static volatile MultiReferenceObject volatileObject = new MultiReferenceObject();
    private static MultiReferenceObject normalObject = new MultiReferenceObject();

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        long f1 = 12131;
        long f16 = 232;

        // 如果 useVolatile = false, 会输出 task timeout
        // 如果 useVolatile = true, 则程序会很快执行结束
        final boolean useVolatile = true;

        System.out.println("use volatile object:" + useVolatile);

        executorService.submit(getTaskCheckObject(useVolatile, f1,f16));
        executorService.submit(getTaskCheckObject(useVolatile, f1,f16));

        executorService.submit(getTaskSetObject(useVolatile, f1,f16));

        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
        if (!terminated) {
            System.out.printf("%s task timeout ...\n", LocalDateTime.now());
            executorService.shutdownNow();
        }
    }

    private static Runnable getTaskSetObject(final boolean writeVolatile, final long f1, final long f16) {
        return () -> {
            System.out.printf("%s %s start getTaskSetKeyValue \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
                MultiFieldObject tmpObj = new MultiFieldObject();
                tmpObj.setF01(f1);
                tmpObj.setF16(f16);
                if (writeVolatile) {
                    volatileObject.setO1(tmpObj);
                } else {
                    normalObject.setO1(tmpObj);
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
                    if (volatileObject.getO1()!=null
                            && volatileObject.getO1().getF11() == f1
                            && volatileObject.getO1().getF16() == f16) {
                        break;
                    }
                } else {
                    if (normalObject.getO1()!=null
                            && normalObject.getO1().getF11() == f1
                            && normalObject.getO1().getF16() == f16) {
                        break;
                    }
                }
            }
            System.out.printf("%s %s Jump out of the loop!\n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }


}