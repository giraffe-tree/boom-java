package me.giraffetree.java.boomjava.concurrent.division.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * fork / join
 * 1. 任务分解
 * 2. 结果合并
 * 一部分是分治任务的线程池 ForkJoinPool，另一部分是分治任务 ForkJoinTask。
 * ForkJoinTask中, fork() 方法会异步地执行一个子任务，
 * 而 join() 方法则会阻塞当前线程来等待子任务的执行结果
 * ForkJoinTask 有两个子类—— RecursiveAction 无返回结果 和 RecursiveTask 有返回结果
 * <p>
 * <b>java stream 与 fork/join</b>
 * Java 1.8 提供的 Stream API 里面并行流也是以 ForkJoinPool 为基础的。不过需要你注意的是
 * ，默认情况下所有的并行流计算都共享一个 ForkJoinPool，这个共享的 ForkJoinPool 默认的线程数是 CPU 的核数；
 * 如果所有的并行流计算都是 CPU 密集型计算的话，完全没有问题，但是如果存在 I/O 密集型的并行流计算，
 * 那么很可能会因为一个很慢的 I/O 计算而拖慢整个系统的性能。所以建议用不同的 ForkJoinPool 执行不同类型的计算任务。
 * </p>
 * <p>
 * 参考:
 * - 极客时间 fork/join 入门
 * - https://time.geekbang.org/column/article/92524
 * - 并发大神 Doug Lea 论文
 * - http://gee.cs.oswego.edu/dl/papers/fj.pdf
 * - 为什么不使用 fork + fork by 廖雪峰
 * - https://www.liaoxuefeng.com/article/1146802219354112
 * - fork join pool FILO 栈
 * - https://kaimingwan.com/post/java/forkjoinpooljie-du
 * @author GiraffeTree
 * @date 2020-05-05
 */
public class ForkJoinTest {


    public static void main(String[] args) {
        //创建分治任务线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        //创建分治任务
        Fibonacci fibonacci =
                new Fibonacci(30);
        //启动分治任务
        Integer result = forkJoinPool.invoke(fibonacci);
        //输出结果
        System.out.println(result);
    }

    /**
     * 递归任务
     */
    private static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1) {
                return n;
            }
            Fibonacci f1 = new Fibonacci(n - 1);
            //创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            //等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }
}
