package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.aqs.reentrant;

import me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.aqs.LockTestUtils;

/**
 * @author GiraffeTree
 * @date 2020-05-04
 */
public class UnFairReentrantLockTest {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        UnFairReentrantLock unFairReentrantLock = new UnFairReentrantLock();
        LockTestUtils.testLockAndUnLock(unFairReentrantLock, 10, 4, 1000L);
    }
}
