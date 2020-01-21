package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

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
public class InsertionSortList3 {

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
     * 来自题解区参考:  java实现 通俗易懂
     * 稍微改了下指针交换, 这里的实现比较清晰
     */
    public static ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        // 1.遍历并与前面已经有序的序列向前逐一比较排序，找到合适为止插入

        // 定义三个指针 pre, cur, lat
        //pre    cur    lat
        // h  ->  4  ->  2  ->  5  ->  3  ->  null

        // 创建哨兵
        ListNode h = new ListNode(0);
        h.next = head;
        ListNode pre = h;
        ListNode cur = head;
        ListNode lat;

        while (cur != null) {
            lat = cur.next;
            // 只有 cur.next 比 cur 小才需要向前寻找插入点
            if (lat != null && lat.val < cur.val) {
                while (pre.next != null && pre.next.val < lat.val) {
                    pre = pre.next;
                }
                cur.next = lat.next;
                lat.next = pre.next;
                pre.next = lat;
                // 复位
                pre = h;
            } else {
                cur = lat;
            }
        }

        return h.next;
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
