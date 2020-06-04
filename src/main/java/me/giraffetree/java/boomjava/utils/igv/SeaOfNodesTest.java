package me.giraffetree.java.boomjava.utils.igv;

/**
 * 以下为配置:
 * -XX:+UnlockExperimentalVMOptions
 * -XX:+UseJVMCICompiler
 * -Dgraal.Dump=:3
 * -Dgraal.MethodFilter=me.giraffetree.java.boomjava.utils.igv.SeaOfNodesTest.loop,me.giraffetree.java.boomjava.utils.igv.SeaOfNodesTest.gvn
 * -Dgraal.OptDeoptimizationGrouping=false
 * -Dgraal.PrintGraph=Network
 *
 * @author GiraffeTree
 * @date 2020/6/4
 */
public class SeaOfNodesTest {

    /**
     * 用于演示 ir 图
     */
    public static int loop(int count) {
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * Global Value Numbering
     * 用于发现并消除等量计算
     */
    public static int gvn(int a, int b) {
        int sum = a * b;
        if (a > 0) {
            sum += a * b;
        }
        if (b > 0) {
            sum += a * b;
        }
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {
        int c = 10_000_000;
        loop(c);
        while (c-- > 0) {
            gvn(1, 2);
        }
        // 需要等待一段时间, 让后台线程将数据传给 igv
        Thread.sleep(3000L);
    }
}
