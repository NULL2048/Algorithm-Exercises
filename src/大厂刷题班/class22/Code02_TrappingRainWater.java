package 大厂刷题班.class22;

// 双指针（放到窗口内最大值那个章节）  贪心
// 本题测试链接 : https://leetcode.cn/problems/trapping-rain-water/
// 接雨水这个题，其实就是单点思维的题目，单点思维在贪心笔记里的超级洗衣机里有讲
// 这个题思路有点像本节课第一题Code01_MaximumSumof3NonOverlappingSubarrays这道题
public class Code02_TrappingRainWater {
    public int trap(int[] height) {
        // 长度小于等于2的都存不住水
        if (height == null || height.length <= 2) {
            return 0;
        }

        int n = height.length;
        // 左右指针，分别从1和n-2开始
        int l = 1;
        int r = n - 2;
        // 左部分最大高度，一开始设置为 height[0]
        int leftMax = height[0];
        // 右部分最大高度，一开始设置为 height[n - 1]
        int rightMax = height[n - 1];

        // 记录总的水量
        int sum = 0;
        // 左右指针开始向中间遍历结算水量
        while (l <= r) {
            int water;
            // 每次结算左右max比较小的那一侧的水量
            // 如果leftMax和rightMax相等，就同时结算
            // 结算的就是左右指针指向的位置
            if (leftMax <= rightMax) {
                // 以leftMax作为瓶颈，如果相减小于0，那么l位置的水量就是0
                water = leftMax - height[l];
                sum += (water > 0 ? water : 0);
                // 更新leftMax，并且左指针右移
                leftMax = Math.max(height[l++], leftMax);
            }

            // 如果存在leftMax和rightMax相等的情况，那么左右两边就要同时结算，并且我们要保证l<=r，因为有可能出现上面的结算完之后其实已经将所有的位置都结算一遍了，然后就不能再进入这个分支了，防止同一个位置重复结算
            if (leftMax >= rightMax && l <= r) {
                // 以rightMax作为瓶颈，如果相减小于0，那么r位置的水量就是0
                water = rightMax - height[r];
                sum += (water > 0 ? water : 0);
                // 更新rightMax，并且右指针zuo移
                rightMax = Math.max(height[r--], rightMax);
            }
        }

        return sum;
    }
}
