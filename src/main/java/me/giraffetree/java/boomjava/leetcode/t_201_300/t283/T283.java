package me.giraffetree.java.boomjava.leetcode.t_201_300.t283;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2021/3/15 18:07
 */
public class T283 {

    @Test
    public void test() {
        int retries = 100;
        for (int i = 0; i < retries; i++) {
            int[] arr = getArr(100);

        }


    }

    private void check(int[] a1, int[] a2) {
        int len = a1.length;
        int tmp = 0;
        for (int i = 0; i < len; i++) {
            if (a1[i] != 0) {
            }
        }
    }

    private static int[] getArr(int len) {
        ThreadLocalRandom cur = ThreadLocalRandom.current();
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            int c = cur.nextInt(0, 10);
            arr[i] = c;
        }
        return arr;
    }

    public void moveZeroes(int[] nums) {

    }

}
