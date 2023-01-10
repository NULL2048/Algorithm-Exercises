package 大厂刷题班.class33;
// 从左往右的尝试模型    动态规划   子序列
// https://leetcode.cn/problems/house-robber-ii/
public class Problem_0213_HouseRobberII {
    public int rob(int[] nums) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 单独讨论特殊情况
        if (nums.length == 1) {
            return nums[0];
        } else if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        // 数组长度
        int n = nums.length;
        // dp[i]：表示到i为止的符合条件的最大累加和
        int[] dp = new int[n];

        // 因为数组是一个环，所以0位置和n-1位置是连在一起的，所以0位置额和n-1位置一定不能同时选择，所以就分两种情况

        // 情况一：从0位置开始选择，0~n-2范围上进行选择
        dp[0] = nums[0];
        // 这里要取0位置和1位置的最大值给dp[1]赋值
        dp[1] = Math.max(nums[0], nums[1]);
        // 构造dp数组
        for (int i = 2; i < n - 1; i++) {
            // 一共三种情况
            // 1、只选i位置
            // 2、不选i位置，只从0~i-1位置自由选择，即dp[i - 1]
            // 3、选择i位置，然后从0~i-2位置自由选择，即dp[i - 2] + nums[i]
            // 这三种情况可以保证完全没有选择相邻位置的情况
            dp[i] = Math.max(nums[i], Math.max(dp[i - 1], dp[i - 2] + nums[i]));
        }

        int max = dp[n - 2];
        // 情况二：从1位置开始选择，1~n-1范围上进行选择
        dp[1] = nums[1];
        // 这里要取1位置和2位置的最大值给dp[2]赋值
        dp[2] = Math.max(nums[1], nums[2]);
        // 构造dp数组
        for (int i = 3; i < n; i++) {
            // 一共三种情况
            // 1、只选i位置
            // 2、不选i位置，只从1~i-1位置自由选择，即dp[i - 1]
            // 3、选择i位置，然后从1~i-2位置自由选择，即dp[i - 2] + nums[i]
            // 这三种情况可以保证完全没有选择相邻位置的情况
            dp[i] = Math.max(nums[i], Math.max(dp[i - 1], dp[i - 2] + nums[i]));
        }
        // 选择两种情况的最大值进行返回
        max = Math.max(max, dp[n - 1]);
        return max;
    }
}
