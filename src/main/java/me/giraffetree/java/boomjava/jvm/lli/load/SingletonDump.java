package me.giraffetree.java.boomjava.jvm.lli.load;

import org.objectweb.asm.*;

public class SingletonDump implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "me/giraffetree/java/boomjava/jvm/lli/load/Singleton", null, "java/lang/Object", null);

        classWriter.visitSource("Singleton.java", null);

        classWriter.visitInnerClass("me/giraffetree/java/boomjava/jvm/lli/load/Singleton$1", null, null, ACC_STATIC | ACC_SYNTHETIC);

        //
        classWriter.visitInnerClass("me/giraffetree/java/boomjava/jvm/lli/load/Singleton$LazyHolder",
                "me/giraffetree/java/boomjava/jvm/lli/load/Singleton", "LazyHolder", ACC_PRIVATE | ACC_STATIC);

        {
            methodVisitor = classWriter.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(13, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(15, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Lme/giraffetree/java/boomjava/jvm/lli/load/Singleton;", null, label0, label2, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "getInstance", "(Z)Ljava/lang/Object;", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(26, label0);
            methodVisitor.visitVarInsn(ILOAD, 0);
            Label label1 = new Label();
            methodVisitor.visitJumpInsn(IFEQ, label1);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(27, label2);
            methodVisitor.visitInsn(ICONST_2);
            methodVisitor.visitTypeInsn(ANEWARRAY, "me/giraffetree/java/boomjava/jvm/lli/load/Singleton$LazyHolder");
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(29, label1);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitFieldInsn(GETSTATIC, "me/giraffetree/java/boomjava/jvm/lli/load/Singleton$LazyHolder", "INSTANCE", "Lme/giraffetree/java/boomjava/jvm/lli/load/Singleton;");
            methodVisitor.visitInsn(ARETURN);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLocalVariable("flag", "Z", null, label0, label3, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(33, label0);
            methodVisitor.visitInsn(ICONST_1);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "me/giraffetree/java/boomjava/jvm/lli/load/Singleton", "getInstance", "(Z)Ljava/lang/Object;", false);
            methodVisitor.visitInsn(POP);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(34, label1);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitLdcInsn("\u5df2\u7ecf\u5b8c\u6210LazyHolder \u7684\u52a0\u8f7d, \u4f46\u6ca1\u6709\u94fe\u63a5.");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(35, label2);
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitMethodInsn(INVOKESTATIC, "me/giraffetree/java/boomjava/jvm/lli/load/Singleton", "getInstance", "(Z)Ljava/lang/Object;", false);
            methodVisitor.visitInsn(POP);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(36, label3);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitLdcInsn("\u5df2\u7ecf\u5b8c\u6210LazyHolder \u7684\u94fe\u63a5\u548c\u521d\u59cb\u5316");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(37, label4);
            methodVisitor.visitInsn(RETURN);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLocalVariable("args", "[Ljava/lang/String;", null, label0, label5, 0);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_SYNTHETIC, "<init>", "(Lme/giraffetree/java/boomjava/jvm/lli/load/Singleton$1;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(11, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "me/giraffetree/java/boomjava/jvm/lli/load/Singleton", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lme/giraffetree/java/boomjava/jvm/lli/load/Singleton;", null, label0, label1, 0);
            methodVisitor.visitLocalVariable("x0", "Lme/giraffetree/java/boomjava/jvm/lli/load/Singleton$1;", null, label0, label1, 1);
            methodVisitor.visitMaxs(1, 2);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
