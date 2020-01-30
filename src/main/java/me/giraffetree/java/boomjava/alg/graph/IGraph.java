package me.giraffetree.java.boomjava.alg.graph;

/**
 * @author GiraffeTree
 * @date 2020/1/21
 */
public interface IGraph {

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
    void addEdge(int v, int w);

    /**
     * 返回和 v 相邻的所有顶点
     */
    Iterable<Integer> adj(int v);

    static int degree(IGraph g, int v) {
        int degree = 0;
        for (int w : g.adj(v)) {
            degree++;
        }
        return degree;
    }

    static int maxDegree(IGraph graph) {
        int max = 0;
        for (int v = 0; v < graph.V(); v++) {
            int cur = degree(graph, v);
            if (cur > max) {
                max = cur;
            }
        }
        return max;
    }

    static double avgDegree(IGraph graph) {
        return 2.0 * graph.E() / graph.V();
    }

    static int numberOfSelfLoops(IGraph graph) {
        int count = 0;
        for (int v = 0; v < graph.V(); v++) {
            for (int w : graph.adj(v)) {
                if (v == w) {
                    count++;
                }
            }
        }
        return count / 2;
    }

}
