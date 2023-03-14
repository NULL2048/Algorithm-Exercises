package 大厂刷题班.class34;

import java.util.Arrays;

// 数组 哈希表  计数
// https://leetcode.cn/problems/intersection-of-two-arrays-ii/
public class Problem_0350_IntersectionOfTwoArraysii {
    public int[] intersect(int[] nums1, int[] nums2) {
        // 词频统计表
        int[] map = new int[1001];
        // 记录要返回的答案
        int[] ans = new int[1000];
        int index = 0;

        for (int i = 0; i < nums1.length; i++) {
            map[nums1[i]]++;
        }

        for (int i = 0; i < nums2.length; i++) {
            map[nums2[i]]--;
            // 保证只有当此时频次大于等于0才将答案写入ans，这样就可以实现如果交集的个数不一样，取较小的那一个词频
            if (map[nums2[i]] >= 0) {
                ans[index++] = nums2[i];
            }
        }
        // 截断数组
        return Arrays.copyOf(ans, index);
    }
}
