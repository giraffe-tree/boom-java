package me.giraffetree.java.boomjava.alg.searching.leetcode.tree.easy;

import me.giraffetree.java.boomjava.alg.searching.leetcode.tree.TreeNode;

/**
 * 给定一个所有节点为非负值的二叉搜索树，求树中任意两节点的差的绝对值的最小值。
 * <p>
 * 示例 :
 * <p>
 * 输入:
 * <p>
 * #   1
 * #    \
 * #     3
 * #    /
 * #   2
 * <p>
 * 输出:
 * 1
 * <p>
 * 解释:
 * 最小绝对差为1，其中 2 和 1 的差的绝对值为 1（或者 2 和 3）。
 * 注意: 树中至少有2个节点。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-absolute-difference-in-bst
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/19
 */
public class GetMinimumDifference {

    private int min = Integer.MAX_VALUE;
    private TreeNode prev = null;

    /**
     * 思路 中序遍历
     * 先开始找的是 diff 的最大值...
     */
    public int getMinimumDifference(TreeNode root) {
        getMinDiff(root);
        return min;
    }

    private void getMinDiff(TreeNode root) {
        if (root.left != null) {
            getMinDiff(root.left);
        }
        if (prev != null) {
            int diff = root.val - prev.val;
            if (diff < min) {
                min = diff;
            }
        }
        prev = root;

        if (root.right != null) {
            getMinDiff(root.right);
        }
    }

    public static void main(String[] args) {

        GetMinimumDifference solution = new GetMinimumDifference();
        TreeNode one = new TreeNode(5);
        TreeNode two = new TreeNode(4);
        TreeNode three = new TreeNode(7);
        one.left = two;
        one.right = three;

        int minimumDifference = solution.getMinimumDifference(one);
        System.out.println(minimumDifference);

    }

}
