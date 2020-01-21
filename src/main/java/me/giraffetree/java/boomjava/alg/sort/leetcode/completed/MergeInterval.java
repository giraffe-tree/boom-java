package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

/**
 * 给出一个区间的集合，请合并所有重叠的区间。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * 示例 2:
 * <p>
 * 输入: [[1,4],[4,5]]
 * 输出: [[1,5]]
 * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-intervals
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/6
 */
public class MergeInterval {

    public int[][] merge(int[][] intervals) {

        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }
        sort(intervals);
        int k = 0;
        int start = intervals[0][0];
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            int curStart = intervals[i][0];
            int curEnd = intervals[i][1];
            if (curStart <= end) {
                if (end < curEnd) {
                    end = curEnd;
                }
            } else {
                // 处理
                intervals[k++] = new int[]{start, end};
                start = curStart;
                end = curEnd;
            }
        }
        intervals[k++] = new int[]{start, end};
        int[][] aux = new int[k][];
        System.arraycopy(intervals, 0, aux, 0, k);
        return aux;
    }

    /**
     * 这里我使用 shell sort 希尔排序
     */
    public static void sort(int[][] a) {

        int h = 1;
        while (a.length > h) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                // 从后向前, 插入排序
                for (int j = i; j >= h && less(a, j, j - h); j -= h) {
                    swap(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    private static boolean less(int[][] a, int i, int j) {
        return a[i][0] - a[j][0] < 0;
    }


    private static void swap(int[][] a, int i, int j) {
        int[] tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

}
