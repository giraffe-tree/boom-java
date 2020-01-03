package me.giraffetree.java.boomjava.alg.sort.bubble;

import me.giraffetree.java.boomjava.alg.sort.Element;
import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class BubbleObjectSort {

    public static void sort(Comparable[] a, boolean asc) {
        for (int i = a.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (asc) {
                    // 从小到大
                    if (a[j].compareTo(a[j + 1]) > 0) {
                        SortUtils.swap(a, j, j + 1);
                    }
                } else {
                    if (a[j].compareTo(a[j + 1]) < 0) {
                        SortUtils.swap(a, j, j + 1);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {

        Element[] elements = SortUtils.generateObj();
        System.out.println(Arrays.toString(elements));

        sort(elements, false);
        SortUtils.checkSorted(elements,false);
        System.out.println(Arrays.toString(elements));

    }

}
