package me.giraffetree.java.boomjava.concurrent.sync_word.biased_lock;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.ExecutorService;

/**
 * ref: https://stackoverflow.com/questions/26357186/what-is-in-java-object-header
 * //  64 bits:
 * //  --------
 * //  unused:25 hash:31 -->| unused:1   age:4    biased_lock:1 lock:2 (normal object)
 * //  JavaThread*:54 epoch:2 unused:1   age:4    biased_lock:1 lock:2 (biased object)
 * //  PromotedObject*:61 --------------------->| promo_bits:3 ----->| (CMS promoted object)
 * //  size:64 ----------------------------------------------------->| (CMS free block)
 * //
 * //  unused:25 hash:31 -->| cms_free:1 age:4    biased_lock:1 lock:2 (COOPs && normal object)
 * //  JavaThread*:54 epoch:2 cms_free:1 age:4    biased_lock:1 lock:2 (COOPs && biased object)
 * //  narrowOop:32 unused:24 cms_free:1 unused:4 promo_bits:3 ----->| (COOPs && CMS promoted object)
 * //  unused:21 size:35 -->| cms_free:1 unused:7 ------------------>| (COOPs && CMS free block)
 *
 * @author GiraffeTree
 * @date 2020/4/28
 */
public class BiasedLockTest {
    private final static Sync sync = new Sync();
    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    public static void main(String[] args) {
        test();
        EXECUTOR_SERVICE.shutdown();
    }

    private static void test() {
        try {
            Thread.sleep(4100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printObjectLayout(sync);
        EXECUTOR_SERVICE.execute(() -> {
            int loop = 1;
            while (loop-- > 0) {
                synchronized (sync) {
                    ClassLayout classLayout = ClassLayout.parseInstance(sync);
                    System.out.println(classLayout.toPrintable());
                }
            }
            ClassLayout classLayout = ClassLayout.parseInstance(sync);
            System.out.println(classLayout.toPrintable());

        });

    }

    private static void printObjectLayout(Object o) {
        ClassLayout classLayout = ClassLayout.parseInstance(o);
        System.out.println(classLayout.toPrintable());
    }

    private static class Sync {

    }

}
