package me.giraffetree.java.boomjava.alg.sort.leetcode;

import java.util.Arrays;

/**
 * 给定一位研究者论文被引用次数的数组（被引用次数是非负整数）。编写一个方法，计算出研究者的 h 指数。
 * <p>
 * h 指数的定义: “h 代表“高引用次数”（high citations），
 * 一名科研人员的 h 指数是指他（她）的 （N 篇论文中）至多有 h 篇论文分别被引用了至少 h 次。
 * （其余的 N - h 篇论文每篇被引用次数不多于 h 次。）”
 * <p>
 * 示例:
 * <p>
 * 输入: citations = [3,0,6,1,5]
 * 输出: 3
 * 解释: 给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
 *      由于研究者有 3 篇论文每篇至少被引用了 3 次，其余两篇论文每篇被引用不多于 3 次，所以她的 h 指数是 3。
 *  
 * <p>
 * 说明: 如果 h 有多种可能的值，h 指数是其中最大的那个。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/h-index
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/8
 */
public class HIndex {

    public static void main(String[] args) {
        int[] a = {1, 4, 3, 2, 5};
        int[] aux = new int[5];
        sort(a, aux, 0, a.length - 1);
        System.out.println(Arrays.toString(a));

        int[] b = {3, 0, 6, 1, 5, 4};

        System.out.println(hIndex(b));

    }

    /**
     * 思路:
     * 1. 从大到小排序  这里我用的是 归并排序
     * 2. 排序之后检查 h 指数
     */
    public static int hIndex(int[] citations) {
        if (citations.length == 0) {
            return 0;
        }
        int[] aux = new int[citations.length];
        sort(citations, aux, 0, citations.length - 1);
        int max = citations[0] > citations.length ? citations.length : citations[0];
        for (int i = max; i > 0; i--) {
            if (check(citations, i)) {
                return i;
            }
        }
        return 0;
    }

    private static boolean check(int[] citations, int h) {
        // 至多有 h 篇论文分别被引用了至少 h 次。
        return citations[h - 1] >= h;
    }

    /**
     * 这里使用 merge sort 练练手
     */
    private static void sort(int[] a, int[] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        int k = lo;
        while (k <= hi) {
            if (i > mid) {
                a[k++] = aux[j++];
            } else if (j > hi) {
                a[k++] = aux[i++];
            } else if (aux[i] > aux[j]) {
                // 从大到小排序
                a[k++] = aux[i++];
            } else {
                a[k++] = aux[j++];
            }
        }
    }


}
