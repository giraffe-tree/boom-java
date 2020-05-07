package me.giraffetree.java.boomjava.jvm.data_type.primitive.bool.bool_modify;

/**
 * @author GiraffeTree
 * @date 2019/11/6
 */
public class AsmBoolean {

    public static void main(String[] args) {

        boolean flag = true;
        // 这里判断 flag 是不是为 0, 如果是 0, 则跳转不进入if块
        // bytecode 为 ifeq
        if (flag) {
            System.out.println("Hello, Java!");
        }
        // 这里判断 flag 是不是 1 , 如果不是1, 则跳转不进入if块
        // bytecode 为 if_icmpne
        if (flag == true) {
            System.out.println("Hello, JVM!");
        }
        // 这里同第一次, 判断 flag 是不是  不为 0, 如果不是 0, 则跳转不进入if块
        //  bytecode 为 ifne
        if (!flag) {
            System.out.println("Hello, JVM! 2");
        }

    }

}
