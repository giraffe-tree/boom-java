package me.giraffetree.java.boomjava.alg.sort.practice.second;

import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 09:25 ~ 09:57
 * 想了蛮久的
 * 大概能记起来怎么做, 但是具体细节都忘光了
 * 还可以,最后写出来了, 最重要的是 partition 函数
 *
 * @author GiraffeTree
 * @date 2020/1/21
 */
public class QuickSort {

    public static void main(String[] args) {
        int[] a = SortUtils.generate();
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    private static void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        int partition = partition(a, lo, hi);
        sort(a, lo, partition - 1);
        sort(a, partition + 1, hi);
    }

    /**
     * 范围: [lo,hi]
     */
    private static int partition(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return hi;
        }
        int tmp = a[lo];
        int j = lo + 1;
        int k = hi;
        while (j < k) {
            while (tmp > a[j]) {
                j++;
            }
            while (tmp < a[k]) {
                k--;
            }
            SortUtils.swap(a, lo, hi);
        }
        SortUtils.swap(a, lo, hi);
        return hi;
    }

}
