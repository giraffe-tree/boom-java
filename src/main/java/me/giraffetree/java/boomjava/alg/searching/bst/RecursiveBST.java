package me.giraffetree.java.boomjava.alg.searching.bst;

import me.giraffetree.java.boomjava.alg.searching.tree.OrderedTree;

/**
 * 递归版 二叉查找树
 *
 * @author GiraffeTree
 * @date 2020-01-08
 */
public class RecursiveBST<Key extends Comparable<Key>, Value> implements OrderedTree<Key, Value> {

    private Node<Key, Value> root;

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.N;
    }

    @Override
    public Value get(Key key) {
        return get(root, key);
    }


    private Value get(Node<Key, Value> node, Key key) {
        if (node == null) {
            return null;
        }
        int compare = node.key.compareTo(key);
        if (compare == 0) {
            return node.value;
        } else if (compare > 0) {
            return get(node.right, key);
        } else {
            return get(node.left, key);
        }

    }

    /**
     * 不要忘记更新 root
     */
    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
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

    /**
     * 不要忘记更新 N
     */
    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        int compare = node.key.compareTo(key);
        if (compare == 0) {
            node.value = value;
            return node;
        } else if (compare > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.left = put(node.left, key, value);
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
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
    public Iterable<Key> keys() {
        return null;
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

}
