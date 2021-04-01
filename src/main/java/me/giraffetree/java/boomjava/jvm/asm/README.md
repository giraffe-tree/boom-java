# ASMifier 的使用

## 使用 ASMifier 通过 原始字节码 生成 可以生成该字节码的 java code

### 原始代码

```java
package me.giraffetree.java.boomjava.jvm.asm;

/**
 * @author GiraffeTree
 * @date 2019/11/6
 */
public class Source {

    private int a = 1;

    private int getA() {
        return a;
    }

    public static void main(String[] args) {
        String s = "hello world";
    }

}

```

### 编译

asm 下载地址 https://mvnrepository.com/artifact/org.ow2.asm/asm-all

```
java -cp /PATH/TO/asm-all-6.0_BETA.jar org.objectweb.asm.util.ASMifier Source.class
```

也可以使用 idea 配置, 实际执行的命令和上面一样

在 build 项目之后, 进行 run config 配置

![](https://open-chen.oss-cn-hangzhou.aliyuncs.com/open/blog/2019/11/asm/601db49763441b7b0d6706328f54f0a.png)


之后我们会发现该工具会打印出 java code , 直接使用就可以了

```
package me.giraffetree.java.boomjava.jvm.asm;

import java.util.*;

import jdk.internal.org.objectweb.asm.*;

public class SourceDump implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, "me/giraffetree/java/boomjava/jvm/asm/Source", null, "java/lang/Object", null);

        {
            fv = cw.visitField(ACC_PRIVATE, "a", "I", null, null);
            fv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitInsn(ICONST_1);
            mv.visitFieldInsn(PUTFIELD, "me/giraffetree/java/boomjava/jvm/asm/Source", "a", "I");
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PRIVATE, "getA", "()I", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "me/giraffetree/java/boomjava/jvm/asm/Source", "a", "I");
            mv.visitInsn(IRETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitLdcInsn("hello world");
            mv.visitVarInsn(ASTORE, 1);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 2);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
```


