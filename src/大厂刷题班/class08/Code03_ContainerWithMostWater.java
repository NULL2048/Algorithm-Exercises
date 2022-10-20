package 大厂刷题班.class08;

// 这个题先去看数据量，发现n是10^5，这就要求我们至少要找到一个O(N*logN)的解法，能找到O(N)最好，如果是O(N^2)肯定就超时了
// 数组三连的第三连，这种类型的题目是比较难的  双指针  贪心
// 本题测试链接 : https://leetcode.cn/problems/container-with-most-water/
public class Code03_ContainerWithMostWater {
    // 1、暴力解O(N^2)
    public static int maxArea1(int[] h) {
        int max = 0;
        int N = h.length;
        for (int i = 0; i < N; i++) { // h[i]
            for (int j = i + 1; j < N; j++) { // h[j]
                max = Math.max(max, Math.min(h[i], h[j]) * (j - i));
            }
        }
        return max;
    }

    // 正确的解 O(N)
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int l = 0;
        int r = height.length - 1;
        int max = 0;
        while (l < r) {
            // 每次计算当前窗口上能后装下的水是否能超过最大值，能超过就更新max
            max = Math.max(max, Math.min(height[l], height[r]) * (r - l));
            // 向中间移动高度较小的那个窗口，这样才有推高最大值的可能性。优先保留高度较高的板子
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }

        return max;
    }
}
