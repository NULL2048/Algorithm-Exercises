package 大厂刷题班.class27;

//  三道题如下：
//	微信面试题
//	题目一：
//	股民小 A 有一天穿越回到了 n 天前，他能记住某只股票连续 n 天的价格；他手上有足够多的启动资金，他可以在这 n 天内多次交易，但是有个限制
//	如果已经购买了一个股票，在卖出它之前就不能再继续购买股票了。
//	到 n 天之后，小 A 不能再持有股票。
//	求问 这 n 天内，小 A 能够获得的利润的最大值
//	如果不需要手续费的话，求最大的利润
//	function(number) {
//	return number
//	}
//	输入: prices = [3, 1, 2, 8, 5, 9]
//	输出: 11
//
//	题目二：
//	企鹅厂每年都会发文化衫，文化衫有很多种，厂庆的时候，企鹅们都需要穿文化衫来拍照
//  一次采访中，记者随机遇到的企鹅，企鹅会告诉记者还有多少企鹅跟他穿一种文化衫
//  我们将这些回答放在 answers 数组里，返回鹅厂中企鹅的最少数量。
//	输入: answers = [1]    输出：2
//	输入: answers = [1, 1, 2]    输出：5
//  贪心
//  Leetcode题目：https://leetcode.cn/problems/rabbits-in-forest/
//
//	题目三：
//	WXG 的秘书有一堆的文件袋，现在秘书需要把文件袋嵌套收纳起来。请你帮他计算下，最大嵌套数量。
//	给你一个二维整数数组 folders ，其中 folders[i] = [wi, hi] ，表示第 i 个文件袋的宽度和长度
//	当某一个文件袋的宽度和长度都比这个另外一个文件袋大的时候，前者就能把后者装起来，称之为一组文件袋。
//	请计算，最大的一组文件袋的数量是多少。
//	实例
//	输入：[[6,10],[11,14],[6,1],[16,14],[13,2]]
//	输出： 3

import java.util.Arrays;

// 题目一，股票系列专题，大厂刷题班15节
// 题目三，最长递增子序列专题，大厂刷题班第9节
// 我们来讲一下题目二
public class Code02_MinPeople {
    public int numRabbits(int[] answers) {
        // 过滤无效参数
        if (answers == null || answers.length == 0) {
            return 0;
        }
        // 排序，把说的数字相同的人排列到一起
        Arrays.sort(answers);
        // 当前统计的是说num的人有多少个
        int num = answers[0];
        // 统计当前这一组有多少人
        int cnt = 1;
        int ans = 0;
        // 看一下说的数字相同的人能划分出几组，再用组数乘以num+1就是这一组能确定的最少人数
        for (int i = 1; i < answers.length; i++) {
            // 当answers[i] != num时，说明已经遍历完了一组，开始结算这一组，并且开始遍历说下一种数字的一组
            if (answers[i] != num) {
                // 结算说num的人的这一组能确定的最少人数，这里要注意除法向上取整的技巧
                ans += (cnt + (num + 1 - 1)) / (num + 1) * (num + 1);
                // 后面开始找说answers[i]这个数字的人有多少个，更新num的值
                num = answers[i];
                // cnt初始化为1，开始找说answers[i]这个数字的人有多少个
                cnt = 1;
                // 遍历看说num这个数字的人有多少个，cnt++
            } else {
                cnt++;
            }
        }

        // 还需要再加上最后一轮计算的，上面循环的最后一轮计算的还没有结算进ans中
        return ans +  (cnt + (num + 1 - 1)) / (num + 1) * (num + 1);
    }
}
