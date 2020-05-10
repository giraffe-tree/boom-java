# 重载

## 理论上讲只有 java 中存在方法重载, 而 jvm 中没有方法重载的概念

因为 jvm 通过 方法名, 参数, 返回值来唯一确定方法

而 java 通过 方法名, 参数来唯一确定方法

两者并不相同

## 如何把 java 编译器弄晕?

[源码](./OverloadTest.java)

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


## 调用阶段

> 具体到每一个方法调用，Java 编译器会根据所传入参数的声明类型（注意与实际类型区分）来选取重载方法。
> 选取的过程共分为三个阶段：
> 1. 在不考虑对基本类型自动装拆箱（auto-boxing，auto-unboxing），以及可变长参数的情况下选取重载方法；
> 2. 如果在第 1 个阶段中没有找到适配的方法，那么在允许自动装拆箱，但不允许可变长参数的情况下选取重载方法；
> 3. 如果在第 2 个阶段中没有找到适配的方法，那么在允许自动装拆箱以及可变长参数的情况下选取重载方法。



