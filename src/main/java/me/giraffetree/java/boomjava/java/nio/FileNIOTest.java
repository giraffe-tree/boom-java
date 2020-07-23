package me.giraffetree.java.boomjava.java.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author GiraffeTree
 * @date 2020/7/21
 */
@Slf4j
public class FileNIOTest {

    public static void main(String[] args) throws IOException {
        String fileName = "src\\main\\java\\me\\giraffetree\\java\\boomjava\\java\\nio\\helloworld.txt";
        readFile(fileName);
    }

    /**
     * 示例代码: http://tutorials.jenkov.com/java-nio/buffers.html
     *
     * @param fileName 文件名
     * @throws IOException IO 异常
     */
    private static void readFile(String fileName) throws IOException {
        RandomAccessFile aFile;
        try {
            aFile = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException e) {
            log.error("file not found - fileName:{}", fileName);
            return;
        }
        FileChannel inChannel = aFile.getChannel();

        //create buffer with capacity of 48 bytes
        ByteBuffer buf = ByteBuffer.allocate(48);
        //read into buffer.
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            // make buffer ready for read
            buf.flip();

            while (buf.hasRemaining()) {
                // read 1 byte at a time
                System.out.print((char) buf.get());
            }

            buf.clear(); //make buffer ready for writing
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }

}
