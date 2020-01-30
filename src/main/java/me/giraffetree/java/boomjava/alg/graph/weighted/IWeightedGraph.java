package me.giraffetree.java.boomjava.alg.graph.weighted;

import java.util.HashSet;

/**
 * @author GiraffeTree
 * @date 2020/1/30
 */
public interface IWeightedGraph {
    /**
     * 顶点数
     * vertex count
     */
    int V();

    /**
     * 边数
     * edge count
     */
    int E();

    /**
     * 向图中添加一条边 v-w
     */
    void addEdge(Edge e);

    /**
     * 返回和 v 相邻的所有顶点
     */
    Iterable<Edge> adj(int v);

    static int degree(IWeightedGraph g, int v) {
        int degree = 0;
        for (Edge w : g.adj(v)) {
            degree++;
        }
        return degree;
    }

    static int maxDegree(IWeightedGraph graph) {
        int max = 0;
        for (int v = 0; v < graph.V(); v++) {
            int cur = degree(graph, v);
            if (cur > max) {
                max = cur;
            }
        }
        return max;
    }

    static double avgDegree(IWeightedGraph graph) {
        return 2.0 * graph.E() / graph.V();
    }

    default Iterable<Edge> edges(){
        HashSet<Edge> set = new HashSet<>();
        for (int v = 0; v < V(); v++) {
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    set.add(e);
                }
            }
        }
        return set;
    };

}
