package me.giraffetree.java.boomjava.alg.sort.practice.second;

import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 17:31 ~ 17:39
 * 刚开始没想出来, 看了 https://visualgo.net/en/sorting 才想起来
 *
 * @author GiraffeTree
 * @date 2020/1/20
 */
public class InsertSort {

    public static void main(String[] args) {
        int[] a = SortUtils.generate();
        sort(a);
        System.out.println(Arrays.toString(a));

    }

    private static void sort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (a[j] < a[j - 1]) {
                    SortUtils.swap(a, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }


}
