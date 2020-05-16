package me.giraffetree.java.boomjava.jvm.method.invokedynamic.methodhandle_reflect;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author GiraffeTree
 * @date 2020-05-16
 */
public class MethodHandleReflectTest {

    static class Bookshelf {

        private String[] books;

        public Bookshelf(String[] books) {
            this.books = books;
        }

        public String getFirstBook() {
            return books[0];
        }

    }


    public static void main(String[] args) throws Throwable {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Bookshelf bookshelf = new Bookshelf(new String[]{"hello", "world"});
        MethodHandle getFirstBook = lookup.findVirtual(Bookshelf.class, "getFirstBook", MethodType.methodType(String.class));
        String result = (String) getFirstBook.invoke(bookshelf);
        System.out.println(result);
    }

}
