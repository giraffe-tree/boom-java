package me.giraffetree.java.boomjava.utils.cache;

/**
 * @author GiraffeTree
 * @date 2021/11/28
 */
public interface IConcurrentCompute<K, V> {

    /**
     * 计算
     *
     * @param key    键
     * @param loader 原始数据计算器
     * @return V 计算结果
     */
    V compute(K key, ICompute<V> loader) throws Exception;

}
