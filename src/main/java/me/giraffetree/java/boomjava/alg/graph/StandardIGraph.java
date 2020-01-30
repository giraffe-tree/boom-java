package me.giraffetree.java.boomjava.alg.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * 采用邻接表实现
 *
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class StandardIGraph implements IGraph {

    private final int V;
    private int E;
    private Set<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public StandardIGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (HashSet<Integer>[]) new HashSet[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new HashSet<>();
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
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

}
