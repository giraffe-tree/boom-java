package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

import java.util.Arrays;

/**
 * 给你一个不同学生的分数列表，请按 学生的 id 顺序 返回每个学生 最高的五科 成绩的 平均分。
 * <p>
 * 对于每条 items[i] 记录， items[i][0] 为学生的 id，items[i][1] 为学生的分数。平均分请采用整数除法计算。
 * <p>
 *  
 * <p>
 * 示例：
 * <p>
 * 输入：[[1,91],[1,92],[2,93],[2,97],[1,60],[2,77],[1,65],[1,87],[1,100],[2,100],[2,76]]
 * 输出：[[1,87],[2,88]]
 * 解释：
 * id = 1 的学生平均分为 87。
 * id = 2 的学生平均分为 88.6。但由于整数除法的缘故，平均分会被转换为 88。
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= items.length <= 1000
 * items[i].length == 2
 * 学生的 ID 在 1 到 1000 之间
 * 学生的分数在 1 到 100 之间
 * 每个学生至少有五个分数
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/high-five
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/6
 */
public class HighFive {

    public static void main(String[] args) {
        int[][] items = {{1, 91}, {1, 92}, {2, 93}, {3, 1}, {2, 97}, {1, 60}, {2, 77}, {1, 65}, {1, 87}, {1, 100}, {2, 100}, {2, 76}};
        int[][] ints = highFive(items);
        for (int[] x : ints) {
            System.out.println(Arrays.toString(x));
        }
    }

    /**
     * 思路:
     * 1. 排序 关键在于实现 less 函数  我这里用了快速排序
     * 2. 遍历取前五个成绩取平均, 放入1000长度的数组中
     * 3. 截取有效数组
     */
    public static int[][] highFive(int[][] items) {
        // 1. 排序 关键在于实现 less 函数
        sort(items, 0, items.length - 1);

        // 2. 遍历取前五个成绩取平均, 放入1000长度的数组中
        int curId = items[0][0];
        int sum = 0;
        int[][] a = new int[1000][];
        int index = 0;
        int scoreCount = 0;
        for (int[] item : items) {
            int cur = item[0];
            if (curId != cur) {
                a[index++] = new int[]{curId, sum / scoreCount};
                curId = cur;
                sum = 0;
                scoreCount = 0;
            }
            if (scoreCount < 5) {
                sum += item[1];
                scoreCount++;
            }
        }
        a[index++] = new int[]{curId, sum / scoreCount};
        // 3. 截取有效数组
        int[][] result = new int[index][];
        System.arraycopy(a, 0, result, 0, index);
        return result;
    }

    private static void sort(int[][] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(int[][] a, int lo, int hi) {

        int i = lo;
        int j = hi + 1;
        int[] tmp = a[lo];
        while (true) {
            while (less(tmp, a[++i])) {
                if (i >= hi) {
                    break;
                }
            }
            while (less(a[--j], tmp)) {
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

    private static boolean less(int[] x, int[] y) {
        if (x[0] < y[0]) {
            return false;
        } else if (x[0] > y[0]) {
            return true;
        } else {
            return x[1] - y[1] < 0;
        }
    }


}
