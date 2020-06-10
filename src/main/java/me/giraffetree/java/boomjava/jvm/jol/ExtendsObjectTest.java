package me.giraffetree.java.boomjava.jvm.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 实验结果:
 * 1. 子类所继承字段的偏移量，和父类对应字段的偏移量保持一致, 但是对于对象最后的 padding gap, 子类并不需要完全空出
 * 2. 如果一个字段占据 C 个字节，那么该字段的偏移量需要对齐至 NC。这里偏移量指的是字段地址与对象的起始地址差值。(来源于 https://time.geekbang.org/column/article/13081)
 * 3. 目前看起来, 类中持有的引用类型的对象会放到对象的末尾
 * <p>
 * 对象默认使用 8 字节的内存对齐
 * 可以使用  `-XX:ObjectAlignmentInBytes=16` 修改对象的内存对齐
 *
 * @author GiraffeTree
 * @date 2020-06-10
 */
public class ExtendsObjectTest {


    public static void main(String[] args) {
        Class x = Cat.class;
        System.out.println(ClassLayout.parseClass(x).toPrintable());
    }

    private static class Animal {
        protected boolean inSea;
        protected long number;
        protected double weight;
        protected int type;
    }

    private static class Cat extends Animal {
        private boolean gender;
        private byte y;
        private String name;
        private int color;
        int type;
    }

}

