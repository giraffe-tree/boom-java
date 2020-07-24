package me.giraffetree.java.boomjava.concurrent.problem.volatile_word;

/**
 * 用于查看反汇编代码, 生成log文件, 需要 JITwatch 工具
 * -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=hotspot.log
 * 在 log 文件中可以看到 lock add dword ptr [rsp],0h  ;*putstatic x
 *
 * @author GiraffeTree
 * @date 2020-05-10
 */
public class VolatileWriteTest {

    private static volatile int x = 0;

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int loop = 100_000_000;
        while (loop-- > 0) {
            x = loop;
        }
    }

}
