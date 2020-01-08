package me.giraffetree.java.boomjava.alg.sort.leetcode;

import java.util.Arrays;

/**
 * 在一个仓库里，有一排条形码，其中第 i 个条形码为 barcodes[i]。
 * <p>
 * 请你重新排列这些条形码，使其中两个相邻的条形码 不能 相等。 你可以返回任何满足该要求的答案，此题保证存在答案。
 * <p>
 * 示例 1：
 * <p>
 * 输入：[1,1,1,2,2,2]
 * 输出：[2,1,2,1,2,1]
 * 示例 2：
 * <p>
 * 输入：[1,1,1,1,2,2,3,3]
 * 输出：[1,3,1,3,2,1,2,1]
 * <p>
 * 提示：
 * <p>
 * 1 <= barcodes.length <= 10000
 * 1 <= barcodes[i] <= 10000
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/distant-barcodes
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/8
 */
public class RearrangeBarcodes {

    public static void main(String[] args) {
        int[] a = {1, 1, 1, 1, 2, 2, 2, 2};
        System.out.println(Arrays.toString(a));
        int[] result = rearrangeBarcodes(a);
        System.out.println(Arrays.toString(result));

    }

    /**
     * 还是没有仔细读题的锅, 先开始还想用排序做....
     * 思路:
     * 题目中指定了 barcodes[i] 最大为 10000, 最小为 1
     * 那我们直接可以用桶排序做
     * 计算出每个数字的 count 数
     * 步骤
     * 1. 桶排序
     * 2. 使用 两个额外的数组 记录 count 和 位置信息
     * 3. 遍历 从小到大 排满奇数位后, 在排偶数位
     * <p>
     * 这题花费的时间有点长
     * 执行用时 :232 ms 在所有 Java 提交中击败了9.62%的用户
     * 内存消耗 :40.8 MB, 在所有 Java 提交中击败了100.00% 的用户
     * <p>
     * 原因分析如下:
     * 1. 不需要排序, 只要找到拥有最多元素的那个数字和它的count 值即可,
     * 然后只要分桶排, 先奇数位后偶数位, 相邻的数字就不会相等
     * <p>
     * 按照先奇后偶, 只有最多的元素才有可能重复, 且它的个数必须大于总数的一半(可能要+1)
     * 所以, 只要找到最大的元素就可以了
     */
    public static int[] rearrangeBarcodes(int[] barcodes) {
        // 使用 aux 来记录 count 的变化
        int[] aux = new int[10001];
        // 使用 aux2 来记录位置的变化
        int[] aux2 = new int[aux.length];
        for (int i = 0; i < barcodes.length; i++) {
            aux[barcodes[i]]++;
        }
        for (int i = 0; i < aux.length; i++) {
            aux2[i] = i;
        }
        sort(aux, aux2);

        int k = aux[0];
        int cur = 0;
        // 是否为奇数
        boolean odd = barcodes.length % 2 == 1;
        for (int i = 0; i < barcodes.length; i++) {
            int index;
            if ((i << 1) < barcodes.length || odd) {
                index = (i << 1) % barcodes.length;
            } else {
                // len 为偶数, 且 i * 2 >= barcodes.length 的情况
                index = ((i << 1) + 1) % (barcodes.length);
            }
            barcodes[index] = aux2[cur];
            k--;
            if (k == 0) {
                cur++;
                if (cur == aux2.length) {
                    return barcodes;
                }
                k = aux[cur];
                if (k == 0) {
                    return barcodes;
                }
            }

        }

        return barcodes;
    }

    /**
     * 这里我使用不占用额外空间, 简单实用的 shell sort
     */
    private static void sort(int[] a, int[] b) {

        int h = 1;
        while (h < a.length / 3) {
            h = h * 3 + 1;
        }

        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && a[j] > a[j - h]; j--) {
                    swap(a, j, j - h);
                    swap(b, j, j - h);
                }
            }
            h /= 3;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
