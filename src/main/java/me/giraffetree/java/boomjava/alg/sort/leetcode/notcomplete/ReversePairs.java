package me.giraffetree.java.boomjava.alg.sort.leetcode.notcomplete;

/**
 * 给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
 * <p>
 * 你需要返回给定数组中的重要翻转对的数量。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,3,2,3,1]
 * 输出: 2
 * 示例 2:
 * <p>
 * 输入: [2,4,3,5,1]
 * 输出: 3
 * 注意:
 * <p>
 * 给定数组的长度不会超过50000。
 * 输入数组中的所有数字都在32位整数的表示范围内。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-pairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/10
 */
public class ReversePairs {

    /**
     * 思路
     */
    public int reversePairs(int[] nums) {

        return 0;
    }

    /**
     * 思路:
     * 目前想到的方法就是 双指针遍历
     * 超出时间限制
     */
    public int reversePairs2(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (check(nums, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean check(int[] nums, int i, int j) {
        return (long) nums[i] > 2 * (long) nums[j];
    }

}
