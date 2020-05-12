package me.giraffetree.java.boomjava.jvm.reflect;

import java.lang.reflect.Method;

/**
 * 代码来自: https://time.geekbang.org/column/article/12192
 * 运行时 vm 参数: -verbose:class
 * 用于打印加载 class 的日志
 * 如果你想只通过生成字节码的方式完成反射, 请使用  `-Dsun.reflect.noInflation=true`
 *
 * @author GiraffeTree
 * @date 2020-05-11
 */
public class ReflectImplTest {

    public static void target(int i) {
        if (i == 15 || i == 0) {
            new Exception("#" + i).printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        Class klass = Class.forName("me.giraffetree.java.boomjava.jvm.reflect.ReflectImplTest");
        Method method = klass.getMethod("target", int.class);
        Method method1 = klass.getMethod("target", int.class);

        // false
        System.out.println(method == method1);
        // true
        System.out.println(method.equals(method1));

        int loop = 16;
        while (loop-- > 0) {
            // 默认 16 次后会动态生成字节码实现
            method.invoke(null, loop);
        }
        method.invoke(null, 33);
    }

}
