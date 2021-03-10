package me.giraffetree.java.boomjava.alg.dp.max_sub_array;

/**
 * @author GiraffeTree
 * @date 2021-01-04
 */
public class MaxProductSubArrayTest {

    public static void main(String[] args) {

        int[] x = {-2, 0, 4, 5, -6, -1};
        int max = maxSubArray(x);
        System.out.println(max);
    }

    private static int maxSubArray(int[] nums) {
        // max
        int[] dp1 = new int[nums.length];
        // min
        int[] dp2 = new int[nums.length];

        int max = nums[0];
        dp1[0] = nums[0];
        dp2[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            dp1[i] = Math.max(Math.max(dp1[i - 1] * nums[i], dp2[i - 1] * nums[i]), nums[i]);
            dp2[i] = Math.max(Math.max(dp1[i - 1] * nums[i], dp2[i - 1] * nums[i]), nums[i]);
            max = Math.max(dp1[i], max);
        }
        return max;
    }


}
