package me.giraffetree.java.boomjava.jvm.jmm.singleton;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressMeta;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

/**
 * Tests the singleton factory.
 *
 * @author Aleksey Shipilev (aleksey.shipilev@oracle.com)
 */
public class Holder {

    @JCStressTest
    @JCStressMeta(GradingSafe.class)
    public static class Unsafe {
        @Actor
        public final void actor1(UnsafeHolderFactory s) {
            s.getInstance();
        }

        @Actor
        public final void actor2(UnsafeHolderFactory s, I_Result r) {
            r.r1 = Singleton.map(s.getInstance());
        }
    }

    @JCStressTest
    @JCStressMeta(GradingSafe.class)
    public static class Safe {
        @Actor
        public final void actor1(SafeHolderFactory s) {
            s.getInstance();
        }

        @Actor
        public final void actor2(SafeHolderFactory s, I_Result r) {
            r.r1 = Singleton.map(s.getInstance());
        }
    }

    @State
    public static class SafeHolderFactory {
        public Singleton getInstance() {
            return H.INSTANCE;
        }

        public static class H {
            public static final Singleton INSTANCE = new SingletonSafe();
        }
    }

    @State
    public static class UnsafeHolderFactory {
        public Singleton getInstance() {
            return H.INSTANCE;
        }

        public static class H {
            public static final Singleton INSTANCE = new SingletonUnsafe();
        }
    }

}