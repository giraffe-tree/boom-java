package me.giraffetree.java.boomjava.alg.sort.quick;

import me.giraffetree.java.boomjava.alg.sort.Element;

import java.util.Arrays;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.*;

/**
 * Dijkstra 的解法
 * 三向切分的快速排序
 * 比如: 使用基准 c
 * a...........lt[c].......[i]...........gt[c].......f
 * 小于               相等      未确定         大于
 * <p>
 * 这种方法, 在对于处理大量重复的元素时, 效率比标准的快速排序算法高得多
 *
 * @author GiraffeTree
 * @date 2020-01-04
 */
public class Quick3way {


    public static void main(String[] args) {
        Element[] elements = generateObj();
        System.out.println(Arrays.toString(elements));
        sort(elements);
        System.out.println(Arrays.toString(elements));

    }

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                swap(a, lt++, i++);
            } else if (cmp > 0) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }
}
