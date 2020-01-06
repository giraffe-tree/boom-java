package me.giraffetree.java.boomjava.alg.sort.leetcode;

import java.util.Arrays;

/**
 * 给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 [[s1,e1],[s2,e2],...] (si < ei)，请你判断一个人是否能够参加这里面的全部会议。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [[0,30],[5,10],[15,20]]
 * 输出: false
 * 示例 2:
 * <p>
 * 输入: [[7,10],[2,4]]
 * 输出: true
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/meeting-rooms
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/6
 */
public class CanAttendMeetings {

    /**
     * 思路:
     * 排序, 然后遍历
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals.length <= 1) {
            return true;
        }
        // 1. 排序
        int[][] aux = new int[intervals.length][];
        sort(intervals, aux, 0, intervals.length - 1);

        // 2. 遍历, 找出前一项的结束时间是否小于下一项的开始时间
        int[] latest = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] - latest[1] < 0) {
                return false;
            }
            latest = intervals[i];
        }

        return true;
    }

    /**
     * 这里我尝试使用归并排序
     */
    private static void sort(int[][] a, int[][] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) >> 1;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }


    private static void merge(int[][] a, int[][] aux, int lo, int mid, int hi) {
//        for (int i = lo; i <= hi; i++) {
//            aux[i] = a[i];
//        }
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int j = lo;
        int k = mid + 1;
        for (int i = lo; i <= hi; i++) {
            if (j > mid) {
                a[i] = aux[k++];
            } else if (k > hi) {
                a[i] = aux[j++];
            } else if (aux[k][0] > aux[j][0]) {
                a[i] = aux[j++];
            } else {
                a[i] = aux[k++];
            }
        }

    }


}
