package me.giraffetree.java.boomjava.alg.searching.leetcode.tree.medium;

import me.giraffetree.java.boomjava.alg.searching.leetcode.tree.TreeNode;

import java.util.ArrayList;

/**
 * 你需要从一个包括括号和整数的字符串构建一棵二叉树。
 * <p>
 * 输入的字符串代表一棵二叉树。它包括整数和随后的0，1或2对括号。整数代表根的值，一对括号内表示同样结构的子树。
 * <p>
 * 若存在左子结点，则从左子结点开始构建。
 * <p>
 * 示例:
 * <p>
 * 输入: "4(2(3)(1))(6(5))"
 * 输出: 返回代表下列二叉树的根节点:
 * <p>
 * #       4
 * #     /   \
 * #    2     6
 * #   / \   /
 * #  3   1 5
 *
 * <p>
 * 注意:
 * <p>
 * 输入字符串中只包含 '(', ')', '-' 和 '0' ~ '9' 
 * 空树由 "" 而非"()"表示。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/construct-binary-tree-from-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/19
 */
public class Str2tree {

    public static void main(String[] args) {

    }

    public TreeNode str2tree(String s) {
        ArrayList<TreeNode> list = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {


        }

        return null;
    }

}
