package me.giraffetree.java.boomjava.alg.searching.rbt;

import me.giraffetree.java.boomjava.alg.searching.tree.TreePrinter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020-01-13
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {

        testPut();
    }

    private static void testPut() {
        RedBlackTree<Integer, String> redBlackTree = generate();
        TreePrinter.print(redBlackTree.root);

    }

    private static RedBlackTree<Integer, String> generate() {
        return generate(10, 30);
    }

    private static RedBlackTree<Integer, String> generate(int size, int max) {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();

        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < size; i++) {
            int num = current.nextInt(max);
            rbt.put(num, "num:" + num);
        }
        return rbt;
    }


}
