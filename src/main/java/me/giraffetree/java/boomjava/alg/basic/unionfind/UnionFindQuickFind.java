package me.giraffetree.java.boomjava.alg.basic.unionfind;

/**
 * 每次 union 都会遍历 id[] 数组一次
 * 效率低, 无法处理大量数据
 * 时间复杂度
 * find  1
 * union N
 *
 * @author GiraffeTree
 * @date 2020-02-16
 */
public class UnionFindQuickFind extends UnionFind {

    public UnionFindQuickFind(int n) {
        super(n);
    }


    @Override
    public void union(int p, int q) {
        if (connected(p, q)) {
            return;
        }

        int x1 = id[q];
        int x2 = id[p];
        // 这里的 x1 和 x2 都是整形值，是固定的
        for (int i = 0; i < id.length; i++) {
            if (id[i] == x2) {
                id[i] = x1;
            }
        }
        // 由于做的是连接, 所有count只会减少, 不会增加
        count--;
    }
}
