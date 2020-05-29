package me.giraffetree.java.boomjava.jvm.lli.load.interface_init;

/**
 * @author GiraffeTree
 * @date 2020/5/29
 */
public class InterfaceInitTest {


}

interface Read {

    default void read() {
        System.out.println("reading...");
    }
}
