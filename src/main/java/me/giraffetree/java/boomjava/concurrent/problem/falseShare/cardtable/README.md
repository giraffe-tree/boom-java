# 卡表中的 falseSharing

为了避免 minor GC (新生代GC) 对全堆的扫描, hotspot 引入了 cardTable 技术 [1]

## 如何实现 卡表 [1]

> 该技术将整个堆划分为一个个大小为 512 字节的卡，并且维护一个卡表，用来存储每张卡的一个标识位。
> 这个标识位代表对应的卡是否可能存有指向新生代对象的引用。如果可能存在，那么我们就认为这张卡是脏的。

> 在进行 Minor GC 的时候，我们便可以不用扫描整个老年代，而是在卡表中寻找脏卡，
> 并将脏卡中的对象加入到 Minor GC 的 GC Roots 里。当完成所有脏卡的扫描之后，Java 虚拟机便会将所有脏卡的标识位清零。

### 实现 [1]

首先，如果想要保证每个可能有指向新生代对象引用的卡都被标记为脏卡，
那么 Java 虚拟机需要截获每个引用型实例变量的写操作，并作出对应的写标识位操作。

这个操作在解释执行器中比较容易实现。但是在即时编译器生成的机器码中，
则需要插入额外的逻辑。这也就是所谓的写屏障（write barrier）。

```
CARD_TABLE [this address >> 9] = DIRTY;
```


虽然CardTable提高了Minor GC 的效率, 但在高并发环境下，写屏障又带来了虚共享（false sharing）问题

##  虚共享

- 卡表false sharing 对应的实际内存大小为 32KB

### UseCondCardMark

原始: `CARD_TABLE [this address >> 9] = DIRTY;`

`-XX:+UseCondCardMark` 通过事先判断了card 是否 dirty , 减少 card table 的写入, 从而提高性能, 减少false sharing [1]

```
if (CARD_TABLE [this address >> 9] != DIRTY) 
  CARD_TABLE [this address >> 9] = DIRTY;
```

### 测试

重要的是, 要构造一个 老年代 -> eden 代的引用, 我这里使用大对象直接进入老年代的机制来构造这个对象[4]

[CardTableFalseSharingTest](./CardTableFalseSharingTest.java)

## 参考

1. 极客时间 垃圾回收
    - https://time.geekbang.org/column/article/13137
2. Why use Conditional Card Marking, in a Concurrent System 
    - http://robsjava.blogspot.com/2013/03/why-to-use-conditional-card-marking.html
3. `-XX：+ UseCondCardMark` 可以在高度并行的应用程序中帮助您
    - https://stackoverflow.com/questions/28568171/examples-for-code-that-is-surprisingly-affected-by-jvm-jvm-options
4. PretenureSizeThreshold 的默认值和作用
    - https://juejin.im/post/5d1b048ff265da1bb67a326e

