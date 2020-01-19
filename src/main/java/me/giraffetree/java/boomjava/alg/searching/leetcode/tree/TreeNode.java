package me.giraffetree.java.boomjava.alg.searching.leetcode.tree;

import lombok.Data;

@Data
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    static TreeNode generateStandard() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        return root;
    }
}