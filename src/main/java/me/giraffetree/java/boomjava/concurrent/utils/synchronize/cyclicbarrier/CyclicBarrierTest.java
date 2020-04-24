package me.giraffetree.java.boomjava.concurrent.utils.synchronize.cyclicbarrier;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author GiraffeTree
 * @date 2020/4/24
 */
public class CyclicBarrierTest {

    private final static Vector<Integer> vector = new Vector<>();

    private final static ExecutorService executorService = ExecutorUtils.getExecutorService();
    /**
     * 用于异步串行执行
     */
    private final static ExecutorService oneThread = Executors.newFixedThreadPool(1);


    public static void main(String[] args) throws InterruptedException {
        int taskCount = 4;
        final AtomicInteger loop = new AtomicInteger(1);
        Task task = new Task(taskCount,
                () -> {
                    // 这里为了保证并发, 使用额外的一个线程进程操作
                    oneThread.execute(() -> {
                        int all = 0;
                        for (int i = 0; i < taskCount; i++) {
                            all += vector.remove(0);
                        }
                        System.out.println(String.format("cycle over - %d all:%d",
                                loop.getAndIncrement(), all));
                    });

                },
                vector);

        for (int i = 0; i < 100; i++) {
            executorService.execute(task::test);
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("all task over ~~~ ");

    }


    private static class Task {

        private CyclicBarrier cyclicBarrier;
        private Vector<Integer> outer;

        public Task(int threadCount, Runnable runnable, Vector<Integer> vector) {
            this.cyclicBarrier = new CyclicBarrier(threadCount, runnable);
            this.outer = vector;
        }

        public void test() {
            int seconds = ThreadLocalRandom.current().nextInt(10, 20);
            System.out.println(String.format("%s sleep %dms",
                    Thread.currentThread().getName(),
                    seconds));

            try {
                Thread.sleep(seconds);
                outer.add(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }


        public void reset() throws InterruptedException {
            cyclicBarrier.reset();
        }
    }


}
