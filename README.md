# BOOM-JAVA

## 前言

本项目主要用来探究 java/jvm 中一些有趣的现象, 及其背后的原因

## Content

### java 

- [java 中的 finally 是如何编译成字节码的](./src/main/java/me/giraffetree/java/boomjava/compile/keyword/word_finally)
        
    <details>
        <summary> 下面的方法能运行的结果是什么? </summary>
        
            // 如果 a = 3, 运行的结果是什么
            public static int test4(int a) {
                try {
                    a += 10;
                    a = a / 0;
                } finally {
                    a++;
                    return a;
                }
            }
            public static String testStr3() {
                while (true) {
                    try {
                        return "foo";
                    } finally {
                        break;
                    }
                }
                return "bar";
            }
        
    </details>
    
- [ try resource 块中的 close 方法 与 finally 方法哪个先执行](./src/main/java/me/giraffetree/java/boomjava/compile/problems/try_resource)
    <details>
        <summary>下面的方法执行结果是什么?</summary>
        
        ```
        public static void main(String[] args) {
            try (Car car = new Car()){
                throw new RuntimeException("exception...");
            } catch (Exception e) {
                System.out.println("catch exception...");
            }finally {
                System.out.println("finally...");
            }
        }
        private static class Car implements AutoCloseable {
            @Override
            public void close() throws Exception {
                System.out.println("close...");
            }
        }
        ```
        
    </details>



### JVM

#### 基础

- [查看Java 对象布局、大小](./src/main/java/me/giraffetree/java/boomjava/jvm/jol/JolTest.java)

    <details>
    <summary>new Object() 在堆中占用多少个字节? </summary>
    
    ```
        // 对象 obj 占用了 几个字节? 
        Object obj = new Object();
    ```
    
    </details>

- [boolean 在操作数栈上/在堆中的不同表现](./src/main/java/me/giraffetree/java/boomjava/jvm/data_type/primitive/bool)

    <details>
    <summary>下面的程序会输出什么? </summary>
    
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


- [栈帧中局部变量区与垃圾回收](./src/main/java/me/giraffetree/java/boomjava/jvm/stack/frame/local_variable)

    <details>
    <summary> 对象_64M 的空间是否能被回收? </summary>
    
    ```
        {
            byte[] _64M = new byte[1024 * 1024 * 64];
        }
        System.gc();
    ```
    
    </details>
    
- [safepoint 与 counted loop 的关系]()

    <details>
        <summary> 下面这段代码的 GC 是否会受到影响 ? </summary>
        
            public static void foo() {
                System.out.println("start foo...");
                int sum = 0;
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    sum += Math.sqrt(i);
                }
            }
    </details>


- [如何利用重载把 java 编译器弄晕?](./src/main/java/me/giraffetree/java/boomjava/jvm/method/overload)

    <details>
    <summary> add(1,1,1) </summary>
    
    ```
        private static int add(int... x) {
            return 5;
        }
        private static int add(Integer... x) {
            return 6;
        }
        private static int add(int a, int... x) {
            return 7;
        }
        private static int add(Integer a, int... x) {
            return 8;
        }
        private static int add(int a, Integer... x) {
            return 9;
        } 
        private static int add(int a, Object... x) {
            return 10;
        }
    ```
    
    </details>
    
### 并发

- [ new Object() 与不安全发布](./src/main/java/me/giraffetree/java/boomjava/jvm/jmm/UnsafePublication.java)
    <details>
        <summary> a!=null 时, a 中的 x00,x01,x02,x03 是否正确初始化了 ?</summary>
        MyObject a = new MyObject(x);
        
        static class MyObject {
            int x00, x01, x02, x03;
    
            public MyObject(int x) {
                x00 = x;
                x01 = x;
                x02 = x;
                x03 = x;
            }
        }
                

    </details>

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

- [卡表 card table 中的 false sharing 与 UseCondCardMark](./src/main/java/me/giraffetree/java/boomjava/concurrent/problem/falseShare/cardtable)
    
    <details>
        <summary> 更新 年老代对象指向eden代对象的引用, 为什么会引起 false sharing 问题? </summary>

    ```
            // 使用 多线程更新 hugeObj 中的引用 x1, x2
            private static class Obj64 {
                long l1;
                long l2, l3, l4, l5;
            }
        
            private static class HugeObj {
                private byte[] hugeArray;
                private Obj64 x1;
                private Obj64 x2;
        
                public HugeObj(int byteLen, Obj64 x1, Obj64 x2) {
                    this.hugeArray = new byte[byteLen];
                    this.x1 = x1;
                    this.x2 = x2;
                }
            }
    ```

    </details>
    
    
### 工具使用

#### asm 使用

[使用 ASMifier 通过 原始字节码 生成 可以生成该字节码的 java code](./src/main/java/me/giraffetree/java/boomjava/jvm/asm)

#### IR 可视化工具 igv 的使用

 [Ideal Graph Visualizer 安装与使用](./src/main/java/me/giraffetree/java/boomjava/utils/igv)
 
#### jcstress 的使用

[并发测试工具 jcstress](./src/main/java/me/giraffetree/java/boomjava/utils/jcstress)

#### jitwatch 的使用

[关于如何打印 JIT 编译后的 汇编代码 - jitwatch 的使用](./src/main/java/me/giraffetree/java/boomjava/utils/jitwatch)

#### Java Microbenchmark Harness JMH 的使用

[JMH (Java Microbenchmark Harness) idea 使用指南](./src/main/java/me/giraffetree/java/boomjava/utils/jmh)
 