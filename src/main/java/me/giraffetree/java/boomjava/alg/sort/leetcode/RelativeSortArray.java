package me.giraffetree.java.boomjava.alg.sort.leetcode;

import java.util.Arrays;
import java.util.HashMap;

import static me.giraffetree.java.boomjava.alg.sort.SortUtils.swap;

/**
 * 给你两个数组，arr1 和 arr2，
 * <p>
 * arr2 中的元素各不相同
 * arr2 中的每个元素都出现在 arr1 中
 * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
 * 示例：
 * <p>
 * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
 * 输出：[2,2,2,1,4,3,3,9,6,7,19]
 *  
 * <p>
 * 提示：
 * <p>
 * arr1.length, arr2.length <= 1000
 * 0 <= arr1[i], arr2[i] <= 1000
 * arr2 中的元素 arr2[i] 各不相同
 * arr2 中的每个元素 arr2[i] 都出现在 arr1 中
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/relative-sort-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/relative-sort-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 思路:
 * 1. 遍历
 * 2. 对额外的数据进行排序
 *
 * @author GiraffeTree
 * @date 2020-01-05
 * 60
 */
public class RelativeSortArray {

    /**
     * 这道题目做的时候审题有问题
     * 没看到后面的条件 在 0-1000 范围内
     * 其实使用 counting sort 就可以了, 非常方便
     */
    public static void main(String[] args) {

        int[] arr1 = {2, 3, 1, 3, 2, 4, 6, 7, 9, 2, 19};
        int[] arr2 = {2, 1, 4, 3, 9, 6};
        int[] ints = relativeSortArray(arr1, arr2);
        System.out.println(Arrays.toString(ints));

    }

    public static int[] relativeSortArray(int[] arr1, int[] arr2) {
        HashMap<Integer, Integer> map = new HashMap<>(arr2.length);
        for (int x : arr2) {
            map.put(x, 0);
        }
        int[] aux = new int[arr1.length - arr2.length];
        int cur = 0;
        for (int y : arr1) {
            if (map.containsKey(y)) {
                map.put(y, map.get(y) + 1);
            } else {
                aux[cur++] = y;
            }
        }
        sort(aux, 0, cur - 1);
        int index = 0;
        for (int x : arr2) {
            Integer count = map.get(x);
            for (; count > 0; count--) {
                arr1[index++] = x;
            }
        }
        System.arraycopy(aux, 0, arr1, index, cur);
        return arr1;
    }

    /**
     * 三向切分快速排序
     */
    private static void sort(int[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int lt = lo, i = lo + 1, gt = hi;
        int v = a[lo];
        while (i <= gt) {
            int cmp = a[i] - v;
            if (cmp < 0) {
                swap(a, lt++, i++);
            } else if (cmp > 0) {
                swap(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }


}
