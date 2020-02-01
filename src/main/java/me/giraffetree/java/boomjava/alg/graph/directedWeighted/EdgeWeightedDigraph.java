package me.giraffetree.java.boomjava.alg.graph.directedWeighted;

import java.util.HashSet;
import java.util.Set;

/**
 * @author GiraffeTree
 * @date 2020/2/1
 */
public class EdgeWeightedDigraph {

    private final int V;
    private int E;
    private Set<DirectedEdge>[] adj;

    public EdgeWeightedDigraph(int v) {
        this.V = v;
        this.E = 0;
        adj = (Set<DirectedEdge>[]) new HashSet[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new HashSet<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge edge) {
        adj[edge.from()].add(edge);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }
    public Iterable<DirectedEdge> edges() {
        HashSet<DirectedEdge> set = new HashSet<>();
        for (int v = 0; v < V(); v++) {
            for (DirectedEdge e : adj[v]) {
                set.add(e);
            }
        }
        return set;
    }

}
