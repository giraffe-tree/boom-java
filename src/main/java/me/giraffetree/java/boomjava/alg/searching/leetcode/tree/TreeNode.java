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
}