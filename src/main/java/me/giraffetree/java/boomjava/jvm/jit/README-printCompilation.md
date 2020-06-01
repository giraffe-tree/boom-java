# PrintCompilation 介绍

下面我截取了 [BranchProfileTest](./profile/BranchProfileTest.java)
经过 `-XX:+PrintCompilation` 的输出结果

```
165   45       3       me.giraffetree.java.boomjava.jvm.jit.profile.BranchProfileTest::foo (30 bytes)
165   46       4       me.giraffetree.java.boomjava.jvm.jit.profile.BranchProfileTest::foo (30 bytes)
165   45       3       me.giraffetree.java.boomjava.jvm.jit.profile.BranchProfileTest::foo (30 bytes)   made not entrant
166   47 %     3       me.giraffetree.java.boomjava.jvm.jit.profile.BranchProfileTest::main @ 3 (20 bytes)
166   48       3       me.giraffetree.java.boomjava.jvm.jit.profile.BranchProfileTest::main (20 bytes)
166   49 %     4       me.giraffetree.java.boomjava.jvm.jit.profile.BranchProfileTest::main @ 3 (20 bytes)
167   47 %     3       me.giraffetree.java.boomjava.jvm.jit.profile.BranchProfileTest::main @ -2 (20 bytes)   made not entrant
167   49 %     4       me.giraffetree.java.boomjava.jvm.jit.profile.BranchProfileTest::main @ -2 (20 bytes)   made not entrant
```

- 包括 %（是否 OSR 编译）
- s（是否 synchronized 方法）
- ！（是否包含异常处理器），
- b（是否阻塞了应用线程，可了解一下参数 -Xbatch），
- n（是否为 native 方法）。

- 再接下来则是编译层次，以及方法名。
- 如果是 OSR 编译，那么方法名后面还会跟着 @以及循环所在的字节码。
- 发生去优化时，你将看到之前出现过的编译，不过被标记了“made not entrant"。它表示该方法不能再被进入。
- 当 Java 虚拟机检测到所有的线程都退出该编译后的“made not entrant”时，会将该方法标记为“made zombie”，此时可以回收这块代码所占据的空间了。

## 参考

- 极客时间 即使编译上
    - https://time.geekbang.org/column/article/14061
