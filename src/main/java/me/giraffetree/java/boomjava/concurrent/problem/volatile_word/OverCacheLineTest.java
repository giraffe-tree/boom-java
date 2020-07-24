package me.giraffetree.java.boomjava.concurrent.problem.volatile_word;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * todo: 测试当一个 volatile 对象大于 cache line 时, 会不会发生锁总线的现象, 即不会发生 false sharing
 * 该测试基于以下猜想:
 * 个人理解, 现在有两个处理器P1,P2, 它们都想修改同一个内存区域的数据,
 * 如果没有 MESIF (缓存一致性机制) 它们会通过 总线LOCK 来确保不会造成写冲突;
 * 如果有了 MESIF, 并且要修改的这块内存区域完全包含在高速缓存行中, 处理器可以通过缓存一致性的机制,
 * 在处理器内部修改内存, 并确保该操作时原子执行的
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 思路
 * 1. lock 超出一个缓存行 好像很难用 java 去构造?
 * 2. java 能够生成 lock 前缀的指令的? 在我的印象中, 只有 volatile 已经 Unsafe 中的 cas
 * <p>
 * 测试条件
 * 1. cache line 为 64 字节
 * 2. java 8
 * <p>
 *
 * @author GiraffeTree
 * @date 2020/7/24
 */
public class OverCacheLineTest {

    private volatile long[] cache = new long[8];

    public static void main(String[] args) {
        // 8 * 16 = 128 字节, 这已经是两个 cache line 的大小了
        AtomicLongArray array = new AtomicLongArray(16);

    }


}
