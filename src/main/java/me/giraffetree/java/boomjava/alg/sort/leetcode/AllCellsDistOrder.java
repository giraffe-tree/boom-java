package me.giraffetree.java.boomjava.alg.sort.leetcode;

import java.util.Arrays;

/**
 * 给出 R 行 C 列的矩阵，其中的单元格的整数坐标为 (r, c)，满足 0 <= r < R 且 0 <= c < C。
 * <p>
 * 另外，我们在该矩阵中给出了一个坐标为 (r0, c0) 的单元格。
 * <p>
 * 返回矩阵中的所有单元格的坐标，并按到 (r0, c0) 的距离从最小到最大的顺序排，其中，两单元格(r1, c1) 和 (r2, c2) 之间的距离是曼哈顿距离，|r1 - r2| + |c1 - c2|。（你可以按任何满足此条件的顺序返回答案。）
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：R = 1, C = 2, r0 = 0, c0 = 0
 * 输出：[[0,0],[0,1]]
 * 解释：从 (r0, c0) 到其他单元格的距离为：[0,1]
 * 示例 2：
 * <p>
 * 输入：R = 2, C = 2, r0 = 0, c0 = 1
 * 输出：[[0,1],[0,0],[1,1],[1,0]]
 * 解释：从 (r0, c0) 到其他单元格的距离为：[0,1,1,2]
 * [[0,1],[1,1],[0,0],[1,0]] 也会被视作正确答案。
 * 示例 3：
 * <p>
 * 输入：R = 2, C = 3, r0 = 1, c0 = 2
 * 输出：[[1,2],[0,2],[1,1],[0,1],[1,0],[0,0]]
 * 解释：从 (r0, c0) 到其他单元格的距离为：[0,1,1,2,2,3]
 * 其他满足题目要求的答案也会被视为正确，例如 [[1,2],[1,1],[0,2],[1,0],[0,1],[0,0]]。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= R <= 100
 * 1 <= C <= 100
 * 0 <= r0 < R
 * 0 <= c0 < C
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/matrix-cells-in-distance-order
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020-01-05
 */
public class AllCellsDistOrder {

    public static void main(String[] args) {
        int[][] ints = allCellsDistOrder(2, 9, 0, 1);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(Arrays.toString(ints[i]));
        }

    }

    /**
     * 先开始没想到什么好的思路
     * 后面做了几个俯卧撑, 然后看看题解, 什么稀奇古怪的实现...
     * 然后突然想到这个问题其实用任意的排序都可以做
     * 主要原理在于要实现一个 自定义的 less 函数
     * 步骤如下:
     * 1. 构建二维数组
     * 2. 排序 我这里使用快速排序
     */
    public static int[][] allCellsDistOrder(int R, int C, int r0, int c0) {

        int[][] a = new int[R * C][2];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                a[i * C + j] = new int[]{i, j};
            }
        }
        sort(a, 0, a.length - 1, r0, c0);

        return a;
    }

    private static void sort(int[][] a, int lo, int hi, int r0, int c0) {
        if (hi <= lo) {
            return;
        }

        int j = partition(a, lo, hi, r0, c0);
        sort(a, lo, j - 1, r0, c0);
        sort(a, j + 1, hi, r0, c0);

    }

    private static int partition(int[][] a, int lo, int hi, int r0, int c0) {

        int i = lo;
        int j = hi + 1;
        while (true) {
            while (less(a, ++i, lo, r0, c0)) {
                if (i >= hi) {
                    break;
                }
            }
            while (less(a, lo, --j, r0, c0)) {
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

    private static void swap(int[][] a, int i, int j) {
        int[] tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean less(int[][] a, int i, int j, int ro, int c0) {
        int[] arr1 = a[i];
        int[] arr2 = a[j];
        int len1 = Math.abs(arr1[0] - ro) + Math.abs(arr1[1] - c0);
        int len2 = Math.abs(arr2[0] - ro) + Math.abs(arr2[1] - c0);
        return len1 - len2 < 0;
    }

}
