package me.giraffetree.java.boomjava.alg.graph;

/**
 * @author GiraffeTree
 * @date 2020/1/22
 */
public interface Paths {

    /**
     * 是否存在从s 到 v 的路径
     */
    boolean hasPathTo(int s, int v);

    /**
     * s 到 v 的路径, 如果不存在则返回 null
     */
    Iterable<Integer> pathTo(int s, int v);

}
