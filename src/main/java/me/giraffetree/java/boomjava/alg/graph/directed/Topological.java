package me.giraffetree.java.boomjava.alg.graph.directed;

import me.giraffetree.java.boomjava.alg.graph.directed.problem.DepthFirstOrder;
import me.giraffetree.java.boomjava.alg.graph.directed.problem.DirectedCycle;

/**
 * @author GiraffeTree
 * @date 2020-02-13
 */
public class Topological {

    private Iterable<Integer> order;

    public Topological(Digraph g) {
        DirectedCycle directedCycle = new DirectedCycle(g);
        if (!directedCycle.hasCycle()) {
            DepthFirstOrder depthFirstOrder = new DepthFirstOrder(g);
            order = depthFirstOrder.reversePost();
        }else {
            throw new RuntimeException("has cycle");
        }
    }

    public Iterable<Integer> order() {
        return order;
    }
}
