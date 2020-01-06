package me.giraffetree.java.boomjava.alg.sort.merge;

import me.giraffetree.java.boomjava.alg.sort.Element;

import java.util.Arrays;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.generateObj;

/**
 * 自底向上的归并排序
 *
 * @author GiraffeTree
 * @date 2020-01-04
 */
public class MergeObjectSort2 {

    public static void sort(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                MergeObjectSort.merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }
        }
    }

    public static void main(String[] args) {

        Element[] elements = generateObj();
        System.out.println(Arrays.toString(elements));
        sort(elements);
        System.out.println(Arrays.toString(elements));

    }


}
