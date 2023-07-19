package 大厂刷题班.class15;

// 测试链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/
// 从左往右的尝试模型
public class Code03_BestTimeToBuyAndSellStockIV {
    // 1、动态规划代码
    public int maxProfit1(int k, int[] prices) {
        // dp[i][j]：只能在arr 0...i上做交易，而且交易次数不要超过j次获得的最大收益
        // dp[i][0]和dp[0][j]都是0
        int[][] dp = new int[prices.length][k + 1];

        // 开始给dp赋值  从i=1开始，因为i=0的dp都为0
        for (int i = 1; i < prices.length; i++) {
            // 从j=1开始，因为j=0的dp都为0
            for (int j = 1; j <= k; j++) {
                // 情况1：在prices[i]的价格时不去做交易，所以这种情况下的dp[i][j] = dp[i - 1][j]
                int p1 = dp[i - 1][j];
                // 情况2：在prices[i]的价格时去做交易，我们要以i时间为最后一次交易的卖出时间，也就是i时间卖出prices[i]元
                // 下面我们就需要去找所有买入时间点的情况，从所有情况中找一个盈利最大值
                int p2 = 0;
                // p就是尝试第j次交易的所有买入时间点
                for (int p = 0; p <= i; p++) {
                    // dp[p][j - 1](在p之前已经完成了j-1次交易的最大盈利) - prices[p](在p时间以prices[p]价格买入) + prices[i](在i时间以prices[i]价格卖出)
                    // 取所有可能情况的总盈利最大值
                    p2 = Math.max(p2, dp[p][j - 1] - prices[p] + prices[i]);
                }
                // 取情况1和情况2之间的最大值
                dp[i][j] = Math.max(p1, p2);
            }
        }
        // 0~prices.length - 1时间交易k次的最大收益
        return dp[prices.length - 1][k];
    }

    // 2、斜率优化代码
    public int maxProfit(int k, int[] prices) {
        // 过滤无效参数
        if (prices == null || prices.length == 0 || k == 0) {
            return 0;
        }
        int n = prices.length;
        // 大过滤，如果k>=n/2，那么这道题就和122. 买卖股票的最佳时机 II这道题解法一样，就相当于不限制交易次数，因为不管怎么交易也不会超过k次
        if (k >= n / 2) {
            return maxProfitAnyK(prices);
        }

        // dp[i][j]：只能在arr 0...i上做交易，而且交易次数不要超过j次获得的最大收益
        // dp[i][0]和dp[0][j]都是0
        int[][] dp = new int[n][k + 1];
        // 从上到下，从左到右赋值
        // 从j=1开始
        for (int j = 1; j <= k; j++) {
            // 需要先单独求一下dp[1][j]
            // dp[1][j]的情况1
            int p1 = dp[0][j];
            // dp[1][j]的情况2中选取所有可能性中的最大值
            // dp[1][j]的情况2所有可能性只有两种   dp[1][j - 1] + prices[1] - prices[1] 和 dp[0][j - 1] + prices[1] - prices[0]
            // 因为所有的情况都有一个+prices[1]的过程，所以这里的best先没有算这个+prices[1]，当求出最大值以后，再给加上即可
            // 取这两种情况的最大值，得到情况2的最大值情况
            int best = Math.max(dp[1][j - 1] - prices[1], dp[0][j - 1] - prices[0]);
            // 在比较情况1和情况2的最大值，将最大值赋值给dp[1][j]，要注意给best加上prices[1]
            dp[1][j] = Math.max(p1, best + prices[1]);

            for (int i = 2; i < n; i++) {
                // 情况1
                p1 = dp[i - 1][j];
                // 情况2的最大值，这里直接用上一轮循环的best和dp[i][j - 1]比较，这两个数的最大值就是当前这一轮情况2的最大值
                best = Math.max(dp[i][j - 1] - prices[i], best);
                // 从情况1和情况2中取最大值赋值给dp[i][j]，要注意给best加上prices[1]
                dp[i][j] = Math.max(p1, best + prices[i]);
            }
        }
        // 返回答案
        return dp[n - 1][k];
    }

    // 不限制交易次数
    public int maxProfitAnyK(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                ans += (prices[i] - prices[i - 1]);
            }
        }
        return ans;
    }
}
