# README

本目录代码主要用于建立一个非阻塞IO的TCP服务器, 实现方法参考了 kafka 请求处理模块的设计, 使用单个 Acceptor 线程接受请求, 并转发给多个 processor 进程进行连接处理.

## NIO TCP SERVER




