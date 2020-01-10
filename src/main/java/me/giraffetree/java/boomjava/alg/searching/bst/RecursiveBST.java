package me.giraffetree.java.boomjava.alg.searching.bst;

import me.giraffetree.java.boomjava.alg.searching.tree.OrderedTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 递归版 二叉查找树
 * recursive binary search tree
 *
 * @author GiraffeTree
 * @date 2020-01-08
 */
public class RecursiveBST<Key extends Comparable<? super Key>, Value> implements OrderedTree<Key, Value> {

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
            node.N = size(node.right) + size(node.left) + 1;
            return node;
        } else if (compare < 0) {
            node.left = delete(node.left, key);
            node.N = size(node.right) + size(node.left) + 1;
            return node;
        } else {
            // 相等的时候
            if (node.right == null) {
                // 右子树为空时, 将它的左子树作为 node 返回
                return node.left;
            }
            // 如果右子树不为空, 找出 max() 替换到当前 node 的位置
            Node<Key, Value> minNode = min(node.right);
            minNode.right = deleteMin(node.right);
            minNode.left = node.left;
            minNode.N = size(minNode.right) + size(minNode.left) + 1;
            return minNode;
        }
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
        return floor(root, key);
    }

    private Key floor(Node<Key, Value> node, Key key) {
        Node<Key, Value> tmp = node;
        Key floorKey = null;
        while (true) {
            if (tmp == null) {
                return floorKey;
            }
            int compare = key.compareTo(tmp.key);
            if (compare > 0) {
                if (floorKey == null || tmp.key.compareTo(floorKey) > 0) {
                    floorKey = tmp.key;
                }
                tmp = tmp.right;
            } else if (compare < 0) {
                tmp = tmp.left;
            } else {
                return key;
            }

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
        List<Key> list = new ArrayList<>(size(root));
        keys(root, list, lo, hi);
        return list;
    }

    private void keys(Node<Key, Value> node, List<Key> list, Key lo, Key hi) {
        if (node == null) {
            return;
        }
        // 中序遍历
        int compareHi = node.key.compareTo(hi);
        if (compareHi > 0) {
            keys(node.left, list, lo, hi);
            return;
        }
        int compareLo = node.key.compareTo(lo);
        if (compareLo < 0) {
            keys(node.right, list, lo, hi);
            return;
        }
        if (node.left == null) {
            list.add(node.key);
            keys(node.right, list, lo, hi);
        } else {
            keys(node.left, list, lo, hi);
            list.add(node.key);
            keys(node.right, list, lo, hi);
        }

    }


}
