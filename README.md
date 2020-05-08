# BOOM-JAVA

## 前言

本项目主要用来探究 java/jvm 中一些有趣的现象, 及其背后的原因

## 目录

### jvm

#### 基础

[boolean 在操作数栈上/在堆中的不同表现](./src/main/java/me/giraffetree/java/boomjava/jvm/data_type/primitive/bool)

<details>
<summary>详细信息</summary>

```
// 通过修改字节码, 将flag的值改为2, 下面的程序会输出什么? 
boolean flag = 2;
if (flag) {
    System.out.println("Hello, Java!");
}
if (flag == true) {
    System.out.println("Hello, JVM!");
} 
```

</details>

- [查看Java 对象布局、大小](./src/main/java/me/giraffetree/java/boomjava/jvm/jol/JolTest.java)



### 并发

- [false sharing 虚共享/伪共享](./src/main/java/me/giraffetree/java/boomjava/concurrent/problem/falseShare)


