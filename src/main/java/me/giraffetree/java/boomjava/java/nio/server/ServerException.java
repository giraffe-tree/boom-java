package me.giraffetree.java.boomjava.java.nio.server;

/**
 * @author GiraffeTree
 * @date 2020/7/22
 */
public abstract class ServerException extends RuntimeException {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }
}
