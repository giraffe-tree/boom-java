package me.giraffetree.java.boomjava.alg.graph.leetcode;

/**
 * 在一个小镇里，按从 1 到 N 标记了 N 个人。传言称，这些人中有一个是小镇上的秘密法官。
 * <p>
 * 如果小镇的法官真的存在，那么：
 * <p>
 * 小镇的法官不相信任何人。
 * 每个人（除了小镇法官外）都信任小镇的法官。
 * 只有一个人同时满足属性 1 和属性 2 。
 * 给定数组 trust，该数组由信任对 trust[i] = [a, b] 组成，表示标记为 a 的人信任标记为 b 的人。
 * <p>
 * 如果小镇存在秘密法官并且可以确定他的身份，请返回该法官的标记。否则，返回 -1。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：N = 2, trust = [[1,2]]
 * 输出：2
 * 示例 2：
 * <p>
 * 输入：N = 3, trust = [[1,3],[2,3]]
 * 输出：3
 * 示例 3：
 * <p>
 * 输入：N = 3, trust = [[1,3],[2,3],[3,1]]
 * 输出：-1
 * 示例 4：
 * <p>
 * 输入：N = 3, trust = [[1,2],[2,3]]
 * 输出：-1
 * 示例 5：
 * <p>
 * 输入：N = 4, trust = [[1,3],[1,4],[2,3],[2,4],[4,3]]
 * 输出：3
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= N <= 1000
 * trust.length <= 10000
 * trust[i] 是完全不同的
 * trust[i][0] != trust[i][1]
 * 1 <= trust[i][0], trust[i][1] <= N
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/find-the-town-judge
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/30
 */
public class FindJudge {

    /**
     * 思路:
     * 计算入度和出度
     * 法官的入度: N-1
     * 法官的出度: 0
     */
    public int findJudge(int N, int[][] trust) {
        int[] in = new int[N + 1];
        int[] out = new int[N + 1];
        for (int i = 0; i < trust.length; i++) {
            in[trust[i][1]]++;
            out[trust[i][0]]++;
        }
        int count = 0;
        int judge = -1;
        for (int i = 1; i < N + 1; i++) {
            if (in[i] == N - 1 && out[i] == 0) {
                judge = i;
                count++;
            }
        }
        if (count > 1) {
            return -1;
        }
        return judge;
    }

}
