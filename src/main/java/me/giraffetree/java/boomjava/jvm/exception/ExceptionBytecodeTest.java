package me.giraffetree.java.boomjava.jvm.exception;

/**
 * @author GiraffeTree
 * @date 2020-05-10
 */
public class ExceptionBytecodeTest {

    static void test() {
        int[] arr = new int[3];
        try {
            arr[4] = 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("process exception...");
        } finally {
            System.out.println("hello finally...");
        }
    }

    public static void main(String[] args) {

        test();
    }

}
