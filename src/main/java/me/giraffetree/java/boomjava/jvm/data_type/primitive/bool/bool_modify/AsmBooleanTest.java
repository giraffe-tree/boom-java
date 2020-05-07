package me.giraffetree.java.boomjava.jvm.data_type.primitive.bool.bool_modify;

import me.giraffetree.java.boomjava.jvm.data_type.primitive.bool.bool_heap.InHeapbooleanDump;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author GiraffeTree
 * @date 2019/11/6
 */
public class AsmBooleanTest {

    public static void main(String[] args) throws Exception {
        // 使用生成的 AsmBoolean.class 代替 target 目录下的 AsmBoolean.class
        // 再运行 AsmBoolean , 会发现输出变了, jvm 中的 flag 值不为 true
        Files.write(Paths.get("target/classes/me/giraffetree/java/boomjava/jvm/data_type/primitive/bool/bool_heap/InHeapboolean.class"), InHeapbooleanDump.dump());
    }


}
