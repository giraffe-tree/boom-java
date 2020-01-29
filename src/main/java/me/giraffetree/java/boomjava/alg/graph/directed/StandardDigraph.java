package me.giraffetree.java.boomjava.alg.graph.directed;

import java.util.HashSet;
import java.util.Set;

/**
 * @author GiraffeTree
 * @date 2020/1/24
 */
public class StandardDigraph implements Digraph {

    private final int V;
    private int E;
    private Set<Integer>[] adj;

    public StandardDigraph(int v) {
        V = v;
        this.E = 0;
        adj = (Set<Integer>[]) new HashSet[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new HashSet<>();
        }
    }

    @Override
    public Digraph reverse() {
        Digraph tmp = new StandardDigraph(V);
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                tmp.addEdge(w, v);
            }
        }
        return tmp;
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
    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

}
