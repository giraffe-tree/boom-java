# 对象的大小

在 64 位系统上, jvm上需要按照 8 个字节对齐, 
每个对象都有 12 个字节的 header, 所以最小的对象为 16 个字节

```
// jdk.nashorn.internal.ir.debug.ObjectSizeCalculator

// out: 16
long objectSize = ObjectSizeCalculator.getObjectSize(new Object());
// out: 16
long intWrapperSize = ObjectSizeCalculator.getObjectSize(1);
// out: 24
long longWrapperSize = ObjectSizeCalculator.getObjectSize(1L);
```
