package me.giraffetree.java.boomjava.alg.graph.undirected;

import me.giraffetree.java.boomjava.alg.graph.Graph;
import me.giraffetree.java.boomjava.alg.graph.GraphUtils;

/**
 * @author GiraffeTree
 * @date 2020/1/22
 */
public class DFSTest {

    public static void main(String[] args) {

        testDFS1_6();

    }

    private static void testDFS() {
        Graph graph = GraphUtils.generate(10, 20);
        DepthFirstSearch search = new DepthFirstSearch(graph, 2);
        System.out.println(search.count());
        System.out.println(search.connected(4));
        System.out.println(search.hasPathTo(4));
        System.out.println(search.pathTo(4));
    }

    private static void testDFS1_6() {
        Graph graph = GraphUtils.generate123456();
        DepthFirstSearch search = new DepthFirstSearch(graph, 1);
        System.out.println(search.hasPathTo(4));
        System.out.println(search.pathTo(4));
    }

}
