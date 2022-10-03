package 大厂刷题班.class02;

// 本题测试链接 : https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
public class Code06_MinLengthForSort {
    public int findUnsortedSubarray(int[] nums) {
        // 特殊情况单独讨论
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int n = nums.length;
        // 记录从左向右划过的最大值下标
        int leftMaxIndex = 0;
        // nums[leftMaxIndex]是在0~l范围上的最大值，l表示这个最大值的边界
        int l = 0;
        for (int i = 1; i < n; i++) {
            // 如果找到更大的，就更新leftMaxIndex
            if (nums[i] >= nums[leftMaxIndex]) {
                leftMaxIndex = i;
            } else {
                // 如果没有找到更大的，则将边界向右移动
                l = i;
            }
        }

        // 记录从右向左划过的最小值下标
        int rightMinIndex = n - 1;
        // nums[rightMinIndex]是在r~n-1范围上的最小值，r表示这个最小值的边界
        int r = n - 1;
        for (int i = n - 2; i >= 0; i--) {
            // 如果找到更小的，就更新rightMinIndex
            if (nums[i] <= nums[rightMinIndex]) {
                rightMinIndex = i;
            } else {
                // 如果没有找到更小的，则将边界向左移动
                r = i;
            }
        }

        // l的右边和r的左边一定都是有序的了，在排序过程中不需要再做调整了
        // 如果nums数组本身已经是整体有序的，那么求出来的l-r是小于0的，这种情况直接返回0
        return Math.max(l - r + 1, 0);
    }
}
