package me.giraffetree.java.boomjava.alg.searching.leetcode.tree.easy;

import me.giraffetree.java.boomjava.alg.searching.leetcode.tree.TreeNode;

/**
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 * <p>
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 * <p>
 * 示例:
 * <p>
 * 给定有序数组: [-10,-3,0,5,9],
 * <p>
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/19
 */
public class SortedArrayToBST {

    public static void main(String[] args) {

        SortedArrayToBST solution = new SortedArrayToBST();
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        TreeNode node = solution.sortedArrayToBST(nums);
        System.out.println(node);
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        TreeNode root = bst(nums, 0, nums.length - 1);
        return root;
    }

    private TreeNode bst(int[] nums, int lo, int hi) {
        if (hi < lo) {
            return null;
        }
        // 这里可能会溢出!!!
//        int n = (hi + lo) / 2;
//        int n = lo + (hi - lo) / 2;
        int n = (lo + hi) >>> 1;
        TreeNode root = new TreeNode(nums[n]);
        root.left = bst(nums, lo, n - 1);
        root.right = bst(nums, n + 1, hi);
        return root;
    }

}
