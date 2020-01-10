package me.giraffetree.java.boomjava.alg.searching.bst;

import me.giraffetree.java.boomjava.alg.searching.tree.TreePrinter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020/1/9
 */
public class StandardRecursiveBSTTest {

    public static void main(String[] args) {

        testKeys();

    }

    private static void testKeys() {
        StandardRecursiveBST<Integer, String> bst = generate();
        TreePrinter.print(bst.root);

        Iterable<Integer> keys = bst.keys();
        keys.forEach(x -> System.out.println(bst.get(x)));
    }

    private static void testDeleteMax() {
        StandardRecursiveBST<Integer, String> generate = generate();
        TreePrinter.print(generate.root);
        generate.deleteMax();
        TreePrinter.print(generate.root);
        generate.deleteMax();
        TreePrinter.print(generate.root);
        generate.deleteMax();
        TreePrinter.print(generate.root);
        generate.deleteMax();
        TreePrinter.print(generate.root);
    }

    private static void testFloor() {
        StandardRecursiveBST<Integer, String> bst = generate();
        TreePrinter.print(bst.root);

        Integer floor = bst.floor(20);
        System.out.println(floor);
    }

    private static void testCeiling() {
        StandardRecursiveBST<Integer, String> bst = generate();
        TreePrinter.print(bst.root);

        Integer ceiling = bst.ceiling(20);
        System.out.println(ceiling);
    }



    private static StandardRecursiveBST<Integer, String> generate() {
        return generate(10, 50);
    }

    private static StandardRecursiveBST<Integer, String> generate(int size, int max) {
        StandardRecursiveBST<Integer, String> bst = new StandardRecursiveBST<>();

        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < size; i++) {
            int num = current.nextInt(max);
            bst.put(num, "num:" + num);
        }
        return bst;
    }

}
