package me.giraffetree.java.boomjava.jvm.jmm.tests;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

/**
 *
 *   Observed state   Occurrences              Expectation  Interpretation
 *             0, 0             3   ACCEPTABLE_INTERESTING  Abnormal outcome
 *             0, 1    38,056,666                FORBIDDEN  No default case provided, assume FORBIDDEN
 *             1, 0    40,395,512                FORBIDDEN  No default case provided, assume FORBIDDEN
 *             1, 1    18,672,440               ACCEPTABLE  Normal outcome
 *
 * @author GiraffeTree
 * @date 2021-03-10
 */
@JCStressTest
@Outcome(id = {"1, 1"}, expect = Expect.ACCEPTABLE, desc = "Normal outcome")
@Outcome(id = {"0, 0"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Abnormal outcome")
@State
public class T1 {
    int a = 1;
    int b = 1;

    @Actor
    public void method1(II_Result r) {
        r.r2 = a;
        b = r.r1;
    }

    @Actor
    public void method2(II_Result r) {
        r.r1 = b;
        a = r.r2;
    }
}
