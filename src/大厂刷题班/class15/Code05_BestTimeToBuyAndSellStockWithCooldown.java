package 大厂刷题班.class15;

// 测试连接：https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/
// 从左往右的尝试模型
public class Code05_BestTimeToBuyAndSellStockWithCooldown {
    // 1、暴力递归
    // 该方法是对的，提交之后大量的测试用例通过，最后几个会超时
    // 如果把这个方法改成动态规划，是可以通过的，但这个尝试不是最优解
    public static int maxProfit1(int[] prices) {
        return process1(prices, false, 0, 0);
    }

    // 这个代码就是暴力递归，用最简单的自然只会写的一个递归，改成记忆化搜索之后力扣也是可以过的
    // buy == false 目前可以交易，而且当前没有购买行为
    // buy == true 已经买了，买入价buyPrices，待卖出
    public static int process1(int[] prices, boolean buy, int index, int buyPrices) {
        if (index >= prices.length) {
            return 0;
        }
        if (buy) {
            int noSell = process1(prices, true, index + 1, buyPrices);
            int yesSell = prices[index] - buyPrices + process1(prices, false, index + 2, 0);
            return Math.max(noSell, yesSell);
        } else {
            int noBuy = process1(prices, false, index + 1, 0);
            int yesBuy = process1(prices, true, index + 1, prices[index]);
            return Math.max(noBuy, yesBuy);
        }
    }

    // 最优尝试如下：
    // buy[i] : 在0...i范围上，最后一次操作是buy动作，
    // 这最后一次操作有可能发生在i位置，也可能发生在i之前
    // buy[i]值的含义是：max{ 所有可能性[之前交易获得的最大收益 - 最后buy动作的收购价格] }
    // 比如：arr[0...i]假设为[1,3,4,6,2,7,1...i之后的数字不用管]
    // 什么叫，所有可能性[之前交易获得的最大收益 - 最后buy动作的收购价格]？
    // 比如其中一种可能性：
    // 假设最后一次buy动作发生在2这个数的时候，那么之前的交易只能在[1,3,4]上结束，因为6要cooldown的，
    // 此时最大收益是多少呢？是4-1==3。那么，之前交易获得的最大收益 - 最后buy动作的收购价格 = 3 - 2 = 1
    // 另一种可能性：
    // 再比如最后一次buy动作发生在最后的1这个数的时候，那么之前的交易只能在[1,3,4,6,2]上发生，因为7要cooldown的，
    // 此时最大收益是多少呢？是6-1==5。那么，之前交易获得的最大收益 - 最后buy动作的收购价格 = 5 - 1 = 4
    // 除了上面两种可能性之外，还有很多可能性，你可以假设每个数字都是最后buy动作的时候，
    // 所有可能性中，(之前交易获得的最大收益 - 最后buy动作的收购价格)的最大值，就是buy[i]的含义
    // 为啥buy[i]要算之前的最大收益 - 最后一次收购价格？尤其是最后为什么要减这么一下？
    // 因为这样一来，当你之后以X价格做成一笔交易的时候，当前最好的总收益直接就是 X + buy[i]了
    //
    // sell[i] :0...i范围上，最后一次操作是sell动作，这最后一次操作有可能发生在i位置，也可能发生在之前
    // sell[i]值的含义：0...i范围上，最后一次动作是sell的情况下，最好的收益
    //
    // 于是通过分析，能得到以下的转移方程：
    // buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i])




    // 如果i位置没有发生buy行为，说明有没有i位置都一样，那么buy[i] = buy[i-1]，这显而易见
    // 如果i位置发生了buy行为, 那么buy[i] = sell[i - 2] - prices[i]，
    // 因为你想在i位置买的话，你必须保证之前交易行为发生在0...i-2上，
    // 因为如果i-1位置有可能参与交易的话，i位置就要cooldown了，
    // 而且之前交易行为必须以sell结束，你才能buy，而且要保证之前交易尽可能得到最好的利润，
    // 这正好是sell[i - 2]所代表的含义，并且根据buy[i]的定义，最后一定要 - prices[i]
    //
    // sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i])
    // 如果i位置没有发生sell行为，那么sell[i] = sell[i-1]，这显而易见
    // 如果i位置发生了sell行为，那么我们一定要找到 {之前获得尽可能好的收益 - 最后一次的收购价格尽可能低}，
    // 而这正好是buy[i - 1]的含义！之前所有的"尽可能"中，最好的一个！
    public static int maxProfit2(int[] prices) {
        // 如果只有一个数，那么最大的收益肯定就是0
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        // buy[i]指的是在0到i的范围上，最后一次操作一定是买操作能拿到的最大收益。
        int[] buy = new int[n];
        // sell[i]指的是0~i做无限制次数的交易，最后一个动作必须是卖，能获得的最好收入。
        int[] sell = new int[n];
        // 有一点需要注意！！ 【题目规定卖出股票之后要间隔一天才可以再买，但是并不要求买入股票后需要间隔一天才能卖，买入股票后可以马上卖出】

        // i=0的时候，我们在0~0选择一个买入时间，只有一个时间点0，所以只能选0时间点买入，但是0之前已经没有别的时间点了，也就是说在0买入时间之前的最大收益就是0，所以buy[0] = 0 -prices[0]
        buy[0] = -prices[0];
        // 取两种情况的最大值
        // 情况1：1不参与交易，buy[1] = buy[0]
        // 情况2：1参与交易，buy[1] = 0 - prices[1]   因为题目的要求，如果1位置需要参与交易，那么就不可能再0位置有卖出，因为不符合冷冻期规定，所以情况2直接就是-prices[1]
        buy[1] = Math.max(buy[0], -prices[1]);
        // 如果0位置是卖出操作，前面必须要有买入操作才可以，但是0前面已经没有时间点了，不可能有买入操作，所以sell[0]这个情况本身就是不存在的，直接设置为0。
        sell[0] = 0;
        // 取两种情况的最大值
        // 情况1：1不参与交易，sell[1] = sell[0]
        // 情况2：1参与交易，sell[1] = buy[0] + prices[1]    题目没有要求买入股票后再卖出需要冷冻期，所以如果1位置参与交易，那么在0位置就可以买入，所以情况2是buy[0] + prices[1]
        sell[1] = Math.max(sell[0], buy[0] + prices[1]);
        // 开始构造buy和sell
        for (int i = 2; i < n; i++) {
            // 取两种情况的最大值    因为卖操作之后需要冻结一天才可以再买，所以这里用sell[i - 2]
            buy[i] = Math.max(buy[i - 1], sell[i - 2] - prices[i]);
            // 取两种情况的最大值
            // 我们能发现sell是依赖buy求出来的    买操作之后并不需要冻结一天，所以这里用buy[i - 1]
            sell[i] = Math.max(sell[i - 1], buy[i - 1] + prices[i]);
        }

        // 最后的结果就是0~n-1位置去进行交易，并且保证最后一次操作一定是卖的情况下的最高收益
        return sell[n - 1];
    }

    // 最优解就是方法2的空间优化而已
    public static int maxProfit3(int[] prices) {
        if (prices.length < 2) {
            return 0;
        }
        // 这道题其实每一个buy和sell只是依赖自己左边的一个值，没有必要为他们开辟一整个数组
        // 就设置好两个变量，然后循环滚下去就可以了，sell滚到最后的值就是我们要的答案
        // 整个流程和上面的写法是一样的
        int buy1 = Math.max(-prices[0], -prices[1]);
        int sell1 = Math.max(0, prices[1] - prices[0]);
        int sell2 = 0;
        for (int i = 2; i < prices.length; i++) {
            int tmp = sell1;
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy1 = Math.max(buy1, sell2 - prices[i]);
            sell2 = tmp;
        }
        return sell1;
    }

}
