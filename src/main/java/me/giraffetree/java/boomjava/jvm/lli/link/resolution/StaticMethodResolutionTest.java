package me.giraffetree.java.boomjava.jvm.lli.link.resolution;

/**
 * 演示子类中调用 父类的静态方法
 *
 * @author GiraffeTree
 * @date 2020-05-08
 */
public class StaticMethodResolutionTest {

    public static void main(String[] args) {

        Son.hello();
        // 子类的静态方法会隐藏（注意与重写区分）父类中的同名、同描述符的静态方法。
        Son.say();

    }

}
