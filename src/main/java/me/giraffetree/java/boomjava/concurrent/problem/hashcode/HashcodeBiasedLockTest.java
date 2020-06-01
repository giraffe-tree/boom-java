package me.giraffetree.java.boomjava.concurrent.problem.hashcode;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;

import static me.giraffetree.java.boomjava.concurrent.problem.hashcode.ObjectHeaderUtils.bytesToHex;

/**
 * 用于测试偏向锁 与 hashcode
 * jdk8 环境
 * vm option:
 * -XX:+UnlockDiagnosticVMOptions -XX:+PrintBiasedLockingStatistics -XX:BiasedLockingStartupDelay=0
 * 需要注意的是 `-XX:BiasedLockingStartupDelay=0` 设定了 biasedLock 在 jvm 启动后没有延迟, 立即使用
 *
 * @author GiraffeTree
 * @date 2020/6/1
 */
public class HashcodeBiasedLockTest {
    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(2, 2);
    private static final Object lock = new Object();

    public static void main(String[] args) {
        testHashInSync();
//        test();
        EXECUTOR_SERVICE.shutdown();
    }

    /**
     * 从结果来看, 在 同步块之外调用 hashcode 方法, 会使该对象变为 偏向锁不可用的状态
     * 最后 3个bit 为 `001`
     * 执行结果如下:
     * 偏向锁可用:     00 00 00 00 00 00 00 05
     * 偏向锁已经加锁:   00 00 00 00 02 85 38 05
     * 同步块之外:     00 00 00 00 02 85 38 05
     * hashcode: 06 99 6D B8
     * 哈希code执行后: 00 00 00 06 99 6D B8 01
     */
    private static void test() {

        byte[] objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
        System.out.println(String.format("%-10s%24s", "偏向锁可用:", bytesToHex(objectMarkWord, true, true)));
        synchronized (lock) {
            objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
            System.out.println(String.format("%-10s%24s", "偏向锁已经加锁:", bytesToHex(objectMarkWord, true, true)));

        }

        objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
        System.out.println(String.format("%-10s%24s", "同步块之外:", bytesToHex(objectMarkWord, true, true)));

        byte[] hashcodeByteArray = ByteBuffer.allocate(4).putInt(System.identityHashCode(lock)).array();
        // 这里注意 hashcode 的字节不需要逆序
        System.out.println(String.format("%-10s%8s", "hashcode:", bytesToHex(hashcodeByteArray, true, false)));

        objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
        System.out.println(String.format("%-10s%24s", "哈希code执行后:", bytesToHex(objectMarkWord, true, true)));
    }

    /**
     * 从结果来看, 在同步块中调用 hashcode 方法, 会导致锁升级至 重量级锁
     * mark word中 , 最后 2个bit 为 `10`
     * 结果如下:
     * 偏向锁可用:     00 00 00 00 00 00 00 05
     * 已进入同步块.....
     * 偏向锁已经加锁:   00 00 00 00 02 EC 38 05
     * hashcode: 06 99 6D B8
     * 哈希code执行后: 00 00 00 00 1C 52 CB AA
     * 已退出同步块......
     * 同步块之外:     00 00 00 00 1C 52 CB AA
     * 再次hashcode:06 99 6D B8
     * 标记字:       00 00 00 00 1C 52 CB AA
     * thread-0 get lock
     * 标记字:       00 00 00 00 1C 52 CB AA
     */
    private static void testHashInSync() {

        byte[] objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
        System.out.println(String.format("%-10s%24s", "偏向锁可用:", bytesToHex(objectMarkWord, true, true)));
        synchronized (lock) {
            System.out.println("已进入同步块.....");
            objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
            System.out.println(String.format("%-10s%24s", "偏向锁已经加锁:", bytesToHex(objectMarkWord, true, true)));
            byte[] hashcodeByteArray = ByteBuffer.allocate(4).putInt(System.identityHashCode(lock)).array();
            // 这里注意 hashcode 的字节不需要逆序
            System.out.println(String.format("%-10s%8s", "hashcode:", bytesToHex(hashcodeByteArray, true, false)));
            objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
            System.out.println(String.format("%-10s%24s", "哈希code执行后:", bytesToHex(objectMarkWord, true, true)));
        }
        System.out.println("已退出同步块......");
        objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
        System.out.println(String.format("%-10s%24s", "同步块之外:", bytesToHex(objectMarkWord, true, true)));

        byte[] hashcodeByteArray = ByteBuffer.allocate(4).putInt(System.identityHashCode(lock)).array();
        // 这里注意 hashcode 的字节不需要逆序
        System.out.println(String.format("%-10s%8s", "再次hashcode:", bytesToHex(hashcodeByteArray, true, false)));
        objectMarkWord = ObjectHeaderUtils.getObjectMarkWord(lock);
        System.out.println(String.format("%-10s%24s", "标记字:", bytesToHex(objectMarkWord, true, true)));
        EXECUTOR_SERVICE.execute(() -> {
            byte[] markWord = ObjectHeaderUtils.getObjectMarkWord(lock);
            System.out.println(String.format("%-10s%24s", "before lock:", bytesToHex(markWord, true, true)));
            synchronized (lock) {
                System.out.println(String.format("%s get lock", Thread.currentThread().getName()));
                System.out.println(String.format("%s %-10s%24s", "标记字:", Thread.currentThread().getName(),
                        bytesToHex(ObjectHeaderUtils.getObjectMarkWord(lock), true, true)));
                byte[] array = ByteBuffer.allocate(4).putInt(System.identityHashCode(lock)).array();
                // 这里注意 hashcode 的字节不需要逆序
                System.out.println(String.format("%s %-10s%8s", "再次hashcode:", Thread.currentThread().getName(),
                        bytesToHex(array, true, false)));
                System.out.println(String.format("%s %-10s%24s", "标记字:", Thread.currentThread().getName(),
                        bytesToHex(ObjectHeaderUtils.getObjectMarkWord(lock), true, true)));
            }
        });
    }

}
