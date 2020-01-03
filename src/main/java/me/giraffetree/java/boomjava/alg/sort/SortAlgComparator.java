package me.giraffetree.java.boomjava.alg.sort;

import me.giraffetree.java.boomjava.alg.sort.bubble.BubbleObjectSort;
import me.giraffetree.java.boomjava.alg.sort.insertion.InsertionObjectSort;
import me.giraffetree.java.boomjava.alg.sort.selection.SelectionObjectSort;
import me.giraffetree.java.boomjava.alg.sort.shell.ShellObjectSort;

import java.util.function.Consumer;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.*;

/**
 * 用于测试排序算法性能
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class SortAlgComparator {

    public static long time(int arrayLength, Consumer<Comparable[]> consumer) {
        Element[] elements = generateObj(arrayLength, arrayLength << 2, arrayLength >> 2);
        long l1 = System.currentTimeMillis();
        consumer.accept(elements);
        return System.currentTimeMillis() - l1;
    }

    /**
     * 求算法执行的平均值
     *
     * @param tryCount    算法执行的轮次
     * @param arrayLength 排序数组的长度
     * @param consumer    所使用的算法
     * @return double 平均花费时间 ms
     */
    public static double averageTime(int tryCount, int arrayLength, Consumer<Comparable[]> consumer) {
        long all = 0L;
        for (int i = 0; i < tryCount; i++) {
            all += time(arrayLength, consumer);
        }
        return (double) all / tryCount;
    }

    public static void main(String[] args) {

        double insertAvgTime = averageTime(3, 10000, x -> InsertionObjectSort.sort(x, false));
        double bubbleAvgTime = averageTime(3, 10000, x -> BubbleObjectSort.sort(x, false));
        double selectionAvgTime = averageTime(3, 10000, x -> SelectionObjectSort.sort(x, false));
        double shellAvgTime = averageTime(3, 10000, x -> ShellObjectSort.sort(x, false));

        System.out.println(String.format("insertAvgTime: %.2fms", insertAvgTime));
        System.out.println(String.format("bubbleAvgTime: %.2fms", bubbleAvgTime));
        System.out.println(String.format("selectionAvgTime: %.2fms", selectionAvgTime));
        System.out.println(String.format("shellAvgTime: %.2fms", shellAvgTime));

    }


}
