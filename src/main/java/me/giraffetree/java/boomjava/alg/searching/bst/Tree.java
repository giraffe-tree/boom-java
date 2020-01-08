package me.giraffetree.java.boomjava.alg.searching.bst;

/**
 * @author GiraffeTree
 * @date 2020-01-08
 */
public interface Tree<Key extends Comparable<Key>, Value> {

    int size();

    Value get(Key key);

    void put(Key key, Value value);


}
