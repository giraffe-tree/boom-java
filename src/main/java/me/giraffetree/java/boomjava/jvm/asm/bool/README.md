# boolean 与 jvm

## 猜测下下面这个程序的输出

```
public class BooleanTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        A a = new A();

        Field boolValueField = A.class.getDeclaredField("flag");
        unsafe.putInt(a, unsafe.objectFieldOffset(boolValueField), 3);

        if (a.flag) {
            System.out.println("Hello, Java!");
        }
        if (a.flag == true) {
            System.out.println("Hello, JVM!");
        }
    }
    private static class A {
        boolean flag;
    }
}
```
