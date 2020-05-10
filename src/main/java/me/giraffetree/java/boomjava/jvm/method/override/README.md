# 重写

## 概述

## 问题

### 为什么禁止接口为java.lang.Object中的方法定义默认方法

keep it simple

保持简单, 足够解释一切了

举个例子:

```java
public interface IToString {
    @Override
    default String toString() {
        return "hello";
    }
}
```

参考: 

https://stackoverflow.com/questions/24016962/java8-why-is-it-forbidden-to-define-a-default-method-for-a-method-from-java-lan


