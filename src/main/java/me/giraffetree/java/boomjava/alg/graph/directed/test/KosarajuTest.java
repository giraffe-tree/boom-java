package me.giraffetree.java.boomjava.alg.graph.directed.test;

import me.giraffetree.java.boomjava.alg.graph.directed.Digraph;
import me.giraffetree.java.boomjava.alg.graph.directed.StandardDigraph;
import me.giraffetree.java.boomjava.alg.graph.directed.problem.DepthFirstOrder;
import me.giraffetree.java.boomjava.alg.graph.directed.problem.KosarajuSCC;

import java.util.Arrays;

import static me.giraffetree.java.boomjava.alg.graph.directed.DigraphUtils.generate;

/**
 * @author GiraffeTree
 * @date 2020/2/27
 */
public class KosarajuTest {

    public static void main(String[] args) {

//        test();
        test4Nodes();
    }

    private static void test() {
        Digraph generate = generate(4, 6);
        System.out.println(generate);
        DepthFirstOrder order = new DepthFirstOrder(generate);
        order.pre().forEach(x -> System.out.print(x + " "));
        System.out.println();
        order.post().forEach(x -> System.out.print(x + " "));
        System.out.println();
        order.reversePost().forEach(x -> System.out.print(x + " "));

        System.out.println();
        KosarajuSCC kosarajuSCC = new KosarajuSCC(generate);
        int[] arr = new int[generate.V()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = kosarajuSCC.id(i);
        }
        System.out.println(Arrays.toString(arr));
    }


    private static void test4Nodes() {
        StandardDigraph digraph = new StandardDigraph(4);
        digraph.addEdge(0, 1);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 0);
        digraph.addEdge(0, 3);
        KosarajuSCC kosarajuSCC = new KosarajuSCC(digraph);
        int[] arr = new int[4];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = kosarajuSCC.id(i);
        }
        System.out.println(Arrays.toString(arr));
    }

}
