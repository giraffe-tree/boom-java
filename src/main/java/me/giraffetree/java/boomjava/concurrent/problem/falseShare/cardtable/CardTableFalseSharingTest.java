package me.giraffetree.java.boomjava.concurrent.problem.falseShare.cardtable;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 用于测试在 hotspot 中 卡表 False Sharing
 * -XX:+UseCondCardMark 使用 jdk8 运行
 * 由于64字节的卡表 表示了 32KB 的内存
 * 使得两个对象之间尽可能地公用同一个卡表
 * 测试结果:
 * jdk1.8.0_161 上运行
 * 不使用
 * average:158ms [257, 111, 116, 210, 164, 131, 187, 120, 146, 147]
 * average:167ms [228, 188, 148, 133, 169, 180, 159, 130, 183, 154]
 * 使用  -XX:+UseCondCardMark
 * average:26ms [116, 17, 12, 13, 12, 13, 27, 31, 12, 14]
 * average:40ms [117, 23, 33, 33, 34, 32, 34, 33, 32, 33]
 * 使用 -XX:-TieredCompilation
 * average:158ms [242, 146, 144, 137, 146, 178, 138, 159, 146, 145]
 * 使用 -XX:-TieredCompilation -XX:+UseCondCardMark
 * average:28ms [111, 18, 12, 13, 34, 13, 13, 13, 30, 28]
 * 不多说, 使用/不使用 UseCondCardMark 差距明显
 * jdk11.0.7 上运行
 * 不使用
 * average:32ms [103, 18, 32, 17, 12, 35, 34, 32, 31, 15]
 * 使用 -XX:+UseCondCardMark
 * average:28ms [73, 17, 13, 33, 33, 22, 13, 13, 33, 32]
 * average:30ms [63, 35, 31, 13, 19, 36, 32, 13, 32, 33]
 * 使用 -XX:-TieredCompilation
 * average:35ms [106, 22, 13, 34, 32, 34, 34, 34, 13, 33]
 * average:29ms [108, 26, 33, 31, 31, 13, 13, 12, 13, 13]
 * 使用 -XX:-TieredCompilation -XX:+UseCondCardMark
 * average:36ms [99, 22, 13, 34, 35, 33, 33, 32, 34, 32]
 * average:20ms [71, 23, 13, 14, 14, 14, 14, 14, 13, 13]
 * jdk11 中可能有了其他的实现和优化手段, 两种差距并不大
 *
 * @author GiraffeTree
 * @date 2020/5/18
 */
public class CardTableFalseSharingTest {
    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(2, 2);
//    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);


    public static void main(String[] args) throws InterruptedException {

        int loop = 10_000_000;
        int retry = 10;
        test(loop, retry);

        EXECUTOR_SERVICE.shutdown();
        EXECUTOR_SERVICE.awaitTermination(1000L, TimeUnit.MILLISECONDS);
    }

    private static void test(int loop, int retry) {
        // 在同一个线程中分配两个对象, 理论上这两个对象相距很近

        Obj64 x1 = new Obj64();

        Obj64 x2 = new Obj64();
        // 大对象直接进入老年代, 为了得到一个 老年代 -> eden 代的引用
        HugeObj hugeObj10M = new HugeObj(10 * 1024 * 1024, x1, x2);
        testX1AndX2(loop, retry, x1, x2, hugeObj10M);
    }

    private static void testX1AndX2(final int loop, final int retry, final Obj64 x1, final Obj64 x2, final HugeObj hugeObj) {
        long all = 0L;
        int r = retry;
        long[] record = new long[retry];
        while (r-- > 0) {
            final CountDownLatch endLatch = new CountDownLatch(2);
            long l1 = System.currentTimeMillis();
            EXECUTOR_SERVICE.execute(() -> {
                int count = loop;
                while (count-- > 0) {
                    if ((count & 1) == 0) {
                        hugeObj.x1 = x1;
                    } else {
                        hugeObj.x1 = x2;
                    }
                }
                endLatch.countDown();
            });
            EXECUTOR_SERVICE.execute(() -> {
                int count = loop;
                while (count-- > 0) {
                    if ((count & 1) == 0) {
                        hugeObj.x2 = x2;
                    } else {
                        hugeObj.x2 = x1;
                    }
                }
                endLatch.countDown();
            });

            try {
                endLatch.await();
                long l2 = System.currentTimeMillis();
                all += (l2 - l1);
                record[retry - 1 - r] = l2 - l1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("average:%dms %s", all / retry, Arrays.toString(record)));

    }

    private static class Obj64 {
        long l1;
        long l2, l3, l4, l5;
    }

    private static class HugeObj {
        private byte[] hugeArray;
        private Obj64 x1;
        private Obj64 x2;

        public HugeObj(int byteLen, Obj64 x1, Obj64 x2) {
            this.hugeArray = new byte[byteLen];
            this.x1 = x1;
            this.x2 = x2;
        }
    }

}
