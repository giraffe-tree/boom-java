package me.giraffetree.java.boomjava.alg.sort.leetcode;

import java.util.Arrays;

/**
 * 给定由一些正数（代表长度）组成的数组 A，返回由其中三个长度组成的、面积不为零的三角形的最大周长。
 * <p>
 * 如果不能形成任何面积不为零的三角形，返回 0。
 * <p>
 * 示例 1：
 * <p>
 * 输入：[2,1,2]
 * 输出：5
 * 示例 2：
 * <p>
 * 输入：[1,2,1]
 * 输出：0
 * 示例 3：
 * <p>
 * 输入：[3,2,3,4]
 * 输出：10
 * 示例 4：
 * <p>
 * 输入：[3,6,2,3]
 * 输出：8
 *  
 * <p>
 * 提示：
 * <p>
 * 3 <= A.length <= 10000
 * 1 <= A[i] <= 10^6
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/largest-perimeter-triangle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/6
 */
public class LargestPerimeter {

    public static void main(String[] args) {
        int[] a = {2, 1, 2, 5, 6, 7, 8, 17};
        int i = largestPerimeter(a);
        System.out.println(Arrays.toString(a));
        System.out.println(i);
    }

    /**
     * 思路:
     * 这道题的关键还是在于排序
     * 组成三角形的条件很简单, 两条小边长度之和大于第三边
     * 我们举个例子中, 现在有一个从大到小排序好的数组:(这个例子中的数可能相等)
     * a b c d e
     * 可以得知: a >= b >= c >= d >= e
     * 可以从左往后遍历
     * 先看 a b c, 要使得三角形成立, 则 a < b+c , 如果这个条件成立则 最大周长肯定是 a + b + c
     * 如果不成立呢? 会不会是 a b d  或者  a c d 呢?
     * 不可能!!
     * 因为 a >= b+c , 且 b >= c >= d 则 a >= b+d 或  a>= c+d , 则三角形不能成立
     * 由上面推论, 对一个从大到小排序好的数组, 只需要检查相邻的三项即可, 第一个匹配的就可以形成最大周长的三角形
     * 步骤:
     * 1. 排序
     * 2. 遍历
     */
    public static int largestPerimeter(int[] a) {
        if (a.length < 3) {
            return 0;
        }

        // 1. 排序
        sort(a, 0, a.length - 1);
        // 2. 遍历
        for (int i = 0; i < a.length - 2; i++) {
            if (a[i] < a[i + 1] + a[i + 2]) {
                return a[i] + a[i + 1] + a[i + 2];
            }
        }
        return 0;
    }

    /**
     * 这里使用 三向切分快速排序
     * .......gt....i....lt.......
     */
    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int i = lo + 1;
        int lt = hi;
        int gt = lo;
        // 这里要缓存a[lo] 的值, 不然在之后的交换可能会改变, 就获取不到了
        // 以 a[lo] 为 基准进行比较
        int tmp = a[lo];

        while (i <= lt) {

            int compare = a[i] - tmp;
            if (compare > 0) {
                swap(a, i++, gt++);
            } else if (compare < 0) {
                swap(a, i, lt--);
            } else {
                i++;
            }
        }

        sort(a, lo, gt - 1);
        sort(a, lt + 1, hi);
    }

    private static void swap(int[] a, int x, int y) {
        int tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
    }

}
