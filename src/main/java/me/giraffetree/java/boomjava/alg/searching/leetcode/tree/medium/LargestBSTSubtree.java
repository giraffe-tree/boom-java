package me.giraffetree.java.boomjava.alg.searching.leetcode.tree.medium;

import me.giraffetree.java.boomjava.alg.searching.leetcode.tree.TreeNode;

/**
 * 给定一个二叉树，找到其中最大的二叉搜索树（BST）子树，其中最大指的是子树节点数最多的。
 * <p>
 * 注意:
 * 子树必须包含其所有后代。
 * <p>
 * 示例:
 * <p>
 * 输入: [10,5,15,1,8,null,7]
 * <p>
 * #   10
 * #   / \
 * #  5  15
 * # / \   \
 * #1   8   7
 * <p>
 * 输出: 3
 * 解释: 高亮部分为最大的 BST 子树。
 * 返回值 3 在这个样例中为子树大小。
 * 进阶:
 * 你能想出用 O(n) 的时间复杂度解决这个问题吗？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/largest-bst-subtree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/19
 */
public class LargestBSTSubtree {

    public static void main(String[] args) {
        TreeNode root = TreeNode.generate42613_5();
        System.out.println(new LargestBSTSubtree().largestBSTSubtree(root));
    }

    public int largestBSTSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        prev = null;
        count = 0;
        boolean isBst = bst(root);
        if (isBst) {
            return count;
        }
        return Math.max(largestBSTSubtree(root.left), largestBSTSubtree(root.right));
    }

    private TreeNode prev;
    private int count;

    private boolean bst(TreeNode node) {
        boolean result = true;
        if (node.left != null) {
            result = bst(node.left);
        }
        if (prev != null) {
            if (node.val <= prev.val) {
                result = false;
            }
        }
        count++;
        prev = node;
        if (node.right != null) {
            result = result & bst(node.right);
        }
        return result;
    }

}
