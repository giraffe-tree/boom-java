package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.atomic;


import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * accumulator 是 adder 的进阶版, 可以自定义新增的 count 数
 *
 * @author GiraffeTree
 * @date 2020/4/26
 */
public class AccumulatorTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
        long loop = 10_000_000L;
        int threadCount = 4;
        for (int i = 0; i < threadCount; i++) {
            final long cur = i;
            EXECUTOR_SERVICE.execute(() -> {
                long c = loop;
                while (c-- > 0) {
                    longAccumulator.accumulate(cur + 1);
                }
            });
        }

        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long sum = longAccumulator.get();
        System.out.println(sum);
        System.out.println(sum == loop * (1 + threadCount) * threadCount / 2);
    }
}
