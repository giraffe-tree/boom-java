package me.giraffetree.java.boomjava.concurrent.problem.falseShare;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * 测试连续的两个不同大小的对象,  对竞争有影响
 * 这次测试其实没有什么结果, 侧面印证了 padding 大小对 竞争的影响
 * <p>
 * 用于测试 两个对象之间 volatile 存在竞争
 * 在内存上(java 堆), 有一定概率, 这两个对象处于同一缓存行
 * 理论上, 两个对象占用的内存越小, 越有可能处于同一个缓存行, 从何导致冲突
 * <p>
 * 执行的顺序对 结果有着 至关重要的影响!!!
 * 猜测的原因 cpu 缓存锁有着类似 synchronized 的锁升级过程
 * 某次的测试结果
 * {64 , 64} => 6.25%  -  average:31.50ms [73, 8, 27, 44, 28, 26, 29, 29, 25, 26]
 * {64 , 48} => 6.25%  -  average:29.30ms [38, 26, 26, 27, 27, 30, 27, 36, 30, 26]
 * {64 , 32} => 6.25%  -  average:52.90ms [39, 39, 110, 52, 46, 87, 39, 36, 38, 43]
 * {64 , 24} => 6.25%  -  average:37.10ms [37, 38, 40, 42, 37, 36, 35, 35, 35, 36]
 * {64 , 16} => 6.25%  -  average:37.40ms [37, 38, 37, 37, 37, 37, 37, 37, 37, 40]
 * {48 , 64} => 31.25%  -  average:76.00ms [39, 130, 39, 133, 38, 38, 136, 38, 131, 38]
 * {48 , 48} => 31.25%  -  average:56.30ms [38, 38, 39, 38, 39, 129, 37, 132, 37, 36]
 * {48 , 32} => 31.25%  -  average:65.50ms [37, 37, 36, 37, 37, 131, 132, 36, 37, 135]
 * {48 , 24} => 31.25%  -  average:55.50ms [132, 36, 37, 37, 36, 37, 36, 37, 131, 36]
 * {48 , 16} => 31.25%  -  average:55.90ms [133, 37, 37, 36, 37, 36, 37, 37, 132, 37]
 * {32 , 64} => 56.25%  -  average:196.70ms [256, 249, 256, 262, 250, 249, 247, 46, 105, 47]
 * {32 , 48} => 56.25%  -  average:121.00ms [37, 253, 239, 36, 259, 37, 37, 37, 37, 238]
 * {32 , 32} => 56.25%  -  average:132.20ms [43, 232, 199, 201, 39, 236, 37, 37, 37, 261]
 * {32 , 24} => 56.25%  -  average:178.10ms [251, 230, 156, 71, 40, 37, 244, 239, 245, 268]
 * {32 , 16} => 56.25%  -  average:188.70ms [261, 256, 246, 37, 36, 236, 259, 264, 37, 255]
 * {24 , 64} => 68.75%  -  average:232.10ms [259, 250, 265, 36, 258, 248, 247, 244, 252, 262]
 * {24 , 48} => 68.75%  -  average:187.20ms [36, 253, 234, 266, 37, 241, 253, 37, 256, 259]
 * {24 , 32} => 68.75%  -  average:123.50ms [242, 37, 249, 37, 37, 37, 264, 40, 254, 38]
 * {24 , 24} => 68.75%  -  average:204.50ms [237, 260, 234, 39, 229, 247, 254, 246, 262, 37]
 * {24 , 16} => 68.75%  -  average:167.60ms [248, 37, 36, 255, 37, 262, 261, 253, 36, 251]
 * {16 , 64} => 81.25%  -  average:213.20ms [260, 264, 37, 250, 264, 36, 262, 248, 255, 256]
 * {16 , 48} => 81.25%  -  average:212.30ms [254, 262, 261, 242, 242, 37, 264, 269, 36, 256]
 * {16 , 32} => 81.25%  -  average:190.20ms [36, 37, 242, 263, 244, 252, 261, 265, 36, 266]
 * {16 , 24} => 81.25%  -  average:210.40ms [36, 240, 262, 264, 36, 266, 266, 247, 240, 247]
 * {16 , 16} => 81.25%  -  average:157.10ms [36, 37, 241, 44, 252, 253, 257, 272, 111, 68]
 *
 * @author GiraffeTree
 * @date 2020/4/28
 */
public class FalseShareTestWithTwoObj {

    private final static ExecutorService EXECUTOR_SERVICE = ExecutorUtils.getExecutorService();

    private final static FlowerInfo[] FLOWER_GENERATORS = new FlowerInfo[]{
            new FlowerInfo(64, new RandomShareModelTest.ObjectAttribute(64, 12, 4)),
            new FlowerInfo(48, new RandomShareModelTest.ObjectAttribute(48, 12, 4)),
            new FlowerInfo(32, new RandomShareModelTest.ObjectAttribute(32, 12, 4)),
            new FlowerInfo(24, new RandomShareModelTest.ObjectAttribute(24, 12, 4)),
            new FlowerInfo(16, new RandomShareModelTest.ObjectAttribute(16, 12, 4)),
    };
    private final static TreeInfo[] TREE_GENERATORS = new TreeInfo[]{
            new TreeInfo(64, new RandomShareModelTest.ObjectAttribute(64, 12, 4)),
            new TreeInfo(48, new RandomShareModelTest.ObjectAttribute(48, 12, 4)),
            new TreeInfo(32, new RandomShareModelTest.ObjectAttribute(32, 12, 4)),
            new TreeInfo(24, new RandomShareModelTest.ObjectAttribute(24, 12, 4)),
            new TreeInfo(16, new RandomShareModelTest.ObjectAttribute(16, 12, 4)),
    };


    public static void main(String[] args) {
        int count = 5_000_000;
        int retry = 10;
        long[] record;
        // 执行的顺序对 结果有着 至关重要的影响!!!
        // 从大对象 -> 小对象的顺序进行测试
        for (FlowerInfo flowerInfo : FLOWER_GENERATORS) {
            for (TreeInfo treeInfo : TREE_GENERATORS) {
                record = testVolatile(flowerInfo.size, treeInfo.size, count, retry);
                double v = RandomShareModelTest.calcObj(flowerInfo.objectAttribute, treeInfo.objectAttribute);

                System.out.println(String.format("{%d , %d} => %.2f%%  -  average:%.2fms %s",
                        flowerInfo.objectAttribute.objSize,
                        treeInfo.objectAttribute.objSize,
                        v * 100,
                        LongStream.of(record).average().orElse(-1.0), Arrays.toString(record)));
            }
        }

        EXECUTOR_SERVICE.shutdown();
    }

    private static class Obj24 {
        long p1;
    }

    private static class GeneratorUtils {
        static IColor generateFlower(int flowerSize) {
            switch (flowerSize) {
                case 16:
                    return new Flower16(0);
                case 24:
                    return new FlowerPadding24(0);
                case 32:
                    return new FlowerPadding32(0);
                case 48:
                    return new FlowerPadding48(0);
                case 64:
                    return new FlowerPadding64(0);
                default:
                    throw new RuntimeException("not supported size");
            }
        }

        static IHeight generateTree(int flowerSize) {
            switch (flowerSize) {
                case 16:
                    return new Tree16(0);
                case 24:
                    return new TreePadding24(0);
                case 32:
                    return new TreePadding32(0);
                case 48:
                    return new TreePadding48(0);
                case 64:
                    return new TreePadding64(0);
                default:
                    throw new RuntimeException("not supported size");
            }
        }
    }

    private static long[] testVolatile(int flowerSize, int treeSize, final int count, final int retry) {

        long[] record = new long[retry];
        int r = retry;
        ThreadLocalRandom current = ThreadLocalRandom.current();
        while (r-- > 0) {
            CountDownLatch startLatch = new CountDownLatch(2);
            CountDownLatch endLatch = new CountDownLatch(2);
            int c = current.nextInt(0, 8);
            long x;
            while (c-- > 0) {
                // 减少对象在同一个缓存行的不同位置的影响
                // 由于java 内存中的对象是按照 8 字节对齐的, 并且缓存行为 64 字节
                // 我们随机模拟新生成的两个对象在任意的 8/16/24/32/40/48/56/64 的起始位置上
                Obj24 obj24 = new Obj24();
                x = obj24.p1;
            }

            IColor flower = GeneratorUtils.generateFlower(flowerSize);
            IHeight tree = GeneratorUtils.generateTree(treeSize);

            long l1 = System.currentTimeMillis();
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignored) {
                }
                int loop = count;
                while (loop-- > 0) {
                    flower.setColor(loop);
                }
                endLatch.countDown();
            });
            EXECUTOR_SERVICE.execute(() -> {
                startLatch.countDown();
                try {
                    startLatch.await();
                } catch (InterruptedException ignored) {
                }
                int loop = count;
                while (loop-- > 0) {
                    tree.setHeight(loop);
                }
                endLatch.countDown();
            });

            try {
                endLatch.await();
                long l2 = System.currentTimeMillis();
                record[retry - 1 - r] = l2 - l1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return record;
    }

    /**
     * 12 + 4
     */
    private static class Flower16 implements IColor {
        volatile int color;

        public Flower16(int color) {
            this.color = color;
        }

        @Override
        public void setColor(int color) {
            this.color = color;
        }
    }

    private static class Tree16 implements IHeight {
        volatile int height;

        public Tree16(int height) {
            this.height = height;
        }


        @Override
        public void setHeight(int height) {
            this.height = height;
        }
    }


    /**
     * 12 + 4 + 8
     */
    private static class FlowerPadding24 implements IColor {
        volatile int color;
        long p1;

        public FlowerPadding24(int color) {
            this.color = color;
        }

        @Override
        public void setColor(int color) {
            this.color = color;
        }
    }

    private static class TreePadding24 implements IHeight {
        volatile int height;
        long p1;

        public TreePadding24(int height) {
            this.height = height;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }
    }

    /**
     * 12 + 4 + 8*2
     */
    private static class FlowerPadding32 implements IColor {
        volatile int color;
        long p1, p2;

        public FlowerPadding32(int color) {
            this.color = color;
        }

        @Override
        public void setColor(int color) {
            this.color = color;
        }
    }

    private static class TreePadding32 implements IHeight {
        volatile int height;
        long p1, p2;

        public TreePadding32(int height) {
            this.height = height;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }
    }


    /**
     * 12 + 4 + 8*4
     */
    private static class FlowerPadding48 implements IColor {
        volatile int color;
        long p1, p2, p3, p4;

        public FlowerPadding48(int color) {
            this.color = color;
        }

        @Override
        public void setColor(int color) {
            this.color = color;
        }
    }

    private static class TreePadding48 implements IHeight {
        volatile int height;
        long p1, p2, p3, p4;

        public TreePadding48(int height) {
            this.height = height;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }
    }

    private static class FlowerPadding64 implements IColor {
        volatile int color;
        long p1, p2, p3, p4, p5, p6;

        public FlowerPadding64(int color) {
            this.color = color;
        }

        public FlowerPadding64() {
        }

        @Override
        public void setColor(int color) {
            this.color = color;
        }
    }

    private static class TreePadding64 implements IHeight {
        volatile int height;
        long p1, p2, p3, p4, p5, p6;

        public TreePadding64(int height) {
            this.height = height;
        }

        public TreePadding64() {
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }
    }


    private interface IColor {
        void setColor(int color);
    }

    private interface IHeight {
        void setHeight(int height);
    }

    private static class TreeInfo {
        int size;
        RandomShareModelTest.ObjectAttribute objectAttribute;

        public TreeInfo(int size, RandomShareModelTest.ObjectAttribute objectAttribute) {
            this.size = size;
            this.objectAttribute = objectAttribute;
        }
    }

    private static class FlowerInfo {
        int size;
        RandomShareModelTest.ObjectAttribute objectAttribute;

        public FlowerInfo(int size, RandomShareModelTest.ObjectAttribute objectAttribute) {
            this.size = size;
            this.objectAttribute = objectAttribute;
        }
    }

}
