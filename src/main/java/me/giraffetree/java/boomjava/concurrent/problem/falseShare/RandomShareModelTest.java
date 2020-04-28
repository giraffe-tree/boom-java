package me.giraffetree.java.boomjava.concurrent.problem.falseShare;

/**
 * 本次测试用于探究
 * 计算出来的值为 猜想值
 * 两个不同大小的对象落在随机的内存区域中, 两个对象中的 volatile 字段在同一缓存行的概率
 *
 * @author GiraffeTree
 * @date 2020/4/28
 */
public class RandomShareModelTest {

    public static void main(String[] args) {
        int[] arr = {16, 24, 32, 48, 64};
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                double v = calcObj(arr[i], 12, 4, arr[j], 12, 4);
                System.out.println(String.format("obj1:%d obj2:%d  =>  冲突概率: %.2f%%", arr[i], arr[j], v * 100));
            }
        }
    }

    public static double calcObj(ObjectAttribute obj1, ObjectAttribute obj2) {
        return calcObj(64, obj1.objSize, obj1.shareOffset, obj1.shareSize, obj2.objSize, obj2.shareOffset, obj2.shareSize);
    }

    public static double calcObj(int objSize1, int shareOffset1, int shareSize1, int objSize2, int shareOffset2, int shareSize2) {
        return calcObj(64, objSize1, shareOffset1, shareSize1, objSize2, shareOffset2, shareSize2);
    }

    /**
     * 结构
     * |        obj1           |            obj2       |
     * | offset| share| other  | offset| share| other  |
     *
     * @param blockSize    一般指 cache line size 为 64
     * @param objSize1     第一个对象的大小
     * @param shareOffset1 第一个对象 需要共享的区域的 offset, 记录了 obj 起始地址到共享地址的差值
     * @param shareSize1   第一个对象 需要共享的区域的 size
     * @param objSize2     第二个对象的大小
     * @param shareOffset2 第二个对象 需要共享的区域的 offset, 记录了 obj 起始地址到共享地址的差值
     * @param shareSize2   第二个对象 需要共享的区域的 size
     * @return 共享对象在同一个 block 中的概率
     */
    public static double calcObj(int blockSize, int objSize1, int shareOffset1, int shareSize1, int objSize2, int shareOffset2, int shareSize2) {
        int interval = objSize1 - shareOffset1 - shareSize1 + shareOffset2;
        return calc(blockSize, interval);
    }

    /**
     * 计算两个共享块在同一个区域的概率
     * <p>
     * |    block size   |    block size    |   =>  很多缓存行
     * | obj block  |  obj block   |    =>  java 堆中的对象
     * |share|  interval | share |      =>  share 为每个对象中的 volatile 字段
     *
     * @param blockSize           区块大小, 假设有一个无限大的区域, 根据区块大小, 来分成不同区块
     * @param shareBlocksInterval 两个共享块之间的距离
     * @return 概率
     */
    private static double calc(int blockSize, int shareBlocksInterval) {
        return 1 - (double) shareBlocksInterval / (double) blockSize;
    }

    public static class ObjectAttribute {

        int objSize;
        int shareOffset;
        int shareSize;

        public ObjectAttribute(int objSize, int shareOffset, int shareSize) {
            if (shareOffset + shareSize > objSize) {
                throw new RuntimeException("objSize/shareOffset/shareSize not match");
            }
            this.objSize = objSize;
            this.shareOffset = shareOffset;
            this.shareSize = shareSize;
        }
    }

}
