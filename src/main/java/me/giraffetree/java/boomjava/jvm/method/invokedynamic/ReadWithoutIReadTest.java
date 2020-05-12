package me.giraffetree.java.boomjava.jvm.method.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * @author GiraffeTree
 * @date 2020-05-12
 */
public class ReadWithoutIReadTest {

    public static void main(String[] args) throws Throwable {

        int loop = 10;
        while (loop-- > 0) {
            Object obj;
            if (loop % 2 == 0) {
                obj = new SpringInAction();
            } else {
                obj = new KotlinInAction();
            }
            MethodType methodType = MethodType.methodType(void.class);
            MethodHandle handle = MethodHandles.lookup().findVirtual(obj.getClass(), "read", methodType);

            handle.invoke(obj);
        }

        System.out.println("tired....");

    }

    interface IRead {
        void read();
    }


    /**
     * 如果使用 IRead 接口
     */
    private static class SpringInAction implements IRead {

        @Override
        public void read() {
            System.out.println("read spring in action");
        }
    }

    /**
     * 不使用 IRead 接口
     */
    private static class KotlinInAction {

        public void read() {
            System.out.println("read kotlin in action");
        }
    }

}
