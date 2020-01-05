package me.giraffetree.java.boomjava.alg.sort.heap;


import me.giraffetree.java.boomjava.alg.sort.Element;
import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 堆排序
 * 在最坏的情况下也只需要 2NlogN 次的比较次数
 * 但是现代系统的许多应用很少使用它, 因为它无法利用缓存
 * 数组元素量很少和邻近的其他元素进行比较
 * 因此缓存未命中的次数远高于大多数在相邻元素间进行比较的算法
 * 堆排序的过程
 * 1. 从一个乱序的数组变成一个最大/小堆
 * 2. 从一个最大/小堆变成一个有序数组
 * <p>
 * 时间复杂度: nlogn
 * 空间复杂度: 1
 *
 * @author GiraffeTree
 * @date 2020-01-04
 */
public class HeapObjectSort {

    public static void main(String[] args) {
        Element[] elements = SortUtils.generateObj(20, 40, 10);
        System.out.println(Arrays.toString(elements));
        sort(elements);
        SortUtils.checkSorted(elements, true);
        System.out.println(Arrays.toString(elements));

    }

    public static void sort(Comparable[] a) {
        int N = a.length;
        // 生成一个最大堆/最小堆
        for (int k = N / 2; k >= 1; k--) {
            sink(a, k, N);
        }
        // 从一个最大堆/最小堆转换成一个有序的数组
        while (N > 1) {
            swap(a, 1, N--);
            sink(a, 1, N);
        }
    }

    /**
     * @param a 原始数组
     * @param k 需要下沉的元素位置
     * @param N 原始数组可用的最大长度 N <= a.length
     */
    private static void sink(Comparable[] a, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(a, j, j + 1)) {
                j++;
            }
            if (!less(a, k, j)) {
                break;
            }
            swap(a, k, j);
            k = j;
        }
    }

    /**
     * 这里参数中的 i, j 都是平常所说的 1 ~ N, 而不是 数组中的 0 ~ N-1
     * 至于为什么要这么做, 原因很简单, 你写一遍就知道, 这样写省脑子, 方便!
     */
    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    /**
     * 这里参数中的 i, j 都是平常所说的 1 ~ N, 而不是 数组中的 0 ~ N-1
     * 至于为什么要这么做, 原因很简单, 你写一遍就知道, 这样写省脑子, 方便!
     */
    private static void swap(Comparable[] pq, int i, int j) {
        Comparable k = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = k;
    }

}
