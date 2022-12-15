package 大厂刷题班.class27;

import java.util.HashMap;
// 数组
// https://leetcode.cn/problems/two-sum/
public class Problem_0001_TwoSum {
    public int[] twoSum(int[] nums, int target) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return null;
        }
        // key 某个之前的数   value 这个数出现的位置
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]  {map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }

        return null;
    }
}
