package me.giraffetree.java.boomjava.jvm.jmm.tests;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.III_Result;
import org.openjdk.jcstress.infra.results.II_Result;

/**
 *  -v -t me.giraffetree.java.boomjava.jvm.jmm.tests.T2
 *
*   Observed state   Occurrences              Expectation  Interpretation
*          1, 1, 1    68,700,552               ACCEPTABLE  Normal outcome
*          1, 1, 2    21,873,747                FORBIDDEN  No default case provided, assume FORBIDDEN
*          2, 2, 2           162   ACCEPTABLE_INTERESTING  Abnormal outcome
 *
 * @author GiraffeTree
 * @date 2021-03-10
 */
@JCStressTest
@Outcome(id = {"1, 1, 1"}, expect = Expect.ACCEPTABLE, desc = "Normal outcome")
@Outcome(id = {"2, 2, 2"}, expect = Expect.ACCEPTABLE_INTERESTING, desc = "Abnormal outcome")
@State
public class T2 {
    int a = 1;
    int b = 1;

    @Actor
    public void method1(III_Result r) {
        r.r1 = a;
        r.r2 = a;
        if (r.r1 == r.r2) {
            b = 2;
        }
    }

    @Actor
    public void method2(III_Result r) {
        r.r3 = b;
        a = r.r3;
    }
}
