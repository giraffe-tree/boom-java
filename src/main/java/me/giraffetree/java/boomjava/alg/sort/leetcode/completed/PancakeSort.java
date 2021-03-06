package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定数组 A，我们可以对其进行煎饼翻转：我们选择一些正整数 k <= A.length，然后反转 A 的前 k 个元素的顺序。
 * 我们要执行零次或多次煎饼翻转（按顺序一次接一次地进行）以完成对数组 A 的排序。
 * <p>
 * 返回能使 A 排序的煎饼翻转操作所对应的 k 值序列。任何将数组排序且翻转次数在 10 * A.length 范围内的有效答案都将被判断为正确。
 * <p>
 * 示例 1：
 * <p>
 * 输入：[3,2,4,1]
 * 输出：[4,2,4,3]
 * 解释：
 * 我们执行 4 次煎饼翻转，k 值分别为 4，2，4，和 3。
 * 初始状态 A = [3, 2, 4, 1]
 * 第一次翻转后 (k=4): A = [1, 4, 2, 3]
 * 第二次翻转后 (k=2): A = [4, 1, 2, 3]
 * 第三次翻转后 (k=4): A = [3, 2, 1, 4]
 * 第四次翻转后 (k=3): A = [1, 2, 3, 4]，此时已完成排序。
 * 示例 2：
 * <p>
 * 输入：[1,2,3]
 * 输出：[]
 * 解释：
 * 输入已经排序，因此不需要翻转任何内容。
 * 请注意，其他可能的答案，如[3，3]，也将被接受。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= A.length <= 100
 * A[i] 是 [1, 2, ..., A.length] 的排列
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/pancake-sorting
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/7
 */
public class PancakeSort {

    public static void main(String[] args) {

        int[] a = {3, 2, 4, 1};
        List<Integer> steps = pancakeSort(a);
        steps.forEach(System.out::println);
        System.out.println(Arrays.toString(a));

    }

    /**
     * 第一次提交 -> 超时
     * 看了下其他题解, 思路都是一样...为啥我的超时了, 没弄明白
     * 后来看到 max==k 上有个bug , 要 k--
     * <p>
     * 原理:
     * 选择排序
     * 假设 c 最大, 为了排序, 我想把 c 放到最后
     * c > d > b > a
     * 第一步: a b c d -2>  c b a d -4>  d a b c
     * 第二步: d a b c -2>  b a d c
     * 第三步: b a d c -1>  a b d c
     * 思路:
     * 选择排序
     */
    public static List<Integer> pancakeSort(int[] a) {
        ArrayList<Integer> list = new ArrayList<>(a.length << 1);

        int k = a.length - 1;

        while (k > 0) {
            int max = k;

            for (int i = 0; i < k; i++) {
                if (a[i] > a[max]) {
                    max = i;
                }
            }
            if (max == k) {
                k--;
                continue;
            }

            if (max > 0) {
                list.add(max + 1);
                flip(a, max + 1);
            }
            list.add(k + 1);
            flip(a, k + 1);
            k--;
        }

        return list;
    }

    /**
     * 翻转
     */
    private static void flip(int[] a, int len) {
        for (int i = 0; i < len / 2; i++) {
            int tmp = a[len - 1 - i];
            a[len - 1 - i] = a[i];
            a[i] = tmp;
        }
    }

}
