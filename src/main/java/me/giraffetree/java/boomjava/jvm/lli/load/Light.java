package me.giraffetree.java.boomjava.jvm.lli.load;

/**
 * @author GiraffeTree
 * @date 2020/5/8
 */
public class Light {

    public static class Holder {

        private final static Light LIGHT = new Light();

        static {
            System.out.println("Holder clinit...");
        }

        public static Light getInstance() {
            return LIGHT;
        }

    }


}
