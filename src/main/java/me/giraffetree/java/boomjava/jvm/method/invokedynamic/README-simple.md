# MethodHandle & invokedynamic

本文旨在简单介绍 MethodHandle 与 invokedynamic, 做为新手入门教程, 为之后的深入理解做准备

主要参考 [java 11 api 官方文档](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/invoke/MethodHandle.html) , [jvm 11 规范官方文档](https://docs.oracle.com/javase/specs/jvms/se11/html/index.html) , [极客时间深入拆解 java 虚拟机](https://time.geekbang.org/column/article/12564)

## 引子

感觉前面讲的太官方了 =.= 

写这篇的原因是我自己在了解 MethodHandle 的时候走了太多弯路, 理解上一直不到位, 写这篇文章是想后来者少走一些弯路, 充分理解 MethodHandle

看了很多资料之后才开始动手写这篇, 希望大家看完这篇文章能够了解清楚

## MethodHandle 

### 基本概念

#### 强类型的/能够被执行的底层方法的引用[1]

> A method handle is a typed, directly executable reference to an underlying method
>
> 方法句柄是一个强类型的、能够被直接执行的底层方法的引用

方法句柄可以指向常规的静态方法或者实例方法，也可以指向构造器或者字段

#### 不可变[1]

> Method handles are immutable and have no visible state. 
>
> 方法句柄是不可变的，且没有可见状态。

#### 联想

看起来上面的概念很死板, 难以理解. 当时的我也是这样, 感觉上面的字讲的都对, 但是我大部分用的是 java 或者是其他静态语言, 我还是不知道 MethodHandle 和普通的方法有什么区别, 和反射又有什么区别.

那到底该怎么想象MethodHandle 是什么东西呢 ? 是不是可以把MethodHandle 转为我们遇到过的事物呢? 

我将 MethodHandle 理解为动态语言中的 普通变量



#### MethodType 

- 方法句柄的类型  - 由参数类型以及返回类型组成

### 调用方法句柄

调用方法句柄，和原本对应的调用指令是一致的。也就是说，对于原本用 invokevirtual 调用的方法句柄，它也会采用动态绑定；而对于原本用 invkespecial 调用的方法句柄，它会采用静态绑定。



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

  - 动态类型语言：是指在运行期间才去做数据类型检查的语言。在用动态语言编程时，不用给变量指定数据类型，该语言会在你第一次赋值给变量时，在内部将数据类型记录下来。
    - 编译的时候不知道每一个变量的类型，因为类型错误而不能做的事情是运行时错误。譬如说你不能对一个数字a写a[10]当数组用。

  - https://www.zhihu.com/question/19918532








