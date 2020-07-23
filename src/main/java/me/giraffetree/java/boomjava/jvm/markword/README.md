# 对象头在其生命周期中可能的变化

64位JVM中, 一个对象的 mark word 的大小为 8 个字节, 默认加上 4 个字节的 class pointer,
即组成了 对象的 header 对象头 (12字节)

但由于 class pointer 一般不发生变化, 所以我们主要关注 mark word 部分

## hashcode 与 对象头

[hashcode 与 对象头](https://github.com/giraffe-tree/boom-java/blob/master/src/main/java/me/giraffetree/java/boomjava/concurrent/problem/hashcode)

## 对象头可能的变化

1. synchronized 带来的对象头变化
2. 调用 System.identityHashcode() 带来的对象头变化 
    - hashcode 与 锁占用 头部信息的冲突

### 细化

- 默认是否使用偏向锁
- 偏向锁
- 轻量级锁
- 只使用重量级锁





