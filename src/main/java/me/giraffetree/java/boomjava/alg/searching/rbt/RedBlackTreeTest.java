package me.giraffetree.java.boomjava.alg.searching.rbt;

import me.giraffetree.java.boomjava.alg.searching.tree.TreePrinter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020-01-13
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {

        testDeleteMin();
    }

    private static void testDeleteMin() {
        RedBlackTree<Integer, String> generate = generate(20,50);
        for (int i = 0; i < generate.size(); i++) {
            TreePrinter.print(generate.root);
            generate.deleteMin();
        }

    }

    private static void testPut() {
        RedBlackTree<Integer, String> redBlackTree = generate(20, 30);
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

    private static RedBlackTree<Integer, String> generateBeginSmall() {
        return generateBeginSmall(10, 100);
    }

    private static RedBlackTree<Integer, String> generateBeginSmall(int size, int max) {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();

        ThreadLocalRandom current = ThreadLocalRandom.current();
        int start = 0;
        for (int i = 0; i < size; i++) {
            int num = current.nextInt(start, max / size + start);
            start = num;
            rbt.put(num, "num:" + num);
            TreePrinter.print(rbt.root);
        }
        return rbt;
    }

    private static RedBlackTree<Integer, String> generateBeginMax() {
        return generateBeginMax(10, 100);
    }

    private static RedBlackTree<Integer, String> generateBeginMax(int size, int max) {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();

        ThreadLocalRandom current = ThreadLocalRandom.current();
        int start = max;
        for (int i = 0; i < size; i++) {
            int num = current.nextInt(start - max / size, start);
            start = num;
            rbt.put(num, "num:" + num);
            TreePrinter.print(rbt.root);
        }
        return rbt;
    }

}
