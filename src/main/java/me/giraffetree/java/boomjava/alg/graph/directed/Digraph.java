package me.giraffetree.java.boomjava.alg.graph.directed;

import me.giraffetree.java.boomjava.alg.graph.IGraph;

/**
 * @author GiraffeTree
 * @date 2020/1/24
 */
public interface Digraph extends IGraph {

    /**
     * 该图的反向图
     */
    default Digraph reverse() {
        int V = V();
        Digraph digraph = new StandardDigraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                digraph.addEdge(w, v);
            }
        }
        return digraph;
    }


}
