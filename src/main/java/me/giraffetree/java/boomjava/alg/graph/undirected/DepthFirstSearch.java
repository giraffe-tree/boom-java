package me.giraffetree.java.boomjava.alg.graph.undirected;

import me.giraffetree.java.boomjava.alg.graph.Graph;

import java.util.Stack;

/**
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class DepthFirstSearch {

    private boolean[] marked;

    private int count;

    private int[] edgeTo;

    private final int s;

    public DepthFirstSearch(Graph graph, int s) {
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        this.s = s;
        dfs(graph, s);
    }

    private void dfs(Graph graph, int v) {
        marked[v] = true;
        count++;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(graph, w);
            }
        }
    }

    /**
     * w 和起点的连通状态
     */
    public boolean connected(int w) {
        return marked[w];
    }

    /**
     * 与起点连通的顶点数
     */
    public int count() {
        return count;
    }

    /**
     * 是否存在起点到 w
     */
    public boolean hasPathTo(int w) {
        return marked[w];
    }

    /**
     * 起点 到 v 的路径
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

}
