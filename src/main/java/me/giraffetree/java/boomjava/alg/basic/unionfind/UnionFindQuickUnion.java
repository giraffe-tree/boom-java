package me.giraffetree.java.boomjava.alg.basic.unionfind;

/**
 * 在 id[] 数组中存储, 同一个连通分量的另一个元素
 * 在find方法中, 递归查找到 这个连通分量的 根元素 就是 连通分量的标识符
 * 时间复杂度
 * find  树的高度
 * union 树的高度
 *
 * @author GiraffeTree
 * @date 2020-02-16
 */
public class UnionFindQuickUnion extends UnionFind {

    public UnionFindQuickUnion(int n) {
        super(n);
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
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) {
            return;
        }
        id[pRoot] = qRoot;
        count--;
    }
}
