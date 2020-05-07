# Cache Line Ping Pong

## 定义

缓存行在多个CPU之间连续不断地快速传输

比如: 有两个CPU: `c1`,`c2` , 一个缓存行 `x1`

> c1 由于缓存行失效, 从主存查询了 x1, 经过一番操作后, 使的 c2 上的对应 x1 的缓存失效;
> 
> c2 也由于缓存行失效, 从主存查询了 x1, 经过一番操作后, 使的 c1 上的对应 x1 的缓存失效;

两个操作一直循环, 导致该缓存行在 多个CPU 之间不断快速传输

## 参考

- [cache-line-ping-pong and false-sharing](https://stackoverflow.com/questions/30684974/are-cache-line-ping-pong-and-false-sharing-the-same)


