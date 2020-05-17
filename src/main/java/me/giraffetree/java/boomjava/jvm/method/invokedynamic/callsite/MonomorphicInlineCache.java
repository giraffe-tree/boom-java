package me.giraffetree.java.boomjava.jvm.method.invokedynamic.callsite;
// 需要更改ASMHelper.MyMethodVisitor中的BOOTSTRAP_CLASS_NAME

import java.lang.invoke.*;

/**
 * 代码来自:
 * https://time.geekbang.org/column/article/12574
 */
public class MonomorphicInlineCache {

    private final MethodHandles.Lookup lookup;
    private final String name;

    public MonomorphicInlineCache(MethodHandles.Lookup lookup, String name) {
        this.lookup = lookup;
        this.name = name;
    }

    private Class<?> cachedClass = null;
    private MethodHandle mh = null;

    public void invoke(Object receiver) throws Throwable {
        if (cachedClass != receiver.getClass()) {
            cachedClass = receiver.getClass();
            mh = lookup.findVirtual(cachedClass, name, MethodType.methodType(void.class));
        }
        mh.invoke(receiver);
    }

    public static CallSite bootstrap(MethodHandles.Lookup l, String name, MethodType callSiteType) throws Throwable {
        MonomorphicInlineCache ic = new MonomorphicInlineCache(l, name);
        MethodHandle mh = l.findVirtual(MonomorphicInlineCache.class, "invoke", MethodType.methodType(void.class, Object.class));
        return new ConstantCallSite(mh.bindTo(ic));
    }
}