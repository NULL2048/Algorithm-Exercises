package 大厂刷题班.class33;

import java.util.HashSet;

// 数组   哈希表
// https://leetcode.cn/problems/contains-duplicate/
public class Problem_0217_ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        int n = nums.length;
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (set.contains(nums[i])) {
                return true;
            }

            set.add(nums[i]);
        }
        return false;
    }
}
