package me.giraffetree.java.boomjava.alg.graph;

/**
 * @author GiraffeTree
 * @date 2020/1/22
 */
public interface Search {

    /**
     * v 和 s 是连通的么
     */
    boolean connected(int v, int s);

    /**
     * 与 s 连通的顶点总数
     */
    int count();


}
