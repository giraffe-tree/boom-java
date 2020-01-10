package me.giraffetree.java.boomjava.alg.searching.bst;

import me.giraffetree.java.boomjava.alg.searching.tree.TreePrinter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GiraffeTree
 * @date 2020/1/9
 */
public class RecursiveBSTTest {

    public static void main(String[] args) {

        testDelete();

    }

    private static void testDelete() {
        RecursiveBST<Integer, String> bst = generate();
        TreePrinter.print(bst.root);
        ThreadLocalRandom current = ThreadLocalRandom.current();
        while (bst.size() > 0) {
            // 随机删除
            Integer key = bst.select(current.nextInt(1, bst.size() + 1));
            System.out.println(String.format("try to delete key:%s", key));
            bst.delete(key);
            TreePrinter.print(bst.root);
        }
    }

    private static void testKeys() {
        RecursiveBST<Integer, String> bst = generate();
        TreePrinter.print(bst.root);

        Iterable<Integer> keys = bst.keys();
        keys.forEach(x -> System.out.println(bst.get(x)));
    }

    private static void testDeleteMax() {
        RecursiveBST<Integer, String> generate = generate();
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
        RecursiveBST<Integer, String> bst = generate();
        TreePrinter.print(bst.root);

        Integer floor = bst.floor(20);
        System.out.println(floor);
    }

    private static void testCeiling() {
        RecursiveBST<Integer, String> bst = generate();
        TreePrinter.print(bst.root);

        Integer ceiling = bst.ceiling(20);
        System.out.println(ceiling);
    }


    private static RecursiveBST<Integer, String> generate() {
        return generate(10, 50);
    }

    private static RecursiveBST<Integer, String> generate(int size, int max) {
        RecursiveBST<Integer, String> bst = new RecursiveBST<>();

        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < size; i++) {
            int num = current.nextInt(max);
            bst.put(num, "num:" + num);
        }
        return bst;
    }

}
