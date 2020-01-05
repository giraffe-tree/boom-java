package me.giraffetree.java.boomjava.alg.sort.heap;

/**
 * @author GiraffeTree
 * @date 2020-01-05
 */
public class HeapObjectSort2 {

    public static void sort(Comparable[] a) {
        int len = a.length;
        // 1. 最大堆
        for (int i = len / 2; i >= 1; i--) {
            sink(a, i, len);
        }
        // 2. 取出元素, 放到数组末尾
        while (len > 1) {
            swap(a, 1, len--);
            sink(a, 1, len);
        }
    }

    /**
     * @param a   原始数组
     * @param k   需要下沉的元素位置 注意: 以1 ~ N 来记,而不是 0 ~ N-1
     * @param max 原始数组可用的最大长度 N <= a.length
     */
    private static void sink(Comparable[] a, int k, int max) {
        while (2 * k < max) {
            int j = 2 * k;
            // 找出最大的元素
            // 目标: 将大的数放前面, 小的数放后面
            if (j < max && less(a, j, j + 1)) {
                j++;
            }
            if (!less(a, k, j)) {
                break;
            }
            swap(a, k, j);
            k = j;
        }
    }

    /**
     * 这里参数中的 i, j 都是平常所说的 1 ~ N, 而不是 数组中的 0 ~ N-1
     * 至于为什么要这么做, 原因很简单, 你写一遍就知道, 这样写省脑子, 方便!
     */
    private static boolean less(Comparable[] a, int i, int j) {
        return a[i - 1].compareTo(a[j - 1]) < 0;
    }

    /**
     * 这里参数中的 i, j 都是平常所说的 1 ~ N, 而不是 数组中的 0 ~ N-1
     */
    private static void swap(Comparable[] a, int i, int j) {
        Comparable tmp = a[j - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = tmp;
    }


}
