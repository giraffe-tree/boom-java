package me.giraffetree.java.boomjava.concurrent.utils.container.list.copyonwrite;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ReentrantLock add + copy + set volatile array
 *
 * @author GiraffeTree
 * @date 2020/4/27
 */
public class CopyOnWriteListTest {

    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(12);
        list.get(0);
    }

}
