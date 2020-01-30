package me.giraffetree.java.boomjava.alg.graph;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class GraphUtils {

    public static void main(String[] args) {

        IGraph g = generate(10, 5);
        int v = g.V();
        System.out.println(String.format("v:%d", v));

        Iterable<Integer> adj = g.adj(0);
        for (Integer a : adj) {
            System.out.println(a);
        }
    }

    public static IGraph generate() {
        return generate(10);
    }

    public static IGraph generate(int vertexCount) {
        // 冗余一倍空间, 方便测试时候添加 edge
        return generate(vertexCount, vertexCount);
    }

    public static IGraph generate(int vertexCount, int edgeCount) {
        StandardIGraph graph = new StandardIGraph(vertexCount);
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < edgeCount; i++) {
            int x1 = current.nextInt(vertexCount);
            int x2 = current.nextInt(vertexCount);
            graph.addEdge(x1, x2);
        }
        return graph;
    }

    public static IGraph generate123456() {
        StandardIGraph graph = new StandardIGraph(6);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        return graph;
    }

}
