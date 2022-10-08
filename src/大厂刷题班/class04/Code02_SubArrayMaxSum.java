package 大厂刷题班.class04;

// 子数组   状态压缩的动态规划
// 不能用窗口，因为存在负数，没有单调性
public class Code02_SubArrayMaxSum {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 记录以上一个位置结尾的子数组中，最大的累加和
        int preMaxSum = nums[0];
        // 记录最大子数组累加和
        int max = preMaxSum;
        // 去遍历以每一个位置为结尾的子数组，查找最大累加和
        for (int i = 1; i < nums.length; i++) {
            // 情况一：以i位置结尾的子数组只有i位置本身
            int p1 = nums[i];
            // 情况二：以i位置结尾的子数组还有左边其他的数，这种情况下最大累加和就是以i-1位置为结尾的子数组中的最大累加和 + nums[i]
            int p2 = preMaxSum + nums[i];

            // 找到两种情况的最大值，并更新preMaxSum
            preMaxSum = Math.max(p1, p2);
            // 更新所有情况中的最大累加和
            max = Math.max(max, preMaxSum);
        }
        return max;
    }

}
