package me.giraffetree.java.boomjava.concurrent.division.threadpool;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池测试
 * 1. 提交任务
 * 2. 核心线程池是否已满
 * 3. 队列是否已满
 * 4. 线程池是否已满
 * 5. 无法执行地策略
 *
 * @author GiraffeTree
 * @date 2020-05-05
 */
public class ThreadPoolTest {

    public static void main(String[] args) {

        // 最后一个任务进入队列
//        test(5);
        // 由于队列大小为 20 , 核心为4, 则任务为24个时, 不会新建线程
//        test(24);
        // 25 个任务大于 coreSize + queueSize , 但小于 maxSize +queueSize , 故而线程池会新建一个 thread-4 线程
//        test(25);
        // 线程池满了之后, 会进入拒绝handler处理 , 输出  thread-new running ... count:0
        test(31);

    }

    private static void test(int loop) {
        ExecutorService executorService = ExecutorUtils.getExecutorService(4, 10, 20, "thread-%d",
                (r, executor) -> {
                    // 新建线程执行
                    Thread t1 = new Thread(r);
                    t1.setName("thread-new");
                    t1.start();
                    // 这里如果不新建线程的话, 实际上是由 main 线程执行
                    // r.run();
                });
        while (loop-- > 0) {
            final int c = loop;
            executorService.execute(() -> {
                System.out.println(String.format("%s running ... count:%d", Thread.currentThread().getName(), c));
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                }
            });
        }
        executorService.shutdown();
    }

}
