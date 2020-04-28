package me.giraffetree.java.boomjava.concurrent.problem.falseShare;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author GiraffeTree
 * @date 2020/4/28
 */
public class FalseShareUtils {

    static void printObjectLayout(Object o) {
        String s = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(s);
    }

}
