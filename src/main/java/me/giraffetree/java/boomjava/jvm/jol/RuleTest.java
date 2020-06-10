package me.giraffetree.java.boomjava.jvm.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author GiraffeTree
 * @date 2020-06-10
 */
public class RuleTest {

    public static void main(String[] args) {

        System.out.println(ClassLayout.parseClass(Rule3.class).toPrintable());
    }

    private static class Rule1 {
        int a;
        long b;
        byte c;
        short d;
        float e;
    }

    private static class Rule3 {
        long b;
        String name;
        long c;
        int e;
        byte f;
    }

}
