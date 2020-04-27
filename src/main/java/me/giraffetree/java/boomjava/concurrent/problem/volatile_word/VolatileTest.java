package me.giraffetree.java.boomjava.concurrent.problem.volatile_word;

/**
 *
 * @author GiraffeTree
 * @date 2020-04-27
 */
public class VolatileTest {

    /**
     * ACC_VOLATILE
     */
    private volatile int a = 1;

    private void test(int b) {
        a = b;
    }

}
