# 讲讲 java 对象在堆中的分布(内存布局)

## 概述

前面一段时间了解了 JOL 这个框架, 很快地熟悉了我们平常使用的一些类的内存布局, 强烈推荐!

maven 配置如下

```
        <dependency>
            <groupId>org.openjdk.jol</groupId>
            <artifactId>jol-core</artifactId>
            <version>0.9</version>
        </dependency>

        <dependency>
            <groupId>org.openjdk.jol</groupId>
            <artifactId>jol-cli</artifactId>
            <version>0.9</version>
        </dependency>
```

通过 `System.out.println(ClassLayout.parseClass(xxx.class).toPrintable());` 打印内存布局

## 内存布局的规则

这里我讲的内存布局有几个前提

1. 基于 hotspot 虚拟机
2. 默认使用 压缩指针, 也就是说, object header 占用 12个字节 (8bytes mark word + 4bytes class pointer) (`-XX:+UseCompressedOops`)[2]
3. 对象的内存对齐默认为 8 字节（对应虚拟机选项 -XX:ObjectAlignmentInBytes，默认值为 8）

具体的规则有以下几点

1. 如果一个字段占据 C 个字节，那么该字段的偏移量需要对齐至 NC。[1]
2. 子类所继承字段的偏移量，和父类对应字段的偏移量保持一致,[1] 但是对于对象最后的 padding gap, 子类并不需要完全空出
3. 引用类型默认放在最后, `-XX:FieldsAllocationStyle` 规定了引用的放置位置(默认为1) [3]
    - `-XX:FieldsAllocationStyle=0` 时, Reference -> long/double ->  int/float -> short/char -> byte/boolean
    - `-XX:FieldsAllocationStyle=1` 时, long/double ->  int/float -> short/char -> byte/boolean -> Reference

### 规则1:  如果一个字段占据 C 个字节，那么该字段的偏移量需要对齐至 NC。

需要说明的是, 这里的偏移都是相对于对象开始位置的偏移

举个例子, 现在对象中有两个字段 `int x ; long y;` , 由于 x 为 int 类型, 需要占据 4 个字节, 则 x 的偏移量就需要为 4 的倍数; 

同理, 由于 y 为 long 类型, 需要占据 8 个字节, 则 y 的偏移量就需要为 8 的倍数; 

想想看下面的对象, 在内存中的布局咋样?

```java
private static class Rule1 {
    int a;
    long b;
    byte c;
    short d;
    float e;
}
```

结果如下 `12 + 4 int  + 8 long + 4 float + 2 short + 1 byte + 1 padding`, 可以发现规则满足占据C字节的字段都在偏移C的倍数的位置上.

```
me.giraffetree.java.boomjava.jvm.jol.RuleTest$Rule1 object internals:
 OFFSET  SIZE    TYPE DESCRIPTION                               VALUE
      0    12         (object header)                           N/A
     12     4     int Rule1.a                                   N/A
     16     8    long Rule1.b                                   N/A
     24     4   float Rule1.e                                   N/A
     28     2   short Rule1.d                                   N/A
     30     1    byte Rule1.c                                   N/A
     31     1         (loss due to the next object alignment)
Instance size: 32 bytes
```

### 规则2: 子类所继承字段的偏移量，和父类对应字段的偏移量保持一致

示例如下, 这里我故意在子类中使用了和 父类相同的 `type` 字段

```java
private static class Animal {
    protected boolean inSea;
    protected long number;
    protected double weight;
    protected int type;
}

private static class Cat extends Animal {
    private boolean gender;
    private byte y;
    private String name;
    private int color;
    int type;
}
```

可以看到, 子类对象中, 父类的字段排列没有改变

```
me.giraffetree.java.boomjava.jvm.jol.ExtendsObjectTest$Cat object internals:
 OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
      0    12                    (object header)                           N/A
     12     4                int Animal.type                               N/A
     16     8               long Animal.number                             N/A
     24     8             double Animal.weight                             N/A
     32     1            boolean Animal.inSea                              N/A
     33     3                    (alignment/padding gap)                  
     36     4                int Cat.color                                 N/A
     40     4                int Cat.type                                  N/A
     44     1            boolean Cat.gender                                N/A
     45     1               byte Cat.y                                     N/A
     46     2                    (alignment/padding gap)                  
     48     4   java.lang.String Cat.name                                  N/A
     52    12                    (loss due to the next object alignment)
Instance size: 64 bytes
Space losses: 5 bytes internal + 12 bytes external = 17 bytes total
```

### 规则3: 引用类型默认放在最后, `-XX:FieldsAllocationStyle` 规定了引用的放置位置(默认为1) 

示例如下, 思考下如果引用放在最后, 下面的对象应该如何布局

```java
    private static class Rule3 {
        long b;
        String name;
        long c;
        int e;
        byte f;
    }
```

结果如下: 
 
`-XX:FieldsAllocationStyle=1` 时, long/double ->  int/float -> short/char -> byte/boolean -> Reference

```
me.giraffetree.java.boomjava.jvm.jol.RuleTest$Rule3 object internals:
 OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
      0    12                    (object header)                           N/A
     12     4                int Rule3.e                                   N/A
     16     8               long Rule3.b                                   N/A
     24     8               long Rule3.c                                   N/A
     32     1               byte Rule3.f                                   N/A
     33     3                    (alignment/padding gap)                  
     36     4   java.lang.String Rule3.name                                N/A
Instance size: 40 bytes
Space losses: 3 bytes internal + 0 bytes external = 3 bytes total
```

`-XX:FieldsAllocationStyle=0` 时,  Reference -> long/double ->  int/float -> short/char -> byte/boolean

```
me.giraffetree.java.boomjava.jvm.jol.RuleTest$Rule3 object internals:
 OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
      0    12                    (object header)                           N/A
     12     4   java.lang.String Rule3.name                                N/A
     16     8               long Rule3.b                                   N/A
     24     8               long Rule3.c                                   N/A
     32     4                int Rule3.e                                   N/A
     36     1               byte Rule3.f                                   N/A
     37     3                    (loss due to the next object alignment)
Instance size: 40 bytes
Space losses: 0 bytes internal + 3 bytes external = 3 bytes total
```

## 常用工具类的内存布局

### String

```
java.lang.String object internals:
 OFFSET  SIZE     TYPE DESCRIPTION                               VALUE
      0    12          (object header)                           N/A
     12     4   char[] String.value                              N/A
     16     4      int String.hash                               N/A
     20     4          (loss due to the next object alignment)
Instance size: 24 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
```

### long[]

数组使用 header 中最后4个字节作为 length

这里我新建了一个 长度为3的 long 数组

```
long[] arr = {1L, 2L, 3L};
```

```
[J object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           a9 01 00 f8 (10101001 00000001 00000000 11111000) (-134217303)
     12     4        (object header)                           03 00 00 00 (00000011 00000000 00000000 00000000) (3)
     16    24   long [J.<elements>                             N/A
Instance size: 40 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
```

### ArrayList

可以看到, ArrayList 中继承了 AbstractList 中的 modCount 字段

```
java.util.ArrayList object internals:
 OFFSET  SIZE                 TYPE DESCRIPTION                               VALUE
      0    12                      (object header)                           N/A
     12     4                  int AbstractList.modCount                     N/A
     16     4                  int ArrayList.size                            N/A
     20     4   java.lang.Object[] ArrayList.elementData                     N/A
Instance size: 24 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
```

### HashMap

同样的, HashMap 中继承了 AbstractMap 中的 keySet, values 字段, 可以看到 hashMap 在和 ArrayList 存储相同多的元素下, 占用的空间更大一些;

```
java.util.HashMap object internals:
 OFFSET  SIZE                       TYPE DESCRIPTION                               VALUE
      0    12                            (object header)                           N/A
     12     4              java.util.Set AbstractMap.keySet                        N/A
     16     4       java.util.Collection AbstractMap.values                        N/A
     20     4                        int HashMap.size                              N/A
     24     4                        int HashMap.modCount                          N/A
     28     4                        int HashMap.threshold                         N/A
     32     4                      float HashMap.loadFactor                        N/A
     36     4   java.util.HashMap.Node[] HashMap.table                             N/A
     40     4              java.util.Set HashMap.entrySet                          N/A
     44     4                            (loss due to the next object alignment)
Instance size: 48 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
```

### ConcurrentHashMap

```
java.util.concurrent.ConcurrentHashMap object internals:
 OFFSET  SIZE                                                   TYPE DESCRIPTION                               VALUE
      0    12                                                        (object header)                           N/A
     12     4                                          java.util.Set AbstractMap.keySet                        N/A
     16     4                                   java.util.Collection AbstractMap.values                        N/A
     20     4                                                    int ConcurrentHashMap.sizeCtl                 N/A
     24     8                                                   long ConcurrentHashMap.baseCount               N/A
     32     4                                                    int ConcurrentHashMap.transferIndex           N/A
     36     4                                                    int ConcurrentHashMap.cellsBusy               N/A
     40     4          java.util.concurrent.ConcurrentHashMap.Node[] ConcurrentHashMap.table                   N/A
     44     4          java.util.concurrent.ConcurrentHashMap.Node[] ConcurrentHashMap.nextTable               N/A
     48     4   java.util.concurrent.ConcurrentHashMap.CounterCell[] ConcurrentHashMap.counterCells            N/A
     52     4      java.util.concurrent.ConcurrentHashMap.KeySetView ConcurrentHashMap.keySet                  N/A
     56     4      java.util.concurrent.ConcurrentHashMap.ValuesView ConcurrentHashMap.values                  N/A
     60     4    java.util.concurrent.ConcurrentHashMap.EntrySetView ConcurrentHashMap.entrySet                N/A
Instance size: 64 bytes
Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
```


## 参考

1. 郑雨迪老师的这篇文章让我重新开始思考这个问题, 非常感谢!
    - https://time.geekbang.org/column/article/13081
2. mark word 
    - http://hg.openjdk.java.net/jdk8/jdk8/hotspot/file/87ee5ee27509/src/share/vm/oops/markOop.hpp
3. `-XX:FieldsAllocationStyle` 的解释
    - https://www.cnblogs.com/plxx/p/4642405.html

