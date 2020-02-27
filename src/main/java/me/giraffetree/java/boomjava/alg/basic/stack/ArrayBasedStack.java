package me.giraffetree.java.boomjava.alg.basic.stack;


import java.util.Iterator;

/**
 * @author GiraffeTree
 * @date 2020/2/27
 */
public class ArrayBasedStack<T> implements IStack<T> {

    private Object[] data;
    private int cur = 0;

    public ArrayBasedStack(int maxLen) {
        data = new Object[maxLen];
    }


    @Override
    public void push(T t) {
        data[cur++] = t;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T pop() {
        return (T) data[cur--];
    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public int size() {
        return cur;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = cur;

            @Override
            public boolean hasNext() {
                return index > 0;
            }

            @Override
            public T next() {
                return (T) data[--index];
            }
        };
    }
}
