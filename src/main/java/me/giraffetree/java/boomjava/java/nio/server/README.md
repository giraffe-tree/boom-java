# README

本目录代码主要用于建立一个非阻塞IO的TCP服务器, 实现方法参考了 kafka 请求处理模块的设计, 使用单个 Acceptor 线程接受请求, 并转发给多个 processor 进程进行连接处理.

## NIO TCP SERVER




## 知识点

### keepalive

这时候TCP协议提出一个办法，当客户端端等待超过一定时间后自动给服务端发送一个空的报文，如果对方回复了这个报文证明连接还存活着，如果对方没有报文返回且进行了多次尝试都是一样，那么就认为连接已经丢失，客户端就没必要继续保持连接了。


1. KeepAlive默认情况下是关闭的，可以被上层应用开启和关闭
2. tcp_keepalive_time: KeepAlive的空闲时长，或者说每次正常发送心跳的周期，默认值为7200s（2小时）
3. tcp_keepalive_intvl: KeepAlive探测包的发送间隔，默认值为75s
4. tcp_keepalive_probes: 在tcp_keepalive_time之后，没有接收到对方确认，继续发送保活探测包次数，默认值为9（次）



