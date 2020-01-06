package me.giraffetree.java.boomjava.alg.sort.quick;

import me.giraffetree.java.boomjava.alg.sort.Element;
import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.*;

/**
 * @author GiraffeTree
 * @date 2020/1/6
 */
public class Quick3way2 {

    public static void main(String[] args) {
        int[] elements = generate();
        System.out.println(Arrays.toString(elements));
        sort(elements);
        System.out.println(Arrays.toString(elements));

    }

    public static void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        int tmp = a[lo];
        while (i <= gt) {
            int compare = a[i] - tmp;
            if (compare > 0) {
                swap(a, i, gt--);
            } else if (compare < 0) {
                swap(a, lt++, i++);
            } else {
                // lt <= ? <i 相等
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }


}
