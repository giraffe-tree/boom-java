package me.giraffetree.java.boomjava.weekly.w20211017;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author GiraffeTree
 * @date 2021/10/17
 */
public class ConcurrentHashMapPutWhenForEach {

    public static void main(String[] args) {
        testSizeCtl();
    }

    /**
     * 1. sizeCtl = 0 数组尚未初始化
     * 2. sizeCtl >0
     *     1. 如果数组未初始化, 则代表初始容量
     *     2. 如果数组已经初始化, 则代表扩容阈值
     * 3. sizeCtl = -1 数组正在进行初始化
     * 4. sizeCtl < -1 时, sizeCtl = -(1+n)  代表有 n 个线程正在共同完成数组的扩容操作
     */
    private static void testSizeCtl() {
        ConcurrentHashMap<Integer, Integer> m1 = new ConcurrentHashMap<>();
        int sizeCtlForM1 = getIntField(m1, ConcurrentHashMap.class, "sizeCtl", true);
        System.out.println(sizeCtlForM1 == 0);
        ConcurrentHashMap<Integer, Integer> m2 = new ConcurrentHashMap<>(16);
        int sizeCtlForM2 = getIntField(m2, ConcurrentHashMap.class, "sizeCtl", true);
        System.out.println(sizeCtlForM2 == 32);

    }

    private static int getIntField(Object o1, Class<?> clz, String fieldName, boolean needSetAccessible) {
        try {
            Field field = clz.getDeclaredField(fieldName);
            if (needSetAccessible) {
                field.setAccessible(true);
            }
            return field.getInt(o1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
