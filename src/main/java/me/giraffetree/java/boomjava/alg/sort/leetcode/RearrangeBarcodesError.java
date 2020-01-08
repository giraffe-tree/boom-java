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
public class RearrangeBarcodesError {

    public static void main(String[] args) {
        int[] a = {1, 9, 3, 6, 5};
        sort(a);
        System.out.println(Arrays.toString(a));
        int[] result = rearrangeBarcodes(a);
        System.out.println(Arrays.toString(result));

    }

    /**
     * 这道题跟之前做过的一道题类似:
     * 摆动排序 https://leetcode-cn.com/problems/wiggle-sort/
     * 重构字符串 https://leetcode-cn.com/problems/reorganize-string/
     * !!!!!!!!!!!错误思路:
     * 1. 排序
     * 2. 先排满奇数位, 再放在偶数位上
     * <p>
     * 实际上两道题目不一样
     * 这道题目要求临近的数字不能相等
     */
    public static int[] rearrangeBarcodes(int[] barcodes) {
        sort(barcodes);
        int k = 0;

        // 由于题目中说: 此题保证存在答案。
        // 所以我这里不再判断 最多的元素 是否小于或者等于 (barcodes.length+1)/2
        int[] aux = new int[barcodes.length];
        int mid = barcodes.length / 2;
        for (int i = 0; i < barcodes.length; i++) {
            int index = (i % 2 == 1) ? (k / 2) + mid : k++;
            aux[i] = barcodes[index];
        }

        return aux;
    }

    /**
     * 这里我使用不占用额外空间, 简单实用的 shell sort
     */
    private static void sort(int[] a) {

        int h = 1;
        while (h < a.length / 3) {
            h = h * 3 + 1;
        }

        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && a[j] > a[j - h]; j--) {
                    swap(a, j, j - h);
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
