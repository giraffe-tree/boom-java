package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.exchanger;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;

/**
 * 用于两个线程之间交换数据
 *
 * @author GiraffeTree
 * @date 2020-05-05
 */
public class ExchangerTest {

    public static void main(String[] args) {
        testExchanger();
    }

    private static void testExchanger() {
        ExecutorService pool = ExecutorUtils.getExecutorService();
        Exchanger<String> exchanger = new Exchanger<>();
        pool.execute(() -> {
            try {
                String world = exchanger.exchange("hello world");
                System.out.println(String.format("first: %s %s", Thread.currentThread().getName(), world));
                world = exchanger.exchange("hello world 2");
                System.out.println(String.format("first: %s %s", Thread.currentThread().getName(), world));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.execute(() -> {
            try {
                String chen = exchanger.exchange("hello chen");
                System.out.println(String.format("second: %s %s", Thread.currentThread().getName(), chen));

                Thread.sleep(1000L);
                chen = exchanger.exchange("hello chen 2 ");
                System.out.println(String.format("second: %s %s", Thread.currentThread().getName(), chen));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        pool.shutdown();
    }


}
