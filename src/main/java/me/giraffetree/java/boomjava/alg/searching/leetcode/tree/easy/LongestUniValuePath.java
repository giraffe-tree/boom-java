package me.giraffetree.java.boomjava.alg.searching.leetcode.tree.easy;

import me.giraffetree.java.boomjava.alg.searching.leetcode.tree.TreeNode;

import java.util.Stack;

/**
 * // 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
 * //
 * //注意：两个节点之间的路径长度由它们之间的边数表示。
 * //
 * //示例 1:
 * //
 * //输入:
 * //
 * //              5
 * //             / \
 * //            4   5
 * //           / \   \
 * //          1   1   5
 * //输出:
 * //
 * //2
 * //示例 2:
 * //
 * //输入:
 * //
 * //              1
 * //             / \
 * //            4   5
 * //           / \   \
 * //          4   4   5
 * //输出:
 * //
 * //2
 * //注意: 给定的二叉树不超过10000个结点。 树的高度不超过1000。
 * //
 * //来源：力扣（LeetCode）
 * //链接：https://leetcode-cn.com/problems/longest-univalue-path
 * //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/19
 */
public class LongestUniValuePath {

    public static void main(String[] args) {
        TreeNode node = TreeNode.generate145445();
        System.out.println(new LongestUniValuePath().longestUnivaluePath(node));

    }

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int max = 0;
        // 遍历
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            int cur = uniLen(node.right, node.val) + uniLen(node.left, node.val) + 1;
            if (cur > max) {
                max = cur;
            }
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        return max - 1;
    }

    private int uniLen(TreeNode root, int val) {
        if (root == null) {
            return 0;
        }
        if (root.val == val) {
            return Math.max(uniLen(root.right, val), uniLen(root.left, val)) + 1;
        }
        return 0;
    }


}
