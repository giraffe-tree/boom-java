package me.giraffetree.java.boomjava.alg.searching.tree;

/**
 * @author GiraffeTree
 * @date 2020-01-13
 */
public interface INode<Key extends Comparable<? super Key>,Value> {

    Key getKey();

    Value getValue();

    INode<Key, Value> left();

    INode<Key, Value> right();

}
