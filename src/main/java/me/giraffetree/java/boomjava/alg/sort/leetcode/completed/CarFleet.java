package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

import java.util.Arrays;
import java.util.HashMap;

/**
 * N  辆车沿着一条车道驶向位于 target 英里之外的共同目的地。
 * 每辆车 i 以恒定的速度 speed[i] （英里/小时），从初始位置 position[i] （英里） 沿车道驶向目的地。
 * 一辆车永远不会超过前面的另一辆车，但它可以追上去，并与前车以相同的速度紧接着行驶。
 * 此时，我们会忽略这两辆车之间的距离，也就是说，它们被假定处于相同的位置。
 * 车队 是一些由行驶在相同位置、具有相同速度的车组成的非空集合。注意，一辆车也可以是一个车队。
 * 即便一辆车在目的地才赶上了一个车队，它们仍然会被视作是同一个车队。
 * 会有多少车队到达目的地?
 * <p>
 * 示例：
 * <p>
 * 输入：target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 * 输出：3
 * 解释：
 * 从 10 和 8 开始的车会组成一个车队，它们在 12 处相遇。
 * 从 0 处开始的车无法追上其它车，所以它自己就是一个车队。
 * 从 5 和 3 开始的车会组成一个车队，它们在 6 处相遇。
 * 请注意，在到达目的地之前没有其它车会遇到这些车队，所以答案是 3。
 * <p>
 * 提示：
 * <p>
 * 0 <= N <= 10 ^ 4
 * 0 < target <= 10 ^ 6
 * 0 < speed[i] <= 10 ^ 6
 * 0 <= position[i] < target
 * 所有车的初始位置各不相同。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/car-fleet
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020-01-06
 */
public class CarFleet {

    public static void main(String[] args) {
        int[] a = {0, 1, 3, 2, 9, 5, 4};
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    /**
     * 由于题目中说不能超车
     * 则距离终点越近的车肯定越先到达
     * 思路如下:
     * 1. 排序 我这里使用的是归并排序
     * 2. 遍历, 检查下一辆车是否可能和前一辆车一个车队
     */
    public int carFleet(int target, int[] position, int[] speed) {
        if (position.length == 0) {
            return 0;
        }
        if (position.length == 1) {
            return 1;
        }
        HashMap<Integer, Integer> map = new HashMap<>(position.length);
        for (int i = 0; i < position.length; i++) {
            map.put(position[i], speed[i]);
        }
        int[] aux = new int[position.length];
        sort(position, aux, 0, position.length - 1);

        double maxTime = (double) (target - position[0]) / map.get(position[0]);
        int max = 1;
        for (int s : position) {
            int len = target - s;
            double time = (double) len / map.get(s);
            if (time > maxTime) {
                maxTime = time;
                max++;
            }
        }

        return max;
    }


    /**
     * 这里使用 merge sort
     */
    public static void sort(int[] a, int[] aux, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = (lo + hi) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
        // 1. 复制到辅助数组中
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        // 2. 归并
        int i = lo;
        int j = lo;
        int k = mid + 1;
        while (i <= hi) {
            if (j > mid) {
                a[i++] = aux[k++];
            } else if (k > hi) {
                a[i++] = aux[j++];
            } else if (aux[j] < aux[k]) {
                a[i++] = aux[k++];
            } else if (aux[k] < aux[j]) {
                a[i++] = aux[j++];
            }
        }
    }

}
