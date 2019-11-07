package me.giraffetree.java.boomjava.jvm.jmm;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.L_Result;

/**
 * -v -t me.giraffetree.java.boomjava.jvm.jmm.StringBuilderTest
 */
@JCStressTest
@Description("Tests the StringBuilders are working good under concurrent updates.")
@Outcome(id = "b{4}", expect = Expect.ACCEPTABLE, desc = "All appends are visible.")
@Outcome(expect = Expect.ACCEPTABLE_INTERESTING, desc = "Other values are expected, threads are messing with each other.")
@State
public class StringBuilderTest {

    StringBuilder sb = new StringBuilder(0);

    @Actor
    public void actor1() {
        try {
            for (int i = 0; i < 2; ++i) {
                sb.append('b');
            }
        } catch (Exception e) {
            // most probably AIOOBE, expected for this test
        }
    }

    @Actor
    public void actor2() {
        try {
            for (int i = 0; i < 2; ++i) {
                sb.append('b');
            }
        } catch (Exception e) {
            // most probably AIOOBE, expected for this test
        }
    }

    @Arbiter
    public void tester(L_Result r) {
        try {
            r.r1 = sb.toString();
        } catch (Exception e) {
            r.r1 = "<ERROR>";
        }
    }

}