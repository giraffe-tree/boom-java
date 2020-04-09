package me.giraffetree.java.boomjava.alg.strings.sort;

import java.util.Arrays;

/**
 * 键索引计数法排序
 *
 * @author GiraffeTree
 * @date 2020-03-05
 */
public class KeyIndexCountingSort {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 1, 2, 4, 5, 4, 5, 4, 3, 3, 3, 3, 3};
        sort(a, 6);
        System.out.println(Arrays.toString(a));
    }

    /**
     * 问题:
     * 排序一定范围内的数字
     *
     * @param a   int 数组
     * @param max 最大整数(不包含), 表示 a 数组中的元素在 0 ~ max-1 之间
     */
    public static void sort(int[] a, int max) {
        int num = a.length;
        int[] aux = new int[num];
        int[] count = new int[max + 1];
        for (int i = 0; i < num; i++) {
            count[a[i] + 1]++;
        }
        for (int r = 0; r < max; r++) {
            count[r + 1] += count[r];
        }
        for (int i = 0; i < num; i++) {
            aux[count[a[i]]++] = a[i];
        }
        System.arraycopy(aux, 0, a, 0, num);
    }

}
