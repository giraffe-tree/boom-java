package me.giraffetree.java.boomjava.alg.graph.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 1042. 不邻接植花
 * 有 N 个花园，按从 1 到 N 标记。在每个花园中，你打算种下四种花之一。
 * <p>
 * paths[i] = [x, y] 描述了花园 x 到花园 y 的双向路径。
 * <p>
 * 另外，没有花园有 3 条以上的路径可以进入或者离开。
 * <p>
 * 你需要为每个花园选择一种花，使得通过路径相连的任何两个花园中的花的种类互不相同。
 * <p>
 * 以数组形式返回选择的方案作为答案 answer，其中 answer[i] 为在第 (i+1) 个花园中种植的花的种类。花的种类用  1, 2, 3, 4 表示。保证存在答案。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：N = 3, paths = [[1,2],[2,3],[3,1]]
 * 输出：[1,2,3]
 * 示例 2：
 * <p>
 * 输入：N = 4, paths = [[1,2],[3,4]]
 * 输出：[1,2,1,2]
 * 示例 3：
 * <p>
 * 输入：N = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
 * 输出：[1,2,3,4]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= N <= 10000
 * 0 <= paths.size <= 20000
 * 不存在花园有 4 条或者更多路径可以进入或离开。
 * 保证存在答案。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/flower-planting-with-no-adjacent
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author GiraffeTree
 * @date 2020/1/30
 */
public class GardenNoAdj {

    /**
     * 思路:
     * 构造一个无向图
     * 这里使用邻接表
     */
    @SuppressWarnings("unchecked")
    public int[] gardenNoAdj(int N, int[][] paths) {
        Set<Integer>[] adj = new HashSet[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new HashSet<>();
        }
        for (int[] path : paths) {
            int i1 = path[0] - 1;
            int i2 = path[1] - 1;
            adj[i1].add(i2);
            adj[i2].add(i1);
        }
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            int[] color = new int[5];
            for (int other : adj[i]) {
                if (result[other] != 0) {
                    color[result[other]]++;
                }
            }
            for (int j = 1; j < 5; j++) {
                if (color[j] == 0) {
                    result[i] = j;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 改编自  gardenNoAdj 方法
     * 将 set 实现替换为 int[]
     */
    public int[] gardenNoAdj3(int N, int[][] paths) {
        // 每个 int[i] = {相邻花园的count, 相邻花园编号,相邻花园编号,相邻花园编号}
        int[][] adj = new int[N][4];

        for (int[] path : paths) {
            int i1 = path[0] - 1;
            int i2 = path[1] - 1;
            adj[i1][0]++;
            adj[i2][0]++;
            adj[i1][adj[i1][0]] = i2;
            adj[i2][adj[i2][0]] = i1;
        }
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            int[] color = new int[5];
            for (int j = 1; j <= adj[i][0]; j++) {
                // 相邻的花园编号
                int other = adj[i][j];
                if (result[other] != 0) {
                    color[result[other]]++;
                }
            }
            for (int j = 1; j < 5; j++) {
                if (color[j] == 0) {
                    result[i] = j;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 来自提交记录中7ms的思路
     */
    public int[] gardenNoAdj2(int N, int[][] paths) {
        if (paths.length == 0) {
            //如果没有花园相通
            int[] result = new int[N];
            for (int i = 0; i < N; i++) {
                result[i] = 1;
            }
        }
        int[][] result = new int[N + 1][5];
        //每个位置维持一个5个长度的数组，
        // 第一个位置存放有多少个相邻花园，最后一个位置放花的种类
        for (int i = 0; i < paths.length; i++) {
            //先遍历 ，把相邻位置放进去
            //增加一个相邻花园
            int i1 = paths[i][0];
            int i2 = paths[i][1];
            result[i1][0]++;
            //增加一个相邻花园
            result[i2][0]++;
            //把相邻花园放进去
            result[i1][result[i1][0]] = i2;
            //把相邻花园放进去
            result[i2][result[i2][0]] = i1;
        }
        for (int i = 1; i <= N; i++) {
            //遍历每一个花园
            for (int j = 1; j <= 4; j++) {
                //遍历决定放哪一种花
                //定义在外面，好判断进行到哪了
                int k = 0;
                for (k = 1; k < 4; k++) {
                    // 看这个花有没有相邻花园使用
                    if (result[result[i][k]][4] == j) {
                        break;//
                    }
                }
                if (k == 4) {
                    result[i][4] = j;
                    break;
                }
            }
        }
        int[] result2 = new int[N];
        //真正的结果
        for (int i = 0; i < N; i++) {
            result2[i] = result[i + 1][4];
        }
        return result2;
    }

}
