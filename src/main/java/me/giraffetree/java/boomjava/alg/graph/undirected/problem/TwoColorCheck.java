package me.giraffetree.java.boomjava.alg.graph.undirected.problem;

import me.giraffetree.java.boomjava.alg.graph.Graph;

/**
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class TwoColorCheck {

    private boolean[] marked;
    private boolean[] color;

    private boolean isTwoColorable = true;

    public TwoColorCheck(Graph graph) {
        marked = new boolean[graph.V()];
        color = new boolean[graph.V()];
        for (int s = 0; s < graph.V(); s++) {
            if (!marked[s]) {
                dfs(graph, s);
            }
        }
    }

    private void dfs(Graph graph, int v) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(graph, w);
            } else if (color[w] == color[v]) {
                isTwoColorable = false;
                return;
            }
        }
    }

    public boolean isBipartite() {
        return isTwoColorable;
    }

}
