package me.giraffetree.java.boomjava.alg.searching.leetcode.tree;

import lombok.Data;

@Data
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    public static TreeNode generateBst() {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        return root;
    }

    public static TreeNode generateStandard() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        return root;
    }

    public static TreeNode generate145445() {
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(4);
        TreeNode r1 = new TreeNode(5);
        TreeNode l2 = new TreeNode(4);
        TreeNode r2 = new TreeNode(4);
        TreeNode l3 = new TreeNode(5);

        root.left = l1;
        root.right = r1;
        l1.left = l2;
        l1.right = r2;
        r1.left = l3;
        return root;
    }

    public static TreeNode generate149356() {
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(4);
        TreeNode r1 = new TreeNode(9);
        TreeNode l2 = new TreeNode(3);
        TreeNode r2 = new TreeNode(5);
        TreeNode l3 = new TreeNode(6);

        root.left = l1;
        root.right = r1;
        l1.left = l2;
        l1.right = r2;
        r1.left = l3;

        return root;
    }

    public static TreeNode generate42613_5() {
        TreeNode root = new TreeNode(4);
        TreeNode l1 = new TreeNode(2);
        TreeNode r1 = new TreeNode(6);
        TreeNode l2 = new TreeNode(1);
        TreeNode r2 = new TreeNode(3);
        TreeNode r3 = new TreeNode(5);

        root.left = l1;
        root.right = r1;
        l1.left = l2;
        l1.right = r2;
        r1.right = r3;

        return root;
    }

    public static TreeNode generate444() {
        TreeNode root = new TreeNode(4);
        TreeNode l1 = new TreeNode(4);
        TreeNode r1 = new TreeNode(4);

        root.left = l1;
        root.right = r1;
        return root;
    }
}