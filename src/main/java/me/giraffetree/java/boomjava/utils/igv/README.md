# Ideal Graph Visualizer

IR 可视化工具 , ideal 为 hotsport c2 编译器中 IR 的名字[1]

本文为最新版2018.7.4 的安装使用教程

## 安装

### 下载

github graal 项目中有最新的下载连接 [idealgraphvisualizer-543](https://github.com/oracle/graal/releases/tag/idealgraphvisualizer-543)

### 配置

下载好之后, 需要解压缩, 然后在 `etc` 文件夹中的 `idealgraphvisualizer.conf` 进行配置

```
# 注意不要放在跟 idealgraphvisualizer 相同的目录下
# 不然会提示一个 Your user directory cannot reside inside your NetBeans installation directory 错误
default_userdir="E:\2020\June\igv\user"
default_cachedir="E:\2020\June\igv\cache"

# 需要手动设置 jdkhome
# 不然会提示 Cannot find java 1.8 or higher
jdkhome="E:\environment\java"
```

![](./img/config.jpg)

### 运行

双击 `bin/idealgraphvisualizer64.exe` 即可运行

如果出现其他错误, 可以通过 `cmd`, 然后运行 `bin/idealgraphvisualizer64.exe` 
看下打印出的日志, 然后排错. 前面的配置也是我一步步排错, 摸索出的=.=



## 参考

1. https://time.geekbang.org/column/article/14270
2. https://github.com/oracle/graal/releases/tag/idealgraphvisualizer-543



