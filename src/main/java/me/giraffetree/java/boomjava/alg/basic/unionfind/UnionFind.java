package me.giraffetree.java.boomjava.alg.basic.unionfind;

/**
 * todo: 实现
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
public abstract class UnionFind {

     int[] id;
     int count;

    public UnionFind(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    /**
     * 在 p, q 之间添加一条边
     */
    public abstract void union(int p, int q);

    /**
     * 返回 p 的连通分量标识
     */
    public int find(int p) {
        return id[p];
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    int count() {
        return count;
    }

}
