package me.giraffetree.java.boomjava.jvm.lli.load;


import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 用于修改 singleton 的class 文件
 * 使其在链接时验证失败报错
 *
 * @author GiraffeTree
 * @date 2020/5/8
 */
public class SingletonClassModifier {

    public static void main(String[] args) throws Exception {
        String path =
                "target/classes/me/giraffetree/java/boomjava/jvm/lli/load/Singleton.class";
        Files.write(Paths.get(path),
                SingletonDump.dump());

    }
}
