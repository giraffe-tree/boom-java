package me.giraffetree.java.boomjava.alg.basic.unionfind;

/**
 * 在 id[] 数组中存储, 同一个连通分量的另一个元素
 * 在find方法中, 递归查找到 这个连通分量的 根元素 就是 连通分量的标识符
 * 时间复杂度
 * find  lgN
 * union lgN
 *
 * @author GiraffeTree
 * @date 2020-02-16
 */
public class WeightedUnionFindQuickUnion extends UnionFind {

    /**
     * 如果当成树的话, 则 sz[p] 即以 点p 作为根节点的树的元素个数
     */
    private int[] sz;

    public WeightedUnionFindQuickUnion(int n) {
        super(n);
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            sz[i] = 1;
        }
    }

    @Override
    public int find(int p) {
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    @Override
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;
    }
}
