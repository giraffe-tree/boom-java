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

    private int size(RedBlackNode<Key, Value> node) {
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
        // 别忘了更新 node.N
        node.N = size(node.right) + size(node.left) + 1;
        return node;
    }

    @Override
    public void delete(Key key) {
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = delete(root, key);
        if (isRed(root)) {
            root.color = BLACK;
        }
    }

    private RedBlackNode<Key, Value> delete(RedBlackNode<Key, Value> h, Key key) {

        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (key.compareTo(h.key) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            if (key.compareTo(h.key) == 0) {
                RedBlackNode<Key, Value> x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    /**
     * 把要删除的结点在不破坏平衡的前提下先染红，再删除。
     * 代码中delete与deleteMin函数其实有一个隐形的不变量(invariant)：
     * h为红或者h.left为红。
     * 这个invariant保证我们最终删除的结点一定是红色的，不用担心黑高平衡的破坏。
     * 参考: https://www.zhihu.com/question/340879955
     */
    @Override
    public void deleteMin() {
        // 为什么要染红呢, 为了整棵树的叶子节点到 root 的路径上, 经过的 黑链接数量 都相等
        if (!isRed(root.left)) {
            assert !isRed(root.right);
            root.color = RED;
        }
        root = deleteMin(root);
        if (!isEmpty()) {
            root.color = BLACK;
        }
    }

    /**
     * 只允许删除 红色节点(3-节点)
     *
     * <p>
     * 节点指的是 2-3 树 的节点
     * 1. 如果当前节点的左子节点不是 2-节点, 则进入左子节点
     * 2. 如果当前节点的左子节点是 2-节点, 且它的右子节点不是 2-节点, 则借一个节点到左子节点中
     * 3. 如果当前节点的左右子节点均为 2-节点, 则将当前节点/其左子节点/其右子节点合并成一个 4-节点(即一个黑色节点的左右子节点均为红色)
     */
    private RedBlackNode<Key, Value> deleteMin(RedBlackNode<Key, Value> h) {
        if (h.left == null) {
            return null;
        }

        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }

        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * 删除最大的元素
     * 删除的节点必须是 红色, 只有这样才能保证: 在删除这个节点之后, 整棵树仍然保持平衡
     * 为了确保这个条件, 必须从根节点开始保证右子节点为 3-节点
     */
    @Override
    public void deleteMax() {
        if (!isRed(root.left)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (isRed(root)) {
            root.color = BLACK;
        }

    }

    /**
     * 删除最大的节点
     * 2-3 树的右子节点为 3-节点
     * 有以下几种情况
     * 1. 左右节点均为 2-节点, 翻转当前节点
     * 2. 左节点为 3-节点, 右节点为 2-节点, 借一个节点到 右边
     * 3. 右节点为 3-节点, 则直接进入 deleteMax(右子节点)
     */
    private RedBlackNode<Key, Value> deleteMax(RedBlackNode<Key, Value> node) {

        if (isRed(node.left)) {
            node = rotateRight(node);
        }
        if (node.right == null) {
            return null;
        }
        if (!isRed(node.right) && !isRed(node.right.left)) {
            // 23树的 右节点为 2-节点
            node = moveRedRight(node);
        }
        // 到这里的右节点 h = node.right
        // 有 h 的每个叶子节点到 h 经过的 黑色链接数都相同
        node.right = deleteMax(node.right);

        return balance(node);
    }

    private RedBlackNode<Key, Value> balance(RedBlackNode<Key, Value> h) {
        assert (h != null);
        if (isRed(h.right)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            // 右旋
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }


    RedBlackNode<Key, Value> moveRedRight(RedBlackNode<Key, Value> h) {
        // 若 h.color = red 且 h.right 和 h.right.left 均为黑色 -> 则将 h.right 或 h.right 的子节点之一变红
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
//            flipColors(h);
        }
        return h;
    }

    RedBlackNode<Key, Value> moveRedLeft(RedBlackNode<Key, Value> h) {
        //      R
        //  B       B
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
//            flipColors(h);
        }
        return h;
    }


    /**
     *
     */
    @Override
    public Key max() {
        if (root == null) {
            return null;
        }
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
        if (root == null) {
            return null;
        }
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
        return select(root, k).key;
    }

    private RedBlackNode<Key, Value> select(RedBlackNode<Key, Value> node, int k) {
        int sizeLeft = size(node.left);
        if (sizeLeft >= k) {
            return select(node.left, k);
        } else if (sizeLeft + 1 == k) {
            return node;
        } else {
            return select(node.right, k - sizeLeft - 1);
        }
    }

    @Override
    public int rank(Key key) {

        return rank(root, key);
    }

    private int rank(RedBlackNode<Key, Value> node, Key key) {
        int compare = key.compareTo(node.key);
        if (compare > 0) {
            return rank(node.right, key) + size(node.left) + 1;
        } else if (compare < 0) {
            return rank(node.left, key);
        } else {
            return size(node.left) + 1;
        }
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }


    /**
     * 左旋
     * 一个3-节点沿着指定方向旋转, 对树的结构不会产生影响
     * 2-3 树
     * 第1层          1   3     --->      1   3
     * 第2层        2   4   5           2   4   5
     * 转成 红黑树:
     * 第1层      B1                             B3
     * 第2层   B2      R3          ==>     R1         B5
     * 第3层         B4   B5            B2    B4
     */
    RedBlackNode<Key, Value> rotateLeft(RedBlackNode<Key, Value> node) {
        assert isRed(node.right) : "key:" + node.key;
        RedBlackNode<Key, Value> right = node.right;
        node.right = right.left;
        right.left = node;
        // 这步颜色转换是关键
        right.color = node.color;
        // 为什么要指定成 红色呢, 因为 node.right.color 原本的颜色就是红色的, 实际上是两个节点的颜色互换
        node.color = RED;
        right.N = node.N;
        node.N = size(node.left) + size(node.right) + 1;
        return right;
    }

    /**
     * 右旋
     * 2-3 树
     * 第1层          1   3     --->      1   3
     * 第2层        2   4   5           2   4   5
     * 红黑树:
     * 第1层       B1                  B2
     * 第2层   R2      B3     ==>  B4      R1
     * 第3层 B4   B5                     B5  B3
     */
    RedBlackNode<Key, Value> rotateRight(RedBlackNode<Key, Value> node) {
        assert isRed(node.left) : "key:" + node.key;
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

    boolean isRed(RedBlackNode node) {
        if (node == null) {
            return false;
        }
        return node.color == RED;
    }

    /**
     * 翻转颜色
     * 不能改变2-3树的结构
     */
    void flipColors(RedBlackNode<Key, Value> node) {
        assert (isRed(node) && !isRed(node.left) && !isRed(node.right)) || (!isRed(node) && isRed(node.left) && isRed(node.right)) : "key:" + node.key;
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    private boolean isEmpty() {
        return root == null;
    }

    public boolean is23() {
        return is23(root);
    }

    private boolean is23(RedBlackNode<Key, Value> x) {
        if (x == null) {
            return true;
        }
        if (isRed(x.right)) {
            return false;
        }
        if (x != root && isRed(x) && isRed(x.left)) {
            return false;
        }
        return is23(x.left) && is23(x.right);
    }
}
