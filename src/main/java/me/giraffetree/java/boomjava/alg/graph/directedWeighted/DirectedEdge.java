package me.giraffetree.java.boomjava.alg.graph.directedWeighted;

import java.util.Objects;

/**
 * @author GiraffeTree
 * @date 2020/2/1
 */
public class DirectedEdge {
    private final int v;
    private final int w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.format("%d -> %d %.2f", v, w, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectedEdge)) {
            return false;
        }
        DirectedEdge that = (DirectedEdge) o;
        return v == that.v &&
                w == that.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, w);
    }
}
