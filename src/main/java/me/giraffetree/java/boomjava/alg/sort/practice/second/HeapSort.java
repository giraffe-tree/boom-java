package me.giraffetree.java.boomjava.alg.sort.practice.second;

import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 11:21 ~
 * 已经忘记了, 看书抄一遍
 * 两个过程
 * 1. 构造, 从中间开始 sink, 直到堆顶
 * 2. 下沉排序, 每次取出堆顶元素 和 数组的最后一个元素交换, 然后下沉, 重复
 *
 * @author GiraffeTree
 * @date 2020/1/21
 */
public class HeapSort {

    public static void main(String[] args) {

        int[] a = SortUtils.generate();
        System.out.println(Arrays.toString(a));
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        int n = a.length;
        for (int k = n / 2; k >= 1; k--) {
            sink(a, k, n);
        }
        while (n > 1) {
            exch(a, 1, n--);
            sink(a, 1, n);
        }
    }

    /**
     * 下沉
     */
    private static void sink(int[] a, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(a, j, j + 1)) {
                j++;
            }
            if (!less(a, k, j)) {
                break;
            }
            exch(a, k, j);
            System.out.println(Arrays.toString(a));
            k = j;
        }
    }

    private static boolean less(int[] a, int i, int j) {
        return a[i - 1] < a[j - 1];
    }

    private static void exch(int[] pq, int i, int j) {
        int swap = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = swap;
    }


}
