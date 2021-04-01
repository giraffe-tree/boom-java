# jmeter

## 基本使用步骤

- 不要使用GUI运行压力测试，GUI仅用于压力测试样例的创建和调试；执行压力测试请不要使用GUI。使用下面的命令来执行测试：
- `jmeter -n -t [jmx file] -l [results file] -e -o [Path to web report folder]`
- GUI 使用步骤
    1.创建线程组
    2. 配置文件
        - http 请求默认值 协议地址端口
        - http 信息头管理器 
    3. 采样器 - 构造http请求
        - 采样器 - http 请求
    4. 添加断言
        - 响应断言
    5. 添加查看结果树
    6. 添加汇总报告

## 测试

### websocket 测试

todo:



