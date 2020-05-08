package me.giraffetree.java.boomjava.jvm.method.overload;

/**
 * 如何把 java 编译器弄晕?
 *
 * @author GiraffeTree
 * @date 2020/5/8
 */
public class OverloadTest {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int a = 1;
        Integer b = 2;
        System.out.println(add(a, a));
        System.out.println(add(a, b));
        System.out.println(add(b, a));
        System.out.println(add(b, b));

        // java 8 中通不过编译
//        System.out.println(add(b, b, b, b));
//        System.out.println(add(b));
//        System.out.println(add(a, a, b));
//        System.out.println(add(null, 1, 1, 1, 1, 1));

        System.out.println(add(new int[]{}));
        System.out.println(add(new Integer[]{}));
        System.out.println(add(1, new int[]{}));
        System.out.println(add(new Integer(1), new int[]{}));
        System.out.println(add(1, new Integer[]{}));
        System.out.println(add(1, new long[]{}));
        System.out.println(add(1, new Object[]{}));


    }

    private static int add(int a, int b) {
        return 1;
    }

    private static int add(int a, Integer b) {
        return 2;
    }

    private static int add(Integer a, int b) {
        return 3;
    }

    private static int add(Integer a, Integer b) {
        return 4;
    }


    private static int add(int... x) {
        return 5;
    }

    private static int add(Integer... x) {
        return 6;
    }

    private static int add(int a, int... x) {
        return 7;
    }

    private static int add(Integer a, int... x) {
        return 8;
    }

    private static int add(int a, Integer... x) {
        return 9;
    }

    private static int add(int a, Object x) {
        return 10;
    }

    private static int add(int a, Object... x) {
        return 11;
    }
}
