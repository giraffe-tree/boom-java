package me.giraffetree.java.boomjava.alg.sort;

import me.giraffetree.java.boomjava.alg.sort.bubble.BubbleObjectSort;
import me.giraffetree.java.boomjava.alg.sort.heap.HeapObjectSort;
import me.giraffetree.java.boomjava.alg.sort.insertion.InsertionObjectSort;
import me.giraffetree.java.boomjava.alg.sort.merge.MergeObjectSort;
import me.giraffetree.java.boomjava.alg.sort.quick.QuickObjectSort;
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

        int tryCount = 3;
        int arrayLength = 10000;

        double insertAvgTime = averageTime(tryCount, arrayLength, x -> InsertionObjectSort.sort(x, true));
        System.out.println(String.format("insertAvgTime: %.2fms", insertAvgTime));

        double bubbleAvgTime = averageTime(tryCount, arrayLength, x -> BubbleObjectSort.sort(x, true));
        System.out.println(String.format("bubbleAvgTime: %.2fms", bubbleAvgTime));

        double selectionAvgTime = averageTime(tryCount, arrayLength, x -> SelectionObjectSort.sort(x, true));
        System.out.println(String.format("selectionAvgTime: %.2fms", selectionAvgTime));

        double shellAvgTime = averageTime(tryCount, arrayLength, x -> ShellObjectSort.sort(x, true));
        System.out.println(String.format("shellAvgTime: %.2fms", shellAvgTime));

        double heapAvgTime = averageTime(tryCount, arrayLength, x -> HeapObjectSort.sort(x));
        System.out.println(String.format("heapAvgTime: %.2fms", heapAvgTime));

        double quickAvgTime = averageTime(tryCount, arrayLength, x -> QuickObjectSort.sort(x));
        System.out.println(String.format("quickAvgTime: %.2fms", quickAvgTime));

        double mergeAvgTime = averageTime(tryCount, arrayLength, x -> MergeObjectSort.sort(x));
        System.out.println(String.format("mergeAvgTime: %.2fms", mergeAvgTime));

    }


}
