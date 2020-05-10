package me.giraffetree.java.boomjava.jvm.method.override;

/**
 * @author GiraffeTree
 * @date 2020-05-08
 */
public interface ISay1 {

    default void say() {
        System.out.println("hello");
    }
}
