package me.giraffetree.java.boomjava.jvm.lock.biased;

/**
 * -XX:+PrintBiasedLockingStatistics 来打印各类锁的个数
 * Run with -XX:+UnlockDiagnosticVMOptions -XX:+PrintBiasedLockingStatistics -XX:TieredStopAtLevel=1
 * -XX:BiasedLockingStartupDelay=0 Hotspot虚拟机在开机启动后有个延迟（4s），经过延迟后才会对每个创建的对象开启偏向锁。
 * -XX:+UseBiasedLocking 使用偏向锁
 * <p>
 * <p>
 * 测试结果: 依次累加条件
 * 不是用偏向锁 380ms
 * 使用偏向锁 240ms
 * 调用 Object.hashCode 420ms 使用了偏向锁?
 * 重写 hashCode 方法  240ms
 * 调用 System.identityHashCode  420ms
 */
public class BiasedSynchronizedTest {

    static Lock lock = new Lock();
    static int counter = 0;

    public static void foo() {
        synchronized (lock) {
            counter++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

//        lock.hashCode(); // Step 2
//        System.identityHashCode(lock); // Step 4

        long l1 = System.currentTimeMillis();

        for (int i = 0; i < 10_000_000; i++) {
            foo();
        }
        long l2 = System.currentTimeMillis();
        System.out.println(String.format("cost: %dms", l2 - l1));

    }

    static class Lock {
        // @Override public int hashCode() { return 0; } // Step 3

        @Override
        public int hashCode() {
            return 1;
        }
    }
}