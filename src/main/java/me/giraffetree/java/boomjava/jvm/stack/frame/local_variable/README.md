# 局部变量区

## 概述

## 问题


### 局部变量区中的 reference 对 gc 的影响

看到这个问题的原因的看到了这篇文章, 我决定自己也试下

> https://www.cnblogs.com/wuzhiwei549/p/9162673.html

代码:

见: [LocalVariableGCTest](./LocalVariableGCTest.java)

解释: 

1. 由于线程栈帧中的 局部变量引用 没有清除, 所以导致System.gc 无法回收
2. 对局部变量区的复用, 会导致新的局部变量覆盖之前的局部变量, 这会帮助 gc 完成

### long 在局部变量区中占用 2 个 slot

LocalVariableGCTest 中的 test03 / test04 对比, 侧面印证了 long 在局部变量区中占用 2 个 slot

