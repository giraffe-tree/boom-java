package me.giraffetree.java.boomjava.java.nio.server;

import java.io.IOException;

/**
 * 非阻塞IO tcp 服务器
 *
 * @author GiraffeTree
 * @date 2020/7/22
 */
public class NIOSocketServer {

    public static void main(String[] args) throws IOException {
        startServer();

    }

    private static void startServer() throws IOException {
        Acceptor acceptor = new Acceptor(7777, 1024, 1024);
        acceptor.startProcessors();
        acceptor.run();
    }

}
