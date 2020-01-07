package me.giraffetree.java.boomjava.alg.sort.leetcode;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 对链表进行插入排序。
 * 插入排序的动画演示如上。从第一个元素开始，该链表可以被认为已经部分排序（用黑色表示）。
 * 每次迭代时，从输入数据中移除一个元素（用红色表示），并原地将其插入到已排好序的链表中。
 * <p>
 * 插入排序算法：
 * <p>
 * 插入排序是迭代的，每次只移动一个元素，直到所有元素可以形成一个有序的输出列表。
 * 每次迭代中，插入排序只从输入数据中移除一个待排序的元素，找到它在序列中适当的位置，并将其插入。
 * 重复直到所有输入数据插入完为止。
 *  
 * 示例 1：
 * <p>
 * 输入: 4->2->1->3
 * 输出: 1->2->3->4
 * 示例 2：
 * <p>
 * 输入: -1->5->3->4->0
 * 输出: -1->0->3->4->5
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/insertion-sort-list
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/7
 */
public class InsertionSortList2 {

    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(4);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(2);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;

        insertionSortList(n1);
        ListNode cur = n1;
        do {
            System.out.println(cur.val);
            cur = cur.next;
        } while (cur != null);
    }

    /**
     * 思路:
     * 由于是单向链表, 所以向前移动元素十分困难
     * 这是我自己实现的插入排序
     * 使用 cur 作为已排序的指针
     * j 作为下一个将要排序的指针
     * 这里的实现主要问题如下
     * 1. 没有用哨兵, 导致实现复杂
     * 2. 没有直接直接过滤已经排好序的情况, 导致考虑的比较复杂
     */
    public static ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode cur = head;
        ListNode j = head.next;
        while (cur.next != null) {
            ListNode k = head;
            ListNode m = null;
            while (k != cur.next) {
                if (less(k, j)) {
                    if (k == cur) {
                        cur = cur.next;
                        break;
                    }
                    m = k;
                    k = k.next;
                } else {
                    // 如果比队列头的大, 则放在队列
                    if (m == null) {
                        cur.next = j.next;
                        j.next = head;
                        head = j;
                        break;
                    } else {
                        // 如果 m 不为空, 元素
                        cur.next = j.next;
                        j.next = m.next;
                        m.next = j;
                        break;
                    }
                }
            }

            j = cur.next;
        }

        return head;
    }

    private static boolean less(ListNode a, ListNode b) {
        return a.val - b.val < 0;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + (next != null ? next.val : null) +
                    '}';
        }
    }

}
