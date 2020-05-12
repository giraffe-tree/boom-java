package me.giraffetree.java.boomjava.jvm.method.invokedynamic;

import java.util.stream.IntStream;

/**
 * @author GiraffeTree
 * @date 2020/5/12
 */
public class LambdaImplTest {

    public static void main(String[] args) {
        int sum = IntStream.of(1, 2, 3, 4, 5).reduce(Integer::sum).orElse(0);
        System.out.println(sum);
    }

}
