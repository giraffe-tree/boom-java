package me.giraffetree.java.boomjava.concurrent.problem.falseShare;

import me.giraffetree.java.boomjava.concurrent.utils.ExecutorUtils;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

/**
 * 猜想不同对象大小, 对竞争有影响
 * 尝试测试了不同对象大小对竞争的影响, 但没有得出有规律的结论!!!!!
 * <p>
 * 用于测试 两个对象之间 volatile 存在竞争
 * 在内存上(java 堆), 有一定概率, 这两个对象处于同一缓存行
 * 理论上, 两个对象占用的内存越小, 越有可能处于同一个缓存行, 从何导致冲突
 * <p>
 * i3 上的结果
 * volatile padding average:56ms [78, 13, 74, 72, 53, 51, 52, 55, 57, 58]
 * volatile average:173ms [17, 38, 211, 172, 197, 226, 221, 217, 215, 220]
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
