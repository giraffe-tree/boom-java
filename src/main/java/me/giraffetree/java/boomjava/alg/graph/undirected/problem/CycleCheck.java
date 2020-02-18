package me.giraffetree.java.boomjava.alg.graph.undirected.problem;

import me.giraffetree.java.boomjava.alg.graph.IGraph;

/**
 * 无环图检查
 * 假设不存在自环, 平行边
 *
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class CycleCheck {

    boolean[] marked;
    private boolean hasCycle;

    public static void main(String[] args) {

    }

    private boolean check(IGraph graph) {
        marked = new boolean[graph.V()];
        for (int s = 0; s < graph.V(); s++) {
            if (!marked[s]) {
                dfs(graph, s, s);
            }
        }
        return hasCycle;
    }

    /**
     * 深度优先搜索
     *
     * @param graph 图
     * @param v     当前深度优先搜索到的点
     * @param u     检查的环的起点
     */
    private void dfs(IGraph graph, int v, int u) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                dfs(graph, w, v);
            } else if (w != u) {
                // 发现一个节点, 被标记过, 且不是自己
                hasCycle = true;
                return;
            }
        }
    }

}
