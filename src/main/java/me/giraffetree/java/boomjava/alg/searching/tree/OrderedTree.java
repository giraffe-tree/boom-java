package me.giraffetree.java.boomjava.alg.searching.tree;

/**
 * @author GiraffeTree
 * @date 2020-01-08
 */
public interface OrderedTree<Key extends Comparable<? super Key>, Value> extends Tree<Key, Value> {

    Key max();

    Key min();

    /**
     * 向下取整
     */
    Key floor(Key key);

    /**
     * 向上取整
     */
    Key ceiling(Key key);

    /**
     * 返回指定排名的键
     */
    Key select(int k);

    /**
     * 返回给定键的排名
     */
    int rank(Key key);

    default Iterable<Key> keys() {
        return keys(min(), max());
    }

    Iterable<Key> keys(Key lo, Key hi);

}
