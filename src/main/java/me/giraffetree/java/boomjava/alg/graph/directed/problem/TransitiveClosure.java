package me.giraffetree.java.boomjava.alg.graph.directed.problem;

import me.giraffetree.java.boomjava.alg.graph.directed.Digraph;
import me.giraffetree.java.boomjava.alg.graph.directed.DirectedDFS;

/**
 * 实际上就是从每个顶点出发遍历, 查找每个顶点可达的点
 * <p>
 * 传递闭包
 * 用于解决顶点对的可达性问题
 * 空间复杂度: V^2
 * 时间复杂度: V(V+E)
 *
 * @author GiraffeTree
 * @date 2020/1/29
 */
public class TransitiveClosure {

    private DirectedDFS[] all;

    public TransitiveClosure(Digraph digraph) {
        all = new DirectedDFS[digraph.V()];
        for (int v = 0; v < digraph.V(); v++) {
            all[v] = new DirectedDFS(digraph, v);
        }
    }

    public boolean reachable(int v, int w) {
        return all[v].marked(w);
    }

}
