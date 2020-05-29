# 内联缓存

有理解内联缓存的机制, 但没有测试出优势=.=

todo: 没有测试出来内联缓存的优势

## 概述

### 定义

内联缓存的目的是通过在调用点直接记住先前方法查找的结果来加快运行时方法绑定

### hotspot 实现

hotspot 使用 单态内联缓存

Java 虚拟机所采用的单态内联缓存将纪录调用者的动态类型，以及它所对应的目标方法。
当碰到新的调用者时，如果其动态类型与缓存中的类型匹配，则直接调用缓存的目标方法。

否则，Java 虚拟机将该内联缓存劣化为超多态内联缓存，在今后的执行过程中直接使用方法表进行动态绑定。

### 为什么要动态绑定? 不能在编译器知道么?

示例如下:  a 的类型在编译器不可知

```
A a = null;
int i = 随机数;
if(i 是奇数){
     a = new B();
}else{
    a = new C();
}
a.fun();
```

另外的例子: 使用接口传参, 只有在运行时才知道类型

## 多态与内联

单态（monomorphic）指的是仅有一种状态的情况。多态（polymorphic）指的是有限数量种状态的情况。
二态（bimorphic）是多态的其中一种。
超多态（megamorphic）指的是更多种状态的情况。
通常我们用一个具体数值来区分多态和超多态。在这个数值之下，我们称之为多态。否则，我们称之为超多态。

```

```

## 关于如何打印 JIT 编译后的 汇编代码

#### 方法1 使用 debug 版本的openjdk

参见: https://github.com/ojdkbuild/ojdkbuild

使用 `-XX:+PrintAssembly` 启动 java 程序即可


#### 方法2 使用 JITwatch

参见: https://github.com/AdoptOpenJDK/jitwatch

下载Release版本然后启动
 
```
# 我在用的时候有部分测试没通过, 所以加了 `-DskipTests`
mvn clean compile test exec:java -DskipTests
mvn exec:java
```

java 运行时的 vm 参数, 这里我指定了 logFile 的位置, 防止它每次都生成一个

`-XX:CompileThreshold=1000 -XX:+UnlockDiagnosticVMOptions -XX:+TraceClassLoading -XX:+LogCompilation -XX:+PrintAssembly -XX:LogFile=hotspot.log `

使用指南: 

> 我在使用 JITwatch 时, 分析了第一种方法得出的 log 日志, 但是没法在 JITwatch 中查看 汇编代码
> 
> 解决方案: 使用下面的方法3, 重新生成包含汇编代码的log日志

原来这个解决方法已经在 JITwatch的文档中写了 =.= 没仔细看, 请参考 https://github.com/AdoptOpenJDK/jitwatch/wiki/Instructions

#### 方法3 使用 hsdis-amd64.dll

这种方法可以使用我们平常使用的 jdk , 只需要加上一个 dll 就可以了

Could not load hsdis-amd64.dll; library not loadable; PrintAssembly is disabled

需要下载 hsdis-amd64.dll

我使用的是 win10 ,下载地址

> http://vorboss.dl.sourceforge.net/project/fcml/fcml-1.1.1/hsdis-1.1.1-win32-amd64.zip
            
解压后将 hsdis-amd64.dll 和 hsdis-amd64.lib 放在 jdk 的 bin 目录下即可

## todo

1. 了解下 C++ 多态的实现
2. -Xint:完全采用解释器模式执行程序; -Xcomp:完全采用即时编译器模式执行程序

## 参考

- 编译
    - `javac -encoding utf8 Passenger.java`
    - 防止 GBK 不可映射字符
- https://en.wikipedia.org/wiki/Inline_caching
- https://time.geekbang.org/column/article/12098
- java disable inlining for a particular class / method
    - https://stackoverflow.com/questions/48029220/jvm-disable-inlining-for-a-particular-class-or-method
    - 阻止编译
        - `-XX:CompileCommand=exclude,java/lang/String.indexOf`
    - 阻止内联
        - `-XX:CompileCommand=dontinline,java/lang/String.indexOf`
- hotspot没实现 Megamorphic inline caching？
    - https://hllvm-group.iteye.com/group/topic/29140
    - https://gist.github.com/rednaxelafx/1344520
    - https://www.iteye.com/blog/rednaxelafx-241430
- VM option 'TraceInlineCacheClearing' is develop and is available only in debug version of VM.
    - `-Xcomp -XX:+TraceInlineCacheClearing`
- openjdk8 debug
    - https://github.com/ojdkbuild/ojdkbuild
- jitwatch 介绍
    - https://juejin.im/post/5e10aecbe51d4541145de5a8




