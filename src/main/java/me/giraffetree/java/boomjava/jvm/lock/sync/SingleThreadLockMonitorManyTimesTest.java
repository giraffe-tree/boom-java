package me.giraffetree.java.boomjava.jvm.lock.sync;

public class SingleThreadLockMonitorManyTimesTest {
    
    public static void main(String[] args) {
        SingleThreadLockMonitorManyTimesTest t = new SingleThreadLockMonitorManyTimesTest();
        synchronized(t) {
            synchronized(t) {
                System.out.println("made it!");
            }
        }
    }
}