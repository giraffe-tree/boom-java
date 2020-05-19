# jvm 配置参数

## 列表

- TieredCompilation
    - 分层编译
        - level 0 - interpreter
        - level 1 - C1 with full optimization (no profiling)
        - level 2 - C1 with invocation and backedge counters
        - level 3 - C1 with full profiling (level 2 + MDO)
        - level 4 - C2
    - `-XX:-TieredCompilation` 禁用中间编译层（1、2、3），以便在最大优化级别（C2）处解释或编译方法。
    - https://stackoverflow.com/questions/38721235/what-exactly-does-xx-tieredcompilation-do
- TieredStopAtLevel
    - To disable C2 compiler and to leave only C1 with no extra overhead, set `-XX:TieredStopAtLevel=1`
    
    

