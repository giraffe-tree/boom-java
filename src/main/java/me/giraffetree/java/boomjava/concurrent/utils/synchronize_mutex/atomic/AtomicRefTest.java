package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 三种实现
 * AtomicReference、AtomicStampedReference 和 AtomicMarkableReference
 *
 * @author GiraffeTree
 * @date 2020/4/26
 */
public class AtomicRefTest {

    public static void main(String[] args) {
//        testAtomicRef();
        testAtomicStampedRef();
        testAtomicMarkableRef();

    }

    private static void testAtomicRef() {
        AtomicReference<Apple> ref = new AtomicReference<>();
        Apple apple = new Apple(10);
        ref.set(apple);
        ref.compareAndSet(apple, new Apple(212));
        System.out.println(ref.get());
        boolean success = ref.compareAndSet(apple, new Apple(12));
        System.out.println(success);
    }

    private static void testAtomicStampedRef() {
        Apple apple = new Apple(12);
        AtomicStampedReference<Apple> ref = new AtomicStampedReference<>(apple, 0);
        boolean b = ref.compareAndSet(apple, new Apple(123), 0, 123);
        System.out.println(b);
    }

    private static void testAtomicMarkableRef() {
        Apple apple = new Apple(23);
        AtomicMarkableReference<Apple> ref = new AtomicMarkableReference<>(apple, false);
        boolean b = ref.compareAndSet(apple, new Apple(23), false, true);
        System.out.println(b);
    }

    private static class Apple {
        int size;

        public Apple(int size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return "Apple{" +
                    "size=" + size +
                    '}';
        }
    }

}
