package me.giraffetree.java.boomjava.alg.searching.leetcode.tree.easy;

import me.giraffetree.java.boomjava.alg.searching.leetcode.tree.TreeNode;

import java.util.Stack;

/**
 * // 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
 * //
 * //你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。
 * //
 * //示例 1:
 * //
 * //输入:
 * //	Tree 1                     Tree 2
 * //          1                         2
 * //         / \                       / \
 * //        3   2                     1   3
 * //       /                           \   \
 * //      5                             4   7
 * //输出:
 * //合并后的树:
 * //	     3
 * //	    / \
 * //	   4   5
 * //	  / \   \
 * //	 5   4   7
 * //注意: 合并必须从两个树的根节点开始。
 * //
 * //来源：力扣（LeetCode）
 * //链接：https://leetcode-cn.com/problems/merge-two-binary-trees
 * //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020-01-18
 */

public class MergeTrees {

    /**
     * 思路: 递归法
     * 将 t2 直接合并到 t1 中
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t2 == null) {
            return t1;
        }
        if (t1 == null) {
            return t2;
        }
        t1.val = t1.val + t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }

    /**
     * 迭代法
     * 蛮重要的一个迭代法, 我一开始想不到的...
     */
    public TreeNode mergeTrees2(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        Stack<TreeNode[]> stack = new Stack<>();
        stack.push(new TreeNode[]{t1, t2});
        while (!stack.isEmpty()) {
            TreeNode[] nodes = stack.pop();
            TreeNode n1 = nodes[0];
            TreeNode n2 = nodes[1];
            if (n2 == null) {
                continue;
            }
            n1.val += n2.val;
            if (n1.left == null) {
                n1.left = n2.left;
            } else {
                stack.push(new TreeNode[]{n1.left, n2.left});
            }
            if (n1.right == null) {
                n1.right = n2.right;
            } else {
                stack.push(new TreeNode[]{n1.right, n2.right});
            }
        }
        return t1;
    }


}
