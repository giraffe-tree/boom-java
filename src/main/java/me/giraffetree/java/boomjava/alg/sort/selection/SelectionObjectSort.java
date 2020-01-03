package me.giraffetree.java.boomjava.alg.sort.selection;

import me.giraffetree.java.boomjava.alg.sort.Element;
import me.giraffetree.java.boomjava.alg.sort.SortUtils;

import java.util.Arrays;

/**
 * uncheck warning solution:
 * https://stackoverflow.com/questions/4830400/java-unchecked-call-to-comparetot-as-a-member-of-the-raw-type-java-lang-compa
 * 时间复杂度 n^2
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class SelectionObjectSort<T extends Comparable<? super T>> {

//    public void sort(T[] comparable, boolean asc) {
//        for (int i = 0; i < comparable.length; i++) {
//            int position = i;
//            for (int j = i + 1; j < comparable.length; j++) {
//                if (asc) {
//                    if (comparable[position].compareTo(comparable[j]) > 0) {
//                        position = j;
//                    }
//                } else {
//                    if (comparable[position].compareTo(comparable[j]) < 0) {
//                        position = j;
//                    }
//                }
//            }
//            SortUtils.swap(comparable, i, position);
//        }
//
//    }

    public static void sort(Comparable[] comparable, boolean asc) {
        for (int i = 0; i < comparable.length; i++) {
            int position = i;
            for (int j = i + 1; j < comparable.length; j++) {
                if (asc) {
                    if (comparable[position].compareTo(comparable[j]) > 0) {
                        position = j;
                    }
                } else {
                    if (comparable[position].compareTo(comparable[j]) < 0) {
                        position = j;
                    }
                }
            }
            SortUtils.swap(comparable, i, position);
        }

    }

    public static void main(String[] args) {
        Element[] elements = SortUtils.generateObj();
        System.out.println(Arrays.toString(elements));

        sort(elements, true);

        System.out.println(Arrays.toString(elements));


    }

}
