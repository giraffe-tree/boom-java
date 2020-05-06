package me.giraffetree.java.boomjava.concurrent.thread.daemon;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * daemon 线程中的 finally 方法块未执行
 *
 * @author GiraffeTree
 * @date 2020-05-01
 */
public class FinallyInDaemonThreadTest {

    public static void main(String[] args) throws InterruptedException {
        Thread daemon = new Thread(() -> {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("daemon dead");
                try {
                    FileUtils.write(new File("C:\\Users\\Administrator\\Desktop\\hello.txt"), "hello\n", "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },
                "daemon");
        daemon.setDaemon(true);
        daemon.start();
        Thread.sleep(1000L);
    }

}
