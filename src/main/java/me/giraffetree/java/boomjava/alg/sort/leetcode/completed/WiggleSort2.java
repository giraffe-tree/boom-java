package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

import java.util.Arrays;

/**
 * 给你一个无序的数组 nums, 将该数字 原地 重排后使得 nums[0] <= nums[1] >= nums[2] <= nums[3]...。
 * <p>
 * 示例:
 * <p>
 * 输入: nums = [3,5,2,1,6,4]
 * 输出: 一个可能的解答是 [3,5,1,6,2,4]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/wiggle-sort
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/7
 */
public class WiggleSort2 {

    public static void main(String[] args) {
        int[] a = new int[]{2, 3, 2, 3, 1, 1, 1};
        wiggleSort(a);
        System.out.println(Arrays.toString(a));
    }

    /**
     * 思路:
     * 这里的思路是由题解中总结而来
     * 需要输出的是 奇数位数字 比周围两个数字都大的数组
     * 实际上只要遍历一遍, 将奇数位周围的三个数排序, 最大的放中间就可以了
     */
    public static void wiggleSort(int[] nums) {

        for (int i = 0; i < nums.length - 1; i++) {
            if ((i % 2 == 0) == (nums[i] > nums[i + 1])) {
                swap(nums, i, i + 1);
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;

    }
}
