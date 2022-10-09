package 大厂刷题班.class04;

// 在线测试链接 : https://leetcode.cn/problems/house-robber/
public class Code04_SubArrayMaxSumFollowUp {
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        // dp[i]表示在0~i范围上选取不相邻的一个子序列，能取得的最大累加和是多少
        int[] dp = new int[nums.length];
        // 先对前两个位置进行复制
        // 0~0的最大累加和一定是nums[0]，因为数组中都是非负数
        dp[0] = nums[0];
        // 0~1的最大累加和之可能来自于nums[0]和nums[1]最大的那一个，因为题目要求不相邻
        dp[1] = Math.max(nums[0], nums[1]);

        // 向后设置后续的不同范围的最大累加和
        for (int i = 2; i < nums.length; i++) {
            // 一共有三种选择
            // 选择一：只选择自己当前位置的数作为子序列
            int p1 = nums[i];
            // 选择二：不选择自己加入到子序列中，也就是谁最大累加和取决于上一个位置的最大不相邻子序列累加和
            int p2 = dp[i - 1];
            // 选择二：选择自己加入子序列，因为题目要求不能相邻，所以前面的子序列只能去i-2位置及之前的数
            int p3 = dp[i - 2] + nums[i];

            // 三种选择取最大值
            dp[i] = Math.max(p1, Math.max(p2, p3));
        }

        // dp[nums.length - 1]表示0~nums.length-1范围上能取得的最大不相邻子序列累加和
        return dp[nums.length - 1];
    }
}
