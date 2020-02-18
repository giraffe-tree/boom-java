package me.giraffetree.java.boomjava.jvm.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Contended;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 参考: https://www.iteye.com/blog/shihlei-2407693
 *
 * @author GiraffeTree
 * @date 2019-11-05
 */
public class JolTest {

    public static void main(String[] args) {

        // use compressedOops
//        printLayout(new Size16());
        // -XX:-UseCompressedOops
//        printLayout(new Size24());

        System.out.println(VM.current().details());
        printLayout(new ContendedClzA());
        // -XX:-UseCompressedOops -XX:-RestrictContended
        System.out.println(ClassLayout.parseClass(ContendedClzA.class).toPrintable());
        System.out.println(ClassLayout.parseClass(ContendedClzB.class).toPrintable());

    }

    private static void printLayout(Object object) {
        String s = ClassLayout.parseInstance(object).toPrintable();
        System.out.println(s);
    }

    private static long getSize(Object object) {
        return GraphLayout.parseInstance(object).totalSize();
    }

    private static Object generate() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("a", 1);
        map.put("b", "b");
        map.put("c", new Date());

        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        return map;
    }

    /**
     * 使用压缩指针
     */
    static class Size16 {
        private int a;
    }

    /**
     * 不使用压缩指针
     * -XX:-UseCompressedOops
     */
    static class Size24 {
        private int a;
    }

    /**
     * -XX:-UseCompressedOops -XX:-RestrictContended
     */
    static class ContendedClzA {
        int a;
        int b;
        /**
         * 要注意的是user classpath使用此注解默认是无效的，需要在jvm启动时设置-XX:-RestrictContended
         */
        @Contended
        int c;
        int d;
    }

    public static class ContendedClzB extends ContendedClzA {
        int e;
    }
}
