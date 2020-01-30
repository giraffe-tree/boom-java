package me.giraffetree.java.boomjava.alg.graph.undirected;

import me.giraffetree.java.boomjava.alg.graph.IGraph;

/**
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class ConnectedComponent {

    private boolean[] marked;

    /**
     * 顶点索引数组
     * 如果 v 属于 第 i 个连通发呢量, 则 id[v]=i;
     */
    private int[] id;

    private int count;

    public ConnectedComponent(IGraph graph) {
        marked = new boolean[graph.V()];
        id = new int[graph.V()];
        for (int s = 0; s < graph.V(); s++) {
            if (!marked[s]) {
                dfs(graph, s);
                count++;
            }
        }
    }

    private void dfs(IGraph graph, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(graph, v);
            }
        }
    }

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }

}
