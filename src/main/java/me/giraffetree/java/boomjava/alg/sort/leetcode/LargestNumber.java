package me.giraffetree.java.boomjava.alg.sort.leetcode;

/**
 * 给定一组非负整数，重新排列它们的顺序使之组成一个最大的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [10,2]
 * 输出: 210
 * 示例 2:
 * <p>
 * 输入: [3,30,34,5,9]
 * 输出: 9534330
 * 说明: 输出结果可能非常大，所以你需要返回一个字符串而不是整数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/largest-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/6
 */
public class LargestNumber {

    public static void main(String[] args) {
        int[] a;
        a = new int[]{12, 121};
        System.out.println(largestNumber(a).equals("12121"));

        a = new int[]{121, 12};
        System.out.println(largestNumber(a).equals("12121"));

        System.out.println(less(2, 232));
        a = new int[]{3, 30, 34, 5, 9};

        System.out.println(largestNumber(a).equals("9534330"));
        a = new int[]{10, 2, 232, 9, 91};
        System.out.println(largestNumber(a).equals("991232210"));
        a = new int[]{9, 8, 7, 0};
        System.out.println(largestNumber(a).equals("9870"));

        a = new int[]{824, 8247};
        System.out.println(largestNumber(a).equals("8248247"));
        a = new int[]{0, 0};
        System.out.println(largestNumber(a).equals("0"));
    }

    /**
     * 思路
     * 这题的思路也很清晰, 只要重写排序的 less 函数就可以了
     * 为了练手, 我这里还是选择了 shell sort
     * <p>
     * 虽然上面的思路是对的, 但是在写 less 函数的时候一直报错
     * 最后看了题解, 原来用两个字符串相连, 比较不同顺序下的大小即可
     */
    public static String largestNumber(int[] nums) {
        sort(nums);
        StringBuilder sb = new StringBuilder();
        boolean k = true;
        for (int i = 0; i < nums.length; i++) {
            if (k) {
                if (nums[i] != 0) {
                    sb.append(nums[i]);
                    k = false;
                }
            } else {
                sb.append(nums[i]);
            }

        }
        if (sb.length() == 0) {
            sb.append(0);
        }
        return sb.toString();
    }

    private static void sort(int[] a) {
        int h = 1;
        while (h < a.length) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && less(a[j - h], a[j]); j -= h) {
                    swap(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    private static boolean less(int i, int j) {
        String s1 = String.valueOf(i);
        String s2 = String.valueOf(j);
        String a = s1 + s2;
        String b = s2 + s1;
        for (int k = 0; k < a.length(); k++) {
            int compare = a.charAt(k) - b.charAt(k);
            if (compare > 0) {
                return false;
            } else if (compare < 0) {
                return true;
            }
        }
        return false;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

}
