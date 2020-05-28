# README.md

官方文档: 

- https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.20.2

## return 和 finally 是如何配合的

java 编译器在 try 中遇到  `return` 时, 会将返回的变量放入一个局部变量中, 
然后再去执行 finally 块中的代码, 当 finally 中也使用 `return` 时, 且没有其他的 finally , 则直接返回该值.

### test1 中 finally 中有 return

```java
public class FinallyTest {

    public static int test1(int a) {
        try {
            a += 10;
            return a;
        }finally {
            a++;
            return a;
        }
    }
}
```

### 字节码解析

我这里使用 asm bytecode viewer  查看字节码

```
  public static test1(I)I
    TRYCATCHBLOCK L0 L1 L2 null
   L0
    LINENUMBER 11 L0
    IINC 0 10           // 将局部变量表中的 (第0位的变量)a 加10
   L3
    LINENUMBER 12 L3
    ILOAD 0             // 将 (局部变量表中第0位的变量)a 加载到 stack 中
    ISTORE 1            // 将 stack 栈顶的值, 加载到 局部变量表第1位的变量中
   L1
    LINENUMBER 14 L1
    IINC 0 1            // 将 a 加 1
   L4
    LINENUMBER 15 L4
    ILOAD 0             // 将 a 加载到 stack 中
    IRETURN             // 返回
   L2
    LINENUMBER 14 L2
   FRAME SAME1 java/lang/Throwable  // 如果出现异常, 则进入下面的字节码进行执行
    ASTORE 2            // 将 Throwable 类型的对象引用放入 局部变量表 第2位变量中
    IINC 0 1            // 将 a +1
   L5
    LINENUMBER 15 L5
    ILOAD 0             // 加载 a 到栈上
    IRETURN             // 返回栈顶的元素
   L6
    LOCALVARIABLE a I L0 L6 0
    MAXSTACK = 1
    MAXLOCALS = 3
```

### try 中抛出了异常, finally 中 return 了

```
public static int test4(int a) {
    try {
        a += 10;
        throw new RuntimeException();
    } finally {
        a++;
        return a;
    }
}
```

#### 字节码解析

```
  // access flags 0x9
  public static test4(I)I
    TRYCATCHBLOCK L0 L1 L2 null
   L0
    LINENUMBER 35 L0
    IINC 0 10
   L3
    LINENUMBER 36 L3
    NEW java/lang/RuntimeException
    DUP
    INVOKESPECIAL java/lang/RuntimeException.<init> ()V
    ATHROW
   L2
    LINENUMBER 38 L2
   FRAME SAME1 java/lang/Throwable
    ASTORE 1            // 将 throwable 对象引用放入局部变量表 第2位
   L1           
    IINC 0 1            //  a+1
   L4           
    LINENUMBER 39 L4
    ILOAD 0             // 加载 a 到栈上
    IRETURN             // 返回 a
   L5
    LOCALVARIABLE a I L0 L5 0
    MAXSTACK = 2
    MAXLOCALS = 2

```