package me.giraffetree.java.boomjava.compile.keyword.word_finally;

/**
 * @author GiraffeTree
 * @date 2020/5/28
 */
public class FinallyTest {

    public static int test1(int a) {
        try {
            a += 10;
            return a;
        } finally {
            a++;
            return a;
        }
    }

    private static int test2(int a) {
        try {
            a += 10;
            return a;
        } finally {
            a++;
        }
    }

    private static int test3(int a) {
        a += 10;
        return a;
    }

    public static int test4(int a) {
        try {
            a += 10;
            a = a / 0;
        } finally {
            a++;
            return a;
        }
    }

    public static void main(String[] args) {

        System.out.println(test1(3));
        System.out.println(test2(3));
        System.out.println(test3(3));
        System.out.println(test4(3));
        // 答案:
        // 14,13,14,13
    }
}
