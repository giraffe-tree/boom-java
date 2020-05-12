package me.giraffetree.java.boomjava.jvm.reflect;

import java.lang.reflect.Method;

/**
 * 源码来自: https://time.geekbang.org/column/article/12192
 */
public class PolluteTest {
    public static void target(int i) {
        // 空方法
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("me.giraffetree.java.boomjava.jvm.reflect.PolluteTest");
        Method method = klass.getMethod("target", int.class);
        // 关闭权限检查
        method.setAccessible(true);
        // 本质上使得 method.invoke 无法内联
        polluteProfile();
        Object[] v = new Object[1];
        v[0] = 128;
        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }

            method.invoke(null, v);
        }
    }

    public static void polluteProfile() throws Exception {
        Method method1 = PolluteTest.class.getMethod("target1", int.class);
        Method method2 = PolluteTest.class.getMethod("target2", int.class);
        for (int i = 0; i < 2000; i++) {
            method1.invoke(null, 0);
            method2.invoke(null, 0);
        }
    }
    public static void target1(int i) { }
    public static void target2(int i) { }
}
