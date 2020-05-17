package me.giraffetree.java.boomjava.jvm.problem.newobj;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author GiraffeTree
 * @date 2020-05-17
 */
public class NewObjTest {

    public static void main(String[] args) {
        reflect();
        newWithClone();
        unsafeAllocateInstance();
    }

    private static void unsafeAllocateInstance() {
        // only in java 8
        Unsafe unsafe;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException e) {
            return;
        } catch (IllegalAccessException e) {
            return;
        }
        try {
            // 不会初始化
            Paper paper = (Paper) unsafe.allocateInstance(Paper.class);
            System.out.println("unsafeAllocateInstance:" + paper);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static void newWithClone() {
        // 复制字节
        Paper paper = new Paper(10);
        Object clone = paper.clone();
        System.out.println("clone:" + clone);
        System.out.println(clone == paper);

    }

    private static void reflect() {
        try {
            Constructor<Paper> constructor = Paper.class.getConstructor(int.class);
            Paper o = constructor.newInstance(1);
            System.out.println("reflect:" + o);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    static class Paper implements Cloneable {
        int size;

        public Paper(int size) {
            this.size = size;
        }

        @Override
        public Paper clone() {
            Paper p = null;
            try {
                p = (Paper) super.clone();
            } catch (CloneNotSupportedException ignored) {
                // 不会执行到
            }
            return p;
        }

        @Override
        public String toString() {
            return "Paper{" +
                    "size=" + size +
                    '}';
        }
    }


}
