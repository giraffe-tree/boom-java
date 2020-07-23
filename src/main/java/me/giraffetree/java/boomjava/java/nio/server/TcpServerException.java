package me.giraffetree.java.boomjava.java.nio.server;

/**
 * tcp 服务器异常
 *
 * @author GiraffeTree
 * @date 2020/7/22
 */
public class TcpServerException extends ServerException {

    public TcpServerException(String message) {
        super(message);
    }

    public TcpServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TcpServerException(Throwable cause) {
        super(cause);
    }
}
