package me.giraffetree.java.boomjava.alg.dp.coin;

/**
 * @author GiraffeTree
 * @date 2021-01-03
 */
public class CoinTest {

    public static void main(String[] args) {
        // 硬币面值
        int[] values = {3, 5};
        // 总值
        int total = 15;

        int minCounts = getMinCounts(total, values);
        System.out.println(minCounts);

    }


    private static int getMinCounts(int k, int[] values) {
        // 创建备忘录
        int[] memo = new int[k + 1];
        // 初始化状态
        memo[0] = 0;
        for (int i = 1; i <= k; i++) {
            memo[i] = -1;
        }
        for (int r = 1; r <= k; r++) {
            for (int v : values) {
                int res = r - v;
                if (res < 0) {
                    continue;
                }
                int resC = memo[res];
                if (resC < 0) {
                    continue;
                }
                memo[r] = memo[r] == -1 ? resC + 1 : Math.min(resC + 1, memo[r]);
            }
        }
        return memo[k];
    }

    private static int getMinCountsV2(int k, int[] values) {
        int[] memo = new int[k + 1];
        memo[0] = 0;
        for (int i = 1; i < k + 1; i++) {
            memo[i] = k + 1;
        }

        for (int i = 1; i < k + 1; i++) {
            for (int coin : values) {
                if (i - coin < 0) {
                    continue;
                }
                memo[i] = Math.min(memo[i], memo[i - coin] + 1);
            }
        }

        return memo[k] == k + 1 ? -1 : memo[k];
    }

}
