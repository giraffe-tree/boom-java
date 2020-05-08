package me.giraffetree.java.boomjava.concurrent.problem.volatile_word;

/**
 * -XX:+PrintCompilation
 * https://gist.github.com/rednaxelafx/1165804#file-notes-md
 *
 * @author GiraffeTree
 * @date 2020-04-27
 */
public class VolatileTest {

    /**
     * ACC_VOLATILE
     */
    private static volatile int a = 1;

    public static void main(String[] args) throws InterruptedException {
        int loop = 1_000_000_000;
        test(loop);

    }

    private static void test(int loop) {
        while (loop-- > 0) {
            a = loop;
        }
//        System.out.println(a);
    }

}
