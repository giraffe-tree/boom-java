package me.giraffetree.java.boomjava.alg.sort.leetcode.completed;

/**
 * 给定一位研究者论文被引用次数的数组（被引用次数是非负整数）。编写一个方法，计算出研究者的 h 指数。
 * <p>
 * h 指数的定义: “h 代表“高引用次数”（high citations），
 * 一名科研人员的 h 指数是指他（她）的 （N 篇论文中）至多有 h 篇论文分别被引用了至少 h 次。
 * （其余的 N - h 篇论文每篇被引用次数不多于 h 次。）”
 * <p>
 * 示例:
 * <p>
 * 输入: citations = [3,0,6,1,5]
 * 输出: 3
 * 解释: 给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
 *      由于研究者有 3 篇论文每篇至少被引用了 3 次，其余两篇论文每篇被引用不多于 3 次，所以她的 h 指数是 3。
 *  
 * <p>
 * 说明: 如果 h 有多种可能的值，h 指数是其中最大的那个。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/h-index
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/8
 */
public class HIndex2 {

    public static void main(String[] args) {
        int[] b = {3, 0, 6, 1, 5, 4};

        System.out.println(hIndex(b));

    }

    /**
     * 至多有 h 篇论文分别被引用了至少 h 次。
     * 思路:
     * 对n篇文章被引用的次数进行统计
     * 根据被引用的次数 放入 0 1 2..... n  一共 n+1 个桶 a 中
     * 如果 a[?>x] >= x 则 结果为 x
     */
    public static int hIndex(int[] citations) {
        int n = citations.length;
        int[] papers = new int[n + 1];
        // 计数
        for (int c : citations) {
            papers[Math.min(n, c)]++;
        }
        int cur = 0;
        for (int i = n; i > 0; i--) {
            if (papers[i] + cur >= i) {
                return i;
            }
            cur += papers[i];
        }
        return 0;
    }


}
