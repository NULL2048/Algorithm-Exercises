package 大厂刷题班.class32;
// 子数组（一般就是以每个位置为结尾来尝试讨论）   从左往右的尝试模型   空间压缩技巧
// https://leetcode.cn/problems/maximum-product-subarray/
public class Problem_0152_MaximumProductSubarray {
    // int版本  进行了空间压缩  可以通过力扣提交
    public int maxProduct(int[] nums) {
        // 上一个位置的乘积最小值
        int preMax = nums[0];
        // 上一个位置的乘积最小值
        int preMin = nums[0];
        // 当前找到的乘积最大值
        int max = nums[0];
        // 当前位置的乘积最大值
        int curMax = 0;
        // 当前位置的乘积最小值
        int curMin = 0;
        // 从左往右构造dp，求以i位置为结尾的子数组的最大累乘积
        for (int i = 1; i < nums.length; i++) {
            // 求最大值只有三种情况
            // 1) i位置的数自己
            // 2) i位置的数乘以i - 1位置的数的时候得到的最大累乘积
            // 3) i位置的数乘以i - 1位置的数的时候得到的最小累乘积（因为有可能i位置是负数，然后再乘以前面计算结果最小值的负数就可以得到一个最大正数解）
            // 计算得到当前位置的乘积最大值
            curMax = Math.max(nums[i], Math.max(nums[i] * preMax, nums[i] * preMin));
            // 计算得到当前位置的乘积最小值
            curMin = Math.min(nums[i], Math.min(nums[i] * preMax, nums[i] * preMin));
            // 找所有情况的最大值
            max = Math.max(max, curMax);
            // 将当前位置的信息赋值给pre，在下一轮计算的时候用
            preMax = curMax;
            preMin = curMin;
        }
        // 返回所有情况的最大值
        return max;
    }


    // double版本
    public static double max(double[] arr) {
        if(arr == null || arr.length == 0) {
            return 0; // 报错！
        }
        int n = arr.length;
        // 上一步的最大
        double premax = arr[0];
        // 上一步的最小
        double premin = arr[0];
        double ans = arr[0];
        for(int i = 1; i < n; i++) {
            double p1 = arr[i];
            double p2 = arr[i] * premax;
            double p3 = arr[i] * premin;
            double curmax = Math.max(Math.max(p1, p2), p3);
            double curmin = Math.min(Math.min(p1, p2), p3);
            ans = Math.max(ans, curmax);
            premax = curmax;
            premin = curmin;
        }
        return ans;
    }
}
