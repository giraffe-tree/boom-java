package me.giraffetree.java.boomjava.alg.searching.bst;

import me.giraffetree.java.boomjava.alg.searching.tree.OrderedTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准 递归版
 * 二叉查找树
 * <p>
 * 源码来自于 alg 第四版
 * <p>
 * recursive binary search tree
 * <p>
 * 相比于我自己的实现, 有以下方法做了修改:
 * 1. floor, ceiling 使用了递归的实现
 * 2. delete 方法统一了 N 的计算
 * 3. keys 方法变得更加简洁了, 但说实话, 有点看不懂了, 原理和我实现的差不多
 *
 * @author GiraffeTree
 * @date 2020-01-08
 */
public class StandardRecursiveBST<Key extends Comparable<? super Key>, Value> implements OrderedTree<Key, Value> {

    /**
     * 为了方便包内访问, 这里没有将 root 定义成 private
     */
    Node<Key, Value> root;

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
        int compare = key.compareTo(node.key);
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

    private Node<Key, Value> put(Node<Key, Value> node, Key key, Value value) {

        if (node == null) {
            return new Node<>(key, value, 1);
        }
        int compare = node.key.compareTo(key);
        if (compare == 0) {
            node.value = value;
            return node;
        } else if (compare < 0) {
            node.right = put(node.right, key, value);
        } else {
            node.left = put(node.left, key, value);
        }
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node<Key, Value> deleteMin(Node<Key, Value> node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    @Override
    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node<Key, Value> deleteMax(Node<Key, Value> node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = deleteMax(node.right);
        node.N = size(node.right) + size(node.left) + 1;
        return node;
    }


    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node<Key, Value> delete(Node<Key, Value> node, Key key) {
        if (node == null) {
            throw new NullPointerException();
        }
        int compare = key.compareTo(node.key);
        if (compare > 0) {
            node.right = delete(node.right, key);
            return node;
        } else if (compare < 0) {
            node.left = delete(node.left, key);
            return node;
        } else {
            // 相等的时候
            if (node.right == null) {
                // 右子树为空时, 将它的左子树作为 node 返回
                return node.left;
            }
            // 如果右子树不为空, 找出 min() 替换到当前 node 的位置
            Node<Key, Value> maxNode = min(node.right);
            maxNode.left = node.left;
            maxNode.right = deleteMax(node.right);
            node = maxNode;
        }

        node.N = size(node.right) + size(node.left) + 1;
        return node;
    }


    @Override
    public Key max() {
        Node<Key, Value> max = max(root);
        if (max == null) {
            throw new NullPointerException();
        }
        return max.key;
    }

    private Node<Key, Value> max(Node<Key, Value> node) {
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    @Override
    public Key min() {
        Node<Key, Value> min = min(root);
        if (min == null) {
            throw new NullPointerException();
        }
        return min.key;
    }

    private Node<Key, Value> min(Node<Key, Value> node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }


    @Override
    public Key floor(Key key) {
        Node<Key, Value> node = floor(root, key);
        if (node == null) {
            return null;
        }
        return node.key;
    }

    private Node<Key, Value> floor(Node<Key, Value> node, Key key) {
        if (node == null) {
            return null;
        }
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            return node;
        } else if (compare < 0) {
            return floor(node.left, key);
        }
        Node<Key, Value> t = floor(node.right, key);
        if (t != null) {
            return t;
        } else {
            return node;
        }

    }

    @Override
    public Key ceiling(Key key) {
        return ceiling(root, key);
    }

    /**
     * 向上取整
     */
    private Key ceiling(Node<Key, Value> node, Key key) {

        Node<Key, Value> tmp = node;
        Key ceilingKey = null;

        while (true) {
            if (tmp == null) {
                return ceilingKey;
            }
            int compare = key.compareTo(tmp.key);
            if (compare > 0) {
                tmp = tmp.right;
            } else if (compare < 0) {
                if (ceilingKey == null || ceilingKey.compareTo(tmp.key) > 0) {
                    ceilingKey = tmp.key;
                }
                tmp = tmp.left;
            } else {
                return key;
            }
        }
    }


    @Override
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node<Key, Value> select(Node<Key, Value> node, int k) {
        if (node == null || size(node) < k) {
            throw new NullPointerException();
        }
        if (size(node.left) >= k) {
            return select(node.left, k);
        } else if (size(node.left) + 1 == k) {
            return node;
        } else {
            return select(node.right, k - size(node.left) - 1);
        }

    }

    @Override
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node<Key, Value> node, Key key) {
        if (node == null) {
            throw new NullPointerException();
        }
        int compare = key.compareTo(node.key);
        if (compare > 0) {
            return size(node.left) + 1 + rank(node.right, key);
        } else if (compare == 0) {
            return size(node.left) + 1;
        } else {
            return rank(node.left, key);
        }
    }

    @Override
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        ArrayList<Key> list = new ArrayList<>(size(root));
        keys(root, list, lo, hi);
        return list;
    }

    private void keys(Node<Key, Value> node, List<Key> list, Key lo, Key hi) {
        if (node == null) {
            return;
        }
        int cmplo = lo.compareTo(node.key);
        int cmphi = hi.compareTo(node.key);
        if (cmplo < 0) {
            keys(node.left, list, lo, hi);
        }
        if (cmplo <= 0 && cmphi >= 0) {
            list.add(node.key);
        }
        if (cmphi > 0) {
            keys(node.right, list, lo, hi);
        }

    }


}
