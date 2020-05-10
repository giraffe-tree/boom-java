package me.giraffetree.java.boomjava.jvm.method.inline_cache;

import java.util.Arrays;

/**
 * 本示例用于研究: hotspot 中的单态内联缓存
 * <p>
 * Java 虚拟机中的即时编译器会使用内联缓存来加速动态绑定。Java 虚拟机所采用的单态内联缓存将纪录调用者的动态类型，
 * 以及它所对应的目标方法。当碰到新的调用者时，如果其动态类型与缓存中的类型匹配，则直接调用缓存的目标方法。
 * 否则，Java 虚拟机将该内联缓存劣化为超多态内联缓存，在今后的执行过程中直接使用方法表进行动态绑定。
 * <p>
 * Run with:
 * `java -XX:CompileCommand=dontinline,*.passThroughImmigration Passenger`
 * `dontinline,*.passThroughImmigration` 这个指令禁止了所有类的 `passThroughImmigration` 方法内联
 * <p>
 * 代码改编自:
 * https://time.geekbang.org/column/article/12098
 * 结果列表:
 * 前提: 默认使用内联
 * 可以不使用 -XX 指定任何参数 也可以 -XX:CompileCommand=inline,*.passThroughImmigration  或者 -XX:CompileCommand="dontinline,*.passThroughImmigration"
 * int loop = 1000_000_000;
 * int retry = 10;
 * 结果如下:
 * c average:0.80ms [4, 4, 0, 0, 0, 0, 0, 0, 0, 0]
 * f average:873.30ms [1026, 855, 857, 854, 855, 854, 857, 854, 855, 866]
 * alternately average:610.00ms [4, 863, 362, 873, 384, 1043, 433, 913, 360, 865]
 * <p>
 * 前提: -XX:CompileCommand=dontinline,*.passThroughImmigration  禁用 passThroughImmigration 的内联
 * 并且每次仅仅启动一个 test开头的方法
 * int loop = 1000_000_000;
 * int retry = 10;
 * 结果如下:
 * c average:2085.20ms [2241, 2091, 2052, 2035, 2240, 2037, 2034, 2074, 2021, 2027]
 * f average:2059.80ms [2026, 2029, 2024, 2026, 2091, 2125, 2025, 2073, 2066, 2113]
 * alternately average:1896.30ms [1923, 1994, 1766, 2003, 1764, 2000, 1759, 1993, 1761, 2000]
 */
public abstract class Passenger {

    abstract void passThroughImmigration();

    abstract String getName();

    public static void main(String[] args) {
        Passenger a = new ChinesePassenger();
        Passenger b = new ForeignerPassenger();
        int loop = 100_000_000;
        int retry = 10;
        testAverage(a, loop, retry);
        testAverage(b, loop, retry);
        testAlternately(a, b, loop, retry);
    }

    private static void testAverage(Passenger p, int loop, int retry) {
        p.passThroughImmigration();
        long all = 0L;
        long[] result = new long[retry];
        int r = retry;
        while (r-- > 0) {
            int c = loop;
            long l1 = System.currentTimeMillis();
            while (c-- > 0) {
                p.passThroughImmigration();
            }
            long l2 = System.currentTimeMillis();
            all += l2 - l1;
            result[retry - r - 1] = l2 - l1;
        }

        System.out.println(String.format("%s average:%.2fms %s", p.getName(), (double) all / retry, Arrays.toString(result)));
    }

    private static void testAlternately(Passenger a, Passenger b, int loop, int retry) {
        long all = 0L;
        int r = retry;
        long[] result = new long[retry];
        Passenger x;
        boolean cur = false;
        while (r-- > 0) {
            int c = loop;
            x = cur ? a : b;
            long l1 = System.currentTimeMillis();
            while (c-- > 0) {
                x.passThroughImmigration();
            }
            long l2 = System.currentTimeMillis();
            all += l2 - l1;
            result[retry - r - 1] = l2 - l1;
            cur = !cur;
        }
        System.out.println(String.format("alternately average:%.2fms %s", (double) all / retry, Arrays.toString(result)));
    }

}

class ChinesePassenger extends Passenger {
    @Override
    void passThroughImmigration() {
    }

    @Override
    String getName() {
        return "c";
    }
}

class ForeignerPassenger extends Passenger {

    @Override
    void passThroughImmigration() {
    }

    @Override
    String getName() {
        return "f";
    }
}
