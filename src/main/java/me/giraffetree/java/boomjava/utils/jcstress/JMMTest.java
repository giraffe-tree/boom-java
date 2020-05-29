package me.giraffetree.java.boomjava.utils.jcstress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

/**
 * run:
 * main class: org.openjdk.jcstress.Main
 * args: -v -t me.giraffetree.java.boomjava.utils.jcstress.JMMTest
 * <p>
 * out:
 * Observed state   Occurrences              Expectation  Interpretation
 * 0, 0     1,169,075               ACCEPTABLE  Normal outcome
 * 0, 2    17,479,421               ACCEPTABLE  Normal outcome
 * 1, 0    12,032,043               ACCEPTABLE  Normal outcome
 * 1, 2            11   ACCEPTABLE_INTERESTING  Abnormal outcome
 * <p>
 * 可以看到有 11 次的结果为 (1,2)
 * 说明指令重排存在!
 *
 * @author GiraffeTree
 * @date 2019/11/6
 */
@JCStressTest
@Outcome(id = {"0, 0", "0, 2", "1, 0"}, expect = Expect.ACCEPTABLE, desc = "Normal outcome")
@Outcome(id = {"1, 2"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Abnormal outcome")
@State
public class JMMTest {

    int a = 0;
    /**
     * 改成volatile试试
     */
    int b = 0;

    @Actor
    public void method1(II_Result r) {
        r.r2 = a;
        b = 1;
    }

    @Actor
    public void method2(II_Result r) {
        r.r1 = b;
        a = 2;
    }

}
