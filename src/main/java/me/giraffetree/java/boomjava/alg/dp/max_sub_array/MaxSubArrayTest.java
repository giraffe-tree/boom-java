package me.giraffetree.java.boomjava.alg.dp.max_sub_array;

/**
 * @author GiraffeTree
 * @date 2021-01-03
 */
public class MaxSubArrayTest {

    public static void main(String[] args) {
        int[] nums = {1, 2, -3, 4, 5, 6, -5, -6, -9, 10};
        int max = maxSubArray(nums);
        System.out.println(max);
    }

    public static int maxSubArray(int[] nums) {
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
