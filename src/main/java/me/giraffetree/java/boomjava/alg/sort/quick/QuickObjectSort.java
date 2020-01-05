package me.giraffetree.java.boomjava.alg.sort.quick;

import me.giraffetree.java.boomjava.alg.sort.Element;

import java.util.Arrays;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.*;

/**
 * 快速排序
 * 平均情况: 只需要 2NlogN 次比较
 * 相比于归并排序, 快速排序虽然比较的次数比归并排序多, 但移动数据的次数更少, 所以一般来说更快
 * 缺点: 切分不平均时, 会导致及其抵消
 *
 * @author GiraffeTree
 * @date 2020-01-04
 */
public class QuickObjectSort {

    public static void main(String[] args) {
        Element[] elements = generateObj();
        System.out.println(Arrays.toString(elements));
        sort(elements);
        System.out.println(Arrays.toString(elements));

    }

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);

    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            swap(a, i, j);
        }
        swap(a, lo, j);
        return j;
    }


}
