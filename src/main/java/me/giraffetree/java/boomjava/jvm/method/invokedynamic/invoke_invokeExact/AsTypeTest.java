package me.giraffetree.java.boomjava.jvm.method.invokedynamic.invoke_invokeExact;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * @author GiraffeTree
 * @date 2020-05-17
 */
public class AsTypeTest {

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findStatic(AsTypeTest.class, "print", MethodType.methodType(void.class, Number.class));

        // invoke 会调用 MethodHandle.asType 方法，生成一个适配器方法句柄，对传入的参数进行适配，再调用原方法句柄。
        // 调用原方法句柄的返回值同样也会先进行适配，然后再返回给调用者。
        Integer x = 1;
        methodHandle.invoke(x);

        MethodHandle handle = methodHandle.asType(MethodType.methodType(void.class, Integer.class));
        handle.invokeExact(x);

        // 反射API 为 java 语言层面的
        Method printMethod = AsTypeTest.class.getDeclaredMethod("print", Number.class);

    }

    static void print(Number number) {
        System.out.println(number);
    }

}
