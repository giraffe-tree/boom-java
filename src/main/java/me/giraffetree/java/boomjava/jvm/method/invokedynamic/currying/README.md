# 柯里化 currying

## 概念

柯里化（英语：Currying），又译为卡瑞化或加里化，是把接受多个参数的函数变换成接受一个单一参数（最初函数的第一个参数）的函数，
并且返回接受余下的参数而且返回结果的新函数的技术。[1]


## 例子

### javascript 中的例子 [2]

```javascript
    // 普通方式
    var add1 = function(a, b, c){
        return a + b + c;
    }
    // 柯里化
    var add2 = function(a) {
        return function(b) {
            return function(c) {
                return a + b + c;
            }
        }
    }
```

### java 例子

[CurryingTest.java](./CurryingTest.java)

```java
public class CurryingTest {

    static int sum(int a, int b, int c) {
        return a + b + c;
    }
    public static void main(String[] args) throws Throwable {

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle methodHandle = lookup.findStatic(CurryingTest.class, "sum"
                , MethodType.methodType(int.class, int.class, int.class, int.class));

        MethodHandle method1 = MethodHandles.insertArguments(methodHandle, 0, 1);
        System.out.println((int) method1.invokeExact(2, 3));
        MethodHandle method2 = MethodHandles.insertArguments(method1, 0, 2);
        System.out.println((int) method2.invokeExact(3));
        MethodHandle method3 = MethodHandles.insertArguments(method2, 0, 3);
        System.out.println((int) method3.invokeExact());
    }
}
```


## 参考

1. 柯里化 wiki
    - https://zh.wikipedia.org/wiki/%E6%9F%AF%E9%87%8C%E5%8C%96
2. javascript 中的例子
    - https://juejin.im/post/5cdab8896fb9a031f525e7fe

