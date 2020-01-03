package me.giraffetree.java.boomjava.alg.basic.unionfind;

/**
 * @author GiraffeTree
 * @date 2020/1/3
 */
public class UF {

    private int[] id;
    private int[] sz;
    private int count;

    public UF(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public void union(int p, int q) {
        return;
    }

    public int find(int p) {
        return 0;
    }

    public boolean connected(int p, int q) {
        return false;
    }

    int count () {
        return 0;
    }

}
