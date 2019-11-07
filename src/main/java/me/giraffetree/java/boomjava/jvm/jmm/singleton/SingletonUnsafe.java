package me.giraffetree.java.boomjava.jvm.jmm.singleton;

public class SingletonUnsafe implements Singleton {
    Byte x;
    public SingletonUnsafe() { x = 42; }

    @Override
    public Byte x() {
        return x;
    }
}