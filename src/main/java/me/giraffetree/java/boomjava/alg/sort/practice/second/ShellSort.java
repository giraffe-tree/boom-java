package me.giraffetree.java.boomjava.alg.sort.practice.second;

import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 17:45 ~ 17:59
 *
 * @author GiraffeTree
 * @date 2020/1/20
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] a = SortUtils.generate(100, 1000);
        sort2(a);
        System.out.println(Arrays.toString(a));

    }

    /**
     * 错误示例!!!!!
     */
    @Deprecated
    private static void sort(int[] a) {
        int h = 1;
        while (a.length > h / 3) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < a.length; i += h) {
                for (int j = i; j > 0 && a[j] < a[j - h]; j--) {
                    SortUtils.swap(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    private static void sort2(int[] a) {
        int h = 1;
        while (a.length / 3 > h) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && a[j] < a[j - h]; j -= h) {
                    SortUtils.swap(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

}
