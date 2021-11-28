package me.giraffetree.java.boomjava.utils.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author GiraffeTree
 * @date 2021/11/28
 */
public class ConcurrentComputerTest {

    public static void main(String[] args) {

        compute();
    }

    public static void compute() {
        ConcurrentComputer<String, Integer> concurrentComputer = new ConcurrentComputer<>();
        String key = "test.key";
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        int size = 20;
        for (int i = 0; i < size; i++) {
            executorService.submit(() -> {
                try {
                    Integer result = concurrentComputer.compute(key, () -> load(990L));
                    System.out.printf("%s -> get result:%d\n", Thread.currentThread().getName(), result);
                } catch (Exception e) {
                    System.out.printf("%s -> %s", Thread.currentThread().getName(), e.getLocalizedMessage());
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static Integer load(long mills) {
        long start = System.currentTimeMillis();
        try {
            System.out.printf("%s - start compute\n", Thread.currentThread().getName());
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.printf("%s - end compute - cost:%dms\n", Thread.currentThread().getName(), System.currentTimeMillis() - start);
        }
        return 0;
    }

}