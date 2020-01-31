package me.giraffetree.java.boomjava.alg.graph.weighted;

import java.util.HashSet;

/**
 * Prim 的即时实现
 * 时间复杂度: O(ElogV) (最坏情况)
 * 空间复杂度: O(V)
 *
 * @author GiraffeTree
 * @date 2020/1/30
 */
public class PrimMST {

    /**
     * edgeTo[v] 是 v 和树连接的最短边
     */
    private Edge[] edgeTo;
    /**
     * distTo[v] 是 { edgeTo[v], v } 这条边的权值
     */
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    public PrimMST(EdgeWeightedGraph g) {
        edgeTo = new Edge[g.V()];
        distTo = new double[g.V()];
        marked = new boolean[g.V()];
        for (int v = 0; v < g.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        pq = new IndexMinPQ<>(g.V());
        distTo[0] = 0.0;
        pq.insert(0, 0.0);
        while (!pq.isEmpty()) {
            visit(g, pq.delMin());
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);
            if (marked[w]) {
                continue;
            }
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w)) {
                    pq.change(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        HashSet<Edge> set = new HashSet<>();
        for (int i = 1; i < edgeTo.length; i++) {
            set.add(edgeTo[i]);
        }
        return set;
    }

    public double weight() {
        // todo
        return 0;
    }


}
