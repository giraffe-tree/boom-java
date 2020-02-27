package me.giraffetree.java.boomjava.alg.graph.directed.problem;

import me.giraffetree.java.boomjava.alg.basic.stack.ArrayBasedStack;
import me.giraffetree.java.boomjava.alg.basic.stack.IStack;
import me.giraffetree.java.boomjava.alg.graph.directed.Digraph;

import java.util.ArrayList;
import java.util.List;

/**
 * 有向图中基于深度优先搜索的顶点排序
 *
 * @author GiraffeTree
 * @date 2020/1/29
 */
public class DepthFirstOrder {

    private boolean[] marked;
    /**
     * 前序排序
     */
    private List<Integer> pre;
    /**
     * 后序排序
     */
    private List<Integer> post;
    /**
     * 逆后序排序
     * 递归调用后, 将顶点压入栈
     * 一幅有向无环图的拓扑排序即为所有顶点的逆后序排序
     */
    private IStack<Integer> reversePost;

    public DepthFirstOrder(Digraph digraph) {
        pre = new ArrayList<>();
        post = new ArrayList<>();
        reversePost = new ArrayBasedStack<>(digraph.V());
        marked = new boolean[digraph.V()];
        for (int v = 0; v < digraph.V(); v++) {
            if (!marked[v]) {
                dfs(digraph, v);
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        pre.add(v);
        marked[v] = true;
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                dfs(digraph, w);
            }
        }
        post.add(v);
        reversePost.push(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }

}
