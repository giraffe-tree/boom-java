package me.giraffetree.java.boomjava.leetcode.jzoffer;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2021/3/12 11:01
 */
public class T22_2 {

    @Test
    public void test() {

        System.out.println("start...");
        ListNode randomListNode = getRandomListNode(5);
        ListNode kthFromEnd = getKthFromEnd(randomListNode, 3);
        Assert.assertEquals(kthFromEnd.val, 2);

        randomListNode = getRandomListNode(2);
        kthFromEnd = getKthFromEnd(randomListNode, 1);
        Assert.assertEquals(kthFromEnd.val, 1);

        randomListNode = getRandomListNode(2);
        kthFromEnd = getKthFromEnd(randomListNode, 2);
        Assert.assertEquals(kthFromEnd.val, 0);

        System.out.println("end...");
    }

    public ListNode getKthFromEnd(ListNode head, int k) {
        // 两个小人前后走
        ListNode cur = head;
        ListNode cur2 = null;

        int count = 0;

        while (cur != null) {
            count++;
            if (count == k) {
                cur2 = head;
            } else if (count > k) {
                cur2 = cur2.next;
            }
            cur = cur.next;
        }
        return cur2;
    }

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    private ListNode getRandomListNode(int len) {
        if (len <= 0) {
            throw new RuntimeException("len error");
        }
        ThreadLocalRandom current = ThreadLocalRandom.current();
//        ListNode first = new ListNode(current.nextInt(0, 1000));
        ListNode first = new ListNode(0);
        ListNode curNode = first;
        for (int i = 1; i < len; i++) {
            ListNode listNode = new ListNode(i);
//            ListNode listNode = new ListNode(current.nextInt(0, 1000));
            curNode.next = listNode;
            curNode = listNode;
        }
        return first;
    }

}
