package me.giraffetree.java.boomjava.utils.igv;

/**
 * @author GiraffeTree
 * @date 2020/6/5
 */
public class Test {

    static int f(boolean c) {
        int i = 0;
        if (c) {
            i++;
        }
        if (c == true) {
            i++;
        }
        return i;
    }

    public static void main(String[] args) {

        f(true);
    }
}
