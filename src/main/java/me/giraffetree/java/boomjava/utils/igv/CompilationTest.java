package me.giraffetree.java.boomjava.utils.igv;

/**
 * methodFilter: http://lafo.ssw.uni-linz.ac.at/javadoc/graalvm/all/com/oracle/graal/debug/MethodFilter.html
 * <p>
 * 目前在 idea 中测试可用的配置如下
 * 1. -XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler -XX:CompileCommand=dontinline,"CompilationTest::hash"
 * -Dgraal.Dump=:3 -Dgraal.MethodFilter=me.giraffetree.java.boomjava.utils.igv.CompilationTest.hash -Dgraal.OptDeoptimizationGrouping=false
 * 2. jre 为 11.0.7
 */
public class CompilationTest {

    public static int hash(Object input) {
        if (input instanceof Exception) {
            return System.identityHashCode(input);
        } else {
            return input.hashCode();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 500000; i++) {
            hash(i);
        }
        // 需要等待一段时间, 让后台线程将数据传给 igv
        Thread.sleep(2000);
    }

}
