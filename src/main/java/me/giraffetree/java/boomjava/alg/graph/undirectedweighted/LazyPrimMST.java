package me.giraffetree.java.boomjava.alg.graph.undirectedweighted;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 最小生成树的Prim 算法的延时实现
 * 空间复杂度: O(E)
 * 时间复杂度: O(ElogE)
 *
 * @author GiraffeTree
 * @date 2020/1/30
 */
public class LazyPrimMST {

    /**
     * 最小生成树的顶点
     */
    private boolean[] marked;

    /**
     * 最小生成树的边
     */
    private Queue<Edge> mst;

    /**
     * 横切边
     * 包括失效的边
     */
    private PriorityQueue<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph g) {
        marked = new boolean[g.V()];
        mst = new LinkedList<>();
        pq = new PriorityQueue<>();
        visit(g, 0);
        while (!pq.isEmpty()) {
            // 得到权值最小的边
            Edge e = pq.remove();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) {
                // 跳过失效的边
                continue;
            }
            mst.add(e);
            if (!marked[v]) {
                visit(g, v);
            }
            if (!marked[w]) {
                visit(g, w);
            }
        }
    }

    private void visit(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            if (!marked[e.other(v)]) {
                pq.add(e);
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return 0;
    }


}
