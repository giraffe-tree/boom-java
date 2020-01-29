package me.giraffetree.java.boomjava.alg.graph.directed.problem;

import me.giraffetree.java.boomjava.alg.graph.directed.Digraph;

/**
 * 用于解决强连通分量问题
 * <p>
 * 使用 逆后序排序, 然后进行 dfs
 * 原理:
 * 使用dfs 查找 有向图的反向图,
 * 并得到所有顶点的逆后序,
 * 再使用 深度优先算法处理,
 * 则其构造函数中每一次递归调用, 所标记的顶点都在同一个强连通分量之中
 * 核心思想:
 * 圈反过来也是圈
 * 把圈反一半过来会得到两条路径
 * <p>
 * 解释:
 * 1 先把所有的强连通分量缩成一团（最近知道了这个叫做缩点）
 * 2 团与团之间的出入边不变，所以整个图就变成了一个团的有向无环图（有几个独立的有向无环图也没有影响，只是dfs的时候多循环几次）
 * 3 对这个有向无环图进行dfs会有一个dfs树，第一次dfs处于根节点的团一定是最后完成的，这个时候根节点是出边（不然怎么叫根节点）
 * 4 所以在第二次逆向dfs中，根节点团就是最先访问的。
 * 5 这时根节点团的出边就变成了入边。所以根节点团不能到达其他的强连通分量团。
 * 6 所以这时候从根节点dfs就只能访问到根节点团（根节点所在的强连通分量）的内部的节点，其他的团同理。
 * 上述解释来自知乎
 * 作者：唐糖糖
 * 链接：https://www.zhihu.com/question/58926821/answer/860576319
 * 来源：知乎
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/29
 */
public class KosarajuSCC {

    private boolean[] marked;
    private int[] id;
    private int count;

    public KosarajuSCC(Digraph digraph) {
        marked = new boolean[digraph.V()];
        id = new int[digraph.V()];
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(digraph.reverse());
        for (int s : depthFirstOrder.reversePost()) {
            if (!marked[s]) {
                dfs(digraph, s);
                count++;
            }
        }
    }

    private void dfs(Digraph digraph, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                dfs(digraph, w);
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
