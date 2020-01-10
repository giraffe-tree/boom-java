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
    ColoredNode<Key, Value> root ;

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

    @Override
    public int size() {
        return 0;
    }

    /**
     * 红黑树也是二叉搜索树
     * 可以和 BST 使用相同原理的 get 方法
     */
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
    public void deleteMin(Key key) {

    }

    @Override
    public void deleteMax(Key key) {

    }

    private Node<Key, Value> rotateLeft(ColoredNode<Key, Value> h) {

    }

    private boolean isRed(ColoredNode node) {
        return node.color == RED;
    }

}
