package me.giraffetree.java.boomjava.alg.searching.leetcode.tree.easy;

import me.giraffetree.java.boomjava.alg.searching.leetcode.tree.TreeNode;

/**
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
 * <p>
 * 示例 :
 * 给定二叉树
 * <p>
 * #     1
 * #   / \
 * #  2   3
 * # / \
 * #4   5
 * 返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]。
 * <p>
 * 注意：两结点之间的路径长度是以它们之间边的数目表示。
 * <p>
 * 在真实的面试中遇到过这道题？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/diameter-of-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/17
 */
public class DiameterOfBinaryTree {

    public static void main(String[] args) {
        DiameterOfBinaryTree tree = new DiameterOfBinaryTree();
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        int i = tree.diameterOfBinaryTree(treeNode);
        System.out.println(i);
    }

    private int max;

    public int diameterOfBinaryTree(TreeNode root) {
        height(root);
        return max;
    }

    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        if (leftHeight + rightHeight > max) {
            max = leftHeight + rightHeight;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }
}
