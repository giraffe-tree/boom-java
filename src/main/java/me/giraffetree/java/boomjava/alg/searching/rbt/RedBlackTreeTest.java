package me.giraffetree.java.boomjava.alg.searching.rbt;

import me.giraffetree.java.boomjava.alg.searching.tree.TreePrinter;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 红黑树测试
 * 使用 `-ea` 加入 VM options 打开断言
 *
 * @author GiraffeTree
 * @date 2020-01-13
 */
public class RedBlackTreeTest {

    public static void main(String[] args) {

        int count = 20;

        for (int i = 0; i < count; i++) {
            testDelete(i, 100);
            testDeleteMax(i, 100);
            testDeleteMin(i, 100);
        }
    }

    /**
     * #                   36:4
     * #           ┌─────────┴─────────┐
     * #          5:1                63:2
     * #                         ┌────┘
     * #                       58:R:1
     */
    private static void testDeleteMax63() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();
        rbt.put(36, "");
        rbt.put(5, "");
        rbt.put(63, "");
        rbt.put(58, "");
        TreePrinter.print(rbt.root);
        System.out.println("try to delete max");
        rbt.deleteMax();
        TreePrinter.print(rbt.root);
    }

    /**
     * #                                       18:6
     * #                     ┌───────────────────┴───────────────────┐
     * #                   5:R:4                                   19:1
     * #            ┌─────────┴─────────┐
     * #           2:2                12:1
     * #    ┌──────┘
     * # 1:R:1
     */
    private static void testDelete5() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();
        rbt.put(18, "");
        rbt.put(12, "");
        rbt.put(19, "");
        rbt.put(5, "");
        rbt.put(2, "");
        rbt.put(1, "");
        TreePrinter.print(rbt.root);
        System.out.println("try to delete 5");
        rbt.delete(5);
        TreePrinter.print(rbt.root);
    }

    /**
     * #                 18:4
     * #         ┌─────────┴─────────┐
     * #       2:R:2               19:1
     * #         └────┐
     * #           12:1
     */
    private static void testDelete12() {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();
        rbt.put(18, "");
        rbt.put(2, "");
        rbt.put(19, "");
        rbt.put(1, "");
        TreePrinter.print(rbt.root);
        rbt.delete(17);
        TreePrinter.print(rbt.root);
    }

    /**
     * 第1行       12
     * 第2行     5     17
     * 第3行 1
     */
    private static void testDelete17() {

        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();
        rbt.put(12, "");
        rbt.put(5, "");
        rbt.put(17, "");
        rbt.put(1, "");
        TreePrinter.print(rbt.root);
        rbt.delete(17);
        TreePrinter.print(rbt.root);
    }


    /**
     * 第1行       11
     * 第2行    6     15
     * 第3行 5
     */
    private static void testDelete11() {

        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();
        rbt.put(11, "");
        rbt.put(6, "");
        rbt.put(15, "");
        rbt.put(5, "");
        TreePrinter.print(rbt.root);
        rbt.delete(11);
        TreePrinter.print(rbt.root);

    }

    private static void testDelete(int size, int max) {
        RedBlackTree<Integer, String> rbt = generate(size, max);
        TreePrinter.print(rbt.root);

        int n = rbt.size();
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        for (int i = 0; i < n; i++) {
            int cur = rbt.size();
            int rank = tlr.nextInt(1, cur + 1);

            Integer key = rbt.select(rank);
            System.out.println(String.format("try to delete: %d", key));

            rbt.delete(key);
            TreePrinter.print(rbt.root);
            boolean is23 = rbt.is23();
            if (!is23) {
                System.out.println("test error ...");
                break;
            }
        }
    }

    private static void testDeleteMax(int size, int max) {
        RedBlackTree<Integer, String> generate = generate(size, max);
        TreePrinter.print(generate.root);
        int n = generate.size();
        for (int i = 0; i < n; i++) {
            System.out.println(String.format("try to delete max:%d", generate.max()));
            generate.deleteMax();
            TreePrinter.print(generate.root);
        }
    }

    private static void testDeleteMin(int size, int max) {
        RedBlackTree<Integer, String> generate = generate(size, max);
        TreePrinter.print(generate.root);
        int n = generate.size();
        for (int i = 0; i < n; i++) {
            System.out.println(String.format("try to delete min:%d", generate.min()));
            generate.deleteMin();
            TreePrinter.print(generate.root);
        }

    }

    private static void testPut() {
        RedBlackTree<Integer, String> redBlackTree = generate(10, 30);
        TreePrinter.print(redBlackTree.root);

    }

     static RedBlackTree<Integer, String> generate() {
        return generate(10, 30);
    }

    static RedBlackTree<Integer, String> generate(int size, int max) {
        RedBlackTree<Integer, String> rbt = new RedBlackTree<>();

        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int i = 0; i < size; i++) {
            int num = current.nextInt(max);
            rbt.put(num, "num:" + num);
        }
        return rbt;
    }

    static RedBlackTree<Integer, String> generateBeginSmall() {
        return generateBeginSmall(10, 100);
    }

    static RedBlackTree<Integer, String> generateBeginSmall(int size, int max) {
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

    static RedBlackTree<Integer, String> generateBeginMax() {
        return generateBeginMax(10, 100);
    }

    static RedBlackTree<Integer, String> generateBeginMax(int size, int max) {
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
