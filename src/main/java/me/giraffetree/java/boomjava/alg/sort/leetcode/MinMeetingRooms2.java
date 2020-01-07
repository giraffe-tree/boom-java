package me.giraffetree.java.boomjava.alg.sort.leetcode;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * 给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 [[s1,e1],[s2,e2],...] (si < ei)，为避免会议冲突，同时要考虑充分利用会议室资源，请你计算至少需要多少间会议室，才能满足这些会议安排。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [[0, 30],[5, 10],[15, 20]]
 * 输出: 2
 * 示例 2:
 * <p>
 * 输入: [[7,10],[2,4]]
 * 输出: 1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/meeting-rooms-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/7
 */
public class MinMeetingRooms2 {

    public static void main(String[] args) {
        int[][] a;
//        a = new int[][]{{0, 30}, {5, 10}, {15, 20}, {1, 3}, {2, 4}, {3, 5}};
//        a = new int[][]{{1, 5}, {8, 9}, {8, 9}};
        // [[26,29],[19,26],[19,28],[4,19],[4,25]]
        a = new int[][]{{26, 29}, {19, 26}, {19, 28}, {4, 19}, {4, 25}};
        int maxCount = minMeetingRooms(a);
        System.out.println(maxCount);


    }

    /**
     * 这里使用 希尔排序+优先队列实现
     */
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals.length;
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>();

        sort(intervals);
        for (int[] a : intervals) {
            int curStart = a[0];
            int curEnd = a[1];
            Integer peek = queue.peek();
            if (peek == null || peek > curStart) {
                queue.add(curEnd);
            } else {
                queue.poll();
                queue.add(curEnd);
            }
        }

        return queue.size();
    }

    /**
     * 这里我使用 希尔排序
     */
    private static void sort(int[][] intervals) {
        int h = 1;
        while (h < intervals.length / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < intervals.length; i++) {
                for (int j = i; j >= h && less(intervals, j, j - h); j -= h) {
                    swap(intervals, j, j - h);
                }
            }
            h /= 3;
        }
    }

    private static boolean less(int[][] a, int i, int j) {
        return a[i][0] < a[j][0];
    }

    private static void swap(int[][] a, int i, int j) {
        int[] tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

}
