# invokedynamic 

## 问题摘要

1. 结合 golang/python 讲讲鸭子类型
2. 为什么需要引入 invokedynamic ?
3. MethodHandle 与 反射的区别?

## 引子

### 鸭子类型

如果一个人长得像鸭子, 那他就是个鸭子??? 哈哈哈

请参考这篇博文

https://github.com/qcrao/Go-Questions/blob/master/interface/Go%20%E8%AF%AD%E8%A8%80%E4%B8%8E%E9%B8%AD%E5%AD%90%E7%B1%BB%E5%9E%8B%E7%9A%84%E5%85%B3%E7%B3%BB.md

### invokedynamic的出现 与 动态类型语言

想想这个问题, invokedynamic 是 jdk1.0 以来第一个新增加的字节码指令, 为什么要新增这个指令呢?

我们知道 jvm 中已经有 4 个与方法调用相关的指令 

- invokespecial
- invokestatic
- invokeinterface
- invokevirtual

难道 invokedynamic 完成的功能这4个指令完成不了么? 呦, 还真是

这就要聊到 jvm 上的动态语言, 想想我之前还用过一会儿 Clojure 一门 lisp 方言. 
哈哈, 不过现在已经忘光了

在过去的 jvm 历史上 , jvm 层面对于动态语言的支持一直不够, 为什么这么讲呢?

- jdk1.7 之前的字节码指令集中, 上面提到的4条字节码指令的第一个参数都是 **被调用的方法的符号引用**. 
- 在类加载的时候, 具体来说是 链接的最后一个阶段解析时, hotspot 虚拟机会将符号引用解析为实际引用
    - 如果想了解的更深一点话, 看下下面的内容, 当然它们和本文的关联很少
    - 对于可以静态绑定(invokespecial, invokestatic)的方法调用而言，实际引用为目标方法的指针。
    - 对于需要动态绑定(invokevirtual, invokeinterface)的方法调用而言，实际引用为辅助动态绑定的信息。
    - 相对于 hotspot 的实现, jvm 只规定了在方法实际调用前完成解析, 而不是指定在类加载的链接阶段  
- 那么问题就来了, 动态语言需要在运行时才能知道对象的类型, 但是需要在类加载的时候就完成解析, 很明显不可能做到

所以, 为了在 jvm 上提供动态类型的支持, 于是在 JDK 1.7 (JSR-292) 中出现了 invokedynamic

### invokedynamic 的实现

- invokedynamic 的位置被称为动态调用点 dynamic call site
- invokedynamic 指令的第一个参数为 CONSTANT_InvokeDynamic_info 的常量
    - 包含如下信息:
        - 引导方法
        - 方法类型 methodType 和名称


### invokedynamic 与 其他四条指令的区别

除了我们之前提到的区别之外, invokedynamic 与其他四条方法指令最大的区别在于 分派逻辑

我们可以自由指定, 我们调用父类的方法/父类的父类的方法, 而不是仅仅只能使用一个 `super`

抽象点说, invokedynamic 可以由程序员指定访问哪个类的方法; 而不是通过虚拟机决定;

### methodHandle 与 反射的区别

1. 反射是为java语言服务的, 而 MethodHandle 是为 jvm 上的语言服务的
2. 反射是模拟java层面的方法调用, methodHandle 是模拟字节码层面的方法调用
    - java 的方法由 方法名+参数唯一确定, 而返回值只能是一个
    - jvm 的方法由 方法名+参数+返回值确定
3. java.lang.refect.Method 所携带的信息远比 MethodHandle 多
4. 权限检查方面, 反射需要在每次调用时检查, 而methodHandle只会在创建时检查

可以这样理解, methodHandle 其实 invokedynamic 在 java 层面上的实现 [?]

### methodHandle 为什么需要指定 class

#### 参考

- 深入理解java虚拟机 8.3.3


### MethodType

方法句柄的类型（MethodType）是由所指向方法的参数类型以及返回类型组成的。
它是用来确认方法句柄是否适配的唯一关键。

### ShowHiddenFrames

`-XX:+UnlockDiagnosticVMOptions -XX:+ShowHiddenFrames`

打印被 Java 虚拟机隐藏了的栈信息

### 导出methodHandle生成的字节码文件

`-Djava.lang.invoke.MethodHandle.DUMP_CLASS_FILES=true`


## 参考

- https://time.geekbang.org/column/article/12564
- jvm spec CONSTANT_Dynamic_info CONSTANT_InvokeDynamic_info
    - https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.10
- jvm spec invokedynamic 
    - https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.10.1.9.invokedynamic
- 什么是 invokedynamic , 如何使用它?
    - https://stackoverflow.com/questions/6638735/whats-invokedynamic-and-how-do-i-use-it

