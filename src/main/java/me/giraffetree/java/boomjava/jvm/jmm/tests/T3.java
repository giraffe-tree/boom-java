package me.giraffetree.java.boomjava.jvm.jmm.tests;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.III_Result;

/**
 * -v -t me.giraffetree.java.boomjava.jvm.jmm.tests.T3
 * 没有发现 1,1,1 的情况
 *
 * @author GiraffeTree
 * @date 2021-03-10
 */
@JCStressTest
@Outcome(id = {"0, 1, 1", "0, 1, 0"}, expect = Expect.ACCEPTABLE, desc = "Normal outcome")
@Outcome(id = {"1, 1, 1"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Abnormal outcome")
@State
public class T3 {
    int a = 0;
    int b = 0;

    @Actor
    public void method1(III_Result r) {
        r.r1 = a;
        r.r2 = r.r1 | 1;
        b = r.r2;
    }

    @Actor
    public void method2(III_Result r) {
        r.r3 = b;
        a = r.r3;
    }

}
