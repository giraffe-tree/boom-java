package me.giraffetree.java.boomjava.alg.sort.selection;

import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * 选择排序
 * 时间复杂度 n^2
 * 所谓的选择排序就是 找到一堆数中最大/小的一个与第一个位置的数进行交换
 * <p>
 * 非稳定排序
 * 示例:
 * 当 a>b>c>d , 且 a1=a2=a3, b1=b2
 * 从大到小排列  a1 b1 a2 b2 a3 c
 * 如果使用选择排序则
 * a1 b1 a2 b2 a3 c
 * a1 a2 b1 b2 a3 c
 * a1 a2 a3 b2 b1 c     <- 排序完成, 但是 b2, b1 的位置发生了变化
 * 故选择排序是 非稳定排序
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class SelectionSort {

    private int[] a;

    public SelectionSort(int[] a) {
        this.a = a;
    }

    public void sort(boolean asc) {
        for (int i = 0; i < a.length; i++) {
            int position = i;
            for (int j = i + 1; j < a.length; j++) {
                if (asc) {
                    if (a[position] > a[j]) {
                        position = j;
                    }
                } else {
                    if (a[position] < a[j]) {
                        position = j;
                    }
                }
            }
            SortUtils.swap(a, i, position);
        }


    }

    public static void main(String[] args) {
        int[] generate = SortUtils.generate();
        System.out.println(Arrays.toString(generate));

        SelectionSort selectionSort = new SelectionSort(generate);
        selectionSort.sort(false);
        System.out.println(Arrays.toString(generate));

    }


}
