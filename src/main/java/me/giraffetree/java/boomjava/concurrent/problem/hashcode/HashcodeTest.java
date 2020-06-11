package me.giraffetree.java.boomjava.concurrent.problem.hashcode;

import java.nio.ByteBuffer;

import static me.giraffetree.java.boomjava.concurrent.problem.hashcode.ObjectHeaderUtils.bytesToHex;
import static me.giraffetree.java.boomjava.concurrent.problem.hashcode.ObjectHeaderUtils.getObjectMarkWord;

/**
 * 当使用 jdk11 时, 需要加上 vm option:
 * `-Djdk.attach.allowAttachSelf=true`
 * object header https://stackoverflow.com/questions/26357186/what-is-in-java-object-header
 * 64 bits:
 * --------
 * unused:25 hash:31 -->| unused:1   age:4    biased_lock:1 lock:2 (normal object)
 *
 * @author GiraffeTree
 * @date 2020/6/1
 */
public class HashcodeTest {


    public static void main(String[] args) {
        testMarkWordAfterCallHashcode();
    }

    /**
     * 测试 object header 中的 mark word 部分
     * 在 64bit vm 上为 8个字节
     * 需要注意大小端问题
     */
    private static void testMarkWordAfterCallHashcode() {
        Object obj = new Object();
        byte[] headerBytes = getObjectMarkWord(obj);
        System.out.println(String.format("%-10s%24s", "before:", bytesToHex(headerBytes, true, true)));
        // 目前测试来看, 未经覆盖的 Object.hashcode() 方法的结果与 System.identityHashcode(obj) 一致
        // 都占用了 markword 中的一部分
//        int hashCode = obj.hashCode();
        int hashCode = System.identityHashCode(obj);

        System.out.println(String.format("hashcode: %d", hashCode));
        byte[] hashcodeByteArray = ByteBuffer.allocate(4).putInt(hashCode).array();
        // 这里注意 hashcode 的字节不需要逆序
        System.out.println(String.format("%-10s%8s", "hashcode:", bytesToHex(hashcodeByteArray, true, false)));

        headerBytes = getObjectMarkWord(obj);
        System.out.println(String.format("%-10s%24s", "after:", bytesToHex(headerBytes, true, true)));

        System.out.println("------------------------------------------");
        Object obj2 = new Object();
        headerBytes = getObjectMarkWord(obj2);
        System.out.println(String.format("%-10s%24s", "before:", bytesToHex(headerBytes, true, true)));
        System.out.println(String.format("hashcode: %d", obj2.hashCode()));

        hashcodeByteArray = ByteBuffer.allocate(4).putInt(obj2.hashCode()).array();
        // 这里注意 hashcode 的字节不需要逆序
        System.out.println(String.format("%-10s%8s", "hashcode:", bytesToHex(hashcodeByteArray, true, false)));

        headerBytes = getObjectMarkWord(obj2);
        System.out.println(String.format("%-10s%24s", "after:", bytesToHex(headerBytes, true, true)));


    }


}
