package 体系学习班.class19;

public class Code01_Knapsack {
    // 1、暴力递归
    // 所有的货，重量和价值，都在w和v数组里
    // 为了方便，其中没有负数
    // bag背包容量，不能超过这个载重
    // 返回：不超重的情况下，能够得到的最大价值
    public static int maxValue(int[] w, int[] v, int bag) {
        // 判空
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        // 尝试函数！   这里就可以知道这道题最终是想要什么情况下的结果，在动态规划的时候知道要返回什么下标的数据（dp[0][bag]）。
        return process(w, v, 0, bag);
    }

    // index 0~N   当前货物
    // rest 负~bag    还剩下的承重
    public static int process(int[] w, int[] v, int index, int rest) {
        // base case 当已经没有承重了，直接返回-1，表示上一层加入的货物是无效的，加上去就超重了，所以不应该加入。
        if (rest < 0) {
            return -1;
        }
        // base case 所有的货物已经遍历完了，也没有超重，此时已经没有货物可以加入了，直接返回0
        if (index == w.length) {
            return 0;
        }
        // 两种情况的尝试，分别取进行递归，并且返回最大值
        // 不加入当前商品，直接去尝试下一个商品index+1，并且剩余承重不变
        int p1 = process(w, v, index + 1, rest);
        // 加入当前商品，如果加入当前商品就超重了，则就不加入当前商品，如果加入没超重，就将当前商品的价值累加上，并且将剩余承重减去当前货物的重量
        int p2 = 0;
        int next = process(w, v, index + 1, rest - w[index]);
        if (next != -1) {
            p2 = v[index] + next;
        }
        // 返回两种情况中的最大值
        return Math.max(p1, p2);
    }

    // 这里就不写加缓存了，练熟了之后直接改动态规划

    // 2、动态规划
    public static int dp(int[] w, int[] v, int bag) {
        // 判空
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        // 创建dp缓存数组   创建的时候每一个元素默认都是0，所以最下面一层就都默认赋值为0了
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];

        // 将dp中其他位置得值都根据依赖关系推出来赋值，从下向上赋值
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                // 下面这段代码，直接照搬暴力递归中的两种情况尝试代码即可，稍微改一下就能用
                // 1、不要当前得货物
                int p1 = dp[index + 1][rest];
                // 2、要当前的货物，但是如果无效，则还是要将p2设置为0
                int p2 = 0;
                // 当加入当前货物之后，超出了承重范围，则说明本次加入的货物无效，直接设置为-1
                int next = rest - w[index] < 0 ? -1 : dp[index + 1][rest - w[index]];
                // 如果无效，p2就设置为0。有效就将加入货物的价值累加进去
                if (next != -1) {
                    p2 = v[index] + next;
                }

                // 每次将p1和p2中最大值加入到dp缓存中
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        // 返回最终结果
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }

}

