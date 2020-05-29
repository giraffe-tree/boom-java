package me.giraffetree.java.boomjava.utils.jmh.basic;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.openjdk.jmh.annotations.Mode.Throughput;

/**
 * 代码来自:
 * https://www.xncoding.com/2018/01/07/java/jmh.html
 * 比较字符串直接相加和StringBuilder的效率
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/1/7
 */
@BenchmarkMode(value = {Throughput})
@Warmup(iterations = 3)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(2)
@Fork(2)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StringBuilderBenchmark {

    @Benchmark
    public void testStringAdd() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
        print(a);
    }

    @Benchmark
    public void testStringBuilderAdd() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        print(sb.toString());
    }

    private void print(String a) {
    }
}