package me.giraffetree.java.boomjava.concurrent.volatile_word;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 替换 HashMap 可见性问题
 * 修改 useVolatile 变量, 观察不同值产生的影响
 * <p>
 *
 * @author giraffetree
 */
public class ReplaceHashMapVolatileTest {

    /**
     * 定义两个map
     */
    private static volatile HashMap<String, String> volatileMap = new HashMap<>();
    private static HashMap<String, String> normalMap = new HashMap<>();

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        final String key = "hello";
        final String value = "world";
        // 如果 useVolatile = false, 会输出 task timeout
        // 如果 useVolatile = true, 则程序会很快执行结束
        final boolean useVolatile = false;
        System.out.println("use volatile array:" + useVolatile);

        executorService.submit(getTaskSetKeyValue(useVolatile, key, value));
        executorService.submit(getTaskCheckKeyValue(useVolatile, key, value));
        executorService.submit(getTaskCheckKeyValue(useVolatile, key, value));

        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
        if (!terminated) {
            System.out.printf("%s task timeout ...\n", LocalDateTime.now());
            executorService.shutdownNow();
        }
    }

    private static Runnable getTaskSetKeyValue(final boolean writeVolatileMap, final String key, final String value) {
        return () -> {
            System.out.printf("%s %s start getTaskSetKeyValue \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
                HashMap<String, String> tmpMap = new HashMap<>();
                tmpMap.put(key, value);
                if (writeVolatileMap) {
                    volatileMap = tmpMap;
                } else {
                    normalMap = tmpMap;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s %s end set arr \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }

    private static Runnable getTaskCheckKeyValue(final boolean readVolatileArray, final String expectKey, final String expectValue) {
        return () -> {
            System.out.printf("%s %s start getTaskCheckKeyValue \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            // 死循环, 直到发现数组被修改
            while (true) {
                if (readVolatileArray) {
                    if (volatileMap.containsKey(expectKey) && expectValue.equals(volatileMap.get(expectKey))) {
                        break;
                    }
                } else {
                    if (normalMap.containsKey(expectKey) && expectValue.equals(normalMap.get(expectKey))) {
                        break;
                    }
                }
            }
            System.out.printf("%s %s Jump out of the loop!\n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }

}