package me.giraffetree.java.boomjava.jvm.method.override;

/**
 * 接口的默认方法, 很容易导致类似多继承的问题
 * 下面这个示例只能说勉强能用,
 * 但如果再实现一个 ISay3 就出现大问题了
 *
 * @author GiraffeTree
 * @date 2020-05-08
 */
public class Singer implements ISay1, ISay2 {

    @Override
    public void say() {
        System.out.println("singer say no");
    }

    public static void main(String[] args) {

        Singer singer = new Singer();
        singer.say();
    }


}
