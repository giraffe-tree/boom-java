package me.giraffetree.java.boomjava.alg.sort.merge;

import me.giraffetree.java.boomjava.alg.sort.Element;

import java.util.Arrays;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.*;

/**
 * Divide and Conquer 分而治之
 * Merge 归并: 所谓的归并就是讲两个有序的数组合并成一个更大的有序数组
 * 时间复杂度:   nlogn 最坏和最好都是 nlogn
 * <p>
 * 空间复杂度:   n
 * 本节方法是: 自顶向下的归并排序
 * 相当于从左到右依次归并
 * 所以比较适合链表类组织的数据
 * <p>
 * 时间复杂度 nlogn 是基于比较的排序算法的上限
 * 但从空间复杂度上来讲 merge sort 并不是最优的
 * <p>
 * 归并排序的比较次数是 1/2NlogN ~ NlogN 次
 * 归并排序最多需要访问数组6NlogN 次
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class MergeObjectSort {

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }


    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {

        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    public static void main(String[] args) {
        Element[] elements = generateObj();
        System.out.println(Arrays.toString(elements));
        sort(elements);
        System.out.println(Arrays.toString(elements));

    }

}
