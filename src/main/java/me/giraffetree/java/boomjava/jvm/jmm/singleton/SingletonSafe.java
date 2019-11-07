package me.giraffetree.java.boomjava.jvm.jmm.singleton;

public class SingletonSafe implements Singleton {
    final Byte x;

    public SingletonSafe() { x = 42; }

    @Override
    public Byte x() {
        return x;
    }
}