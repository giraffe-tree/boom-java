# JAVA 内存模型 JMM

主要用了 jcstress 工具, 测试重排序问题

## todo

1. 测试 https://zhuanlan.zhihu.com/p/75509358 中的例子
2. 


## 概念

### 重排序

重排序问题主要分为以下3种

1. 即时编译器的重排序(不是编译成 bytecode 的时候)
2. CPU 指令的重排序
3. 内存重排序

## jcstress 使用

Java Concurrency Stress Tests

- https://github.com/openjdk/jcstress

### 注意事项

1. 使用 run 而不是 debug =.=







