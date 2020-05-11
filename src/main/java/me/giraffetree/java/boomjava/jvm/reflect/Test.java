package me.giraffetree.java.boomjava.jvm.reflect;

import java.util.concurrent.TimeUnit;

/**
 * 示例代码来自:
 * https://stackoverflow.com/questions/1392351/java-reflection-why-is-it-so-slow
 * wtf?????? jdk 1.8.0_161
 * Reflecting instantiation took:1826ms
 * Normal instaniation took: 9232ms
 * jdk 11.0.7
 * Reflecting instantiation took:293ms
 * Normal instaniation took: 182ms
 *
 * 这提升?? 太香了吧
 */
public class Test {

    static class B {

    }

    public static long timeDiff(long old) {
        return System.nanoTime() - old;
    }

    public static void main(String args[]) throws Exception {

        int numTrials = 10000000;
        B[] bees = new B[numTrials];
        Class<B> c = B.class;
        for (int i = 0; i < numTrials; i++) {
            bees[i] = c.newInstance();
        }
        for (int i = 0; i < numTrials; i++) {
            bees[i] = new B();
        }

        long nanos;

        nanos = System.nanoTime();
        for (int i = 0; i < numTrials; i++) {
            bees[i] = c.newInstance();
        }
        System.out.println("Reflecting instantiation took:" + TimeUnit.NANOSECONDS.toMillis(timeDiff(nanos)) + "ms");

        nanos = System.nanoTime();
        for (int i = 0; i < numTrials; i++) {
            bees[i] = new B();
        }
        System.out.println("Normal instaniation took: " + TimeUnit.NANOSECONDS.toMillis(timeDiff(nanos)) + "ms");
    }


}