package me.giraffetree.java.boomjava.alg.searching.leetcode.tree;

/**
 * // 给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。s 也可以看做它自身的一棵子树。
 * //
 * //示例 1:
 * //给定的树 s:
 * //
 * //     3
 * //    / \
 * //   4   5
 * //  / \
 * // 1   2
 * //给定的树 t：
 * //
 * //   4
 * //  / \
 * // 1   2
 * //返回 true，因为 t 与 s 的一个子树拥有相同的结构和节点值。
 * //
 * //示例 2:
 * //给定的树 s：
 * //
 * //     3
 * //    / \
 * //   4   5
 * //  / \
 * // 1   2
 * //    /
 * //   0
 * //给定的树 t：
 * //
 * //   4
 * //  / \
 * // 1   2
 * //返回 false。
 * //
 * //来源：力扣（LeetCode）
 * //链接：https://leetcode-cn.com/problems/subtree-of-another-tree
 * //著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/17
 */
public class IsSubtree {

    public static void main(String[] args) {
        IsSubtree isSubtree = new IsSubtree();
        TreeNode root = new TreeNode(3);
        TreeNode right1 = new TreeNode(5);
        TreeNode left1 = new TreeNode(4);
        left1.left = new TreeNode(1);
        left1.right = new TreeNode(2);
        root.left = left1;
        root.right = right1;

        TreeNode root2 = new TreeNode(4);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(2);
        boolean subtree = isSubtree.isSubtree(root, root2);
        System.out.println(subtree);
    }


    private boolean isSub = false;

    public boolean isSubtree(TreeNode s, TreeNode t) {
        int hash = hash(t);
        int i = hashT(s, hash, t);
        if (i == hash) {
            return checkNode(s, t);
        }

        return isSub;
    }

    private boolean checkNode(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        if (node1.val != node2.val) {
            return false;
        }
        return checkNode(node1.left, node2.left) && checkNode(node1.right, node2.right);
    }

    private int hash(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return hash(node.left) + hash(node.right) + node.val;
    }

    private int hashT(TreeNode node, int hash, TreeNode t) {
        if (node == null) {
            return 0;
        }
        int hashCur = hashT(node.left, hash, t) + hashT(node.right, hash, t) + node.val;
        if (hashCur == hash) {
            boolean check = checkNode(node, t);
            if (check) {
                isSub = true;
            }
        }
        return hashCur;
    }

}
