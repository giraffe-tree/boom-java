package me.giraffetree.java.boomjava.concurrent.sync_word.jit;

/**
 * todo: 目前还没有看明白JIT编译后的代码
 * <p>
 * 配置1 使用偏向锁
 * -XX:+UnlockDiagnosticVMOptions -XX:BiasedLockingStartupDelay=0 -XX:+PrintCompilation -XX:+PrintAssembly -XX:+LogCompilation -XX:LogFile=hotspot.log
 * 需要设置偏向锁的延迟为 0
 * 并打印汇编后的代码日志
 * <p>
 * 配置2 关闭偏向锁
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:+PrintAssembly -XX:+LogCompilation -XX:LogFile=hotspot.log -XX:-UseBiasedLocking
 * 关闭偏向锁, 明显变慢了很多
 *
 * @author GiraffeTree
 * @date 2020/6/1
 */
public class SyncJitTest {
    private static int x = 0;
    private final static Object obj = new Object();

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        int loop = 1_000_000_000;
        while (loop-- > 0) {
            synchronized (obj) {
                x++;
            }
        }
    }

}
