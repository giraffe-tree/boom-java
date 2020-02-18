package me.giraffetree.java.boomjava.jvm.size;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;


/**
 * @author GiraffeTree
 * @date 2019-10-17
 */
public class GetObjectSize {

    /**
     * 在 64 位系统上, jvm上需要按照 8 个字节对齐,
     * 每个对象都有 12 个字节的 header, 所以最小的对象为 16 个字节
     */
    public static void main(String[] args) {

        long objectSize = ObjectSizeCalculator.getObjectSize(new Object());
        // out: 16
        System.out.println(objectSize);

        long intWrapperSize = ObjectSizeCalculator.getObjectSize(1);
        // out:16
        System.out.println(intWrapperSize);

        long longWrapperSize = ObjectSizeCalculator.getObjectSize(1L);
        // out:24
        System.out.println(longWrapperSize);

        Size24 size24 = new Size24();
        // out:24
        System.out.println(ObjectSizeCalculator.getObjectSize(size24));

        Size24_2 size24_2 = new Size24_2();
        System.out.println(ObjectSizeCalculator.getObjectSize(size24_2));

    }

    private static class Size24{
        int a;
        long b;
    }

    private static class Size24_2{
        int a;
        long b;
        byte c;
    }
}


