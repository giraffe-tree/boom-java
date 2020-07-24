package me.giraffetree.java.boomjava.concurrent.problem.volatile_word;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=AtomicIntegerTest-hotspot.log
 * log 文件中你会发现, jvm 内部调用了 lock cmpxchg
 *
 * @author GiraffeTree
 * @date 2020/7/24
 */
public class AtomicIntegerTest {
    private final static AtomicInteger cache = new AtomicInteger(0);

    public static void main(String[] args) {
        long count = 1_000_000_000;
        loop(count);

    }

    private static void loop(long loop) {
        while (loop-- > 0L) {
            cache.incrementAndGet();
        }
    }

}
