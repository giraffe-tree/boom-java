package me.giraffetree.java.boomjava.jvm.jmm;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * @author GiraffeTree
 * @date 2019/11/7
 */
@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "The object is published")
@Outcome(id = "0", expect = Expect.FORBIDDEN, desc = "The object is null")
@State
public class UnsafeSingleton {

    private Object object;

    @Actor
    public void calc01(I_Result r) {
        if (object == null) {
            synchronized (this) {
                if (object == null) {
                    object = new Object();
                }
            }
        }
        r.r1 = object == null ? 0 : 1;
    }

//    @Actor
//    public void calc02(I_Result r) {
//        if (object == null) {
//            synchronized (this) {
//                if (object == null) {
//                    object = new Object();
//                }
//            }
//        }
//        r.r1 = object == null ? 0 : 1;
//    }


}
