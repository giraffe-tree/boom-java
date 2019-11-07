package me.giraffetree.java.boomjava.jvm.jmm.volatileee;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * @author GiraffeTree
 * @date 2019/11/7
 */
@JCStressTest
@Outcome(id = "2",expect = Expect.ACCEPTABLE,desc = "true...")
@Outcome(id = "1",expect = Expect.FORBIDDEN,desc = "false...")
@Outcome(id = "0",expect = Expect.FORBIDDEN,desc = "false...")
@State
public class VolatileTest {

    private volatile int value;

    @Actor
    public void actor1() {
        value++;
    }

    @Actor
    public void actor2() {
        value++;
    }

    @Arbiter
    public void arbiter(I_Result r) {
        r.r1 = value;
    }

}
