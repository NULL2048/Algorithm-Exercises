package 大厂刷题班.class38;

import java.util.HashMap;

// 数组  前缀和
// https://leetcode.cn/problems/subarray-sum-equals-k/
public class Problem_0560_SubarraySumEqualsK {
    public int subarraySum(int[] nums, int k) {
        // 前缀和记录表
        // key：前缀和大小
        // value：此时已经找到的前缀和为key的前缀子数组有多少个，因为数组是存在负数的，所以有可能出现同一个前缀和，有多个不同前缀的情况
        HashMap<Integer, Integer> map = new HashMap<>();
        // 统计累加和为k的子数组个数
        int ans = 0;
        // 所有利用前缀和定律来求子结构累加和相关的问题，都需要在一开始向map中插入前缀和为0的情况，这是因为有可能存在一个前缀0~x的前缀和正好是k，那么这种情况如果不在map中存储前缀为0的，就可能少统计了0~x这种情况
        map.put(0, 1);
        // 计算当前0~i的前缀和
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            // 计算0~i的前缀和
            sum += nums[i];

            // 查找当前已经找到的前缀中，是否存在前缀和为sum-k的
            if (map.containsKey(sum - k)) {
                // 如果存在sum-k的前缀，也就相当于我们找到了累加和为k的以i结尾的子数组了
                // 将答案个数加上前缀和为sum-k的前缀的个数
                ans += map.get(sum - k);
            }

            // 将当前0~i的累加和sum信息加入到map中
            if (map.containsKey(sum)) {
                // 如果之前已经存在了sum的前缀和，就将个数加1
                map.put(sum, map.get(sum) + 1);
            } else {
                // 第一次找到前缀和为sum的前缀
                map.put(sum, 1);
            }

        }
        // 返回答案
        return ans;
    }
}
