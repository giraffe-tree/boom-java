# jvm配置参数

## 列表

- `-XX:+TraceClassLoading`
    - 打印 class 加载的信息, 等同于 `-verbose:class`
- `-XX:+PrintCompilation`
    - 打印即时编译相关的信息
- `-XX:-TieredCompilation`
    - 分层编译
        - level 0 - interpreter
        - level 1 - C1 with full optimization (no profiling)
        - level 2 - C1 with invocation and backedge counters
        - level 3 - C1 with full profiling (level 2 + MDO)
        - level 4 - C2
    - `-XX:-TieredCompilation` 禁用中间编译层（1、2、3），以便在最大优化级别（C2）处解释或编译方法。
    - https://stackoverflow.com/questions/38721235/what-exactly-does-xx-tieredcompilation-do
- `-XX:TieredStopAtLevel=1`
    - To disable C2 compiler and to leave only C1 with no extra overhead, set `-XX:TieredStopAtLevel=1`
- `-Xint` / `-Xcomp` 仅通过解释执行/JIT 编译后执行
    - `-Xint` 仅通过解释执行
        - 等价于 `-XX:+UseInterpreter -XX:-UseCompiler -XX:-UseLoopCounter -XX:-AlwaysCompileLoopMethods -XX:-UseOnStackReplacement`
    - `-Xcomp` 仅通过JIT编译后执行
        - 等价于 `-XX:-UseInterpreter -XX:+UseCompiler -XX:+UseLoopCounter -XX:-BackgroundCompilation -XX:-ClipInlining`
- `-XX:SurvivorRatio=1`
    - Ratio of eden/survivor space size
- `-XX:+MaxTenuringThreshold=15`
    - Maximum value for tenuring threshold
    - 如果一个对象被复制的次数为 15（对应虚拟机参数 -XX:+MaxTenuringThreshold），那么该对象将被晋升（promote）至老年代
- `-XX:TargetSurvivorRatio=50`
    - Desired percentage of survivor space used after scavenge
    - 如果单个 Survivor 区已经被占用了 50%（对应虚拟机参数 -XX:TargetSurvivorRatio），那么较高复制次数的对象也会被晋升至老年代。
- `-XX:-DoEscapeAnalysis`
    - 关闭默认开启的逃逸分析

### graal

- `-Dgraal.MethodFilter='CompilationTest.hash'`
    - 格式参考 http://lafo.ssw.uni-linz.ac.at/javadoc/graalvm/all/com/oracle/graal/debug/MethodFilter.html
    
