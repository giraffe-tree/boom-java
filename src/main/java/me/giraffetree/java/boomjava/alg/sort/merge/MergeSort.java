package me.giraffetree.java.boomjava.alg.sort.merge;

import java.util.Arrays;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.generate;

/**
 * @author GiraffeTree
 * @date 2020-01-03
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        int[] b = {0, 0, 0, 0, 0, 0};
        System.arraycopy(a, 2, b, 1, 2);
        System.out.println(Arrays.toString(b));

        int[] generate = generate();
        System.out.println(Arrays.toString(generate));
        sort(generate);
        System.out.println(Arrays.toString(generate));

    }

    public static void sort(int[] a) {
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi) {
        // 自己写的时候缺了个终止条件, 别忘了
        if (hi <= lo) {
            return;
        }
        // 防止溢出
        int mid = lo + (hi - lo) / 2;

        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        int cur = lo;
        while (cur <= hi) {
            if (i > mid) {
                aux[cur++] = a[j];
                j++;
            } else if (j > hi) {
                aux[cur++] = a[i];
                i++;
            } else if (a[i] >= a[j]) {
                aux[cur++] = a[j];
                j++;
            } else if (a[i] < a[j]) {
                aux[cur++] = a[i];
                i++;
            }
        }
        System.arraycopy(aux, lo, a, lo, hi - lo + 1);
    }


}
