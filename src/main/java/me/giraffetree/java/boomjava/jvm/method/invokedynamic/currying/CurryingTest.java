package me.giraffetree.java.boomjava.jvm.method.invokedynamic.currying;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author GiraffeTree
 * @date 2020-05-17
 */
public class CurryingTest {

    static int sum(int a, int b, int c) {
        return a + b + c;
    }
    public static void main(String[] args) throws Throwable {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findStatic(CurryingTest.class, "sum"
                , MethodType.methodType(int.class, int.class, int.class, int.class));

        MethodHandle method1 = MethodHandles.insertArguments(methodHandle, 0, 1);
        System.out.println((int) method1.invokeExact(2, 3));
        MethodHandle method2 = MethodHandles.insertArguments(method1, 0, 2);
        System.out.println((int) method2.invokeExact(3));
        MethodHandle method3 = MethodHandles.insertArguments(method2, 0, 3);
        System.out.println((int) method3.invokeExact());
    }
}
