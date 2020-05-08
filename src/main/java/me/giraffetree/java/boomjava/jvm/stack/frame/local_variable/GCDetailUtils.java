package me.giraffetree.java.boomjava.jvm.stack.frame.local_variable;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

/**
 * @author GiraffeTree
 * @date 2020/5/8
 */
public class GCDetailUtils {

    public static MemoryUsage getEdenUsage() {
        return getUsage("Eden");
    }

    public static MemoryUsage getUsage(Region region) {
        return getUsage(region.getName());
    }

    public static MemoryUsage getUsage(String name) {
        List<MemoryPoolMXBean> list = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pool : list) {
            if (pool.getName().contains(name)) {
                return pool.getUsage();
            }
        }
        return null;
    }

    public enum Region {

        /**
         * parallel scavenge
         */
        PS_EDEN_SPACE("PS Eden Space"),
        PS_SUVIVOR_SPACE("PS Survivor Space"),
        PS_OLD_GEN("PS Old Gen"),
        CODE_CACHE("Code Cache"),
        METASPACE("Metaspace"),
        COMPRESSED_CLASS_SPACE("Compressed Class Space"),
        ;

        Region(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }
    }


}
