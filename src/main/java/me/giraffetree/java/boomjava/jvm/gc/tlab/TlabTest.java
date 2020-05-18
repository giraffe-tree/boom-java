package me.giraffetree.java.boomjava.jvm.gc.tlab;

import org.openjdk.jol.info.ClassLayout;

/**
 * Thread local Allocate Buffer
 * todo: 本文件用于 TLAB 测试
 * -XX:+MaxTenuringThreshold=15
 * 测试多少次之后 对象会被晋升到 老年代
 * 测试失败!!!! 在 gc 之后没有看到 object header 中的预期变化
 *
 * @author GiraffeTree
 * @date 2020/5/18
 */
public class TlabTest {

    public static void main(String[] args) {

        testObjectHeaderAfterGC();

    }

    private static void testObjectHeaderAfterGC() {

        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        int max = 16;
        int loop = max;
        while (loop-- > 0) {
            String out = ClassLayout.parseInstance(obj).toPrintable();
            System.gc();
            System.out.println(String.format("loop:%d - %s", max - loop, out));
        }

    }


}
