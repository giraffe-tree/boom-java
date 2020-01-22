package me.giraffetree.java.boomjava.alg.graph;

import java.util.LinkedList;

/**
 * 采用邻接表实现
 *
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class StandardGraph implements Graph {

    private final int V;
    private int E;
    private LinkedList<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public StandardGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (LinkedList<Integer>[]) new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
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
