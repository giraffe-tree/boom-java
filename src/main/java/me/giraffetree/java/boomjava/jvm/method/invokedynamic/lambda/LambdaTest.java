package me.giraffetree.java.boomjava.jvm.method.invokedynamic.lambda;

import java.util.stream.IntStream;

/**
 * -Djdk.internal.lambda.dumpProxyClasses=D:\2019\October\git\boom-java\src\main\java\me\giraffetree\java\boomjava\jvm\method\invokedynamic\lambda
 *
 * @author GiraffeTree
 * @date 2020-05-17
 */
public class LambdaTest {

    public static void main(String[] args) {
        System.out.println("start...");
        OpAdd<String> op = (x, y) -> x + y;
        OpAdd<Integer> op2 = (x, y) -> x + y;
        // 中间有调用 ConstantCallSite 构造函数
        int x = 1;
        IntStream.of(1, 2, 3).map(i -> i * 2).map(i -> i * x);

    }

    interface OpAdd<T> {
        T add(T t1, T t2);
    }

}
