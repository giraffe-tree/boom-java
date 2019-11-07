package me.giraffetree.java.boomjava.jvm.jmm.singleton;

public interface Singleton {

    Byte x();

    public static int map(Singleton singleton) {
        if (singleton == null) {
            return 0;
        }
        if (singleton.x() == null) {
            return 1;
        }
        return singleton.x();
    }
}
