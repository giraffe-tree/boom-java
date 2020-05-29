package me.giraffetree.java.boomjava.jvm.lli.load;

/**
 * 添加虚拟机参数观察 class 加载情况
 * -verbose:class
 * -XX:+TraceClassLoading
 * <p>
 * 示例代码来源:
 * https://time.geekbang.org/column/article/11523
 */
public class Singleton {

    private Singleton() {

    }

    private static class LazyHolder {
        static final Singleton INSTANCE = new Singleton();

        static {
            // clinit 方法在类加载的初始化阶段完成
            System.out.println("LazyHolder...");
        }
    }

    public static Object getInstance(boolean flag) {
        if (flag) {
            return new LazyHolder[2];
        }
        return LazyHolder.INSTANCE;
    }

    public static void main(String[] args) {
        getInstance(true);
        System.out.println("已经完成LazyHolder 的加载, 但没有链接.");
        getInstance(false);
        System.out.println("已经完成LazyHolder 的链接和初始化");
    }

}