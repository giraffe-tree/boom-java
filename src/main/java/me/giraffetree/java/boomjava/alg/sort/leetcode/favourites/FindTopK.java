package me.giraffetree.java.boomjava.alg.sort.leetcode.favourites;

import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [3,2,1,5,6,4] 和 k = 2
 * 输出: 5
 * 示例 2:
 * <p>
 * 输入: [3,2,3,1,2,4,5,5,6] 和 k = 4
 * 输出: 4
 * 说明:
 * <p>
 * 你可以假设 k 总是有效的，且 1 ≤ k ≤ 数组的长度。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/kth-largest-element-in-an-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/21
 */
public class FindTopK {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int[] a = SortUtils.generate();
            System.out.println(Arrays.toString(a));
            System.out.println(findKthLargest(a, 4));

        }

    }

    // - [BFPRT算法原理](https://zhuanlan.zhihu.com/p/31498036)
    //    - 又称为中位数的中位数算法，它的最坏时间复杂度为 O(n) ，它是由Blum、Floyd、Pratt、Rivest、Tarjan提出。
    //    - 该算法的思想是修改快速选择算法的主元选取方法，提高算法在最坏情况下的时间复杂度
    //    - 步骤 来自: https://www.cnblogs.com/wade-luffy/p/7777313.html
    //        - 将n个元素每 5 个一组，分成n/5(上界)组。
    //        - 取出每一组的中位数，任意排序方法，比如插入排序。
    //        - 递归的调用 selection 算法查找上一步中所有中位数的中位数，设为x，偶数个中位数的情况下设定为选取中间小的一个。
    //        - 用x来分割数组，设小于等于x的个数为k，大于x的个数即为n-k。
    //        - 若i==k，返回x；若i<k，在小于x的元素中递归查找第i小的元素；若i>k，在大于x的元素中递归查找第i-k 小的元素。
    //
    // 作者：rsk
    // 链接：https://leetcode-cn.com/problems/kth-largest-element-in-an-array/solution/bfprtsuan-fa-zai-onshi-jian-nei-jie-jue-wen-ti-by-/
    // 来源：力扣（LeetCode）
    // 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    // 算法接口
    public static int findKthLargest(int[] nums, int k) {
        if (nums == null || k > nums.length || k == 0) {
            return 0;
        }
        int position = BFPRT(0, nums.length - 1, nums.length - k + 1, nums);
        return nums[position];
    }

    //算法主入口
    private static int BFPRT(int left, int right, int k, int[] array) {
        if (left >= right) {
            return left;
        }
        int pivotIndex = getPivotIndex(left, right, array);
        int mid = Partition(left, right, pivotIndex, array);
        int count = mid - left + 1;
        if (count == k) {
            return mid;
        } else if (count > k) {
            return BFPRT(left, mid - 1, k, array);
        } else {
            return BFPRT(mid + 1, right, k - count, array);
        }
    }

    //得到中位数
    private static int getPivotIndex(int left, int right, int[] array) {
        if ((right - left) < 5) {
            return insertSort(left, right, array);
        }
        int back = left - 1;
        for (int i = left; i + 4 < right; i += 5) {
            int index = insertSort(i, i + 4, array);
            Swap(array, ++back, index);
        }
//        return BFPRT(left, back, ((back + left) >> 1) + 1, array);
        return BFPRT(left, back, ((back - left) >> 1) + 1, array);
    }

    //插入排序
    // 返回中位数
    private static int insertSort(int left, int right, int[] array) {
        for (int i = left + 1; i < right + 1; i++) {
            int temp = array[i];
            int j;
            for (j = i; j > left && array[j - 1] > temp; j--) {
                array[j] = array[j - 1];
            }
            array[j] = temp;
        }
        return (right + left) >> 1;
    }

    //交换
    private static void Swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
        return;
    }

    //划分
    private static int Partition(int left, int right, int index, int[] array) {
        Swap(array, right, index);
        int position = left;
        for (int i = left; i < right; i++) {
            if (array[i] < array[right]) {
                Swap(array, i, position++);
            }
        }
        Swap(array, right, position);
        return position;
    }

}
