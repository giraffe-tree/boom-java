package me.giraffetree.java.boomjava.jvm.data_type.primitive.bool.bool_heap;

public class InHeapboolean {

    static boolean v;

    public static void main(String[] args) {
        // 将这个true替换为2或者3，再看看打印结果
        v = true;
        if (v) {
            System.out.println("Hello, Java!");
        }
        if (v == true) {
            System.out.println("Hello, JVM!");
        }
    }
}