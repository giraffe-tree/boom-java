package me.giraffetree.java.boomjava.jvm.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author GiraffeTree
 * @date 2020-06-10
 */
public class JavaUtilsJolTest {

    public static void main(String[] args) {

        print(String.class);
        long[] arr = {1L, 2L, 3L};
        print(arr);
        print(ArrayList.class);
        print(HashMap.class);
        print(ConcurrentHashMap.class);

    }

    private static <T> void print(Class<T> clz) {
        System.out.println(ClassLayout.parseClass(clz).toPrintable());
    }

    private static void print(Object object) {
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }

}
