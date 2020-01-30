package me.giraffetree.java.boomjava.alg.graph.undirected;

import me.giraffetree.java.boomjava.alg.graph.IGraph;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 广度优先
 * 这里使用 迭代法
 *
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class BreadthFirstSearch {

    private boolean[] marked;

    private int[] edgeTo;

    private final int s;

    public BreadthFirstSearch(IGraph graph, int s) {
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        this.s = s;
        bfs(graph, s);
    }

    private void bfs(IGraph graph, int s) {
        Queue<Integer> queue = new LinkedBlockingQueue<>();

        marked[s] = true;
        queue.add(s);
        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (int w : graph.adj(v)) {
                edgeTo[w] = v;
                marked[w] = true;
                queue.add(w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * 同深度优先中的路径搜索
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
