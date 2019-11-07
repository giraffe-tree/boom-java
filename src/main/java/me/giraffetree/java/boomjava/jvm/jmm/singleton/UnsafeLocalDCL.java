package me.giraffetree.java.boomjava.jvm.jmm.singleton;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressMeta;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

import java.util.function.Supplier;

/**
 * Tests the unsafe double-checked locking singleton.
 *
 * @author Aleksey Shipilev (aleksey.shipilev@oracle.com)
 */
public class UnsafeLocalDCL {

    @JCStressTest
    @JCStressMeta(GradingUnsafe.class)
    public static class Unsafe {
        @Actor
        public final void actor1(UnsafeLocalDCLFactory s) {
            s.getInstance(SingletonUnsafe::new);
        }

        @Actor
        public final void actor2(UnsafeLocalDCLFactory s, I_Result r) {
            r.r1 = Singleton.map(s.getInstance(SingletonUnsafe::new));
        }
    }

    @JCStressTest
    @JCStressMeta(GradingSafe.class)
    public static class Safe {
        @Actor
        public final void actor1(UnsafeLocalDCLFactory s) {
            s.getInstance(SingletonSafe::new);
        }

        @Actor
        public final void actor2(UnsafeLocalDCLFactory s, I_Result r) {
            r.r1 = Singleton.map(s.getInstance(SingletonSafe::new));
        }
    }

    @State
    public static class UnsafeLocalDCLFactory {
        private Singleton instance; // specifically non-volatile

        public Singleton getInstance(Supplier<Singleton> s) {
            Singleton i = instance;
            if (i == null) {
                synchronized (this) {
                    i = instance;
                    if (i == null) {
                        i = s.get();
                        instance = i;
                    }
                }
            }
            return i;
        }
    }

}