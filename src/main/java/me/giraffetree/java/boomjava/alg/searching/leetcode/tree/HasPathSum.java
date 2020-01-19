package me.giraffetree.java.boomjava.alg.searching.leetcode.tree;

import java.util.Stack;

/**
 * // 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 * //
 * //说明: 叶子节点是指没有子节点的节点。
 * //
 * //示例: 
 * //给定如下二叉树，以及目标和 sum = 22，
 * //
 * //              5
 * //             / \
 * //            4   8
 * //           /   / \
 * //          11  13  4
 * //         /  \      \
 * //        7    2      1
 * //返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。
 * //
 * //来源：力扣（LeetCode）
 * //链接：https://leetcode-cn.com/problems/path-sum
 * //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020-01-18
 */
public class HasPathSum {

    public static void main(String[] args) {
        HasPathSum hasPathSum = new HasPathSum();
        TreeNode root = TreeNode.generateStandard();
        boolean b = hasPathSum.hasPathSum3(root, 3);
        System.out.println(b);

    }

    private boolean hasSum = false;

    /**
     * 递归法
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        sum(root, sum, 0);
        return hasSum;
    }

    private void sum(TreeNode node, int sum, int cur) {
        if (node == null) {
            return;
        }
        cur += node.val;
        if (node.left == null && node.right == null) {
            if (sum == cur) {
                hasSum = true;
            }
        }
        sum(node.left, sum, cur);
        sum(node.right, sum, cur);
    }

    /**
     * 递归法
     * 官方题解中的答案, 比我自己写的要简洁
     */
    public boolean hasPathSum2(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        sum -= root.val;
        if ((root.left == null) && (root.right == null)) {
            return (sum == 0);
        }
        return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
    }


    /**
     * 迭代法
     */
    public boolean hasPathSum3(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> sumStack = new Stack<>();
        nodeStack.push(root);
        sumStack.push(sum);
        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            Integer cur = sumStack.pop();
            cur -= node.val;
            if (node.right == null && node.left == null) {
                if (cur == 0) {
                    return true;
                }
            }

            if (node.right != null) {
                sumStack.push(cur);
                nodeStack.push(node.right);
            }
            if (node.left != null) {
                sumStack.push(cur);
                nodeStack.push(node.left);
            }
        }

        return false;
    }

}
