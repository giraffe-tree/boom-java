package me.giraffetree.java.boomjava.jvm.method.override;

/**
 * @author GiraffeTree
 * @date 2020-05-08
 */
public interface ISay3 {

    default int say() {
        System.out.println("world");
        return 0;
    }
}
