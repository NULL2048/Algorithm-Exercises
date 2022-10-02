package 测试;

import java.util.HashMap;
class Solution {
    // public int findTargetSumWays(int[] nums, int target) {
    //     return process(nums, 0, target);
    // }

    // public int process(int[] nums, int index, int rest) {
    //     if (index == nums.length) {
    //         return rest == 0 ? 1 : 0;
    //     }

    //     return process(nums, index + 1, rest + nums[index]) + process(nums, index + 1, rest - nums[index]);
    // }

    // public int findTargetSumWays(int[] nums, int target) {
    //     // 因为可变参数有可能为负数，我们这里就不用二维数组了，直接用map
    //     HashMap<Integer, HashMap<Integer, Integer>> dp = new HashMap<>();
    //     return process(nums, 0, target, dp);
    // }

    // public int process(int[] nums, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
    //     if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
    //         return dp.get(index).get(rest);
    //     }

    //     if (index == nums.length) {
    //         return rest == 0 ? 1 : 0;
    //     }

    //     int ans = process(nums, index + 1, rest + nums[index], dp) + process(nums, index + 1, rest - nums[index], dp);
    //     if (!dp.containsKey(index)) {
    //         dp.put(index, new HashMap<>());
    //     }
    //     dp.get(index).put(rest, ans);
    //     return ans;
    // }





    public static int findTargetSumWays(int[] nums, int target) {
        int n = nums.length;
        int sum = 0;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
            sum += arr[i];
        }

        if (Math.abs(target) > sum || ((target & 1) ^ (sum & 1)) != 0) {
            return 0;
        }

        int[][] dp = new int[n + 1][(sum << 1) + 2];

        dp[n][0] = 1;

        for (int index = n - 1; index >= 0; index--) {
            for (int rest = -sum; rest <= sum; rest++) {
                int ans = 0;
                if (rest + arr[index] <= sum && rest + arr[index] >= -sum) {
                    ans = dp[index + 1][(rest + arr[index]) <= 0 ? -(rest + arr[index]) : (((rest + arr[index]) << 1) + 1)];
                }

                if (rest - arr[index] <= sum  && rest - arr[index] >= -sum) {
                    ans += dp[index + 1][(rest - arr[index]) <= 0 ? -(rest - arr[index]) : (((rest - arr[index]) << 1) + 1)];
                }
                dp[index][(rest <= 0 ? -rest : ((rest << 1)) + 1)] = ans;
            }
        }

        return dp[0][(target <= 0 ? -target : ((target << 1) + 1))];
    }

    public static void main(String[] args) {
        int[] nums = {1,2,1};
        System.out.println(findTargetSumWays(nums, 0));
    }


}