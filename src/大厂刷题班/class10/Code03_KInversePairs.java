package 大厂刷题班.class10;

// 测试链接 : https://leetcode.cn/problems/k-inverse-pairs-array/
// 看题目的数据量，发现n*k=10^6，可以通过，马上意识到可能是是n*k的方法,这不就是建立一个行为n列为k的二维表嘛,明显是一个样本对应模型
// 样本对应模型   斜率优化
public class Code03_KInversePairs {
    // 1、这个思路是对的，只是因为没有取模。最后面那个代码是正确能通过的版本
    public static int kInversePairs2(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= k; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                if (j >= i) {
                    dp[i][j] -= dp[i - 1][j - i];
                }
            }
        }
        return dp[n][k];
    }

    // 2、能通过的取模版本
    public static int kInversePairs(int n, int k) {
        if (n < 1 || k < 0) {
            return 0;
        }
        // dp[i][j]：当我用1、2、3...直到i这些数字组合排列的情况，正好逆序对数量有j个的排列有几个
        int[][] dp = new int[n + 1][k + 1];
        // dp表中第一列的含义是用 1、2、3...i这些个数字排列，能正好逆序对有0个排列有几种。肯定是都有并且只有一种，就是升序的那个排列，所以第一列都是1
        dp[0][0] = 1;
        int mod = 1000000007;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= k; j++) {
                dp[i][j] = (dp[i][j - 1] + dp[i - 1][j]) % mod;
                if (j >= i) {
                    // dp中存储的是mod完的值，所以虽然dp[i][j]原本的值是大于dp[i - 1][j - i]，但是有可能mod完得到的模反而是dp[i][j]小
                    // 这样他们两个数减完就是一个负数，然后再模mod得到的就成了一个负数了，这里讲一个基本技巧
                    // 在相减的时候先加一个mod，然后得到结果之后再去模mod，这样就避免了负数情况，将最后的结果修正过来了。这个技巧要记一下，以后用得到
                    dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;
                }
            }
        }
        return dp[n][k];
    }
}

