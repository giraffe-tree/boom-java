package me.giraffetree.java.boomjava.jvm.method.invokedynamic.callsite;

import java.lang.invoke.*;

/**
 * 示例来源:
 * https://time.geekbang.org/column/article/12574
 */
class Horse {
    public void race() {
        System.out.println("Horse.race()");
    }
}

class Deer {
    public void race() {
        System.out.println("Deer.race()");
    }
}

// javac Circuit.java
// java Circuit
public class Circuit {

    public static void startRace(Object obj) {
        // aload obj
        // invokedynamic race()
    }

    public static void main(String[] args) {
        startRace(new Horse());
//        startRace(new Deer());
    }

    public static CallSite bootstrap(MethodHandles.Lookup l, String name, MethodType callSiteType) throws Throwable {
        MethodHandle mh = l.findVirtual(Horse.class, name, MethodType.methodType(void.class));
        return new ConstantCallSite(mh.asType(callSiteType));
    }
}