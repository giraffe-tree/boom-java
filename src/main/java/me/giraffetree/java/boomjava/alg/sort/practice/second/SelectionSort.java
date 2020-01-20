package me.giraffetree.java.boomjava.alg.sort.practice.second;

import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 17:41 ~ 17:44
 *
 * @author GiraffeTree
 * @date 2020/1/20
 */
public class SelectionSort {

    public static void main(String[] args) {
        int[] a = SortUtils.generate();
        sort(a);
        System.out.println(Arrays.toString(a));
    }

    private static void sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = 0;
            for (int j = i + 1; j < a.length; i++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            SortUtils.swap(a, min, i);
        }

    }

}
