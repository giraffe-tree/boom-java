package me.giraffetree.java.boomjava.alg.sort.practice.second;

import java.util.Arrays;

/**
 * @author GiraffeTree
 * @date 2020/1/20
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] a = {1, 3, 0, 9, 4, 5, 7, 6, 8};
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    public static void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                }
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

}
