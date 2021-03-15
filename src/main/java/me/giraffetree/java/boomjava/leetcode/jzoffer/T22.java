package me.giraffetree.java.boomjava.leetcode.jzoffer;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2021/3/12 11:01
 */
public class T22 {

    @Test
    public void test() {

        System.out.println("start...");
        ListNode randomListNode = getRandomListNode(5);
        ListNode kthFromEnd = getKthFromEnd(randomListNode, 3);

        System.out.println("end...");
    }

    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode[] arr = new ListNode[k];
        int index = 0;
        arr[index] = head;
        ListNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
            index = getNextIndex(k, index);
            arr[index] = cur;
        }
        return arr[getNextIndex(k, index)];
    }

    private static int getNextIndex(int max, int cur) {
        if (cur >= max - 1) {
            return 0;
        }
        return cur + 1;
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
