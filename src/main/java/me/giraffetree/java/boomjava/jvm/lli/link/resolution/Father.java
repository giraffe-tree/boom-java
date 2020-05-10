package me.giraffetree.java.boomjava.jvm.lli.link.resolution;

/**
 * 用于测试静态方法的链接时的解析
 *
 * @author GiraffeTree
 * @date 2020-05-08
 */
public class Father {

    public static void say() {
        System.out.println("Father say...");
    }

    public static void hello() {
        System.out.println("Father hello...");
    }

}


class Son extends Father {
    public static void say() {
        System.out.println("Son say...");
    }
}