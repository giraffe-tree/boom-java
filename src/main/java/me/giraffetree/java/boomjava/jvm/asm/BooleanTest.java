package me.giraffetree.java.boomjava.jvm.asm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author GiraffeTree
 * @date 2019/10/17
 */
public class BooleanTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        A a = new A();

        Field boolValueField = A.class.getDeclaredField("flag");
        unsafe.putInt(a, unsafe.objectFieldOffset(boolValueField),  1);

        if (a.flag) {
            System.out.println("Hello, Java!");
        }
        if (a.flag == true) {
            System.out.println("Hello, JVM!");
        }
    }


}

class A {
    boolean flag;
}