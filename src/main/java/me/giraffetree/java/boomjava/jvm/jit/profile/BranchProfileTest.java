package me.giraffetree.java.boomjava.jvm.jit.profile;

/**
 * vm options: -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:+PrintAssembly -XX:+LogCompilation -XX:LogFile=hotspot.log
 * 可以将 hotspot.log 放入 jitwatch 中查看, 也可以直接在控制台看打印出的汇编代码
 * 部分代码来自:
 * https://time.geekbang.org/column/article/14070
 *
 * @author GiraffeTree
 * @date 2020/6/1
 */
public class BranchProfileTest {

    public static int foo(boolean f, int in) {
        int v;
        if (f) {
            v = in;
        } else {
            v = (int) Math.sin(in);
        }
        if (v == in) {
            return 0;
        } else {
            return (int) Math.cos(v);
        }
    }

    public static void main(String[] args) {
        int loop = 1_000_000_000;
        while (loop-- > 0) {
            foo(true, 1);
        }

    }

}
