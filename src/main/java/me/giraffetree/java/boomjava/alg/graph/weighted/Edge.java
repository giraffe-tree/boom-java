package me.giraffetree.java.boomjava.alg.graph.weighted;

import java.util.Objects;

/**
 * @author GiraffeTree
 * @date 2020/1/30
 */
public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;

    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    /**
     * vertex 顶点
     */
    public int other(int vertex) {
        if (vertex == v) {
            return w;
        } else if (vertex == w) {
            return v;
        } else {
            throw new RuntimeException("inconsistent edge");
        }
    }

    @Override
    public int compareTo(Edge o) {
        if (this.weight > o.weight) {
            return 1;
        } else if (this.weight < o.weight) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Edge)) {
            return false;
        }
        Edge edge = (Edge) o;
        return v == edge.v &&
                w == edge.w &&
                Double.compare(edge.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, w, weight);
    }
}
