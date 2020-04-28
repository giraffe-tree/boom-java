# False Sharing

这里的测试了两个 volatile 的写操作由于 false sharing 带来的性能影响
分成了以下3种情况

1. 对照组, 仅仅两个 `volatile` 变量
2. Contented组, 使用 `@Contented` 避免了 false sharing
3. 手动填充组, 使用手动的 padding 填充, 来避免 false sharing

## 测试结果

下面我将同一段java代码, 在不同的主机上运行

结果可以自己看, 显而易见

### 本地 i3 8100

```
normal volatile average:169ms [112, 43, 223, 214, 117, 258, 209, 226, 72, 224]
contended volatile average:61ms [14, 13, 72, 73, 73, 75, 73, 73, 71, 74]
padding volatile average:65ms [17, 14, 78, 73, 73, 83, 86, 78, 76, 72]
```

### 本地 linux Intel(R) Xeon(R) CPU E5-2403 v2 @ 1.80GHz

```
normal volatile average:449ms [328, 172, 638, 558, 447, 462, 494, 508, 476, 416]
contended volatile average:138ms [35, 25, 177, 187, 186, 186, 187, 156, 124, 124]
padding volatile average:121ms [31, 29, 123, 124, 124, 124, 124, 181, 187, 171]
```

### 阿里云 c5 主机 2核 Intel(R) Xeon(R) Platinum 8269CY CPU @ 2.50GHz

```
normal volatile average:133ms [112, 34, 148, 149, 148, 149, 149, 148, 149, 148]
contended volatile average:129ms [40, 93, 93, 148, 148, 149, 176, 149, 148, 148]
padding volatile average:127ms [58, 29, 148, 148, 149, 148, 149, 148, 148, 149]
```

### Ryzen 7 1700 3Ghz cache-line 64B

```
normal volatile average:187ms [118, 29, 227, 246, 260, 231, 242, 214, 66, 243]
contended volatile average:60ms [13, 64, 64, 65, 67, 66, 65, 64, 66, 67]
padding volatile average:55ms [16, 11, 65, 68, 67, 67, 66, 64, 66, 65]
```


### 总结

- 公司电脑(cpu i3 8100) 上测试出来, 对照组耗时最长, 大约为 contended/手动填充组的 3 倍
- 在自己搭建的物理 centos7 主机上的耗时情况和预测差不多, 对照组的耗时比 Contented组/手动填充组 长, 大约 3倍
- 在阿里云 c5 主机 centOS 实例上, 三个组平均耗时都差不多
- 另外还有一台电脑, 用的是 amd 锐龙 1700, 对照组的耗时明显比 contended/手动填充组快

## 问题

### 1. 为什么测试出来, 每个组的前两次的耗时都比较短, 而后面的耗时都比较长

猜想原因: cpu 上长时间的缓存行竞争会导致 缓存锁 升级, 但具体升级的机制不明了

关于这个问题的猜想原因的来源, 是因为我在测试 FalseSharePaddingLenTest 时,
将测试小对象 -> 大对象 的顺序, 变成了 大对象 -> 小对象, 在开始测试的时候减少了竞争, 从而得到了预测的结果,
从这里想到, cpu 的一致性缓存协议会不会也和 synchronized 实现类似, 采取了升级锁的机制

关于这个问题, 也欢迎大家发表看法
 
### 2. 为什么 在阿里云 c5 主机 centOS 实例上, 三个组平均耗时都差不多

原因:

阿里云 c5 主机处理器为, 2.5 GHz主频的Intel ® Xeon ® Platinum 8163（Skylake）或者8269CY（Cascade Lake）

```
cat /proc/cpuinfo
processor	: 0
vendor_id	: GenuineIntel
cpu family	: 6
model		: 85
model name	: Intel(R) Xeon(R) Platinum 8269CY CPU @ 2.50GHz
stepping	: 7
microcode	: 0x1
cpu MHz		: 2500.000
cache size	: 36608 KB
physical id	: 0
siblings	: 2
core id		: 0
cpu cores	: 1
apicid		: 0
initial apicid	: 0
fpu		: yes
fpu_exception	: yes
cpuid level	: 22
wp		: yes
flags		: fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush mmx fxsr sse sse2 ss ht syscall nx pdpe1gb rdtscp lm constant_tsc rep_good nopl eagerfpu pni pclmulqdq monitor ssse3 fma cx16 pcid sse4_1 sse4_2 x2apic movbe popcnt aes xsave avx f16c rdrand hypervisor lahf_lm abm 3dnowprefetch invpcid_single fsgsbase tsc_adjust bmi1 hle avx2 smep bmi2 erms invpcid rtm mpx avx512f avx512dq rdseed adx smap avx512cd avx512bw avx512vl xsaveopt xsavec xgetbv1 arat avx512_vnni
bogomips	: 5000.00
clflush size	: 64
cache_alignment	: 64
address sizes	: 46 bits physical, 48 bits virtual
power management:
```

我们从上面看到 cpu 型号是 Intel(R) Xeon(R) Platinum 8269CY CPU @ 2.50GHz, 缓存行大小为 64 字节

> 而在Pentium及Pentium之前的处理器中，带有lock前缀的指令在执行期间会锁住总线，使得其他处理器暂时无法通过总线访问内存。
很显然，这会带来昂贵的开销。
> 
> 从Pentium 4，Intel Xeon及P6处理器开始，intel在原有总线锁的基础上做了一个很有意义的优化：
如果要访问的内存区域（area of memory）在lock前缀指令执行期间已经在处理器内部的缓存中被锁定
（即包含该内存区域的缓存行当前处于独占或以修改状态），并且该内存区域被完全包含在单个缓存行（cache line）中，
那么处理器将直接执行该指令。由于在指令执行期间该缓存行会一直被锁定，其它处理器无法读/写该指令要访问的内存区域，
因此能保证指令执行的原子性。这个操作过程叫做 缓存锁定（cache locking）.
> 
> 缓存锁定将大大降低lock前缀指令的执行开销，但是当多处理器之间的竞争程度很高或者指令访问的内存地址未对齐时，仍然会 锁住总线。

简单来讲, 对 volatile 的写, 会导致 cpu 锁定内存总线, 使得其他cpu不能对内存进行操作;

阿里云 c5 主机使用的 cpu 在对 lock 前缀的命令进行处理时, 会进行内存总线的锁定, 

而我公司的电脑 i3 8100, 仅仅对缓存行进行锁定, 锁的粒度更细, 从而性能更高


参考:

1. https://help.aliyun.com/document_detail/108491.html
2. https://cloud.tencent.com/developer/article/1189884
3. https://en.wikipedia.org/wiki/Cascade_Lake_(microarchitecture)

## 其他

### rpm 安装

```
rpm -ivh xxx.rpm
```

### linux cache line, l1/l2 cache size

```
getconf -a | grep -i cache
```