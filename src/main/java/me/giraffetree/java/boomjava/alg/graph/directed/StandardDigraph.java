package me.giraffetree.java.boomjava.alg.graph.directed;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author GiraffeTree
 * @date 2020/1/24
 */
public class StandardDigraph implements Digraph {

    private final int V;
    private int E;
    private Set<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public StandardDigraph(int v) {
        V = v;
        this.E = 0;
        adj = (Set<Integer>[]) new LinkedHashSet[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new LinkedHashSet<>();
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
        Set<Integer> set = adj[v];
        if (!set.contains(w)) {
            set.add(w);
            E++;
        }
    }

    @Override
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("G:\n").append("v=").append(V).append("\n").append("E=").append(E).append("\n");
        for (int i = 0; i < V; i++) {
            Set<Integer> set = adj[i];
            sb.append(i).append("->").append(Arrays.toString(set.toArray())).append("\n");
        }

        return sb.toString();
    }
}
