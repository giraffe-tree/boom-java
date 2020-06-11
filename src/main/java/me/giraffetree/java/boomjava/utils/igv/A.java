package me.giraffetree.java.boomjava.utils.igv;

class A {
    static Class<?> klass = B.class;
    static int i = C.i;

    static {
        System.out.print("A");
    }

    public static void main(String[] args) {
    }
}

class B {
    static {
        System.out.print("B");
    }
}

class C {
    static int i = 0;

    static {
        System.out.print("C");
    }
}
