package me.giraffetree.java.boomjava.concurrent.problem.falseShare.cardtable;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 测试失败!!
 * 用于测试在 hotspot 中 卡表 False Sharing
 * -XX:+UseCondCardMark 使用 jdk11 运行
 * https://github.com/unofficial-openjdk/openjdk/blob/f2757c6c03e0000047d2b32f6198f9bc9a46e9d0/test/hotspot/jtreg/gc/CondCardMark/Basic.java
 * 由于64字节的卡表 表示了 32KB 的内存
 * 使得两个对象之间尽可能地公用同一个卡表
 * 测试结果:
 * 和预期的结果不符
 * 使用 jdk11 运行
 * 参数 `-XX:-TieredCompilation`
 * volatile average:66ms [92, 69, 63, 63, 63, 65, 63, 64, 63, 64]
 * 使用 `-XX:+UseCondCardMark -XX:-TieredCompilation`
 * volatile average:67ms [87, 67, 65, 66, 64, 64, 66, 65, 64, 64]
 * 两者几乎没有差距?
 * 再用 jdk7 试了下
 * > D:\env\jdk7\jdk\bin\javac -encoding utf8 CardTableFalseSharingTest.java
 * > D:\env\jdk7\jdk\bin\java -XX:+UseCondCardMark CardTableFalseSharingTest
 * volatile average:84ms [101, 84, 81, 85, 80, 82, 81, 87, 70, 90]
 * > D:\env\jdk7\jdk\bin\java  CardTableFalseSharingTest
 * volatile average:89ms [81, 70, 88, 97, 83, 82, 84, 92, 106, 113]
 * 差距还是不大 =.= 这就有点不知道怎么回事了
 *
 * @author GiraffeTree
 * @date 2020/5/18
 */
public class CardTableFalseSharingTest {
    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService(2, 2);
//    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            Thread.sleep(1000L);
        }

        int loop = 10_000_000;
        int retry = 10;
        test(loop, retry);

        EXECUTOR_SERVICE.shutdown();
        EXECUTOR_SERVICE.awaitTermination(1000L, TimeUnit.MILLISECONDS);
    }

    private static void test(int loop, int retry) {
        // 在同一个线程中分配两个对象, 理论上这两个对象相距很近

        Obj64 x1 = new Obj64();
        // 策略1: 防止 x1, x2 在同一缓存行
        Obj64 padding = new Obj64();

        // 策略2: 中间间隔大于 32KB (一个缓存行大小64B的卡表的 , 对应的实际内存为 32KB)
//        byte[] padding = new byte[32 * 1024];

        Obj64 x2 = new Obj64();
        testX1AndX2(loop, retry, x1, x2);
    }

    private static void testX1AndX2(final int loop, final int retry, final Obj64 x1, final Obj64 x2) {
        long all = 0L;
        int r = retry;
        long[] record = new long[retry];
        while (r-- > 0) {
            final CountDownLatch endLatch = new CountDownLatch(2);
            long l1 = System.currentTimeMillis();
            EXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    int count = loop;
                    while (count-- > 0) {
                        x1.l1 = count;
                    }
                    endLatch.countDown();
                }
            });
            EXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    int count = loop;
                    while (count-- > 0) {
                        x2.l1 = count;
                    }
                    endLatch.countDown();
                }
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
        System.out.println(String.format("volatile average:%dms %s", all / retry, Arrays.toString(record)));

    }

    private static class Obj64 {
        volatile long l1;
        long l2, l3, l4, l5;
    }

}
