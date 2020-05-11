# 反射

## 问题摘要

1. 反射的开销来自哪里
2. 委派模式的作用
3. 动态实现与本地实现的区别?
4. 为什么 native 实现(本地实现) 反而慢?


### 反射的开销

1. 变长参数方法导致的 Object 数组
2. 基本类型的自动装箱、拆箱
3. 本地方法开销, 通过生成字节码, JIT 编译, 通过方法内联减少开销
4. 每一个Method都有一个root，不暴漏给外部，而是每次copy一个Method。

### 委派模式

在委派模式（Delegate）中，有两个或多个对象参与处理同一个请求，接受请求的对象将请求委派给其他对象来处理。

委派模式参考: https://juejin.im/post/5aaf7bf8f265da23731441e0

- Method.invoke
- DelegatingMethodAccessorImpl
- NativeMethodAccessorImpl

```
at me.giraffetree.java.boomjava.jvm.reflect.ReflectImplTest.target(ReflectImplTest.java:14)
at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
at java.lang.reflect.Method.invoke(Method.java:498)
at me.giraffetree.java.boomjava.jvm.reflect.ReflectImplTest.main(ReflectImplTest.java:24)
```

### 为什么要使用委派模式

在动态实现和本地实现之间进行切换

动态实现和本地实现相比，其运行效率要快上 20 倍. 这是因为动态实现无需经过 Java 到 C++ 再到 Java 的切换，

但由于生成字节码十分耗时，仅调用一次的话，反而是本地实现要快上 3 到 4 倍 .


动态实现: 动态生成字节码的实现，直接使用 invoke 指令来调用目标方法

### 默认使用动态实现的阈值 sun.reflect.inflationThreshold

```
private static boolean noInflation        = false;
// 默认阈值为 15
private static int     inflationThreshold = 15;
```

http://hg.openjdk.java.net/jdk10/jdk10/jdk/file/777356696811/src/java.base/share/classes/jdk/internal/reflect/ReflectionFactory.java#l90

通过 `-Dsun.reflect.inflationThreshold` 来调整

```
val = props.getProperty("sun.reflect.inflationThreshold");
if (val != null) {
    try {
        inflationThreshold = Integer.parseInt(val);
    } catch (NumberFormatException e) {
        throw new RuntimeException("Unable to parse property sun.reflect.inflationThreshold", e);
    }
}
```

http://hg.openjdk.java.net/jdk10/jdk10/jdk/file/777356696811/src/java.base/share/classes/jdk/internal/reflect/ReflectionFactory.java#l627

### int cache:  java.lang.Integer.IntegerCache.high

```
-Djava.lang.Integer.IntegerCache.high=128
```

### 关闭委派实现, 直接使用动态实现 sun.reflect.noInflation

通过 `-Dsun.reflect.noInflation=true` 可以关闭本地实现, 而只使用动态实现

```
private static boolean noInflation        = false;

Properties props = GetPropertyAction.privilegedGetProperties();
String val = props.getProperty("sun.reflect.noInflation");
if (val != null && val.equals("true")) {
    noInflation = true;
}
```

http://hg.openjdk.java.net/jdk10/jdk10/jdk/file/777356696811/src/java.base/share/classes/jdk/internal/reflect/ReflectionFactory.java#l622

### 虚拟机关于每个调用能够记录的类型数量 TypeProfileWidth

我在 jdk8 中使用这个参数, 没有明显变化(TypeProfileWidth=1 -> 2 的时候有变化, 2->3 时 没有明显变化, 1 为 750ms , 2/3 均为 600ms), 
但使用 jdk11 时, 2->3 有明显改善,  600ms 降低为 240ms

```
// 默认值为 2
-XX:TypeProfileWidth=3
```

猜测实现有过修改, 具体原因未知

### 为什么本地实现反而比java实现慢?

参考 R 大的话

> 跨越native边界会对优化有阻碍作用，它就像个黑箱一样让虚拟机难以分析也将其内联，于是运行时间长了之后反而是托管版本的代码更快些。
> 
> 为了权衡两个版本的性能，Sun的JDK使用了“inflation”的技巧：让Java方法在被反射调用时，开头若干次使用native版，
>   等反射调用次数超过阈值时则生成一个专用的MethodAccessor实现类，生成其中的invoke()方法的字节码，以后对该Java方法的反射调用就会使用Java版。

https://www.iteye.com/blog/rednaxelafx-548536

### 反射的基本操作

- getClass
- newInstance()/Array.newInstance(Class,int)/getFields()/getConstructors()/getMethods()
    - 获取类成员
- 类成员操作
    - 使用 Constructor/Field/Method.setAccessible(true) 来绕开 Java 语言的访问限制。
    - 使用 Constructor.newInstance(Object[]) 来生成该类的实例。
    - 使用 Field.get/set(Object) 来访问字段的值。
    - 使用 Method.invoke(Object, Object[]) 来调用方法。

### jdk11 中的反射性能提升

[源代码 Test](./Test.java)

wtf?????? jdk 1.8.0_161 下的结果

Reflecting instantiation took:1826ms
Normal instaniation took: 9232ms

为什么 反射性能更好? 我惊了.... todo: why!!!

jdk 11.0.7 下的结果

Reflecting instantiation took:293ms
Normal instaniation took: 182ms

这提升?? 太香了吧

## 参考

- https://time.geekbang.org/column/article/12192
- http://hg.openjdk.java.net/jdk10/jdk10/jdk/file/777356696811/src/java.base/share/classes/jdk/internal/reflect/ReflectionFactory.java#l80

>  "Inflation" mechanism. Loading bytecodes to implement
> Method.invoke() and Constructor.newInstance() currently costs
> 3-4x more than an invocation via native code for the first
>  invocation (though subsequent invocations have been benchmarked
>  to be over 20x faster). Unfortunately this cost increases
>  startup time for certain applications that use reflection
>  intensively (but only once per class) to bootstrap themselves.
>  To avoid this penalty we reuse the existing JVM entry points
>  for the first few invocations of Methods and Constructors and
>  then switch to the bytecode-based implementations.