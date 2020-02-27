package me.giraffetree.java.boomjava.alg.basic.stack;


/**
 * @author GiraffeTree
 * @date 2020/2/27
 */
public interface IStack<T> extends Iterable<T> {

    void push(T t);

    T pop();

    T peek();

    int size();

}
