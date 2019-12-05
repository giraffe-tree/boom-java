package me.giraffetree.java.boomjava.jvm.lock.sync;

/**
 * @author GiraffeTree
 * @date 2019/11/14
 */
public class MonitorenterTest {

    public void monitorThis() {
        synchronized (this) {

        }
    }

    public void monitorClass() {
        synchronized (MonitorenterTest.class) {
            int a = 1;
        }
    }


}
