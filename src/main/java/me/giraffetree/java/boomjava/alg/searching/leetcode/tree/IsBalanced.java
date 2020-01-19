package me.giraffetree.java.boomjava.alg.searching.leetcode.tree;

/**
 * // 给定一个二叉树，判断它是否是高度平衡的二叉树。
 * //
 * //本题中，一棵高度平衡二叉树定义为：
 * //
 * //一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
 * //
 * //示例 1:
 * //
 * //给定二叉树 [3,9,20,null,null,15,7]
 * //
 * //    3
 * //   / \
 * //  9  20
 * //    /  \
 * //   15   7
 * //返回 true 。
 * //
 * //示例 2:
 * //
 * //给定二叉树 [1,2,2,3,3,null,null,4,4]
 * //
 * //       1
 * //      / \
 * //     2   2
 * //    / \
 * //   3   3
 * //  / \
 * // 4   4
 * //返回 false 。
 * //
 * //来源：力扣（LeetCode）
 * //链接：https://leetcode-cn.com/problems/balanced-binary-tree
 * //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020-01-18
 */
public class IsBalanced {

    public static void main(String[] args) {
        IsBalanced b = new IsBalanced();
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(2);
        root.left = l1;
        TreeNode l2 = new TreeNode(3);
        root.right = l2;
        l1.right = new TreeNode(4);
        l1.left = new TreeNode(4);
        System.out.println(b.isBalanced(root));

    }

    private boolean isBalanced = true;

    public boolean isBalanced(TreeNode root) {
        height(root);
        return isBalanced;
    }

    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int h1 = height(node.left);
        int h2 = height(node.right);
        if (Math.abs(h1 - h2) > 1) {
            isBalanced = false;
        }
        return Math.max(h1, h2) + 1;
    }

}
