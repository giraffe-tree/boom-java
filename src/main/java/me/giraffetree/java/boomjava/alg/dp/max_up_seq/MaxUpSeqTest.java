package me.giraffetree.java.boomjava.alg.dp.max_up_seq;

import org.assertj.core.api.Assertions;

/**
 * @author GiraffeTree
 * @date 2021-01-04
 */
public class MaxUpSeqTest {

    public static void main(String[] args) {

        int[] nums = {10, 9, 2, 5, 3, 7, 66, 18};
        int len = maxUpSeqLen(nums);
        Assertions.assertThat(len).isEqualTo(4);

    }

    private static int maxUpSeqLen(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = 1;

        for (int i = 1; i < nums.length; i++) {

        }

        return 0;
    }

}
