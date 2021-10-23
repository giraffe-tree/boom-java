package me.giraffetree.java.boomjava.jvm.stack.frame.local_variable;

import java.lang.management.MemoryUsage;

/**
 * 探究由于 局部变量表中含有 对大数组的引用 而不能及时进行垃圾回收的问题
 * 除了示例中打印gc信息的方法, 也可以使用 -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * <p>
 * 这里的 test03 / test04 对比, 侧面印证了 long 在局部变量区中占用 2 个 slot
 *
 * @author GiraffeTree
 * @date 2020/5/8
 */
public class LocalVariableGCTest {

    public static void main(String[] args) {
        double[] doubles = new double[10000];
        while (true) {
            int[] array = new int[10000];
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        test01();
//        test02();
//        test03();
//        test04();
    }

    private static void test01() {
        {
            byte[] _64M = new byte[1024 * 1024 * 64];
        }
        printGCInfo("test01 before gc");
        System.gc();
        // 未回收
        printGCInfo("test01 after gc");
    }

    private static void test02() {
        {
            byte[] _64M = new byte[1024 * 1024 * 64];
        }
        printGCInfo("test02 before gc");
        int x = 1;
        System.gc();
        // 回收了
        printGCInfo("test02 after gc");
    }

    private static void test03() {
        {
            int x = 1;
            byte[] _64M = new byte[1024 * 1024 * 64];
        }
        int y = 2;

        printGCInfo("test03 before gc");
        System.gc();
        // 未回收 _64M 的空间
        printGCInfo("test03 after gc");
    }

    private static void test04() {
        {
            int x = 1;
            byte[] _64M = new byte[1024 * 1024 * 64];
        }
        // 说明 long 类型的 y 占用两个 slot
        long y = 2;
        printGCInfo("test04 before gc");
        System.gc();
        // 已经回收 _64M 的空间
        printGCInfo("test04 after gc");
    }


    private static void printGCInfo(String prefix) {
        // 由于 64M 过大, 它们都将会被分配到 老年代中
        MemoryUsage edenUsage = GCDetailUtils.getUsage(GCDetailUtils.Region.PS_OLD_GEN);
        System.out.println(prefix + ": " + edenUsage.getUsed() / 1024 + "KB");
    }

}
