package me.giraffetree.java.boomjava.alg.searching.rbt;

import me.giraffetree.java.boomjava.alg.searching.tree.Tree;
import me.giraffetree.java.boomjava.alg.searching.tree.TreePrinter;

/**
 * @author GiraffeTree
 * @date 2020/1/16
 */
public class RbtRotateAndMoveTest {


    public static void main(String[] args) {

        moveRedLeft();
    }

    private static void rotateRight() {
        for (int i = 0; i < 10; i++) {
            RedBlackTree<Integer, String> rbt = RedBlackTreeTest.generate(i + 3, 20);
            if (rbt.root.left.color) {
                System.out.println("new rbt ...");
                TreePrinter.print(rbt.root);
                rbt.root = rbt.rotateRight(rbt.root);
                System.out.println("after rotate right ");
                TreePrinter.print(rbt.root);
            }
        }
    }

    private static void moveRedLeft() {
        for (int i = 0; i < 20; i++) {
            RedBlackTree<Integer, String> rbt = RedBlackTreeTest.generate(i + 3, 50);
            Tree.RedBlackNode<Integer, String> h = rbt.root;
            if (!rbt.isRed(h.left) && !rbt.isRed(h.left.left)) {
                System.out.println("new rbt ...");
                TreePrinter.print(rbt.root);
                if (!rbt.isRed(h.right)) {
                    h.color = true;
                }
                rbt.root = rbt.moveRedLeft(rbt.root);
                System.out.println("after moveRedLeft  ");
                TreePrinter.print(rbt.root);
            }

        }
    }


}
