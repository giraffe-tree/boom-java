package me.giraffetree.java.boomjava.alg.graph.directed.test;

import me.giraffetree.java.boomjava.alg.graph.directed.Digraph;
import me.giraffetree.java.boomjava.alg.graph.directed.DigraphUtils;
import me.giraffetree.java.boomjava.alg.graph.directed.problem.DepthFirstOrder;


/**
 * @author GiraffeTree
 * @date 2020/2/27
 */
public class ReversePostOrderTest {

    public static void main(String[] args) {

        test4_2_10();
    }

    private static void test4_2_10() {

        // 用于测试 algorithms 图 4.2.10 顶点排序的示例
        Digraph g = DigraphUtils.generate4_2_10();

        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(g);
        StringBuilder reversePost = new StringBuilder();
        StringBuilder pre = new StringBuilder();
        StringBuilder post = new StringBuilder();
        depthFirstOrder.reversePost().forEach(x -> reversePost.append(x).append(" "));
        depthFirstOrder.pre().forEach(x -> pre.append(x).append(" "));
        depthFirstOrder.post().forEach(x -> post.append(x).append(" "));
        System.out.println(pre.toString());
        System.out.println(post.toString());
        System.out.println(reversePost.toString());

    }

}
