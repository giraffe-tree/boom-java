# 桥接

由于 java 中重写 和 jvm 中的重写定义不同, 
而使用桥接方法来实现java中的重写

注: 

> jvm 中的重写需要 方法名/参数/返回值 都一致, 
> 而 java中重写由于相同方法名/参数 对应的返回值唯一, 只需要 方法名/参数 一致

### 示例1: 重写返回类型不同

```java
public class BridgeTest {
    private abstract static class Fruit {
        abstract Number eat();
    }
    private static class Apple extends Fruit {
        @Override
        Integer eat() {
            System.out.println("apple eat...");
            return 1;
        }
        
    }
}
```

通过编译后查看 Apple 的字节码 `javap -v BridgeTest$Apple.class`

可以看到多了一个  `Number eat()` 的方法, 这个方法就是桥接方法


```
{
  java.lang.Integer eat();
    descriptor: ()Ljava/lang/Integer;
    flags:
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String apple eat...
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: iconst_1
         9: invokestatic  #5                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        12: areturn
      LineNumberTable:
        line 17: 0
        line 18: 8

  java.lang.Number eat();
    descriptor: ()Ljava/lang/Number;
    flags: ACC_BRIDGE, ACC_SYNTHETIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokevirtual #6                  // Method eat:()Ljava/lang/Integer;
         4: areturn
      LineNumberTable:
        line 13: 0
}
```

#### 关于 BridgeTest$1.class

编译器通过生成一些在源代码中不存在的synthetic方法和类的方式，
实现了对private级别的字段和类的访问，从而绕开了语言限制。

参考: https://www.cnblogs.com/bethunebtj/p/7761596.html

### 示例二: 由于泛型, 引起参数类型不同的重写

```java
public class GenericBridgeTest {
    private class Room<T> {
        public void add(T t) {
        }
    }

    private class ClassRoom extends Room<String> {

        @Override
        public void add(String s) {
            System.out.println("add:" + s);
        }
    }
}
```

编译后查看 `javap -v GenericBridgeTest$ClassRoom.class`
多了一个  `void add(Object);` 的方法, 这个方法就是桥接方法

```
  public void add(java.lang.String);
    descriptor: (Ljava/lang/String;)V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=2, args_size=2
         0: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: new           #4                  // class java/lang/StringBuilder
         6: dup
         7: invokespecial #5                  // Method java/lang/StringBuilder."<init>":()V
        10: ldc           #6                  // String add:
        12: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        15: aload_1
        16: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        19: invokevirtual #8                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        22: invokevirtual #9                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        25: return
      LineNumberTable:
        line 18: 0
        line 19: 25

  public void add(java.lang.Object);
    descriptor: (Ljava/lang/Object;)V
    flags: ACC_PUBLIC, ACC_BRIDGE, ACC_SYNTHETIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: aload_1
         2: checkcast     #10                 // class java/lang/String
         5: invokevirtual #11                 // Method add:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 14: 0

```


## 参考

https://time.geekbang.org/column/article/11539

