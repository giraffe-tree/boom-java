package me.giraffetree.java.boomjava.concurrent.problem.hashcode;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

/**
 * @author GiraffeTree
 * @date 2020/6/1
 */
public class ObjectHeaderUtils {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    /**
     * only in 64bit vm
     */
    static byte[] getObjectMarkWord(Object object) {
        // 获取8个字节
        return getObjectHeader(object, 8);
    }

    static byte[] getObjectHeader(Object obj) {
        ClassLayout classLayout = ClassLayout.parseInstance(obj);
        int size = classLayout.headerSize();
        return getObjectHeader(obj, size);
    }

    /**
     * UseCompressedOops
     * mark word 8 byte + class pointer 4 byte
     */
    static byte[] getObjectHeaderDefault(Object obj) {
        return getObjectHeader(obj, 12);
    }

    static byte[] getObjectHeader(Object obj, int len) {
        VirtualMachine vm = VM.current();
        byte[] header = new byte[len];
        for (int i = 0; i < len; i++) {
            header[i] = vm.getByte(obj, i);
        }
        return header;
    }

    static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, false, false);
    }

    static String bytesToHex(byte[] bytes, boolean withSpace, boolean reverseOrder) {
        int max = (bytes.length << 1) + (withSpace ? bytes.length - 1 : 0);
        char[] hexChars = new char[max];
        int mult = withSpace ? 3 : 2;
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[reverseOrder ? bytes.length - j - 1 : j] & 0xFF;
            hexChars[j * mult] = HEX_ARRAY[v >>> 4];
            hexChars[j * mult + 1] = HEX_ARRAY[v & 0x0F];
            if (withSpace && j * mult + 2 < max) {
                hexChars[j * 3 + 2] = ' ';
            }
        }
        return new String(hexChars);
    }
}
