package 大厂刷题班.class35;
import java.util.HashMap;
// 数组   哈希表
// https://leetcode.cn/problems/4sum-ii/
public class Problem_0454_4SumII {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> map = new HashMap<>();

        // 找出nums1+nums2的所有组合，将加和数据和组合数存入到map中
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                int sum = nums1[i] + nums2[j];
                if (map.containsKey(sum)) {
                    map.put(sum, map.get(sum) + 1);
                } else {
                    map.put(sum, 1);
                }
            }
        }

        // 再去找nums3+nums4的所有组合，看能不能和nums1+nums2的组合中凑出来相加为0的情况，统计组合数量
        int ans = 0;
        for (int i = 0; i < nums3.length; i++) {
            for (int j = 0; j < nums4.length; j++) {
                int sum = nums3[i] + nums4[j];
                if (map.containsKey(-sum)) {
                    ans += map.get(-sum);
                }
            }
        }
        return ans;
    }
}
