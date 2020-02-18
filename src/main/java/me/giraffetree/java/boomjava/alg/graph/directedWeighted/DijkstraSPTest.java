package me.giraffetree.java.boomjava.alg.graph.directedWeighted;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020-02-13
 */
public class DijkstraSPTest {

    public static void main(String[] args) {
        testDijkstraSP();

    }

    private static void testDijkstraSP() {
        EdgeWeightedDigraph digraph = generate();
        DijkstraSP dijkstraSP = new DijkstraSP(digraph, 0);
        for (int i = 0; i < digraph.V(); i++) {
            if (dijkstraSP.hasPathTo(i)) {
                double v = dijkstraSP.distTo(i);
                StringBuilder sb = new StringBuilder();
                Iterable<DirectedEdge> directedEdges = dijkstraSP.pathTo(i);
                Iterator<DirectedEdge> iterator = directedEdges.iterator();
                while (iterator.hasNext()) {
                    DirectedEdge edge = iterator.next();
                    sb.append(edge.from());
                    sb.append("->");
                    if (!iterator.hasNext()) {
                        sb.append(edge.to());
                    }
                }

                System.out.println(String.format("%d has short path, weight:%.2f path:%s", i, v, sb.toString()));

            }
        }

    }

    public static EdgeWeightedDigraph generate() {
        return generate(10);
    }

    public static EdgeWeightedDigraph generate(int v) {
        return generate(v, v * 2, v);
    }

    public static EdgeWeightedDigraph generate(int v, int e, double maxWeight) {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(v);
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < e; i++) {
            int e1 = current.nextInt(v);
            int e2 = current.nextInt(v);
            double weight = current.nextDouble(maxWeight);
            if (e1 != e2) {
                edgeWeightedDigraph.addEdge(new DirectedEdge(e1, e2, weight));
            }
        }
        return edgeWeightedDigraph;
    }

}
