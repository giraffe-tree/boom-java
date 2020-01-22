package me.giraffetree.java.boomjava.alg.graph.undirected.problem;

import me.giraffetree.java.boomjava.alg.graph.Graph;

/**
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class CycleCheck {

    boolean[] marked;
    private boolean hasCycle;

    public static void main(String[] args) {


    }

    private boolean check(Graph graph) {
        marked = new boolean[graph.V()];
        for (int s = 0; s < graph.V(); s++) {
            if (!marked[s]) {
                dfs(graph, s, s);
            }
        }
        return hasCycle;
    }

    private void dfs(Graph graph, int v, int u) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(graph, w, v);
            } else if (w != u) {
                hasCycle = true;
                return;
            }
        }
    }

}
