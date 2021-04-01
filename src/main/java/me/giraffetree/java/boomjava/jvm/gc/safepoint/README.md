# safepoint 与 循环回边

> 对于解释执行来说，字节码与字节码之间皆可作为安全点。Java 虚拟机采取的做法是，执行一条字节码便进行一次安全点检测。


> 对于JIT编译来说,  HotSpot 会在在生成代码的方法出口以及非计数循环 uncounted loop 的循环回边（back-edge）处插入安全点检测

## 使用 int 循环可能会导致 safepoint spin 时间过长

```
public static void foo() {
    System.out.println("start foo...");
    int sum = 0;
    for (int i = 0; i < Integer.MAX_VALUE; i++) {
        sum += Math.sqrt(i);
    }
}
```

执行后可以看到如下日志, 我们会发现有一个 spin 特别长, 这就是由于 counted loop 一直到达不了 safepoint 导致的;

```
         vmop                    [threads: total initially_running wait_to_block]    [time: spin block sync cleanup vmop] page_trap_count
0.137: ForceSafepoint                   [       7          0              0    ]      [     0     0     0     0     0    ]  0   
0.137: ChangeBreakpoints                [       8          1              0    ]      [     0     0     0     0     0    ]  0   
0.209: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0   
0.209: ChangeBreakpoints                [       8          0              0    ]      [     0     0     0     0     0    ]  0   
1.208: no vm operation                  [      21          1              1    ]      [ 19300     0 19300     0     0    ]  0   
20.509: EnableBiasedLocking              [      21          0              0    ]      [     0     0     0     0     0    ]  0   
20.511: no vm operation                  [      20          0              3    ]      [     0     0     0     0     0    ]  0   
```

- spin: 等待线程响应safepoint号召的时间
-  block: 暂停所有线程所用的时间
- sync: 等于 spin+block，这是从开始到进入安全点所耗的时间，可用于判断进入安全点耗时
-  cleanup: 清理所用时间
- vmop: 真正执行VM Operation的时间




