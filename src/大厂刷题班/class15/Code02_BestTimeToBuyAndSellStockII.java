package 大厂刷题班.class15;

// 测试链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/
// 【动态规划】【数组】【模拟】
public class Code02_BestTimeToBuyAndSellStockII {
    public int maxProfit(int[] prices) {
        // 过滤无效参数
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            // 如果是升序，就开始累加每一轮和自己前面相邻位置的能盈利的钱数
            // 如果不是升序，就不做累加
            if (prices[i] > prices[i - 1]) {
                ans += (prices[i] - prices[i - 1]);
            }
        }

        // 完成循环后，ans中存储的就是整个数组中所有升序序列的盈利累加和
        return ans;
    }
}
