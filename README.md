# BOOM-JAVA

## 前言

本项目主要用来探究 java/jvm 中一些有趣的现象, 及其背后的原因

## 目录

### JVM

#### 基础

- [查看Java 对象布局、大小](./src/main/java/me/giraffetree/java/boomjava/jvm/jol/JolTest.java)

    <details>
    <summary>new Object() 在堆中占用多少个字节? </summary>
    
    ```java
        // 对象 obj 占用了 几个字节? 
        Object obj = new Object();
    ```
    
    </details>

- [boolean 在操作数栈上/在堆中的不同表现](./src/main/java/me/giraffetree/java/boomjava/jvm/data_type/primitive/bool)

    <details>
    <summary>下面的程序会输出什么? </summary>
    
    ```java
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


- [栈帧中局部变量区与垃圾回收](./src/main/java/me/giraffetree/java/boomjava/jvm/stack/frame/local_variable)

    <details>
    <summary> 对象_64M 的空间是否能被回收? </summary>
    
    ```java
        {
            byte[] _64M = new byte[1024 * 1024 * 64];
        }
        System.gc();
    ```
    
    </details>


### 并发


- [false sharing 虚共享/伪共享](./src/main/java/me/giraffetree/java/boomjava/concurrent/problem/falseShare)
    
    <details>
    <summary> 相邻的 volatile 字段如何导致 false sharing ? </summary>
    
    ```java
        private static class Foo {
            volatile int a;
            volatile int b;
        }
    
    ```
    
    </details>

