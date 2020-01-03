package me.giraffetree.java.boomjava.alg.sort.bubble;

import me.giraffetree.java.boomjava.alg.sort.SortUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 冒泡排序
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class BubbleSort {

    private int[] a;

    public BubbleSort(int[] a) {
        this.a = a;
    }

    /**
     * 时间复杂度 n^2
     * 稳定排序
     * 原地排序
     *
     * @param asc 排序方式
     */
    public void sort(boolean asc) {
        for (int i = a.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (asc) {
                    // 最后一个数最大
                    if (a[j] > a[j + 1]) {
                        SortUtils.swap(a, j, j + 1);
                    }
                } else {
                    if (a[j] < a[j + 1]) {
                        SortUtils.swap(a, j, j + 1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] a = SortUtils.generate();
        String origin = StringUtils.join(a, ',');
        System.out.println(origin);

        BubbleSort bubbleSort = new BubbleSort(a);
        bubbleSort.sort(true);

        String join = StringUtils.join(a, ',');
        System.out.println(join);
    }


}
