package me.giraffetree.java.boomjava.alg.sort;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class SortUtils {

    public static void checkSorted(Comparable[] a, boolean asc) {
        if (a == null) {
            return;
        }
        boolean sorted = true;
        for (int i = 1; i < a.length; i++) {
            if (asc) {
                if (greater(a[i - 1], a[i])) {
                    sorted = false;
                }
            } else {
                if (less(a[i - 1], a[i])) {
                    sorted = false;
                }
            }
        }
        if (!sorted) {
            throw new RuntimeException("array is not sorted - " + Arrays.toString(a));
        } else {
            System.out.println("This array is successfully sorted! ");
        }
    }

    public static boolean greater(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }

    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static void swap(Comparable[] a, int pos1, int pos2) {
        Comparable tmp = a[pos1];
        a[pos1] = a[pos2];
        a[pos2] = tmp;
    }

    public static void swap(int[] a, int pos1, int pos2) {
        int tmp = a[pos1];
        a[pos1] = a[pos2];
        a[pos2] = tmp;
    }

    public static int[] generate() {
        return generate(8, 30);
    }

    public static int[] generate(int count) {
        return generate(count, 30);
    }

    public static int[] generate(int count, int max) {
        int[] a = new int[count];
        for (int i = 0; i < count; i++) {
            a[i] = ThreadLocalRandom.current().nextInt(max);
        }
        return a;
    }

    public static Element[] generateObj() {
        return generateObj(8, 30, 5);
    }

    public static Element[] generateObj(int count, int max, int divisor) {
        Element[] a = new Element[count];
        for (int i = 0; i < count; i++) {
            int tmp = ThreadLocalRandom.current().nextInt(max);
            a[i] = new Element(tmp, divisor);
        }
        return a;
    }


}
