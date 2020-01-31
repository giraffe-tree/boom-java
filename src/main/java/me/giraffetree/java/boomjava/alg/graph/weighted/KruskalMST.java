package me.giraffetree.java.boomjava.alg.graph.weighted;

import me.giraffetree.java.boomjava.alg.basic.unionfind.UF;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * kruskal 算法
 * 用于解决最小生成树问题
 *
 * @author GiraffeTree
 * @date 2020/1/31
 */
public class KruskalMST {

    private Queue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph g) {
        mst = new LinkedList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (Edge e : g.edges()) {
            pq.add(e);
        }
        UF uf = new UF(g.V());
        while (!pq.isEmpty() && mst.size() < g.V() - 1) {
            Edge e = pq.remove();
            int v = e.either();
            int w = e.other(v);
            if (uf.connected(v, w)) {
                continue;
            }
            uf.union(v, w);
            mst.add(e);
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        // todo 实现
        return 0.0;
    }
}
