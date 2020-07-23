package me.giraffetree.java.boomjava.java.nio.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 主要用来接收请求, 并分配给 processor
 *
 * @author GiraffeTree
 * @date 2020/7/22
 */
@Slf4j
public class Acceptor extends AbstractServerThread {

    private final static int PROCESSOR_COUNT = 4;

    private List<Processor> processors = new ArrayList<>(PROCESSOR_COUNT);
    private final AtomicBoolean processorStarted = new AtomicBoolean(false);

    private final Selector selector = Selector.open();
    private ServerSocketChannel serverChannel;

    private final String host;
    private final int port;
    private int sendBufferSize;
    private int recvBufferSize;

    public Acceptor(int port, int sendBufferSize, int recvBufferSize) throws IOException {
        this(null, port, sendBufferSize, recvBufferSize);
    }

    public Acceptor(@Nullable String host, int port, int sendBufferSize, int recvBufferSize) throws IOException {
        this.host = host;
        this.port = port;
        this.sendBufferSize = sendBufferSize;
        this.recvBufferSize = recvBufferSize;
        this.serverChannel = openServerSocket();
    }

    public void startProcessors() {
        if (!processorStarted.getAndSet(true)) {
            for (int i = 0; i < PROCESSOR_COUNT; i++) {
                Processor processor = new Processor();
                processors.add(processor);
                Thread thread = new Thread(processor, "tcp-server-" + i);
                thread.setDaemon(false);
                thread.start();
            }
        }
    }

    @Override
    public void run() {
        // 注册感兴趣的时间, ACCEPT
        SelectionKey selectionKey;
        try {
            selectionKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            throw new TcpServerException("channel close", e);
        }
        startupComplete();
        int currentProcessorIndex = 0;
        while (isRunning()) {
            try {
                int events = selector.select(500);
                if (events > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext() && isRunning()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            // 对于非阻塞 io 而言, accept 方法会立即返回 null?
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            // todo: 什么含义
                            socketChannel.socket().setTcpNoDelay(true);
                            // 建立长连接
                            socketChannel.socket().setKeepAlive(true);
                            if (sendBufferSize != -1) {
                                socketChannel.socket().setSendBufferSize(sendBufferSize);
                            }
                            // 分配给 processor, 由于 processor 的数量固定, 这里不做同步
                            int retriesLeft = processors.size();
                            Processor processor;
                            do {
                                retriesLeft -= 1;
                                processor = processors.get(currentProcessorIndex % processors.size());
                                currentProcessorIndex++;
                                // 当所有 processor 连接队列都满时, 进行阻塞排队
                            } while (processor.accept(socketChannel, retriesLeft == 0));
                        }
                    }
                }
            } catch (IOException e) {
                throw new TcpServerException(e);
            }

        }


    }

    private ServerSocketChannel openServerSocket() throws IOException {
        InetSocketAddress address;
        if (host == null || StringUtils.trim(host).isEmpty()) {
            address = new InetSocketAddress(port);
        } else {
            address = new InetSocketAddress(host, port);
        }
        ServerSocketChannel channel = ServerSocketChannel.open();
        // 设置为非阻塞 IO
        channel.configureBlocking(false);
        if (recvBufferSize != -1) {
            channel.socket().setReceiveBufferSize(recvBufferSize);
        }
        try {
            channel.socket().bind(address);
        } catch (SocketException e) {
            log.error("the server could not bind port:{}", port);
            throw e;
        }
        return channel;
    }

    @Override
    void wakeup() {
        selector.wakeup();
    }
}
