package me.giraffetree.java.boomjava.alg.sort.insertion;

import me.giraffetree.java.boomjava.alg.sort.Element;
import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 插入排序
 * 由于插入排序和冒泡排序一样, 只进行相邻元素交换, 故而也是稳定排序
 * 时间复杂度 n^2
 * 虽然插入排序和冒泡排序的时间复杂度一样, 但是两者还是有一些执行效率上的差距
 * 一般来讲插入排序比冒泡排序快一点
 * <p>
 * <p>
 * 对于小规模数据和基本有序数据效率比较高
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class InsertionObjectSort {

    public static void sort(Comparable[] a, boolean asc) {
        if (asc) {
            sortAsc(a);
        } else {
            sortDesc(a);
        }

    }

     private static void sortAsc(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && SortUtils.less(a[j], a[j - 1]); j--) {
                SortUtils.swap(a, j, j - 1);
            }
        }
    }

    private static void sortDesc(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && SortUtils.greater(a[j], a[j - 1]); j--) {
                SortUtils.swap(a, j, j - 1);
            }
        }
    }

    public static void main(String[] args) {
        Element[] elements = SortUtils.generateObj();
        System.out.println(Arrays.toString(elements));

        sort(elements, true);
        SortUtils.checkSorted(elements, true);
        System.out.println(Arrays.toString(elements));

    }
}
