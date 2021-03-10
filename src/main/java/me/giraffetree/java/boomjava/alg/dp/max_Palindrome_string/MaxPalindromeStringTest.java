package me.giraffetree.java.boomjava.alg.dp.max_Palindrome_string;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author GiraffeTree
 * @date 2021-01-04
 */
public class MaxPalindromeStringTest {

    public static void main(String[] args) {

        String s1 = "abba";
        assertThat(maxString(s1))
                .isEqualTo("abba");
        String s2 = "abccbca";
        assertThat(maxString(s2))
                .isEqualTo("bccb");
    }

    private static String maxString(String s) {
        // 存放子串长度
        int[] dp = new int[s.length()];
        dp[0] = 1;
        int maxIndex = 0;
        int maxLen = 1;
        for (int i = 1; i < s.length(); i++) {
            boolean pass;
            if (i - dp[i - 1] - 1 >= 0) {
                pass = s.charAt(i) == s.charAt(i - dp[i - 1] - 1);
                if (pass) {
                    dp[i] = dp[i - 1] + 2;
                }
            }else {
                pass = s.charAt(i) == s.charAt(i - dp[i - 1]);
                if (pass) {
                    dp[i] = dp[i - 1] + 1;
                }
            }
            if (dp[i] > maxLen) {
                maxIndex = i;
                maxLen = dp[i];
            }
        }
        return s.substring(maxIndex - maxLen + 1, maxIndex+1);
    }

    /**
     * 检查该子串是否为回文字符串
     *
     * @param s     字符串
     * @param start 开始索引, 包含
     * @param end   结束索引, 包含
     * @return boolean 该子串是否为回文字符串
     */
    private static boolean check(String s, int start, int end) {
        int diff = end - start;
        for (int i = 0; i < (diff + 1) / 2; i++) {
            if (s.charAt(start + i) != s.charAt(start + diff - i)) {
                return false;
            }
        }
        return true;
    }
}
