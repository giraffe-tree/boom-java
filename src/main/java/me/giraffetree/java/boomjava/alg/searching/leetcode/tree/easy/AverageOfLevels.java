package me.giraffetree.java.boomjava.alg.searching.leetcode.tree.easy;

import me.giraffetree.java.boomjava.alg.searching.leetcode.tree.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组.
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * #    3
 * #   / \
 * #  9  20
 * #    /  \
 * #   15   7
 * 输出: [3, 14.5, 11]
 * 解释:
 * 第0层的平均值是 3,  第1层是 14.5, 第2层是 11. 因此返回 [3, 14.5, 11].
 * 注意：
 * <p>
 * 节点值的范围在32位有符号整数范围内。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/average-of-levels-in-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020-01-18
 */
public class AverageOfLevels {

    public static void main(String[] args) {
        TreeNode node = TreeNode.generateStandard();
        List<Double> doubles = new AverageOfLevels().averageOfLevels3(node);
        doubles.forEach(System.out::println);

    }

    /**
     * 思路
     * 广度优先 bfs
     */
    public List<Double> averageOfLevels(TreeNode root) {
        ArrayList<TreeNode> stack = new ArrayList<>();
        ArrayList<Double> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        ArrayList<Double> sum = new ArrayList<>();
        ArrayList<TreeNode> cache = new ArrayList<>();

        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode remove = stack.remove(stack.size() - 1);
            sum.add((double) remove.val);
            boolean empty = stack.isEmpty();
            if (remove.left != null) {
                cache.add(remove.left);
            }
            if (remove.right != null) {
                cache.add(remove.right);
            }
            if (empty) {
                int size = sum.size();

                result.add(sum.stream().reduce(Double::sum).get() / size);
                sum.clear();
                ArrayList<TreeNode> tmp = cache;
                cache = stack;
                stack = tmp;
            }
        }

        return result;
    }

    /**
     * 广度优先
     */
    public List<Double> averageOfLevels2(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            long sum = 0, count = 0;
            Queue<TreeNode> temp = new LinkedList<>();
            while (!queue.isEmpty()) {
                TreeNode n = queue.remove();
                sum += n.val;
                count++;
                if (n.left != null) {
                    temp.add(n.left);
                }
                if (n.right != null) {
                    temp.add(n.right);
                }
            }
            queue = temp;
            res.add(sum * 1.0 / count);
        }
        return res;
    }

    /**
     * 深度优先
     */
    public List<Double> averageOfLevels3(TreeNode node) {
        ArrayList<Double> sumList = new ArrayList<>();
        ArrayList<Integer> sizeList = new ArrayList<>();

        recursive(node, 0, sumList, sizeList);
        for (int i = 0; i < sumList.size(); i++) {
            sumList.set(i, sumList.get(i) / sizeList.get(i));
        }
        return sumList;
    }

    private void recursive(TreeNode root, int level, List<Double> sumList, List<Integer> sizeList) {
        if (root == null) {
            return;
        }
        System.out.println(level);
        if (sumList.size() <= level) {
            sumList.add(level, root.val * 1.0);
            sizeList.add(level, 1);
        } else {
            sumList.set(level, sumList.get(level) + root.val);
            sizeList.set(level, sizeList.get(level) + 1);
        }
        recursive(root.left, level + 1, sumList, sizeList);
        recursive(root.right, level + 1, sumList, sizeList);
    }

}
