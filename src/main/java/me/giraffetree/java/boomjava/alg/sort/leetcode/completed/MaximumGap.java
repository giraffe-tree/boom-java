package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

import java.util.Arrays;

/**
 * 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
 * <p>
 * 如果数组元素个数小于 2，则返回 0。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,6,9,1]
 * 输出: 3
 * 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
 * 示例 2:
 * <p>
 * 输入: [10]
 * 输出: 0
 * 解释: 数组元素个数小于 2，因此返回 0。
 * 说明:
 * <p>
 * 你可以假设数组中所有元素都是非负整数，且数值在 32 位有符号整数范围内。
 * 请尝试在线性时间复杂度和空间复杂度的条件下解决此问题。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-gap
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020-01-06
 */
public class MaximumGap {

    public static void main(String[] args) {
        System.out.println(1 / 3);
        System.out.println((int) Math.ceil(1.1));
        System.out.println((int) Math.ceil(1.6));
        System.out.println((int) Math.ceil(1.5));
        int[] a;
        a = new int[]{6, 6, 6, 7, 6};
        System.out.println(maximumGap(a));

        a = new int[]{1, 1, 1, 1};
        System.out.println(maximumGap(a));

        a = new int[]{3, 6, 9, 1};
        System.out.println(maximumGap(a));

    }

    /**
     * 还有问题!!!!!!! 没有想明白!!!! bucket 的 size, range
     * <p>
     * 这题刚刚开始看的时候, 感觉非常奇怪, 既要求排序, 又要求线性复杂度 ???
     * 看了下题解才明白过来
     * 时间复杂度为 O(n) 的排序算法有一下三种
     * 1. 桶排序 bin sort
     * 2. 基数排序 radix  sort
     * 3. 计数排序 counting sort
     * <p>
     * 下面的算法我参考的是:
     * https://github.com/wind-liang/leetcode/blob/master/leetcode-164-Maximum-Gap.md
     * 以及 陈乐乐的题解
     * <p>
     * 1. 先循环遍历数组获得最大值maxn和最小值minn
     * 2. 根据最大值和最小值以及数组容量计算出每个桶的容量size=(maxn-minn)/nums.size()
     * 3. 根据容量计算出需要多少个这样的桶num=(maxn-minn)/size+1
     * 4. 最后将nums数组中的元素放在对应区间的桶中。
     * 5. 最大间距一定是由一个桶的最小值减去上一个桶的最大值(桶中有元素的情况下)
     * <p>
     * 作者：chenlele
     * 链接：https://leetcode-cn.com/problems/maximum-gap/solution/ctong-pai-xu-by-gpe3dbjds1/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public static int maximumGap(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        // 1. 获取最大/小值
        int max = nums[0];
        int min = max;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            } else if (nums[i] < min) {
                min = nums[i];
            }
        }
        // 关于桶的容量, 数量的计算, 要达到的目标只有一个: 至少有一个桶是空的
        // 其实要满足这个条件很容易, 取 nums.length + 1 个桶就可以, 每个数字对应桶的序号为 (int) Math.floor((double) (cur - min) / (max - min) * num)  这里
        // 但是为了节省空间, 我们需要一定量的计算
        // 2. 计算每个桶的容量 这里需要向下取整
        if (max - min == 0) {
            return 0;
        }

//        int size = (int) Math.floor((double) (max - min) / (nums.length - 1));
        int size = Math.max(1, (max - min) / (nums.length - 1));

        // 3. 桶的数量  桶的数量越多, 占用空间越多
        // 桶之间的最小间距是 1
        int num = (max - min) / size + 1;

//        int num;
//        if (size == 0) {
//            num = max - min + 2;
//        } else {
//            num = Math.min(max - min + 2, (max - min) / size + 2);
//        }

        // 4. 将 nums 中的数据放入桶中 鸽笼原理, 至少有一个桶是空的?
        int[] binMin = new int[num];
        Arrays.fill(binMin, 0, binMin.length, Integer.MAX_VALUE);

        int[] binMax = new int[num];
        Arrays.fill(binMax, 0, binMin.length, -1);

        for (int i = 0; i < nums.length; i++) {
            int cur = nums[i];
            int binIndex = (int) Math.floor((double) (cur - min) / (max - min) * (num - 1));
            if (binMax[binIndex] < cur) {
                binMax[binIndex] = cur;
            }
            if (binMin[binIndex] > cur) {
                binMin[binIndex] = cur;
            }
        }
        // 5. 最大间距一定是由一个桶的最小值减去上一个桶的最大值(桶中有元素的情况下)
        int maxGap = 0;
        int latestMax = -1;
        int k = 0;
        while (latestMax < 0) {
            latestMax = binMax[k++];
        }

        for (int i = k; i < num; i++) {
            if (binMin[i] == Integer.MAX_VALUE) {
                continue;
            }
            int cur = binMin[i] - latestMax;
            if (cur > maxGap) {
                maxGap = cur;
            }
            latestMax = binMax[i];
        }
        return maxGap;
    }

}
