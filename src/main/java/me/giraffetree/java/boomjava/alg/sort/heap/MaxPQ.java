package me.giraffetree.java.boomjava.alg.sort.heap;

/**
 * priority queue
 *
 * @author GiraffeTree
 * @date 2020-01-04
 */
public class MaxPQ<K extends Comparable<K>> {

    /**
     * 其中 K[0] 不使用
     */
    private K[] pq;

    private int N = 0;

    public MaxPQ(int maxN) {
        pq = (K[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(K k) {
        pq[++N] = k;
        swim(N);
    }

    public K delMax() {
        K max = pq[1];
        swap(1, N--);
        // gc
        pq[N + 1] = null;
        sink(1);
        return max;
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            swap(k / 2, k);
            k = k / 2;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void swap(int i, int j) {
        K k = pq[i];
        pq[i] = pq[j];
        pq[j] = k;
    }

}
