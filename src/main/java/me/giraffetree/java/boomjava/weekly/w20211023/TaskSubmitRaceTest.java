package me.giraffetree.java.boomjava.weekly.w20211023;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池任务提交竞争关系研究
 * 多线程 提交任务到线程池 看看竞争情况
 * 所以这里需要有两个线程池
 * A 线程池 -> 提交任务 -> B 线程池
 *
 * @author GiraffeTree
 * @date 2021/10/23
 */
public class TaskSubmitRaceTest {

    public static void main(String[] args) {
        test();

    }

    public static void test() {
        int count = 50000;
        ThreadPoolExecutor aPool = getThreadPool("A", 200, count);
        ThreadPoolExecutor bPool = getThreadPool("B", 200, count);

        for (long i = 1L; i <= count; i++) {
            final long tmp = i;
            aPool.execute(() -> {
                Runnable task = TaskUtils.getTask(tmp, System.currentTimeMillis(), 0);
                bPool.execute(task);
            });
        }
        aPool.shutdown();
        // 等待 a 线程池 把任务全部提交给 b 线程池
        while (true) {
            try {
                if (aPool.awaitTermination(1000, TimeUnit.SECONDS)) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        bPool.shutdown();
        // 等待 b 线程 把任务全部执行完
        while (true) {
            try {
                if (bPool.awaitTermination(1000, TimeUnit.SECONDS)) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        TaskStatisticsDTO statistics = TaskUtils.statistics();
        System.out.println(statistics);
    }


    private static ThreadPoolExecutor getThreadPool(String poolName, int threadSize, int queueSize) {
        return new ThreadPoolExecutor(threadSize, threadSize, 2000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize), new BasicThreadFactory.Builder().namingPattern(poolName + "-%d").build());
    }

}
