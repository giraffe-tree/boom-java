package me.giraffetree.java.boomjava.alg.sort;

import lombok.Data;

/**
 * 为了检查是否为稳定排序, 我使用 Element 进行比较
 * 使用 origin 的余数进行大小比较
 *
 * @author GiraffeTree
 * @date 2020/1/3
 */
@Data
public class Element implements Comparable<Element> {
    /**
     * 被除数
     */
    private int origin;

    /**
     * 除数
     */
    private transient int divisor;

    public Element(int origin) {
        this.origin = origin;
        this.divisor = 10;
    }

    public Element(int origin, int divisor) {
        this.origin = origin;
        this.divisor = divisor;
    }

    /**
     * 使用余数进行比较
     */
    @Override
    public int compareTo(Element o) {
        return (this.origin % divisor) - (o.origin % o.divisor);
    }

    @Override
    public String toString() {
        return "{" + origin + "," + origin % divisor + "}";
    }

}
