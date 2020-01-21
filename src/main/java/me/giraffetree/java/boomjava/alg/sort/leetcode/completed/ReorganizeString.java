package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;


/**
 * 给定一个字符串S，检查是否能重新排布其中的字母，使得两相邻的字符不同。
 * <p>
 * 若可行，输出任意可行的结果。若不可行，返回空字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: S = "aab"
 * 输出: "aba"
 * 示例 2:
 * <p>
 * 输入: S = "aaab"
 * 输出: ""
 * 注意:
 * <p>
 * S 只包含小写字母并且长度在[1, 500]区间内。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reorganize-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * 和仓库条形码问题很像
 * https://leetcode-cn.com/problems/distant-barcodes
 *
 * @author GiraffeTree
 * @date 2020/1/8
 */
public class ReorganizeString {

    public static void main(String[] args) {
        System.out.println((int) 'a');
        String s = "abbbbbaaacccbbb";
        System.out.println(reorganizeString(s));
    }

    /**
     * 思路:
     * 1. 遍历, 找到一个与前一个不同的字符, 交换-----错误思路
     * 2. 桶排序, 每次输出最多数量的字母  这里使用的是这种思路
     * 缺点是每次都要重新计算max的字母, 不过字母一共就26个, 找出max并不是很困难
     * 3. 桶排序, 将字母按照从多到少, 放入奇数位, 奇数位满后放入偶数位
     * 这种方法减少了每次计算max,只需排序依次所有字母的个数就可以了, 减少了计算量
     */
    public static String reorganizeString(String s) {
        int[] bins = new int[26];
        char[] chars = s.toCharArray();
        int len = chars.length;
        for (int i = 0; i < len; i++) {
            bins[chars[i] - 97]++;
        }
        int max = max(bins, -1);
        if (bins[max] > (len + len % 2) / 2) {
            return "";
        }
        int latest = -1;
        for (int i = 0; i < s.length(); i++) {
            int cur = max(bins, latest);
            chars[i] = getChar(cur);
            latest = cur;
            bins[cur]--;
        }

        return new String(chars);
    }

    private static char getChar(int a) {
        return (char) (a + 97);
    }

    private static void swap(char[] a, int i, int j) {
        char tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }


    private static int max(int[] a, int notAllow) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            if (i == notAllow) {
                continue;
            }
            if (max < 0 || a[i] > a[max]) {
                max = i;
            }
        }
        return max;
    }

}
