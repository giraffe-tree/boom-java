package me.giraffetree.java.boomjava.alg.searching.leetcode.tree;

/**
 * 给定一个二叉树，检查它是否是镜像对称的。
 * <p>
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 * <p>
 * #     1
 * #    / \
 * #   2   2
 * #  / \ / \
 * # 3  4 4  3
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 * <p>
 * #     1
 * #    / \
 * #   2   2
 * #    \   \
 * #    3    3
 * 说明:
 * <p>
 * 如果你可以运用递归和迭代两种方法解决这个问题，会很加分。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/symmetric-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/17
 */
public class IsSymmetric {

    /**
     * 有两种方法
     * 1. 递归 本方法
     * 2. 迭代 顺序插入队列
     */
    public static void main(String[] args) {
        IsSymmetric s = new IsSymmetric();
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
//        TreeNode n3 = new TreeNode(11);
        treeNode.right = new TreeNode(3);

        System.out.println(s.isSymmetric(treeNode));

    }

    public boolean isSymmetric(TreeNode root) {

        return isSymmetric(root, root);
    }

    private static boolean isSymmetric(TreeNode x, TreeNode y) {
        if (x == null && y == null) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        if (x.val != y.val) {
            return false;
        }
        return isSymmetric(x.left, y.right) && isSymmetric(x.right, y.left);
    }

}
