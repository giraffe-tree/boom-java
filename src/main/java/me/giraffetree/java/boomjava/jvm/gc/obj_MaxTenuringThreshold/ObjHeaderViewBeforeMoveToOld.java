package me.giraffetree.java.boomjava.jvm.gc.obj_MaxTenuringThreshold;

import org.openjdk.jol.info.ClassLayout;

/**
 * 用于探究对象在被移动到 old 代前, object header 的变化
 * 参考运行配置:
 * <p>
 * 可以用到的 vm options:
 * -XX:+MaxTenuringThreshold=15
 * -Xmx50M
 * -XX:+PrintGC
 *
 * @author GiraffeTree
 * @date 2020/5/25
 */
public class ObjHeaderViewBeforeMoveToOld {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Cap cap = new Cap(20);
        synchronized (cap) {
            System.out.println(String.format("%s - %s", "cap in sync", ClassLayout.parseInstance(cap).toPrintable()));
        }
        long loop = 1_000_000L;
        System.out.println(String.format("%s - %s", "before execute", ClassLayout.parseInstance(cap).toPrintable()));

        // 使用 long 避免计数循环导致的 safepoint 长时间到达不了
        for (long i = 0; i < loop; i++) {
            new Object().hashCode();
        }
        System.out.println(String.format("%s - %s", "after execute", ClassLayout.parseInstance(cap).toPrintable()));

    }

    /**
     * 16 byte object
     * 12B header + 4B int
     */
    private static class Cap {
        int size;

        public Cap(int size) {
            this.size = size;
        }
    }

}
