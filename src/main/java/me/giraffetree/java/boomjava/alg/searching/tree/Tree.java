package me.giraffetree.java.boomjava.alg.searching.tree;

/**
 * @author GiraffeTree
 * @date 2020-01-08
 */
public interface Tree<Key extends Comparable<? super Key>, Value> {

    int size();

    Value get(Key key);

    void put(Key key, Value value);

    void delete(Key key);

    void deleteMin();

    void deleteMax();

    /**
     * 普通树节点
     */
    class Node<Key extends Comparable<? super Key>, Value> {

        /**
         * 为了进行通用的访问, 我这里使用 public
         */
        public Key key;
        public Value value;
        public Node<Key, Value> left, right;

        public int N;

        public Node(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            this.N = n;
        }
    }

    /**
     * 红黑树节点
     */
    class RedBlackNode<Key extends Comparable<? super Key>, Value> {
        public Key key;
        public Value value;
        public boolean color;
        public RedBlackNode<Key, Value> left, right;
        public int N;

        public RedBlackNode(Key key, Value value, boolean color, RedBlackNode<Key, Value> left, RedBlackNode<Key, Value> right, int n) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.left = left;
            this.right = right;
            N = n;
        }
    }

}
