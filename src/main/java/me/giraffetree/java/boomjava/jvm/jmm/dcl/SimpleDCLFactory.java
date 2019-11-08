package me.giraffetree.java.boomjava.jvm.jmm.dcl;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = {"1, 2"}, expect = Expect.ACCEPTABLE, desc = "The result is true")
@Outcome(id = {"0, 2", "1, 0", "0, 0"}, expect = Expect.FORBIDDEN, desc = "error ...")
@State
public class SimpleDCLFactory {

    /**
     * specifically non-volatile
     */
    private ObjectField2 instance;

    public ObjectField2 getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = new ObjectField2(1, 2);
                }
            }
        }
        return instance;
    }

    @Actor
    public void actor1(II_Result r) {
        ObjectField2 instance = getInstance();
        r.r1 = instance.a;
        r.r2 = instance.b;
    }

    @Actor
    public void actor2() {
        ObjectField2 instance = getInstance();

    }

    private static class ObjectField2 {
        public int a;
        public int b;

        public ObjectField2(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    private static class ObjectField1 {
        public int a;

        public ObjectField1(int a) {
            this.a = a;
        }
    }
}