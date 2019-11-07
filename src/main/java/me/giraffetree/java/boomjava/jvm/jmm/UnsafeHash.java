package me.giraffetree.java.boomjava.jvm.jmm;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * @author GiraffeTree
 * @date 2019/11/7
 */
@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "The hash is published")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "The hash is error")
@State
public class UnsafeHash {

    int hash;

    @Actor
    public void hash01(I_Result r) {
        if (hash == 0) {
            hash = 1;
        }
        r.r1 = hash;
    }

    @Actor
    public void hash02(I_Result r) {
        if (hash == 0) {
            hash = 1;
        }
        r.r1 = hash;
    }

    public int hashcode_shared() {
        if (hash == 0) {
            hash = 1;
        }
        return hash;
    }

    public int hashcode_shared2() {
        int h = hash;
        if (hash != 0) {
            return h;
        }
        return (hash = 1);
    }

    public int hashcode_local() {
        int h = hash;
        if (h == 0) {
            hash = h = 1;
        }
        return h;
    }

}
