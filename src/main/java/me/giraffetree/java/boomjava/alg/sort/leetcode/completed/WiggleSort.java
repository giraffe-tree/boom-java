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
public class WiggleSort {

    public static void main(String[] args) {
        int[] a = new int[]{1, 3, 2, 4, 5};
        wiggleSort(a);
        System.out.println(Arrays.toString(a));
    }

    /**
     * 思路:
     * 1. 排序
     * 2. 第0位和第1位交换, 第2位和第3位交换....
     */
    public static void wiggleSort(int[] nums) {


        sort(nums, 0, nums.length - 1);
        if (nums.length <= 2) {
            return;
        }
        for (int i = 2; i < nums.length; i += 2) {
            swap(nums, i, i - 1);
        }
    }

    /**
     * 题目要求原地排序, 我这里使用 快速排序
     */
    private static void sort(int[] a, int lo, int hi) {

        if (lo >= hi) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(int[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        int v = a[lo];
        while (true) {
            while (less(a[++i], v)) {
                if (i >= hi) {
                    break;
                }
            }
            while (less(v, a[--j])) {
                if (j <= lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            swap(a, i, j);

        }

        swap(a, lo, j);
        return j;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean less(int a, int b) {
        return a - b < 0;
    }

}
