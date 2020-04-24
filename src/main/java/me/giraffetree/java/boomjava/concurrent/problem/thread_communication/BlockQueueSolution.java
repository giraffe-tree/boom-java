package me.giraffetree.java.boomjava.concurrent.problem.thread_communication;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author GiraffeTree
 * @date 2020/4/24
 */
public class BlockQueueSolution {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();
    private final static BlockingQueue<Integer> QUEUE_ODD = new ArrayBlockingQueue<>(1);
    private final static BlockingQueue<Integer> QUEUE_EVEN = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) {


        EXECUTOR_SERVICE.execute(() -> {
            int loop = 50;
            int take = 1;
            QUEUE_EVEN.offer(take);
            while (loop-- > 0) {
                try {
                    take = QUEUE_ODD.take();
                    System.out.println(String.format("%s - %d", Thread.currentThread().getName(), take));
                    QUEUE_EVEN.offer(take + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        EXECUTOR_SERVICE.execute(() -> {
            int loop = 50;
            while (loop-- > 0) {
                try {
                    Integer take = QUEUE_EVEN.take();
                    System.out.println(String.format("%s - %d", Thread.currentThread().getName(), take));
                    QUEUE_ODD.offer(take + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        EXECUTOR_SERVICE.shutdown();
        try {
            EXECUTOR_SERVICE.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
