# boolean 在堆中的存储

## 堆中 boolean 字段

对于java heap 中的 boolean 字段, 将其放入操作数栈中时, 会进行掩码操作, 只取最后一位

对于 boolean、char 这两个无符号类型来说，加载伴随着零扩展。
举个例子，char 的大小为两个字节。
在加载时 char 的值会被复制到 int 类型的低二字节，
而高二字节则会用 0 来填充。

