package me.giraffetree.java.boomjava.alg.graph.directed;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020/2/27
 */
public class DigraphUtils {

    public static Digraph generate4_2_10() {
        // 用于生成 algorithms 图 4.2.10 顶点排序的示例
        StandardDigraph g = new StandardDigraph(13);
        g.addEdge(0, 5);
        g.addEdge(5, 4);
        g.addEdge(0, 1);
        g.addEdge(0, 6);
        g.addEdge(6, 9);
        g.addEdge(9, 11);
        g.addEdge(11, 12);
        g.addEdge(9, 10);
        g.addEdge(6, 4);
        g.addEdge(9, 12);
        g.addEdge(2, 0);
        g.addEdge(2, 3);
        g.addEdge(8, 7);
        g.addEdge(7, 6);

        return g;
    }

    public static Digraph generate() {
        return generate(8);
    }

    public static Digraph generate(int vCount) {
        return generate(vCount, vCount << 1);
    }

    public static Digraph generate(int vCount, int eCount) {
        StandardDigraph digraph = new StandardDigraph(vCount);
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < eCount; i++) {
            int i1 = current.nextInt(0, vCount);
            int i2 = current.nextInt(0, vCount);
            digraph.addEdge(i1, i2);
        }
        return digraph;
    }

}
