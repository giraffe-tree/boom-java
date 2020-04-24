# 读写锁


## 死锁问题

如何分析读写锁中出现的死锁问题

### jstack 

```
$ jstack  77012
2020-04-24 13:53:24
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.151-b12 mixed mode):
"Service Thread" #12 daemon prio=9 os_prio=0 tid=0x000000001dc80000 nid=0x10aac runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"C1 CompilerThread2" #11 daemon prio=9 os_prio=2 tid=0x000000001dbf5800 nid=0x135c8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"C2 CompilerThread1" #10 daemon prio=9 os_prio=2 tid=0x000000001dbef800 nid=0xed64 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"C2 CompilerThread0" #9 daemon prio=9 os_prio=2 tid=0x000000001dbee000 nid=0x12b64 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"JDWP Command Reader" #8 daemon prio=10 os_prio=0 tid=0x000000001c6ca800 nid=0x13594 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"JDWP Event Helper Thread" #7 daemon prio=10 os_prio=0 tid=0x000000001c6c7000 nid=0x135bc runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"JDWP Transport Listener: dt_socket" #6 daemon prio=10 os_prio=0 tid=0x000000001c6b9800 nid=0x13180 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"Attach Listener" #5 daemon prio=5 os_prio=2 tid=0x000000001da32800 nid=0x13628 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"Signal Dispatcher" #4 daemon prio=9 os_prio=2 tid=0x000000001c6a2000 nid=0x13230 runnable [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE
"Finalizer" #3 daemon prio=8 os_prio=1 tid=0x000000001c678800 nid=0x12a20 in Object.wait() [0x000000001d9ef000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076b588ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
        - locked <0x000000076b588ec8> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:164)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:209)
"Reference Handler" #2 daemon prio=10 os_prio=2 tid=0x0000000002ff4000 nid=0x10f28 in Object.wait() [0x000000001d8ef000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x000000076b586b68> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference.tryHandlePending(Reference.java:191)
        - locked <0x000000076b586b68> (a java.lang.ref.Reference$Lock)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:153)
"main" #1 prio=5 os_prio=0 tid=0x0000000002f03800 nid=0x122e4 waiting on condition [0x000000000298e000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x000000076ba09fc0> (a java.util.concurrent.locks.ReentrantReadWriteLock$NonfairSync)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
        at java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock.lock(ReentrantReadWriteLock.java:943)
        at me.giraffetree.java.boomjava.concurrent.utils.synchronize.readwritelock.ReadWriteLockTest.testDegrade(ReadWriteLockTest.java:50)
        at me.giraffetree.java.boomjava.concurrent.utils.synchronize.readwritelock.ReadWriteLockTest.main(ReadWriteLockTest.java:34)
"VM Thread" os_prio=2 tid=0x000000001c657000 nid=0xee2c runnable
"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x0000000002f19800 nid=0x12784 runnable
"GC task thread#1 (ParallelGC)" os_prio=0 tid=0x0000000002f1b000 nid=0x13478 runnable
"GC task thread#2 (ParallelGC)" os_prio=0 tid=0x0000000002f1c800 nid=0x12684 runnable
"GC task thread#3 (ParallelGC)" os_prio=0 tid=0x0000000002f1e000 nid=0x13324 runnable
"VM Periodic Task Thread" os_prio=2 tid=0x000000001dd03800 nid=0x1340c waiting on condition
JNI global references: 1798
```
