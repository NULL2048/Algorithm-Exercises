package 大厂刷题班.class09;

// 本题测试链接 : https://leetcode.cn/problems/longest-increasing-subsequence
// 动态规划    辅助数组    二分
// 这个题就是一个算法模板，直接背，以后如果能用上直接无脑套模板。
public class Code04_LIS {

    // 1、动态规划代码
    public int lengthOfLIS1(int[] nums) {
        // 一般子序列的这种题我们就使用动态规划求解。以i位置结尾的子序列怎么怎么样，以这个角度去写动态规划。
        // dp[i]：以下标i位置结尾的子序列的最长递增长度
        int[] dp = new int[nums.length];
        // 先赋初值，以0结尾的子序列递增长度为1
        dp[0] = 1;
        // 我们要找到以所有位置为结尾的最长递增子序列中的最大值为返回的答案，也就是找dp数组中的最大值为最终答案
        // 记录最大递增子序列长度
        int ans = dp[0];
        // 遍历计算所有位置结尾情况的最长递增子序列长度
        for (int i = 1; i < nums.length; i++) {
            // 记录以i下标结尾的最长递增子序列长度
            int max = 0;
            // 去遍历i位置之前的dp答案，找前面能够和i位置组成更长的递增子序列的子序列
            for (int j = 0; j < i; j++) {
                // 如果前面的子序列的结尾位置的数小于i位置的数，就说明这个子序列可以和i位置组成更长的递增子序列
                if (nums[i] > nums[j]) {
                    // 看看能不能推高最大长度
                    max = Math.max(max, dp[j]);
                }
            }
            // 将前面的最长递增子序列加上当前i位置的数组成了以i位置结尾的最长递增子序列
            dp[i] =  max + 1;
            // 找dp数组中的最大值
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

    // 2、优化代码
    public static int lengthOfLIS(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // ends数组
        // ends[i]表示 : 目前所有长度为i+1的递增子序列的最小结尾
        int[] ends = new int[arr.length];
        // 根据含义, 一开始ends[0] = arr[0]
        ends[0] = arr[0];
        // ends有效区范围是0...right，right往右为无效区
        // 所以一开始right = 0, 表示有效区只有0...0范围
        int right = 0;
        // 最长递增子序列的长度
        // 全局变量，抓取每一步的答案，取最大的结果。初始值是1，数组中只有1个数的话，最大的递增子序列长度就是1
        int max = 1;
        for (int i = 1; i < arr.length; i++) {
            int l = 0;
            int r = right;
            // 在ends[l...r]范围上二分
            // 如果 当前数(arr[i]) > ends[m]，砍掉左侧
            // 如果 当前数(arr[i]) <= ends[m]，砍掉右侧
            // 整个二分就是在ends里寻找 >= 当前数(arr[i])的最左位置
            // 就是从while里面出来时，l所在的位置。
            // 如果ends中不存在 >= 当前数(arr[i])的情况，将返回有效区的越界位置
            // 也就是从while里面出来时，l所在的位置，是有效区的越界位置
            // 比如 : ends = { 3, 5, 9, 12, 再往右无效}
            // 如果当前数为8, 从while里面出来时，l将来到2位置
            // 比如 : ends = { 3, 5, 9, 12, 再往右无效}
            // 如果当前数为13, 从while里面出来时，l将来到有效区的越界位置，4位置
            while (l <= r) {
                int m = (l + r) / 2;
                // ends数组的值也是有序的
                if (arr[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }

            // end数组的值也是由大到小排序好的
            // 上面这个while循环表面是是找end数组最左边的数值大于envelopesArr[i].length的位置
            // 但实际就是在找end数组最右边数值不大于envelopesArr[i].length的位置，很好理解。

            // 从while里面出来，看l的位置
            // 如果l比right大，说明扩充了有效区，那么right变量要随之变大
            // 如果l不比right大，说明l没有来到有效区的越界位置，right不变
            right = Math.max(right, l);
            // l的位置，就是当前数应该填到ends数组里的位置
            ends[l] = arr[i];
            // 更新全局变量
            max = Math.max(max, l + 1);
        }
        return max;
    }
}
