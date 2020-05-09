package me.giraffetree.java.boomjava.jvm.method.override.bridge;

/**
 * @author GiraffeTree
 * @date 2020/5/9
 */
public class GenericBridgeTest {
    private class Room<T> {
        public void add(T t) {
        }
    }

    private class ClassRoom extends Room<String> {

        @Override
        public void add(String s) {
            System.out.println("add:" + s);
        }
    }
}
