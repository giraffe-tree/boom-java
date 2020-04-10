package me.giraffetree.java.boomjava.alg.graph.sp.undirectedweighted;

import me.giraffetree.java.boomjava.alg.graph.directedWeighted.DirectedEdge;
import me.giraffetree.java.boomjava.alg.graph.directedWeighted.EdgeWeightedDigraph;

/**
 * todo
 * 无环加权有向图的最短路径算法
 *
 * @author GiraffeTree
 * @date 2020-02-13
 */
public class AcyclicSP {

    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph g, int s) {
        edgeTo = new DirectedEdge[g.V()];
        distTo = new double[g.V()];
        for (int i = 0; i < g.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;

    }

}
