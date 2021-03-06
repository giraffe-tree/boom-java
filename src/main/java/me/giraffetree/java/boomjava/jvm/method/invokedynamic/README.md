# invokedynamic 

## 问题摘要

1. 结合 golang/python 讲讲鸭子类型
2. 为什么需要引入 invokedynamic 这个指令?
3. MethodHandle 与 反射的区别?
4. invokedynamic 与 其他四条指令的区别

todo:

1. methodHandle 与 reflection 的性能差异
    - https://stackoverflow.com/questions/15621434/methodhandle-performance
    - with JMH
2. 



## 引子

### 鸭子类型

如果一个人长得像鸭子, 那他就是个鸭子??? 哈哈哈

请参考这篇博文

https://github.com/qcrao/Go-Questions/blob/master/interface/Go%20%E8%AF%AD%E8%A8%80%E4%B8%8E%E9%B8%AD%E5%AD%90%E7%B1%BB%E5%9E%8B%E7%9A%84%E5%85%B3%E7%B3%BB.md

## 问题与解答

### invokedynamic 的出现 与 动态类型语言

想想这个问题, invokedynamic 是 jdk1.0 以来第一个新增加的字节码指令, 为什么要新增这个指令呢?

我们知道 jvm 中已经有 4 个与方法调用相关的指令 

- invokespecial
- invokestatic
- invokeinterface
- invokevirtual

难道 invokedynamic 完成的功能这4个指令完成不了么? 呦, 还真是

这就要聊到 jvm 上的动态语言, 想想我之前还用过一会儿 Clojure 一门运行在 JVM 上的 lisp 方言. 
哈哈, 不过现在已经忘得差不多了

在过去的 jvm 历史上 , jvm 层面对于动态语言的支持一直不够, 为什么这么讲呢?

- jdk1.7 之前的字节码指令集中, 上面提到的4条字节码指令的第一个参数都是 **被调用的方法的符号引用**. 
- 在类加载的时候, 具体来说是 链接的最后一个阶段解析(resolution)时, hotspot 虚拟机会将符号引用解析为实际引用
    - 如果想了解的更深一点话, 看下下面的内容, 当然它们和本文的关联很少
    - 对于可以静态绑定(invokespecial, invokestatic)的方法调用而言，实际引用为目标方法的指针。
    - 对于需要动态绑定(invokevirtual, invokeinterface)的方法调用而言，实际引用为辅助动态绑定的信息。
    - 相对于 hotspot 的实现, jvm 只规定了在方法实际调用前完成解析, 而不是指定在类加载的链接阶段  
- 那么问题就来了, 动态语言需要在运行时才能知道对象的类型, 但是需要在类加载的时候就完成解析, 很明显不可能做到

所以, 为了在 jvm 上提供动态类型的支持, 于是在 JDK 1.7 (JSR-292) 中出现了 invokedynamic

### methodhandle

#### 概念

[Class MethodHandle](https://docs.oracle.com/javase/10/docs/api/java/lang/invoke/MethodHandle.html)

> A method handle is a typed, directly executable reference to an underlying method
> 
> 方法句柄是一个强类型的、能够被直接执行的底层方法的引用

1. 方法句柄可以指向常规的静态方法或者实例方法，也可以指向构造器或者字段
2. 方法句柄


#### methodhandle 与 invokevirtual/invokeinterface/invokestatic/invokespecial 的关联

调用方法句柄，和原本对应的调用指令是一致的。
也就是说，对于原本用 invokevirtual 调用的方法句柄，它也会采用动态绑定；
而对于原本用 invkespecial 调用的方法句柄，它会采用静态绑定。

如下面的示例[MethodHandleTest.java](./MethodHandleTest.java):

```java
public class MethodHandleTest {
    static class Book {
        static void read() {
            System.out.println("read book...");
        }
        static MethodHandles.Lookup getLookup() {
            // 方法句柄的访问权限不取决于方法句柄的创建位置，而是取决于 Lookup 对象的创建位置。
            return MethodHandles.lookup();
        }
    }
    public static void main(String[] args) throws Throwable {
        MethodType methodType = MethodType.methodType(void.class);
        MethodHandles.Lookup lookup = Book.getLookup();
        MethodHandle read = lookup.findStatic(Book.class, "read", methodType);
        read.invoke();
    }
}
```



参考:  https://zhuanlan.zhihu.com/p/26389041


### 从 jvm 11规范中讲讲 invokedynamic 的实现

invokedynamic: Invoke a dynamically-computed call site

- https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-6.html#jvms-6.5.invokedynamic

#### 讲讲 constant pool 中关于 invokedynamic 需要使用到的信息

java class file 中有一部分为  [Constant Pool](https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4) , 
我们从 `constant pool` 讲起

[jvm 11 spec 4.4.10](https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.10) 中有如下描述

> `constant_pool` 表中的大多数结构通过组合表中静态记录的名称，描述符和值来直接表示实体。
> 相反，`CONSTANT_Dynamic_info` 和 `CONSTANT_InvokeDynamic_info` 结构通过指向 *动态计算实体
> 的代码* 间接表示实体。
>
> 该代码被称为**引导方法**，
> 这个引导方法会在 JAVA 虚拟机解析(resolution)时根据上面的数据结构调用((§5.1, §5.4.3.6)),
> 每个结构都指定一个引导方法以及表示要计算实体特征的辅助名称和类型。

- CONSTANT_Dynamic_info
    - 用来表示一个动态计算的常量(a dynamically-computed constant)
    - 这个常量由引导方法(bootstrap method)经过一系列的计算产生
    - 结构中指定的辅助类型限制了动态计算常量的类型
- CONSTANT_InvokeDynamic_info
    - 用来表示一个动态计算的调用点 (a dynamically-computed call site)
    - 在 invokedynamic 指令过程中, 通过调用引导方法产生 `java.lang.invoke.CallSite` 的一个实例
    - 结构中指定的辅助类型限制了动态计算的调用站点的方法类型。

```
CONSTANT_Dynamic_info {
    // 值为 CONSTANT_Dynamic
    u1 tag;
    
    // bootstrap_methods 的 index 值, 不允许循环引用, we permit cycles initially but mandate a failure at resolution      
    u2 bootstrap_method_attr_index;
    
    // constant_pool 的 index 值, CONSTANT_NameAndType_info 类型, a field descriptor, 
    u2 name_and_type_index;
}

CONSTANT_InvokeDynamic_info {
    // 值为 CONSTANT_InvokeDynamic
    u1 tag;   
    
    // bootstrap_methods 的 index 值, 不允许循环引用, we permit cycles initially but mandate a failure at resolution                         
    u2 bootstrap_method_attr_index; 
    
    // constant_pool 的 index 值, CONSTANT_NameAndType_info 类型, a method descriptor        
    u2 name_and_type_index;             }
```

#### 动态计算常量与调用点的解析 Dynamically-Computed Constant and Call Site Resolution

[jvm 11 spec 5.4.3.6](https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-5.html#jvms-5.4.3.6)

为了将未解析的符号引用 `R` 解析为动态计算的常量或调用点，需要执行三个任务。

1. 检查 `R` 以确定哪些代码将用作其引导方法(bootstrap method)，以及哪些参数将传递给该代码。
2. 参数会被打包到一个Object数组中，并调用 bootstrap 方法。
    - 该Object数组的前三个参数类型如下
        1. `java.lang.invoke.MethodHandles.Lookup`
        2. `String` 从R中解析出来的名称
        3. 如果是 field descriptor -> 则为 `Class`; 如果是 method descriptor -> 则为 `java.lang.invoke.MethodType `
3. bootstrap 方法的结果经过验证，返回解析的结果 X
    - 如果 `R` 为动态计算常量 (dynamically-computed constant), 则 X 为 field descriptor 中的类型
    - 如果 `R` 为动态计算调用点(dynamically-computed call site), 则 X 为`java.lang.invoke.CallSite` 的实例, 或其子类的实例
        - The type of the `java.lang.invoke.CallSite` is semantically equal to the method descriptor given by R.

> 关于多线程竞争: 
> 调用引导方法会有多线程竞争问题; 所以跟类加载一样, 要加锁;
>
> If several threads attempt resolution of R at the same time, the bootstrap method may be invoked concurrently. 
> Therefore, bootstrap methods which access global application data must take precautions against race conditions.  


#### invokedynamic 

- invokedynamic 指令的第一个参数为 CONSTANT_InvokeDynamic_info 的常量
    - 包含如下信息:
        - 引导方法
        - 方法类型 methodType 
        - 名称



### invokedynamic 与 其他四条指令的区别


除了我们之前提到的区别之外, invokedynamic 与其他四条方法指令最大的区别在于 **分派逻辑**

我们可以自由指定, 我们调用父类的方法/父类的父类的方法, 而不是仅仅只能使用一个 `super`

抽象点说, invokedynamic 可以由程序员指定访问哪个类的方法; **而不是通过虚拟机决定**;

> 来源于:  周志明的深入理解 JVM

### methodHandle 与 反射的区别

1. 反射是为java语言服务的, 而 MethodHandle 是为 jvm 上的语言服务的
2. **反射是模拟java层面的方法调用, methodHandle 是模拟字节码层面的方法调用**
    - java 的方法由 方法名+参数唯一确定, 而返回值只能是一个
    - jvm 的方法由 方法名+参数+返回值确定
3. java.lang.refect.Method 所携带的信息远比 MethodHandle 多
4. 权限检查方面, 反射需要在每次调用时检查, 而methodHandle只会在创建时检查

可以这样理解, methodHandle 其实 invokedynamic 在 java 层面上的实现 [?]

### methodHandle 为什么需要指定 class

### Signature polymorphism

```
// java.lang.invoke.MethodHandle
public final native @PolymorphicSignature Object invoke(Object... args) throws Throwable;

public final native @PolymorphicSignature Object invokeExact(Object... args) throws Throwable;
```

[MethodHandle docs](https://docs.oracle.com/javase/10/docs/api/java/lang/invoke/MethodHandle.html)

> a signature polymorphic method is one which can operate with any of a wide range of call signatures and return types.
> 
> In source code, a call to a signature polymorphic method will compile, regardless of the requested symbolic type descriptor. 
> 
> As usual, the Java compiler emits an invokevirtual instruction with the given symbolic type descriptor against the named method. 
> The unusual part is that the symbolic type descriptor is derived from the actual argument and return types, not from the method declaration.

签名多态性的方法可以操作各种各样的调用签名(方法参数)和返回类型




### 参考

- 深入理解java虚拟机 8.3.3


### MethodType

方法句柄的类型（MethodType）是由所指向方法的参数类型以及返回类型组成的。
它是用来确认方法句柄是否适配的唯一关键。

## 一些可能用来分析的 jvm options

### ShowHiddenFrames

`-XX:+UnlockDiagnosticVMOptions -XX:+ShowHiddenFrames`

打印被 Java 虚拟机隐藏了的栈信息

### 导出methodHandle生成的字节码文件

`-Djava.lang.invoke.MethodHandle.DUMP_CLASS_FILES=true`

### 关闭逃逸分析

`-XX:-DoEscapeAnalysis`

### gbk 不可映射字符

`javac -encoding UTF-8  MethodHandleTest.java`

## 参考

- 极客时间 jvm 专栏
    - https://time.geekbang.org/column/article/12564
- jvm spec CONSTANT_Dynamic_info CONSTANT_InvokeDynamic_info
    - https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.10
- jvm spec invokedynamic 
    - https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.10.1.9.invokedynamic
- 什么是 invokedynamic , 如何使用它?
    - https://stackoverflow.com/questions/6638735/whats-invokedynamic-and-how-do-i-use-it
- what-is-call-site-in-java-when-calling-method
    - https://stackoverflow.com/questions/49414207/what-is-call-site-in-java-when-calling-method
- https://www.javaworld.com/article/2860079/invokedynamic-101.html
- Invokedynamic 和 MethodHandle的缘由
    - https://zhuanlan.zhihu.com/p/26389041
- JSR292: InvokeDynamic和MethodHandle的优化
    - https://zhuanlan.zhihu.com/p/30936412

