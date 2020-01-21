package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

/**
 * 易位构词游戏
 * silent" = listen (安静=聆听)
 * "earth = "heart" (地球 = 心)
 * <p>
 * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
 * <p>
 * 示例 1:
 * <p>
 * 输入: s = "anagram", t = "nagaram"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: s = "rat", t = "car"
 * 输出: false
 * 说明:
 * 你可以假设字符串只包含小写字母。
 * <p>
 * 进阶:
 * 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-anagram
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 *
 * @author GiraffeTree
 * @date 2020/1/6
 */
public class Anagram {

    public static void main(String[] args) {


    }

    /**
     * 思路:
     * 使用 26 个字母对应数组 0- 25
     * 在 ascii 中 A-Z 对应 65-90，a-z 对应 97-122。
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] a = new int[26];
        for (char c : s.toCharArray()) {
            int index = (int) c - 97;
            a[index] += 1;
        }
        for (char c : t.toCharArray()) {
            int index = (int) c - 97;
            a[index] -= 1;
            if (a[index] < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] a = new int[26];
        for (int i = 0; i < s.length(); i++) {
            a[s.charAt(i) - 97]++;
        }
        for (int i = 0; i < s.length(); i++) {
            int index = t.charAt(i) - 97;
            a[index] --;
            if (a[index] < 0) {
                return false;
            }
        }

        return true;
    }

}
