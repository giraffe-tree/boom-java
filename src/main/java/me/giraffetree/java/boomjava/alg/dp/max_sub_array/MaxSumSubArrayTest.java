package me.giraffetree.java.boomjava.alg.dp.max_sub_array;

/**
 * 如果给你一个包含正数和负数的整数数组，你能否找到一个具有最大和的连续子数组，其中子数组最少包含一个元素，返回其最大和。
 * 比如输入的子数组是 [-2, 1, -3, 1, -1, 6, 2, -5, 4]，输出是 8，
 * 因为连续子数组 [1, -1, 6, 2] 的和最大。请你思考一下，并使用动态规划的方法来求解此问题。
 *
 *
 * @author GiraffeTree
 * @date 2021-01-03
 */
public class MaxSumSubArrayTest {

    public static void main(String[] args) {
        int[] nums = { 4, -3, 6, -5, -6, -9, 6};
        int max = maxSubArray(nums);
        System.out.println(max);
    }

    private static int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        int maxSum = nums[0];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            maxSum = Math.max(maxSum, dp[i]);
        }
        return maxSum;
    }

}
