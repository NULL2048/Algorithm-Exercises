package 大厂刷题班.class15;

// 从左往右的尝试模型
// 测试谅解：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
public class Code06_BestTimeToBuyAndSellStockWithTransactionFee {
    public int maxProfit(int[] prices, int fee) {
        // 如果数量小于2，最大收益就是0
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        // bestBuy：表示0~i范围上做无限制次数的交易，并且保证最后一次操作一定是买，考虑上每次交易要交手续费的情况下，最大的收益
        // 先给初始化i=0时，bestBuy的值，记得要减去手续费
        int bestBuy = 0 - prices[0] - fee;
        // bestSell：表示0~i范围上做无限制次数的交易，并且保证最后一次操作一定是卖，这里就不用考虑手续费了，一次交易只需要交一次手续费，我们就默认是在买操作的时候交手续费
        int bestSell = 0;
        // 开始向后推
        for (int i = 1; i < n; i++) {
            // 两种情况，一种是i位置不参与交易，一种是i位置就是最后一次买操作，这两种情况取最大值
            bestBuy = Math.max(bestBuy, bestSell - prices[i] - fee);
            // 两种情况，一种是i位置不参与交易，一种是i位置就是最后一次卖操作，这两种情况取最大值
            bestSell = Math.max(bestSell, bestBuy + prices[i]);
        }

        // 取最后一次是卖操作的最大收益
        return bestSell;
    }
}
