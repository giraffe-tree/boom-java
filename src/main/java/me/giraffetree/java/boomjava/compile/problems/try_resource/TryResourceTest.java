package me.giraffetree.java.boomjava.compile.problems.try_resource;

/**
 * try resource 中的 close 方法, 与 finally 块哪个先执行 ?
 *
 * @author GiraffeTree
 * @date 2021/4/1 13:42
 */
public class TryResourceTest {

    public static void main(String[] args) {
        try (Car car = new Car()) {
            throw new RuntimeException("exception...");
        } catch (Exception e) {
            System.out.println("catch exception...");
        } finally {
            System.out.println("finally...");
        }
    }

    private static class Car implements AutoCloseable {
        @Override
        public void close() throws Exception {
            System.out.println("close...");
        }
    }


}
