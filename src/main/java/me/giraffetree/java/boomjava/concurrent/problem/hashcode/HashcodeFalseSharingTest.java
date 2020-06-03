package me.giraffetree.java.boomjava.concurrent.problem.hashcode;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;

/**
 * 思路:
 * 提前新建好对象, 然后两个线程, 同时去 调用不同对象的 System.identityHashCode
 *
 * @author GiraffeTree
 * @date 2020/6/1
 */
public class HashcodeFalseSharingTest {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(2, 2);

    public static void main(String[] args) {


    }

}
