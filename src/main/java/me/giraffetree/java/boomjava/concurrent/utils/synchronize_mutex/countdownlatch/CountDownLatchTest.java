package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.countdownlatch;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020/3/27
 */
public class CountDownLatchTest {

    private final static ExecutorService executorService = ExecutorUtils.getExecutorService();

    public static void main(String[] args) throws InterruptedException {
        int taskCount = 4;
        Apple apple = new Apple(taskCount);
        for (int i = 0; i < taskCount; i++) {
            executorService.execute(apple::test);
        }
        apple.waitLatch();
        System.out.println("all task over ~~~ ");

        executorService.shutdown();
    }

    private static class Apple {

        private final int threadCount;
        private CountDownLatch countDownLatch;

        public Apple(int threadCount) {
            this.threadCount = threadCount;
            this.countDownLatch = new CountDownLatch(threadCount);
        }

        public void test() {
            int seconds = ThreadLocalRandom.current().nextInt(0, 5);
            System.out.println(String.format("%s sleep %ds",
                    Thread.currentThread().getName(),
                    seconds));

            try {
                Thread.sleep(seconds * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }


        public void waitLatch() throws InterruptedException {
            countDownLatch.await();
        }
    }


}
