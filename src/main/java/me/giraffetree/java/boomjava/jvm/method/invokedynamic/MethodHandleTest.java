package me.giraffetree.java.boomjava.jvm.method.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 使用 lookup 暴露方法
 *
 * @author GiraffeTree
 * @date 2020/5/12
 */
public class MethodHandleTest {
    static class Book {
        static void read() {
            System.out.println("read book...");
        }


        static MethodHandles.Lookup getLookup() {
            // 方法句柄的访问权限不取决于方法句柄的创建位置，而是取决于 Lookup 对象的创建位置。
            return MethodHandles.lookup();
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodType methodType = MethodType.methodType(void.class);
        MethodHandles.Lookup lookup = Book.getLookup();
        MethodHandle read = lookup.findStatic(Book.class, "read", methodType);
        Object invoke = read.invoke();
        // 返回 null
        System.out.println(invoke);


    }

}
