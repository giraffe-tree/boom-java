package me.giraffetree.java.boomjava.java.nio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * 用来处理连接 connect 请求, 并将请求加入请求队列中
 * 发送 response 给客户端
 *
 * @author GiraffeTree
 * @date 2020/7/22
 */
@Slf4j
public class Processor extends AbstractServerThread {
    /**
     * 默认连接队列长度为 16
     */
    private final static int DEFAULT_CONNECTION_QUEUE_SIZE = 16;

    private final ArrayBlockingQueue<SocketChannel> newConnections = new ArrayBlockingQueue<>(DEFAULT_CONNECTION_QUEUE_SIZE);

    private final Selector selector;

    public Processor() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            throw new TcpServerException(e);
        }
    }

    /**
     * inflightResponses
     * responseQueue
     */
    @Override
    public void run() {
        startupComplete();
        while (isRunning()) {
            // 注册 OP_READ
            configureNewConnections();
            // 将 response 放入 inflightResponse 队列汇总

            // 执行 NIO poll, 获取 socket channel 上准备就绪的 IO 操作
            poll(200);
            // 将接收到的 request 放入 request 队列

            // 为临时Response队列中的Response执行回调逻辑
        }
    }


    private void configureNewConnections() {
        int connectionsProcessed = 0;
        while (connectionsProcessed < DEFAULT_CONNECTION_QUEUE_SIZE && !newConnections.isEmpty()) {
            SocketChannel channel = newConnections.poll();
            try {
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
                connectionsProcessed += 1;
            } catch (ClosedChannelException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean accept(SocketChannel channel, boolean mayBlock) {
        if (newConnections.offer(channel)) {
            return true;
        } else if (mayBlock) {
            try {
                newConnections.put(channel);
            } catch (InterruptedException e) {
                throw new TcpServerException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    public void poll(long timeout) {
        try {
            int keyNum = selector.select(timeout);
            if (keyNum > 0) {
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext() && isRunning()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 固定长度, 表示最大包
                    int bufSize = 1024;
                    ByteBuffer buffer = ByteBuffer.allocate(bufSize);
                    int read = channel.read(buffer);
                    // tcp 空报文  以此来维持长连接
                    // [255,241] 是什么? telnet 为了保持心跳发送的 IAC NOP：255 241
                    log.info("receive size:{} ", read);

                }
            }
        } catch (IOException e) {
            throw new TcpServerException(e);
        }
    }

    @Override
    void wakeup() {

    }
}
