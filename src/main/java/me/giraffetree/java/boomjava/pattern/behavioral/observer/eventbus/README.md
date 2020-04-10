# EVENT-BUS 事件总线

## 概述

event bus 是一种观察者模式的实现

比如 guava 中的 EventBus , 它利用注册表来实现 参数类型 -> 函数(@Subscribe)的映射, 
实现了数据的(同步阻塞/异步非阻塞)分发,

例如

```
void f1(long a);
void f2(long a, int b);
void f3(double c);

long a = 1L;
eventBus.post(a);  // 就可以自动分发到 f1 进行处理
int b = 2;
eventBus.post(b);  // 就可以自动分发到 f2 进行处理
double c = 3.0;
eventBus.post(c);  // 就可以自动分发到 f3 进行处理
```

更进一步的, 通过组合继承, 我们可以将一个消息分发到 多个函数

```
class Fruit{}
class Apple extends Fruit{}

void f4(Fruit fruit);
void f5(Apple apple);

Fruit fruit = new Fruit();
eventBus.post(fruit);  // 就可以自动分发到 f4 进行处理
Apple apple = new Apple();
eventBus.post(apple);  // 就可以自动分发到 f4, f5 进行处理
```


## 参考

- EventBus guava 实现
- 极客时间观察者模式/EventBus 讲解
    - https://time.geekbang.org/column/article/211239
