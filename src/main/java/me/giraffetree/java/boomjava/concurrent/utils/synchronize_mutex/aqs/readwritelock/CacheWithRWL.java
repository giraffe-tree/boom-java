package me.giraffetree.java.boomjava.concurrent.utils.synchronize_mutex.aqs.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁来使用 hashMap 作为缓存
 *
 * @author GiraffeTree
 * @date 2020-05-04
 */
public class CacheWithRWL {

    private static Map<String, Object> map = new HashMap<>();
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock r = lock.readLock();
    private static ReentrantReadWriteLock.WriteLock w = lock.writeLock();

    public static Object get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    public static Object put(String key, Object value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public static void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }

}
