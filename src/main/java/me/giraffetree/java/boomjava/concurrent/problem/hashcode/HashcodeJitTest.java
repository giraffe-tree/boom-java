package me.giraffetree.java.boomjava.concurrent.problem.hashcode;

/**
 * @author GiraffeTree
 * @date 2020/6/1
 */
public class HashcodeJitTest {
    static volatile int v = 0;

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int loop = 1_000_000;
        int x = 0;
        while (loop-- > 0) {
            v = loop;
            Object obj = new Object();
            x = System.identityHashCode(obj);
        }
        System.out.println(x);
        System.out.println(v);
    }

}
