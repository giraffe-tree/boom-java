# `lock add DWORD PTR [rsp],0x0`

## 背景

在 java 内存模型 JMM, 会在 volatile 变量写之后插入 写读屏障

而 hotspot 在具体实现时, 会将写读屏障代替为 `lock add DWORD PTR [rsp],0x0`

## 疑问

### LOCK 前缀如何与 volatile 字段的写操作会强制刷新缓存 联系在一起的?

lock后的写操作会回写已修改的数据，同时让其它CPU相关缓存行失效，从而重新从主存中加载最新的数据

### C++ 中的内存屏障如何实现的?




## 参考

1. %rsp 是堆栈指针寄存器，通常会指向栈顶位置，堆栈的 pop 和push 操作就是通过改变 %rsp 的值即移动堆栈指针的位置来实现的。
    - https://zhuanlan.zhihu.com/p/27339191
2. volidate关键字解析
    - https://juejin.im/post/5c35cd9de51d45522a41f3a0
3. C/C++ -- 编程中的内存屏障(Memory Barriers)
    - https://www.cnblogs.com/hehehaha/archive/2013/05/07/6332845.html
4. volatile 关键字 在 java / c++ 中有什么不同
    - https://stackoverflow.com/questions/19923352/whats-the-difference-of-the-usage-of-volatile-between-c-c-and-c-java
5. 编译器内存屏障/cpu内存屏障
    - https://cloud.tencent.com/developer/article/1403223
6. 内存屏障也称内存栅栏，内存栅障，屏障指令等，是一类同步屏障指令，它使得 CPU 或编译器在对内存进行操作的时候, 严格按照一定的顺序来执行, 也就是说在memory barrier 之前的指令和memory barrier之后的指令不会由于系统优化等原因而导致乱序。
    - https://zh.wikipedia.org/wiki/%E5%86%85%E5%AD%98%E5%B1%8F%E9%9A%9C
7. 什么时候需要x86 LFENCE，SFENCE和MFENCE指令？
    - https://stackoverflow.com/questions/27595595/when-are-x86-lfence-sfence-and-mfence-instructions-required
8. intel x86系列CPU既然是strong order的，不会出现loadload乱序，为什么还需要lfence指令？
    - https://www.zhihu.com/question/29465982



