package me.giraffetree.java.boomjava.alg.searching.rbt;

import me.giraffetree.java.boomjava.alg.searching.tree.OrderedTree;

/**
 * 红黑树
 *
 * @author GiraffeTree
 * @date 2020-01-09
 */
public class RedBlackTree<Key extends Comparable<? super Key>, Value> implements OrderedTree<Key, Value> {

    private final static boolean RED = true;
    private final static boolean BLACK = false;

    ColoredNode<Key, Value> root;

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Value get(Key key) {
        return null;
    }

    @Override
    public void put(Key key, Value value) {

    }

    @Override
    public void delete(Key key) {

    }

    @Override
    public void deleteMin() {

    }

    @Override
    public void deleteMax() {

    }

    @Override
    public Key max() {
        return null;
    }

    @Override
    public Key min() {
        return null;
    }

    @Override
    public Key floor(Key key) {
        return null;
    }

    @Override
    public Key ceiling(Key key) {
        return null;
    }

    @Override
    public Key select(int k) {
        return null;
    }

    @Override
    public int rank(Key key) {
        return 0;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

    private ColoredNode<Key, Value> rotateLeft(ColoredNode<Key, Value> h) {

        return null;
    }

    private boolean isRed(ColoredNode node) {
        return node.color == RED;
    }

}
