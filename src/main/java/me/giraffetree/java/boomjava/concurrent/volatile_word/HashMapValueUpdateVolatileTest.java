package me.giraffetree.java.boomjava.concurrent.volatile_word;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 实际测试没有明显变化!!!!!!!!!!
 *
 * 替换 HashMap 中 value 的 field 对应的可见性问题
 * 修改 useVolatile 变量, 观察不同值产生的影响
 * <p>
 *
 * @author giraffetree
 */
public class HashMapValueUpdateVolatileTest {

    /**
     * 定义两个map
     */
    private static volatile HashMap<String, ValueObject> volatileMap = new HashMap<>();
    private static HashMap<String, ValueObject> normalMap = new HashMap<>();

    private final static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        final String key = "hello";
        final ValueObject vo = new ValueObject(1, 10L, "world");
        normalMap.put(key, vo);

        final String key2 = "hello";
        final ValueObject vo2 = new ValueObject(1, 10L, "world");
        volatileMap.put(key2, vo2);

        final boolean useVolatile = true;
        System.out.println("use volatile map:" + useVolatile);
        ValueObject vo3 = new ValueObject(2, 11L, "China");

        executorService.submit(getTaskCheckKeyValue(useVolatile, key, vo3));

        executorService.submit(getTaskSetKeyValue(useVolatile, key, vo3));

        executorService.shutdown();
        boolean terminated = executorService.awaitTermination(10, TimeUnit.SECONDS);
        if (!terminated) {
            System.out.printf("%s task timeout ...\n", LocalDateTime.now());
            executorService.shutdownNow();
        }
    }

    private static Runnable getTaskSetKeyValue(final boolean writeVolatileMap, final String key, final ValueObject newValue) {
        return () -> {
            System.out.printf("%s %s start getTaskSetKeyValue \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            try {
                Thread.sleep(2000L);
                if (writeVolatileMap) {
                    ValueObject vo = volatileMap.get(key);
                    vo.setF1(newValue.f1);
                    vo.setF2(newValue.f2);
                    vo.setF3(newValue.f3);
                } else {
                    ValueObject vo = normalMap.get(key);
                    vo.setF1(newValue.f1);
                    vo.setF2(newValue.f2);
                    vo.setF3(newValue.f3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s %s end operate map \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
        };
    }

    private static Runnable getTaskCheckKeyValue(final boolean readVolatileArray, final String expectKey, final ValueObject expectValue) {
        return () -> {
            System.out.printf("%s %s start getTaskCheckKeyValue \n",
                    LocalDateTime.now(), Thread.currentThread().getName());
            // 死循环, 直到发现value被修改
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

    private static class ValueObject {
        private int f000;
        private long f001;
        private int f1;
        private long f002;
        private long f2;
        private long f003;
        private String f3;
        private long f004;

        public int getF1() {
            return f1;
        }

        public void setF1(int f1) {
            this.f1 = f1;
        }

        public long getF2() {
            return f2;
        }

        public void setF2(long f2) {
            this.f2 = f2;
        }

        public String getF3() {
            return f3;
        }

        public void setF3(String f3) {
            this.f3 = f3;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ValueObject)) {
                return false;
            }
            ValueObject that = (ValueObject) o;
            return f1 == that.f1 && f2 == that.f2 && Objects.equals(f3, that.f3);
        }

        @Override
        public int hashCode() {
            return Objects.hash(f1, f2, f3);
        }

        public ValueObject(int f1, long f2, String f3) {
            this.f1 = f1;
            this.f2 = f2;
            this.f3 = f3;
        }
    }

}