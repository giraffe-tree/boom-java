package me.giraffetree.java.boomjava.alg.graph.weighted;

import java.util.HashSet;
import java.util.Set;

/**
 * @author GiraffeTree
 * @date 2020/1/30
 */
public class EdgeWeightedGraph implements IWeightedGraph {

    private final int V;
    private int E;
    private Set<Edge>[] adj;

    public EdgeWeightedGraph(int count) {
        this.V = count;
        this.E = 0;
        adj = new HashSet[count];
        for (int v = 0; v < count; v++) {
            adj[v] = new HashSet<>();
        }
    }


    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        return E;
    }

    @Override
    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    @Override
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }



}
