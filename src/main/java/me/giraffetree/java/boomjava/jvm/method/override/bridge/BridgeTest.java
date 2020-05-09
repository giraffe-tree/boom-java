package me.giraffetree.java.boomjava.jvm.method.override.bridge;

/**
 * @author GiraffeTree
 * @date 2020/5/9
 */
public class BridgeTest {

    private abstract static class Fruit {
        abstract Number eat();
    }

    private static class Apple extends Fruit {

        @Override
        Integer eat() {
            System.out.println("apple eat...");
            return 1;
        }
    }

}
