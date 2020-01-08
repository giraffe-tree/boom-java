package me.giraffetree.java.boomjava.alg.searching.bst;

/**
 * 递归版 二叉查找树
 *
 * @author GiraffeTree
 * @date 2020-01-08
 */
public class RecursiveBST<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    private Node root;

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


    private Value get(Node node, Key key) {
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

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;

        private int N;

        public Node(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            this.N = n;
        }
    }

}
