package me.giraffetree.java.boomjava.alg.sort.merge;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.*;

/**
 * Divide and Conquer 分而治之
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class MergeObjectSort {

    public static void sort(Comparable[] a, boolean asc) {
        mergeSort(a, asc, 0, a.length);
    }

    private static void mergeSort(Comparable[] a, boolean asc, int start, int end) {


    }


    private Comparable[] merge(Comparable[] b, Comparable[] c) {
        if (b == null || b.length == 0) {
            return c;
        }
        if (c == null || c.length == 0) {
            return b;
        }

        for (int i = 0; i < b.length; i++) {

        }
        return null;

    }

}
