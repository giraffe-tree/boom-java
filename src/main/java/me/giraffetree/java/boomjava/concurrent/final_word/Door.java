package me.giraffetree.java.boomjava.concurrent.final_word;

/**
 * 这是一个会在构造函数阶段 暴露自身实例引用 的类
 *
 * @author GiraffeTree
 * @date 2021-03-09
 */
public class Door {

    final int height;

    static Door defaultDoor;

    public Door() {
        // 当前这种执行顺序, 没有测试出 final
        this.height = 10;
        defaultDoor = this;
    }

    public Door(boolean order) {
        if (order) {
            defaultDoor = this;
            this.height = 10;
        } else {
            this.height = 10;
            defaultDoor = this;
        }
    }

    public static void write() {
        new Door();
    }

    public static void write(boolean order) {
        new Door(order);
    }

    public static int read() {
        if (defaultDoor != null) {
            return defaultDoor.height;
        }
        return 1;
    }

}
