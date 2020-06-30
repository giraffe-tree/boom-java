# 同步块中的对象头

## 探究的问题

1. jvm默认延迟4秒开启偏向锁之后, 对象头的变化
2. 在使用偏向锁的同步块中, 对象头的变化
3. 

## 问题探究

### 1. jvm默认延迟4秒开启偏向锁之后, 对象头的变化

测试代码如下: 

```
    /**
     * 使用 java 8 默认的配置运行
     * 不进行任何的 jvm 参数配置
     */
    private static void biasedLockDelayOnTest() {
        // 1. 获取 jvm 启动时间
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        long startTime = bean.getStartTime();
        // 2. 循环获取 header 信息
        long loop = 10;
        while (loop-- > 0) {
            long current = System.currentTimeMillis();
            Object x = new Object();
            JavaUtilsJolTest.print(x);
            System.out.println(String.format("lost %dms\n", current - startTime));
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                System.exit(1);
            }
        }
    }
```

涉及的原理:
 
1. jvm 在默认情况下会延迟4秒开启偏向锁[2]
2. 大小端问题, 请参考[1]
    - 计算机电路先处理低位字节，效率比较高，因为计算都是从低位开始的。所以，计算机的内部处理都是小端字节序。

部分输出如下, 可以看到在 4 秒前, 对象头的最后3位为 `001` , 而在4秒后, 对象头的最后3位位 `101`, 表示偏向锁已经开启

```

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
                                    -> 低位字节在前，高位字节在后, 实际上为 00000000 00000000 00000000 00000001
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
                                    -> 低位字节在前，高位字节在后, 实际上为 11111000 00000000 00000001 11100101
                                    -> Long.valueOf("f80001e5", 16).intValue()= -134217243  
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

lost 3692ms

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 00 00 00 (00000101 00000000 00000000 00000000) (5)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

lost 4193ms
```

### 2. 在使用偏向锁的同步块中, 对象头的变化




### 3. 


## 参考

1. [大小端问题 理解字节序-阮一峰](https://www.ruanyifeng.com/blog/2016/11/byte-order.html)
    - 计算机电路先处理低位字节，效率比较高，因为计算都是从低位开始的。所以，计算机的内部处理都是小端字节序。
2. [Does Java ever rebias an individual lock](https://stackoverflow.com/questions/46312817/does-java-ever-rebias-an-individual-lock)
    - `-XX:BiasedLockingStartupDelay=0 - by default there is a delay 4s`
3. 

    
