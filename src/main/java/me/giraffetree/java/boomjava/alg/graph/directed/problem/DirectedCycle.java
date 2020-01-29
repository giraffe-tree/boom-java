package me.giraffetree.java.boomjava.alg.graph.directed.problem;

import me.giraffetree.java.boomjava.alg.graph.directed.Digraph;

import java.util.Stack;

/**
 * 有向图中检查环
 * 主要原理:
 * 构造一个当前走过路径的 set (实现中即 onStack 数组)
 * 进入 dfs 时, 加入这个 set,
 * 从 dfs 中出来时, 从 set 中 remove 掉
 *
 * @author GiraffeTree
 * @date 2020/1/29
 */
public class DirectedCycle {

    private boolean[] marked;
    /**
     * dfs 时, 指向这个顶点的顶点
     * 可能有多个顶点,
     * 但是由于深度优先搜索, 只搜索一次,
     * 故而是dfs路径上的上一个点
     */
    private int[] edgeTo;
    /**
     * 有向环中的所有顶点
     */
    private Stack<Integer> cycle;
    private boolean[] onStack;

    public DirectedCycle(Digraph digraph) {
        onStack = new boolean[digraph.V()];
        edgeTo = new int[digraph.V()];
        marked = new boolean[digraph.V()];
        for (int v = 0; v < digraph.V(); v++) {
            if (!marked[v]) {
                dfs(digraph, v);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : digraph.adj(v)) {
            if (hasCycle()) {
                return;
            } else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(digraph, w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }

        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
}
