package 大厂刷题班.class37;
// 动态规划  背包问题
// https://leetcode.cn/problems/partition-equal-subset-sum/
public class Problem_0416_PartitionEqualSubsetSum {
    // 1、暴力递归
    public boolean canPartition1(int[] nums) {
        // 计算整个数组的累加和
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        // 如果累加和是奇数，那么不可能平分，返回false
        if ((sum & 1) != 0) {
            return false;
        }
        // 将累加和除以二
        sum >>= 1;
        // 递归求解，返回能不能在nums数组中找到一个集合可以凑出来累加和的一半
        return process(sum, nums, 0);
    }

    // 当前还剩下rest没有凑出，此时已经尝试到i位置了
    public boolean process(int rest, int[] nums, int i) {
        // 如果剩余为0，说明凑出来了，返回true
        if (rest == 0) {
            return true;
        // 剩余没凑出来的不为0
        } else {
            // 如果此时已经把所有的情况都尝试过了，还没有凑出数组累加和的一半，说明无法凑出，返回false
            if (i == nums.length) {
                return false;
            // 还没有遍历完，继续尝试
            } else {
                // 情况一：不选择i位置的数，直接去下个位置做选择
                boolean p1 = process(rest, nums, i + 1);
                // 情况二：选择i位置的数，剩余没凑出来的数就是rest - nums[i]
                boolean p2 = false;
                // 需要帮正选择nums[i]后，凑出来的数不能超过rest
                if (rest - nums[i] >= 0) {
                    p2 = process(rest - nums[i], nums, i + 1);
                }
                // 只要是两种情况有能成的，就返回true
                return p1 || p2;
            }
        }
    }

    // 2、记忆化搜索
    public boolean canPartition2(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) != 0) {
            return false;
        }

        sum >>= 1;
        // dp[i][rest]：表示来到i位置尝试，还剩下rest没有凑出来，这种情况能否凑出来整个数组累加和的一半
        // 1  true
        // -1 false
        // 0 还未赋值
        int[][] dp = new int[nums.length + 1][sum + 1];

        return process(sum, nums, 0, dp);
    }

    public boolean process(int rest, int[] nums, int i, int[][] dp) {
        // 如果当前情况已经计算过了，直接返回答案
        if (dp[i][rest] != 0) {
            return dp[i][rest] == 1;
        }

        if (rest == 0) {
            // 赋值
            dp[i][rest] = 1;
            return true;
        } else {
            if (i == nums.length) {
                // 赋值
                dp[i][rest] = -1;
                return false;
            } else {
                boolean p1 = process(rest, nums, i + 1, dp);
                boolean p2 = false;
                if (rest - nums[i] >= 0) {
                    p2 = process(rest - nums[i], nums, i + 1, dp);
                }
                if (p1 || p2) {
                    // 赋值
                    dp[i][rest] = 1;
                    return true;
                } else {
                    // 赋值
                    dp[i][rest] = -1;
                    return false;
                }
            }
        }
    }

    // 3、动态规划
    public static boolean canPartition3(int[] nums) {
        int N = nums.length;
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += nums[i];
        }
        if ((sum & 1) != 0) {
            return false;
        }
        sum >>= 1;
        boolean[][] dp = new boolean[N][sum + 1];
        // 赋初值  rest = 0，一定能凑出来，不选当前位置的i即可
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        // 赋初值
        if (nums[0] <= sum) {
            dp[0][nums[0]] = true;
        }

        // 构造dp
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                // 情况一：dp[i - 1][j]
                dp[i][j] = dp[i - 1][j];
                // 情况二：dp[i - 1][j - nums[i]]
                if (j - nums[i] >= 0) {
                    // 情况一和情况二只要有一种能成，dp[i][j]就为true
                    dp[i][j] |= dp[i - 1][j - nums[i]];
                }
                if (dp[i][sum]) {
                    return true;
                }
            }
        }
        return false;
    }
}
