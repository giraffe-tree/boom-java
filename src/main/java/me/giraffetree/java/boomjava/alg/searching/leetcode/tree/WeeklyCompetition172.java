package me.giraffetree.java.boomjava.alg.searching.leetcode.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/maximum-69-number/
 * https://leetcode-cn.com/problems/print-words-vertically/
 * https://leetcode-cn.com/problems/delete-leaves-with-a-given-value/
 * https://leetcode-cn.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/
 * <p>
 * -----------------
 * <p>
 * 5316. 竖直打印单词 显示英文描述
 * 用户通过次数593
 * 用户尝试次数641
 * 通过次数597
 * 提交次数1024
 * 题目难度Medium
 * 给你一个字符串 s。请你按照单词在 s 中的出现顺序将它们全部竖直返回。
 * 单词应该以字符串列表的形式返回，必要时用空格补位，但输出尾部的空格需要删除（不允许尾随空格）。
 * 每个单词只能放在一列上，每一列中也只能有一个单词。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：s = "HOW ARE YOU"
 * 输出：["HAY","ORO","WEU"]
 * 解释：每个单词都应该竖直打印。
 * "HAY"
 * "ORO"
 * "WEU"
 * 示例 2：
 * <p>
 * 输入：s = "TO BE OR NOT TO BE"
 * 输出：["TBONTB","OEROOE","   T"]
 * 解释：题目允许使用空格补位，但不允许输出末尾出现空格。
 * "TBONTB"
 * "OEROOE"
 * "   T"
 * 示例 3：
 * <p>
 * 输入：s = "CONTEST IS COMING"
 * 输出：["CIC","OSO","N M","T I","E N","S G","T"]
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= s.length <= 200
 * s 仅含大写英文字母。
 * 题目数据保证两个单词之间只有一个空格。
 * <p>
 * --------------------------------------------------
 * <p>
 * 给你一棵以 root 为根的二叉树和一个整数 target ，请你删除所有值为 target 的 叶子节点 。
 * <p>
 * 注意，一旦删除值为 target 的叶子节点，它的父节点就可能变成叶子节点；如果新叶子节点的值恰好也是 target ，那么这个节点也应该被删除。
 * <p>
 * 也就是说，你需要重复此过程直到不能继续删除。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：root = [1,2,3,2,null,2,4], target = 2
 * 输出：[1,null,3,null,4]
 * 解释：
 * 上面左边的图中，绿色节点为叶子节点，且它们的值与 target 相同（同为 2 ），它们会被删除，得到中间的图。
 * 有一个新的节点变成了叶子节点且它的值与 target 相同，所以将再次进行删除，从而得到最右边的图。
 * 示例 2：
 * <p>
 * <p>
 * <p>
 * 输入：root = [1,3,3,3,2], target = 3
 * 输出：[1,3,null,null,2]
 * 示例 3：
 * <p>
 * <p>
 * <p>
 * 输入：root = [1,2,null,2,null,2], target = 2
 * 输出：[1]
 * 解释：每一步都删除一个绿色的叶子节点（值为 2）。
 * 示例 4：
 * <p>
 * 输入：root = [1,1,1], target = 1
 * 输出：[]
 * 示例 5：
 * <p>
 * 输入：root = [1,2,3], target = 1
 * 输出：[1,2,3]
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= target <= 1000
 * 每一棵树最多有 3000 个节点。
 * 每一个节点值的范围是 [1, 1000] 。
 * <p>
 * -------------------------------------------------------------------------
 * <p>
 * 在 x 轴上有一个一维的花园。花园长度为 n，从点 0 开始，到点 n 结束。
 * <p>
 * 花园里总共有 n + 1 个水龙头，分别位于 [0, 1, ..., n] 。
 * <p>
 * 给你一个整数 n 和一个长度为 n + 1 的整数数组 ranges ，其中 ranges[i] （下标从 0 开始）表示：如果打开点 i 处的水龙头，可以灌溉的区域为 [i -  ranges[i], i + ranges[i]] 。
 * <p>
 * 请你返回可以灌溉整个花园的 最少水龙头数目 。如果花园始终存在无法灌溉到的地方，请你返回 -1 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * <p>
 * <p>
 * 输入：n = 5, ranges = [3,4,1,1,0,0]
 * 输出：1
 * 解释：
 * 点 0 处的水龙头可以灌溉区间 [-3,3]
 * 点 1 处的水龙头可以灌溉区间 [-3,5]
 * 点 2 处的水龙头可以灌溉区间 [1,3]
 * 点 3 处的水龙头可以灌溉区间 [2,4]
 * 点 4 处的水龙头可以灌溉区间 [4,4]
 * 点 5 处的水龙头可以灌溉区间 [5,5]
 * 只需要打开点 1 处的水龙头即可灌溉整个花园 [0,5] 。
 * 示例 2：
 * <p>
 * 输入：n = 3, ranges = [0,0,0,0]
 * 输出：-1
 * 解释：即使打开所有水龙头，你也无法灌溉整个花园。
 * 示例 3：
 * <p>
 * 输入：n = 7, ranges = [1,2,1,0,2,1,0,1]
 * 输出：3
 * 示例 4：
 * <p>
 * 输入：n = 8, ranges = [4,0,0,0,0,0,0,0,4]
 * 输出：2
 * 示例 5：
 * <p>
 * 输入：n = 8, ranges = [4,0,0,0,4,0,0,0,4]
 * 输出：1
 * <p>
 * <p>
 * 提示：
 * <p>
 * 1 <= n <= 10^4
 * ranges.length == n + 1
 * 0 <= ranges[i] <= 100
 */
public class WeeklyCompetition172 {

    public static void main(String[] args) {
        String s = "CONTEST IS COMING";
        List<String> result = new WeeklyCompetition172().printVertically(s);
        result.forEach(System.out::println);
        TreeNode treeNode = new WeeklyCompetition172().removeLeafNodes(TreeNode.generate444(), 4);
        System.out.println(treeNode);
    }


    public int maximum69Number(int num) {
        String s = Integer.toString(num);
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if (chars[i] == '6') {
                chars[i] = '9';
                return Integer.valueOf(new String(chars));
            }
        }
        return num;
    }


    public List<String> printVertically(String s) {
        ArrayList<String> result = new ArrayList<>();
        char[] chars = s.toCharArray();
        int start = 0;
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            int resultIndex = i - start;
            if (chars[i] == ' ') {
                start = i + 1;
                count++;
                continue;
            }
            boolean b = result.size() > resultIndex;
            if (!b) {
                result.add(getRepeat(count, ' ') + chars[i]);
            } else {
                String curS = result.get(resultIndex);
                String repeat = getRepeat(count - curS.length(), ' ');
                result.set(resultIndex, curS + repeat + chars[i]);
            }
        }
        return result;
    }

    private String getRepeat(int count, char c) {
        if (count <= 0) {
            return "";
        }
        char[] chars = new char[count];
        for (int i = 0; i < count; i++) {
            chars[i] = c;
        }
        return new String(chars);
    }

    /**
     * 思路:
     * 递归删除
     */
    public TreeNode removeLeafNodes(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        root.right = removeLeafNodes(root.right, val);
        root.left = removeLeafNodes(root.left, val);
        if (root.val == val && root.right == null && root.left == null) {
            return null;
        }
        return root;
    }

    /**
     * 花园浇水问题
     * 贪心算法, 动态规划
     * 未解决!!!!!!!
     */
    public int minTaps(int n, int[] ranges) {
        int min = 0xffffffff;
        for (int i = 0; i < ranges.length; i++) {

        }
        return 0;
    }

}