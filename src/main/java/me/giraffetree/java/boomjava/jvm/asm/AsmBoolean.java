package me.giraffetree.java.boomjava.jvm.asm;

/**
 * @author GiraffeTree
 * @date 2019/11/6
 */
public class AsmBoolean {

    public static void main(String[] args) {

        boolean flag = true;
        if (flag) {
            System.out.println("Hello, Java!");
        }
        if (flag == true) {
            System.out.println("Hello, JVM!");
        }
        if (flag) {
            System.out.println("Hello, JVM! 2");
        }
    }


}
