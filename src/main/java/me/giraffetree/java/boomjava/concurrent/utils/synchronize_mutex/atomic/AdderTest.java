package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.atomic;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * 用于只增不减的计数
 * LongAdder 分析
 * cas 多线程累加器
 * https://juejin.im/post/5d2eb113518825305f248079
 *
 * @author GiraffeTree
 * @date 2020/4/26
 */
public class AdderTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
        testAdder();

    }

    private static void testAdder() {

        int loop = 10_000_000;
        int threadCount = 4;
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < threadCount; i++) {
            EXECUTOR_SERVICE.execute(() -> {
                int c = loop;
                while (c-- > 0) {
                    longAdder.increment();
                }
            });
        }
        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(longAdder.sum());
        System.out.println(longAdder.sum() == loop * threadCount);

    }
}
