package me.giraffetree.java.boomjava.weekly.w20211023;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试因为线程池队列过长, 导致部分任务因为执行超时
 *
 * @author GiraffeTree
 * @date 2021/10/23
 */
public class QueueTooLongTimeoutTest {

    public static void main(String[] args) {
        queueTooLongTimeout(2000L, 20L, 5, 2000);
        TaskStatisticsDTO statistics = TaskUtils.statistics();
        System.out.println(statistics);
    }

    public static void queueTooLongTimeout(long taskSize, long taskTimeMills, int threadSize, int queueSize) {
        System.out.printf("try to execute - task:%d threadSize:%d queueSize:%d\n",
                taskSize, threadSize, queueSize);
        ThreadPoolExecutor threadPool = getThreadPool(threadSize, queueSize);
        for (long i = 1; i <= taskSize; i++) {
            Runnable task = TaskUtils.getTask(i, System.currentTimeMillis(), taskTimeMills);
            threadPool.execute(task);
        }
        // 线程池不再接受新的任务, 立即返回
        threadPool.shutdown();
        try {
            // 等待1分钟, 如果线程执行的任务全部完成, 则立即返回
            boolean finished = threadPool.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println("threadPool finished:" + finished);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static ThreadPoolExecutor getThreadPool(int threadSize, int queueSize) {
        return new ThreadPoolExecutor(threadSize, threadSize, 2000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize), new BasicThreadFactory.Builder().namingPattern("QueueTooLongTest-%d").build());
    }

}
