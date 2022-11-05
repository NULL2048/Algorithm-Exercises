package 大厂刷题班.class15;
// 测试链接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
public class Code01_BestTimeToBuyAndSellStock {
    public int maxProfit(int[] prices) {
        // 过滤无效参数
        if (prices == null || prices.length == 0) {
            return 0;
        }

        // 先将0位置的值赋值给min
        int min =prices[0];
        // 一开始最多能赚的钱就是0，不可能比0再小了
        int ans = 0;
        // 从1开始
        for (int i = 1; i < prices.length; i++) {
            // 取最大值
            ans = Math.max(ans, prices[i] - min);
            // 更新前面时间点的最小价格
            min = Math.min(min, prices[i]);
        }

        return ans;
    }
}
