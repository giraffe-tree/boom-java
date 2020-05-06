# 并发

## 概述

并发部分的目录如下:

1. 并发时遇到的问题
    - [dcl 双重检查锁](./problem/dcl)
    - [dead lock 死锁](./problem/deadlock)
    - [live lock 活锁](./problem/livelock)
    - [false sharing 虚共享/伪共享](./problem/falseShare)
    - [重排序](./problem/reordering)
    - [线程间通讯](./problem/thread_communication)
    - [volatile](./problem/volatile_word)

2. synchronized 关键字的实现探究
    - [偏向锁 biased lock](./sync_word/biased_lock)

3. 线程
    - [线程间通讯 - 管道](./thread/communication)
    - [daemon 线程探究](./thread/daemon)
    - [thread 中的 deprecated 方法, 为什么不使用](./thread/deprecated)
    - [thread interrupt 中断](./thread/interrupt)
    - [thread join](./thread/join)
    - [todo: 用于探究 thread.join 的实现](./thread/join)
    - [java 打印当前线程信息](./thread/multi_thread)
    - [thread state 变化](./thread/state)

4. java 并发工具类
    - 并发容器
        - list
            - [CopyOnWriteList](./utils/container/list/copyonwrite)
    - 分工
        - [thread pool 线程池](./utils/dispatch/threadpool)
        - [fork join](./utils/dispatch/forkjoin)
    - 互斥
        - unsafe cas
        - synchronized
        - thread local
        - copy on write
    - 协作
        - [AQS](./utils/synchronize_mutex/aqs)
        - [Atomic 原子类](./utils/synchronize_mutex/atomic)
        - [CountDownLatch](./utils/synchronize_mutex/countdownlatch)
        - [cyclicBarrier](./utils/synchronize_mutex/cyclicbarrier)
        - [exchanger](./utils/synchronize_mutex/exchanger)
        - [Condition](./utils/synchronize_mutex/lock_condition)
        - [ReadWriteLock](./utils/synchronize_mutex/readwritelock)
        - [Semaphore](./utils/synchronize_mutex/semaphore)
        - [StampedLock](./utils/synchronize_mutex/stampedlock)
        




