package me.giraffetree.java.boomjava.compile.keyword.word_finally;

/**
 * 部分代码来源于:
 * https://web.archive.org/web/20160407142041/http://devblog.guidewire.com/2009/10/22/compiling-trycatchfinally-on-the-jvm/
 *
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

    public static String testStr() {
        try {
            return "foo";
        } finally {
            return "bar";
        }
    }

    public static String testStr2() {
        String value = "foo";
        try {
            return value;
        } finally {
            value = "bar";
        }
    }

    public static String testStr3() {
        while (true) {
            try {
                return "foo";
            } finally {
                break;
            }
        }
        return "bar";
    }

    public static void main(String[] args) {

        System.out.println(test1(3));
        System.out.println(test2(3));
        System.out.println(test3(3));
        System.out.println(test4(3));

        System.out.println(testStr());
        System.out.println(testStr2());
        System.out.println(testStr3());
        // 答案:
        // 14,13,14,13
        // bar,foo,bar
    }
}
