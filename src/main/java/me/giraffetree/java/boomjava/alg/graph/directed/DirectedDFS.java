package me.giraffetree.java.boomjava.alg.graph.directed;

/**
 * 从顶点 s 出发的图, 计算 s 可达的所有顶点
 *
 * @author GiraffeTree
 * @date 2020/1/24
 */
public class DirectedDFS {

    private boolean[] marked;

    public DirectedDFS(Digraph digraph, int s) {
        marked = new boolean[digraph.V()];
        dfs(digraph, s);
    }

    private void dfs(Digraph digraph, int v) {
        marked[v] = true;
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                dfs(digraph, w);
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

}
