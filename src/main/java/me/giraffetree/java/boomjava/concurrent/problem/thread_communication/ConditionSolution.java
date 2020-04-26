package me.giraffetree.java.boomjava.concurrent.problem.thread_communication;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author GiraffeTree
 * @date 2020/4/21
 */
public class ConditionSolution {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 1000L,
            TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("thread-%d").build());


    public static void main(String[] args) {
        BlockOddEven blockOddEven = new BlockOddEven(100);
        executor.execute(blockOddEven::printEven);
        executor.execute(blockOddEven::printOdd);
        executor.shutdown();
    }

    private static class BlockOddEven {

        /**
         * count 值
         */
        private int count = 0;
        /**
         * 最大值
         * exclude
         */
        private int max;

        private final Lock lock = new ReentrantLock();
        /**
         * 奇数 条件变量
         */
        private final Condition odd = lock.newCondition();
        /**
         * 偶数 条件变量
         */
        private final Condition even = lock.newCondition();

        public BlockOddEven(int max) {
            this.max = max;
        }

        void printOdd() {
            lock.lock();
            try {
                while (count < max) {
                    if (count % 2 == 0) {
                        System.out.println(String.format("%s - %d", Thread.currentThread().getName(), count++));
                        even.signalAll();
                    } else {
                        // 当线程被唤醒后，是从await命令后开始执行的
                        System.out.println(String.format("%s - await", Thread.currentThread().getName()));
                        odd.await();
                    }
                }

            } catch (InterruptedException ignored) {
            } finally {
                lock.unlock();
            }
        }

        void printEven() {
            lock.lock();
            try {
                while (count < max) {
                    if (count % 2 == 1) {
                        System.out.println(String.format("%s - %d", Thread.currentThread().getName(), count++));
                        odd.signalAll();
                    } else {
                        System.out.println(String.format("%s - await", Thread.currentThread().getName()));
                        even.await();
                    }
                }
            } catch (InterruptedException ignored) {

            } finally {
                lock.unlock();
            }
        }

    }
}
