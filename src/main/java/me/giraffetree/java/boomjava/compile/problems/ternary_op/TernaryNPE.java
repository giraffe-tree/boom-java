package me.giraffetree.java.boomjava.compile.problems.ternary_op;

/**
 * 三元运算符 空指针问题
 * 第一次了解到这个问题时来自
 * https://mp.weixin.qq.com/s/iQ6qdNv7WTLa3drxNa9png
 * 关于反编译后的代码, 大家可以参考上面的链接
 *
 * @author GiraffeTree
 * @date 2020/4/27
 */
public class TernaryNPE {

    public static void main(String[] args) {

        Integer b = null;
        // 条件运算符是右结合的
        try {
            // 当第二，第三位操作数分别为基本类型和对象时，
            // 其中的对象就会拆箱为基本类型进行操作。
            Integer result = false ? 1 : b;
            System.out.println(result);
        } catch (NullPointerException e) {
            // NPE的原因应该是三目运算符和自动拆箱导致了空指针异常。
            System.out.println("oh! you catch a null pointer exception");
        }

    }

}
