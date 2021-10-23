package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.lock_condition;

import me.giraffetree.java.boomjava.alg.basic.stack.IStack;
import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author GiraffeTree
 * @date 2020/4/26
 */
public class LockAndConditionTest {

    private final static ExecutorService POOL = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
        testProduceAndConsume();
//        testBlock();

    }

    private static void testBlock() {
        BlockingStack<Integer> stack = new BlockingStack<>(10);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        POOL.execute(() -> {
            int c = 0;
            while (c++ < 10) {
                stack.push(c);
            }
            countDownLatch.countDown();
            stack.push(11);
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException ignore) {
        }
        int c = 11;
        while (c-- > 0) {
            Integer pop = stack.pop();
            System.out.println(pop);
        }
        POOL.shutdown();
    }

    private static void testProduceAndConsume() {

        BlockingStack<Integer> stack = new BlockingStack<>(10);
        final int loop = 1_000;
        POOL.execute(() -> {
            int c = loop;
            while (c-- > 0) {
                stack.push(c);
            }
        });

        POOL.execute(() -> {
            int c = loop;
            while (c-- > 0) {
                stack.pop();
            }
        });

        POOL.shutdown();
        try {
            POOL.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * thread safe blocking stack
     *
     * @param <T> element
     */
    private static class BlockingStack<T> implements IStack<T> {
        private Object[] array;
        private int index = 0;
        ReentrantLock reentrantLock = new ReentrantLock();

        Condition full = reentrantLock.newCondition();
        Condition empty = reentrantLock.newCondition();

        BlockingStack(int capacity) {
            array = new Object[capacity];
        }

        @Override
        public void push(T t) {
            reentrantLock.lock();
            try {
                while (index == array.length) {
                    try {
                        full.await(200, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException ignored) {
                    }
                }
                array[index++] = t;
                empty.signalAll();
            } finally {
                reentrantLock.unlock();
            }

        }

        @Override
        @SuppressWarnings("unchecked")
        public T pop() {
            T t;
            reentrantLock.lock();
            try {
                while (index == 0) {
                    try {
                        empty.await();
                    } catch (InterruptedException ignore) {
                    }
                }
                t = (T) array[index - 1];
                // gc
                array[--index] = null;

                full.signalAll();
            } finally {
                reentrantLock.unlock();
            }

            return t;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T peek() {
            return (T) array[index - 1];
        }

        @Override
        public int size() {
            return index;
        }

        /**
         * 非线程安全
         */
        @SuppressWarnings("unchecked")
        @Override
        public Iterator<T> iterator() {

            return new Iterator<T>() {

                private int cur;

                @Override
                public boolean hasNext() {
                    return cur < index;
                }

                @Override
                public T next() {
                    return (T) array[cur++];
                }
            };
        }


    }

}
