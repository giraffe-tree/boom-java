package me.giraffetree.java.boomjava.alg.sort.leetcode;

import java.util.*;

/**
 * 给定一个字符串和一个字符串字典，找到字典里面最长的字符串，该字符串可以通过删除给定字符串的某些字符来得到。
 * 如果答案不止一个，返回长度最长且字典顺序最小的字符串。如果答案不存在，则返回空字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入:
 * s = "abpcplea", d = ["ale","apple","monkey","plea"]
 * <p>
 * 输出:
 * "apple"
 * 示例 2:
 * <p>
 * 输入:
 * s = "abpcplea", d = ["a","b","c"]
 * <p>
 * 输出:
 * "a"
 * 说明:
 * <p>
 * 所有输入的字符串只包含小写字母。
 * 字典的大小不会超过 1000。
 * 所有输入的字符串长度不会超过 1000。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-word-in-dictionary-through-deleting
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/8
 */
public class FindLongestWord2 {

    public static void main(String[] args) {
        String s = "ghellwoirld";

        List<String> list = Arrays.asList("hello", "world", "hi", "girl");
        System.out.println(findLongestWord(s, list));

    }

    /**
     * 这个方法是看了题解之后做的,
     * 原理是先排序, 后匹配, 一旦匹配到就已经是最优先的那个字符串了
     */
    public static String findLongestWord(String s, List<String> d) {

        // 对 list 进行排序, 如果是 链表实现的 list 怎么办?
        // 会先转成 数组, 再进行排序
        d.sort((s1, s2) -> s2.length() != s1.length() ? s2.length() - s1.length() : s1.compareTo(s2));

        // 匹配
        char[] a = s.toCharArray();
        for (int i = 0; i < d.size(); i++) {
            boolean check = check(a, d.get(i));
            if (check) {
                return d.get(i);
            }
        }
        return "";

    }

    private static boolean check(char[] a, String s) {
        int k = 0;

        for (char c : a) {
            if (c == s.charAt(k)) {
                k++;
            }
            if (k == s.length()) {
                return true;
            }
        }
        return false;
    }


}
