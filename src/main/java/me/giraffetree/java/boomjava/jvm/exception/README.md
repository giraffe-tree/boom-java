# 异常

## finally

我们都知道 finally 代码块的代码, 在大多数情况下, 都会执行.
(比如 daemon 线程在所有非daemon线程执行完成之后, 并不会再执行下去, 导致 finally 代码块不执行)

Java 编译器会复制 finally 代码块的内容，分别放在 try-catch 代码块所有正常执行路径以及异常执行路径的出口中。

所以说起来, 这点和 monitorenter/ monitorexit 类似

## 被屏蔽的异常

问题: 如果在执行finally语句时抛出异常，那么，catch语句的异常还能否继续抛出

具体请查看: https://www.liaoxuefeng.com/wiki/1252599548343744/1264738764506656

在面试的时候, 会遇到什么 catch 里面返回了, finally 会不会执行; finally 里面返回了, catch 的异常还会抛出么; 
诸如此类, 一句话, 看字节码就行了. 

## 异常表

jvm 使用 异常表来捕获异常

我们来看下 [ExceptionBytecodeTest](./ExceptionBytecodeTest.java) 的 .class 文件反汇编之后的异常表

```
javac ExceptionBytecodeTest.java
javap -c ExceptionBytecodeTest
```

结果如下: 

```
  static void test();
    Code:
       0: iconst_3
       1: newarray       int
       3: astore_0
       4: aload_0
       5: iconst_4
       6: iconst_1
       7: iastore
       8: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      11: ldc           #3                  // String hello finally...
      13: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      16: goto          50
      19: astore_1
      20: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      23: ldc           #6                  // String process exception...
      25: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      28: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      31: ldc           #3                  // String hello finally...
      33: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      36: goto          50
      39: astore_2
      40: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      43: ldc           #3                  // String hello finally...
      45: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      48: aload_2
      49: athrow
      50: return
    Exception table:
       from    to  target type
           4     8    19   Class java/lang/ArrayIndexOutOfBoundsException
           4     8    39   any
          19    28    39   any
```

我们可以看到有个 Exception table 的输出

```
from    to  target type
   4     8    19   Class java/lang/ArrayIndexOutOfBoundsException
   4     8    39   any
  19    28    39   any
```

### 异常表的第一行

我们对这条进行分析

```
from    to  target type
   4     8    19   Class java/lang/ArrayIndexOutOfBoundsException
```

#### from 和 to 

- 代表监控抛出异常的范围
- 比如表中的 from=4, to=8(不包含8) 均为字节码索引



4 ~7 索引对应的字节码如下: 

```
4: aload_0
5: iconst_4
6: iconst_1
7: iastore
```

其实就是  `arr[4] = 1;` 的字节码

#### target

target 表示捕获异常后, 需要执行的字节码开始的索引

例如: target=19

则从 19 号索引的字节码开始执行

```
19: astore_1
20: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
23: ldc           #6                  // String process exception...
... 后面的字节码也会执行, 但这里我省略了没写
```

#### type

即为需要捕获的异常类型

any 表示捕获所有种类的异常

### 关于异常表第二行和第三行

```
from    to  target type
   4     8    39   any
  19    28    39   any
```

这里涉及到异常表和 finally 的关系

finally 需要在任何时候执行, 则 try / catch 块中的任意发生的异常都需要执行到 finally 块, 
即 target=39指向的字节码

## 参考

- https://time.geekbang.org/column/article/12134
