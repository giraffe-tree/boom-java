package me.giraffetree.java.boomjava.alg.graph.directed.problem;

import me.giraffetree.java.boomjava.alg.graph.directed.Digraph;
import me.giraffetree.java.boomjava.alg.graph.directed.StandardDigraph;

import java.util.Stack;

/**
 * tarjan 算法
 * 用于解决有向图连通性
 * 伪代码:
 * tarjan(u){
 * 　　DFN[u]=Low[u]=++Index // 为节点u设定次序编号和Low初值
 * 　　Stack.push(u)   // 将节点u压入栈中
 * 　　for each (u, v) in E // 枚举每一条边
 * 　　　　if (v is not visted) // 如果节点v未被访问过
 * 　　　　　　　　tarjan(v) // 继续向下找
 * 　　　　　　　　Low[u] = min(Low[u], Low[v])
 * 　　　　else if (v in S) // 如果节点u还在栈内
 * 　　　　　　　　Low[u] = min(Low[u], DFN[v])
 * 　　if (DFN[u] == Low[u]) // 如果节点u是强连通分量的根
 * 　　repeat v = S.pop  // 将v退栈，为该强连通分量中一个顶点
 * 　　print v
 * 　　until (u== v)
 * }
 *
 * @author GiraffeTree
 * @date 2020/1/31
 */
public class TarjanSCC {

    public static void main(String[] args) {
        StandardDigraph g = new StandardDigraph(6);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(1, 4);
        g.addEdge(0, 3);
        g.addEdge(2, 5);
        g.addEdge(4, 3);
        g.addEdge(4, 5);
        g.addEdge(4, 0);

        TarjanSCC tarjanSCC = new TarjanSCC(g);
        System.out.println(tarjanSCC.count);
        System.out.println(tarjanSCC.stronglyConnected(0,4));
    }

    private boolean[] marked;
    private int[] id;
    private int count;

    /**
     * 用于检查元素是否在 stack 中
     */
    private boolean[] vis;
    private int[] dfn;
    private int[] low;
    private int tot;
    private Stack<Integer> stack;

    public TarjanSCC(Digraph digraph) {
        int v = digraph.V();
        id = new int[v];
        dfn = new int[v];
        low = new int[v];
        vis = new boolean[v];
        marked = new boolean[v];

        stack = new Stack<>();

        tarjan(digraph, 0);
    }

    /**
     *
     */
    private void tarjan(Digraph g, int x) {
        vis[x] = true;
        marked[x] = true;
        dfn[x] = low[x] = ++tot;
        stack.push(x);
        for (int e : g.adj(x)) {
            if (!marked[e]) {
                tarjan(g, e);
                low[x] = Math.min(low[e], low[x]);
            } else if (vis[e]) {
                low[x] = Math.min(low[e], dfn[x]);
            }
        }
        if (dfn[x] == low[x]) {
            count++;
            while (!stack.isEmpty()) {
                Integer pop = stack.pop();
                vis[x] = false;
                id[pop] = count;
                if (pop == x) {
                    break;
                }
            }
        }
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }

}
