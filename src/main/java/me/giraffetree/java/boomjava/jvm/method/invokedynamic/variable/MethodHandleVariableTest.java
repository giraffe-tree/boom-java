package me.giraffetree.java.boomjava.jvm.method.invokedynamic.variable;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

/**
 * @author GiraffeTree
 * @date 2020-05-16
 */
public class MethodHandleVariableTest {

    public static void main(String[] args) throws Throwable {
        Computer computer = new Computer(4, 8, "intel");
        core(computer);
    }

    private static void core(Computer computer) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle coreGetter = lookup.findGetter(Computer.class, "core", int.class);
        int core = (int) coreGetter.invokeExact(computer);
        System.out.println(String.format("computer core:%d", core));
    }

    static class Computer {
        int core;
        int memory;
        String cpu;

        public Computer(int core, int memory, String cpu) {
            this.core = core;
            this.memory = memory;
            this.cpu = cpu;
        }
    }


}
