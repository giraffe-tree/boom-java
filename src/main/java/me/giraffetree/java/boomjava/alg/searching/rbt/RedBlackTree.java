package me.giraffetree.java.boomjava.alg.searching.rbt;

import me.giraffetree.java.boomjava.alg.searching.tree.OrderedTree;

/**
 * 左倾红黑树
 *
 * @author GiraffeTree
 * @date 2020-01-09
 */
public class RedBlackTree<Key extends Comparable<? super Key>, Value> implements OrderedTree<Key, Value> {

    private final static boolean RED = true;
    private final static boolean BLACK = false;

    RedBlackNode<Key, Value> root;

    @Override
    public int size() {
        return size(root);
    }

    int size(RedBlackNode<Key, Value> node) {
        if (node == null) {
            return 0;
        }
        return node.N;
    }

    /**
     * get 方法的思路和 bst 是一样的
     * 红黑树是2-3树, 也是二叉树
     */
    @Override
    public Value get(Key key) {
        RedBlackNode<Key, Value> node = get(root, key);
        return node == null ? null : node.value;
    }

    private RedBlackNode<Key, Value> get(RedBlackNode<Key, Value> node, Key key) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);
        if (compare > 0) {
            return get(node.right, key);
        } else if (compare < 0) {
            return get(node.left, key);
        } else {
            return node;
        }
    }

    @Override
    public void put(Key key, Value value) {
        root = put(root, key, value);
        root.color = BLACK;
    }

    /**
     * put 方法主要是用好 左旋,右旋,翻转
     * 要注意判断的顺序
     */
    private RedBlackNode<Key, Value> put(RedBlackNode<Key, Value> node, Key key, Value value) {
        if (node == null) {
            node = new RedBlackNode<>(key, value, RED, null, null, 1);
        }
        int compare = key.compareTo(node.key);
        if (compare > 0) {
            node.right = put(node.right, key, value);
        } else if (compare < 0) {
            node.left = put(node.left, key, value);
        } else {
            node.value = value;
        }
        // 什么情况下需要进行旋转或者翻转
        // 参见: https://www.processon.com/view/link/5cfb1b11e4b0591fc0d94564
        if (isRed(node.right)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    @Override
    public void delete(Key key) {

    }

    @Override
    public void deleteMin() {

    }

    /**
     * 删除最大的元素
     * 删除的节点必须是 红色, 只有这样才能保证: 在删除这个节点之后, 整棵树仍然保持平衡
     * 为了确保这个条件, 必须从根节点开始保证有一个红色节点在右子树上
     */
    @Override
    public void deleteMax() {

    }

    private RedBlackNode<Key, Value> deleteMax(RedBlackNode<Key, Value> node) {


        return null;
    }

    /**
     * 可能 NPE
     */
    @Override
    public Key max() {
        return max(root).key;
    }

    /**
     * max(),min() 的思路和 bst 中一样
     */
    private RedBlackNode<Key, Value> max(RedBlackNode<Key, Value> node) {
        RedBlackNode<Key, Value> tmp = node;
        while (tmp.right != null) {
            tmp = tmp.right;
        }
        return tmp;
    }

    @Override
    public Key min() {
        return min(root).key;
    }

    private RedBlackNode<Key, Value> min(RedBlackNode<Key, Value> node) {
        RedBlackNode<Key, Value> tmp = node;
        while (tmp.left != null) {
            tmp = tmp.left;
        }
        return tmp;
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

    /**
     * 左旋
     * 实际上, 左旋或者右旋对于 2-3 树的结构不会产生影响
     * 第1层      B1                             B3
     * 第2层   B2      R3          ==>     R1         B5
     * 第3层         B4   B5            B2    B4
     */
    private RedBlackNode<Key, Value> rotateLeft(RedBlackNode<Key, Value> node) {
        RedBlackNode<Key, Value> right = node.right;
        node.right = right.left;
        right.left = node;
        // 这步颜色转换是关键
        right.color = node.color;
        node.color = RED;
        right.N = node.N;
        node.N = size(node.left) + size(node.right) + 1;
        return right;
    }

    /**
     * 右旋
     * 第1层       B1                  B2
     * 第2层   R2      B3     ==>  B4      R1
     * 第3层 B4   B5                     B5  B3
     */
    private RedBlackNode<Key, Value> rotateRight(RedBlackNode<Key, Value> node) {
        RedBlackNode<Key, Value> left = node.left;
        node.left = left.right;
        left.right = node;
        // 这步颜色转换是关键
        left.color = node.color;
        node.color = RED;
        left.N = node.N;
        node.N = size(node.left) + size(node.right) + 1;
        return left;
    }

    private boolean isRed(RedBlackNode node) {
        if (node == null) {
            return false;
        }
        return node.color == RED;
    }

    /**
     * 翻转颜色
     * 翻转会改变2-3树的结构
     */
    private void flipColors(RedBlackNode<Key, Value> node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

}
