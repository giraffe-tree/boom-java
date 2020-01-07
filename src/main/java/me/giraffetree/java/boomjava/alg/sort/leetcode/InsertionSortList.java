package me.giraffetree.java.boomjava.alg.sort.leetcode;

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
public class InsertionSortList {

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
     * 要解决这个问题
     * 可以使用双向链表或者数组暂存 listNode 的顺序
     */
    public static ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ArrayList<ListNode> list = new ArrayList<>(16);
        list.add(head);
        ListNode cur = head;
        while (cur.next != null) {
            list.add(cur.next);
            cur = cur.next;
        }

        for (int i = 1; i < list.size(); i++) {
            for (int j = i; j > 0 && less(list.get(j), list.get(j - 1)); j--) {
                swap(list, j - 1, j);
            }
        }

        return list.get(0);
    }

    private static void swap(List<ListNode> list, int a, int b) {
        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        if (b - a > 1) {
            throw new RuntimeException("not supported swap");
        }
        ListNode aNode = list.get(a);
        ListNode bNode = list.get(b);
        if (a >= 1) {
            ListNode first = list.get(a - 1);
            first.next = bNode;
            aNode.next = bNode.next;
            bNode.next = aNode;
        } else {
            aNode.next = bNode.next;
            bNode.next = aNode;
        }
        list.set(a, bNode);
        list.set(b, aNode);
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
    }

}
