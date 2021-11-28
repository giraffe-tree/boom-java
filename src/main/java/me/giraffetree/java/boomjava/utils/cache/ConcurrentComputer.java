package me.giraffetree.java.boomjava.utils.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author GiraffeTree
 * @date 2021/11/28
 */
public class ConcurrentComputer<K, V> implements IConcurrentCompute<K, V> {


    private final ConcurrentHashMap<K, FutureTask<V>> futureMap;
    private final int defaultTimeout;
    private final TimeUnit defaultTimeUnit;

    public ConcurrentComputer() {
        futureMap = new ConcurrentHashMap<>(128);
        defaultTimeout = 1000;
        defaultTimeUnit = TimeUnit.MILLISECONDS;
    }

    @Override
    public V compute(K key, ICompute<V> loader) throws Exception {
        return compute(key, loader, defaultTimeout, defaultTimeUnit);
    }

    private V compute(K key, ICompute<V> loader, int timeout, TimeUnit timeUnit) throws Exception {
        FutureTask<V> task = futureMap.get(key);
        if (task == null) {
            task = new FutureTask<>(loader::compute);
            FutureTask<V> oldTask = futureMap.putIfAbsent(key, task);
            if (oldTask != null) {
                // 已经有 task 了
                task = oldTask;
            }else {
                task.run();
            }
        }
        return task.get(timeout, timeUnit);
    }


}
