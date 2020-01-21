package me.giraffetree.java.boomjava.alg.sort.practice.second;

import java.util.Arrays;

/**
 * 10:31 ~ 10:50
 *
 * @author GiraffeTree
 * @date 2020/1/21
 */
public class MergeSort {

    public static void main(String[] args) {

        int[] a = {8, 9, 7, 0, 2};
        System.out.println(Arrays.toString(a));
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1);
        System.out.println(Arrays.toString(a));

    }

    private static void sort(int[] a, int[] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        // 防止溢出
        int mid = (lo + hi) >>> 1;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        int cur = lo;
        while (cur <= hi) {
            if (i > mid) {
                System.arraycopy(aux, j, a, cur, hi - j + 1);
                break;
            }
            if (j > hi) {
                System.arraycopy(aux, i, a, cur, mid - i + 1);
                break;
            }
            // 到这步, 至少还有两个元素未 merge, 故而不会出现溢出的情况
            if (aux[i] <= aux[j]) {
                a[cur++] = aux[i++];
                // continue;
            }
            // continue 不加时, 测试了 10000 次没有出现 cur 溢出的情况....

            if (aux[i] > aux[j]) {
                a[cur++] = aux[j++];
            }
        }
    }

}
