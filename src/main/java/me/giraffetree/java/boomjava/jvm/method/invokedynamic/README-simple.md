# MethodHandle & invokedynamic

本文旨在简单介绍 MethodHandle 与 invokedynamic, 做为新手入门教程, 为之后的深入理解做准备

## 引子

写这篇的原因是我自己在了解 `MethodHandle` 的时候走了太多弯路, 理解上一直不到位, 想着后来者可以少走一些弯路, 充分理解 `MethodHandle`,  于是写下了这篇. 

我也是看了很多资料之后才开始动手写, 希望大家看完这篇文章能够了解清楚 MethodHandle . 

## MethodHandle 

### 基本概念

#### 强类型的/能够被执行的底层方法的引用[1]

> A method handle is a typed, directly executable reference to an underlying method
>
> 方法句柄是一个强类型的、能够被直接执行的底层方法的引用
>
> Method handles are dynamically and strongly typed according to their parameter and return types.
>
> 方法句柄根据参数和返回类型,  是动态强类型的。

方法句柄可以指向常规的静态方法或者实例方法，也可以指向构造器或者字段

- 关于动态类型, 强类型请参考 [6]

#### 不可变[1]

> Method handles are immutable and have no visible state. 
>
> 方法句柄是不可变的，且没有可见状态。

#### 动态类型语言中的 普通变量

看起来上面的概念很死板, 难以理解. 当时的我也是这样, 感觉上面的字讲的都对, 但是我大部分用的是 java 或者是其他静态语言, 我还是不知道 `MethodHandle` 和普通的方法有什么区别, 和反射又有什么区别.

那到底该怎么想象 `MethodHandle` 是什么东西呢 ? 是不是可以把MethodHandle 转为我们遇到过的事物呢? 

这里我将 `MethodHandle` 理解为  **动态类型语言中的 普通变量** [7] [8]

要理解这句话, **首先得了解 什么是 动态类型语言 / 强类型?** [6]

通俗的来说: 

动态类型/静态类型:

> 指变量与类型的绑定方法。
>
> 动态类型语言, 编译的时候不知道每一个变量的类型; 运行时进行类型检查
>
> 而静态类型语言, 编译的时候就知道每一个变量的类型; 编译时进行类型检查

强类型/弱类型: 

>指的是语言类型系统的类型检查的严格程度
>
>强类型: 偏向于不容忍隐式类型转换;
>
>弱类型：偏向于容忍隐式类型转换;

我们使用 python3.6 示例如下,  需要知道的是 python 为强类型动态类型语言

> [Python is dynamically typed and garbage-collected.](https://en.wikipedia.org/wiki/Python_(programming_language)) 
>
> Python is [strongly typed](https://en.wikipedia.org/wiki/Strongly_typed_programming_language), forbidding operations that are not well-defined (for example, adding a number to a string) rather than silently attempting to make sense of them.

```python
>>> a =1
>>> type(a)
<class 'int'>
>>> a = "s"
>>> type(a)
<class 'str'>

>>> def add(x,y):
...     return x+y
...
>>> add(1,2)
3
>>> add('hello','world')
'helloworld'
```

于是我们将 MethodHandle 类比成 python 中的 变量 `a`



#### MethodType 

- 方法句柄的类型  - 由参数类型以及返回类型组成

### 调用方法句柄

调用方法句柄，和原本对应的调用指令是一致的。也就是说，对于原本用 invokevirtual 调用的方法句柄，它也会采用动态绑定；而对于原本用 invkespecial 调用的方法句柄，它会采用静态绑定。



## 思考

### java 真的是静态类型语言么?

> Java不是完全静态类型的语言。每当将对象从类型转换为子类型时，JVM都会执行动态（运行时）类型检查，以检查该对象确实是该子类型的实例。
>
> 字节码示例: instanceof 即为动态类型检查

- https://stackoverflow.com/questions/11407385/how-can-we-have-a-dynamically-typed-language-over-jvm



## 参考

1. java 11 api 官方文档

	- https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/invoke/MethodHandle.html
2. jvm 11 规范官方文档

	- https://docs.oracle.com/javase/specs/jvms/se11/html/index.html
3. 极客时间深入拆解 java 虚拟机

	- https://time.geekbang.org/column/article/12564
4. 编程语言中的强类型和弱类型
   
   - https://en.wikipedia.org/wiki/Strong_and_weak_typing
5. 动态语言

   - 它是一类在运行时可以改变其结构的语言：例如新的函数、对象、甚至代码可以被引进，已有的函数可以被删除或是其他结构上的变化。

   - https://en.wikipedia.org/wiki/Dynamic_programming_language
6. 弱类型、强类型、动态类型、静态类型语言的区别是什么？

    - 动态类型: 编译的时候不知道每一个变量的类型，因为类型错误而不能做的事情是运行时错误。譬如说你不能对一个数字a写a[10]当数组用。
    - 变量可以改变类型 `a = 1; a="s";`
	- https://www.zhihu.com/question/19918532
7.  JDK 7的新功能：支持Java虚拟机中的动态类型语言

    - https://www.oracle.com/technical-resources/articles/javase/dyntypelang.html
8.  Dynamic languages support
    - https://www.javacodegeeks.com/2015/09/dynamic-languages-support.html
9. 各个语言中中的 invokedynamic
    - groovy
      - https://melix.github.io/blog/2013/01/31/using_groovy_to_play_with.html
    - 
10. invokedynamic 字节码解释
    - https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-6.html#jvms-6.5.invokedynamic







