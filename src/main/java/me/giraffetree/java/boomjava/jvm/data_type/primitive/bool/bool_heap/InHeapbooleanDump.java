package me.giraffetree.java.boomjava.jvm.data_type.primitive.bool.bool_heap;

import org.objectweb.asm.*;

public class InHeapbooleanDump implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "me/giraffetree/java/boomjava/jvm/data_type/primitive/bool/bool_heap/InHeapboolean", null, "java/lang/Object", null);

        classWriter.visitSource("InHeapboolean.java", null);

        {
            fieldVisitor = classWriter.visitField(ACC_STATIC, "v", "Z", null, null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(3, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lme/giraffetree/java/boomjava/jvm/asm/bool_heap/InHeapboolean;", null, label0, label1, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(9, label0);

            // ICONST_1 -> ICONST_2/ICONST_3
            methodVisitor.visitInsn(ICONST_2);
            methodVisitor.visitFieldInsn(PUTSTATIC, "me/giraffetree/java/boomjava/jvm/data_type/primitive/bool/bool_heap/InHeapboolean", "v", "Z");
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(10, label1);
            methodVisitor.visitFieldInsn(GETSTATIC, "me/giraffetree/java/boomjava/jvm/data_type/primitive/bool/bool_heap/InHeapboolean", "v", "Z");
            Label label2 = new Label();
            methodVisitor.visitJumpInsn(IFEQ, label2);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(11, label3);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitLdcInsn("Hello, Java!");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(13, label2);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitFieldInsn(GETSTATIC, "me/giraffetree/java/boomjava/jvm/data_type/primitive/bool/bool_heap/InHeapboolean", "v", "Z");
            methodVisitor.visitInsn(ICONST_1);
            Label label4 = new Label();
            methodVisitor.visitJumpInsn(IF_ICMPNE, label4);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLineNumber(14, label5);
            methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            methodVisitor.visitLdcInsn("Hello, JVM!");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(16, label4);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(RETURN);
            Label label6 = new Label();
            methodVisitor.visitLabel(label6);
            methodVisitor.visitLocalVariable("args", "[Ljava/lang/String;", null, label0, label6, 0);
            methodVisitor.visitMaxs(2, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
