package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.atomic;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @author GiraffeTree
 * @date 2020/4/26
 */
public class AtomicArrayTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {

        test();

    }

    public static void test() {
        int len = 1;
        int loop = 1_000_000;
        int threadCount = 2;
        AtomicIntegerArray array = new AtomicIntegerArray(len);
        for (int x = 0; x < threadCount; x++) {
            EXECUTOR_SERVICE.execute(() -> {
                for (int i = 0; i < loop; i++) {
                    for (int index = 0; index < len; index++) {
                        array.getAndIncrement(index);
                    }
                }
            });
        }

        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += array.get(i);
        }
        System.out.println(sum);
        System.out.println(sum == len * loop * threadCount);

    }


}
