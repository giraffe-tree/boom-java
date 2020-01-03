package me.giraffetree.java.boomjava.alg.sort.shell;

import me.giraffetree.java.boomjava.alg.sort.Element;

import java.util.Arrays;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.*;

/**
 * 希尔排序
 * 基于插入排序的一种快速排序算法
 * 漫画将 shell 排序: https://blog.csdn.net/qq_39207948/article/details/80006224
 * 时间复杂度: 小于 n^2
 * 原地排序
 * 不稳定排序
 * <p>
 * 不同点:
 * Shell排序允许交换相距较远的索引，而冒泡排序仅交换相邻的项。
 * <p>
 * 易错点:
 * less 应该放在 第二个 for 循环中, 深层次的原因是这个数组的前面部分其实已经排序好了, 不需要再排序下去了
 * 优点:
 * 不需要递归, 能减少栈空间的占用
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class ShellObjectSort {

    public static void sort(Comparable[] a, boolean asc) {
        int len = a.length;
        int h = 1;
        while (h < len / 3) {
            // 增量序列
            h = 3 * h + 1;
        }
        if (asc) {
            sortAsc(a, h);
        } else {
            sortDesc(a, h);
        }
    }

    private static void sortAsc(Comparable[] a, int h) {
        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    swap(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    private static void sortDesc(Comparable[] a, int h) {

        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && greater(a[j], a[j - h]); j -= h) {
                    swap(a, j, j - h);

                }
            }
            h = h / 3;
        }
    }


    public static void main(String[] args) {
        Element[] elements = generateObj(8, 10, 10);
        System.out.println(Arrays.toString(elements));

        sort(elements, true);
        checkSorted(elements, true);
        System.out.println(Arrays.toString(elements));
    }

}
