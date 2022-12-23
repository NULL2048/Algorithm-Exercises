package 大厂刷题班.class28;
// 二分
// https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/
public class Problem_0034_FindFirstAndLastPositionOfElementInSortedArray {
    public int[] searchRange(int[] nums, int target) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return new int[] { -1, -1 };
        }

        // 找数组中小于target的最右下标位置，然后再加1
        int L = lessMostRight(nums, target) + 1;

        // 如果L没越界并且L位置的数等于target，就说明找到了小于target的最右边的数，并且数组中存在target
        // 否则数组中就没有target，直接返回（-1，-1）
        if (L == nums.length || nums[L] != target) {
            return new int[] { -1, -1 };
        }

        // 找数组中小于target + 1的最右位置
        int R = lessMostRight(nums, target + 1);
        // 返回数组中等于target的区间范围
        return new int[] {L, R};
    }

    // 二分，利用二分在数组arr中找到小于num的最右位置的数
    public int lessMostRight(int[] arr, int num) {
        int l = 0;
        int r = arr.length - 1;
        // 记录下标答案
        int ans = -1;
        // 二分到l和r错开结束
        while (l <= r) {
            // 取中间位置
            int mid = (l + r) >> 1;

            // 如果此时arr[mid]大于等于mid，说明还没找到小于num的数，我们再去二分到左部分去判断，看能否找到小于num的数
            if (num <= arr[mid]) {
                r = mid - 1;
                // 找到了一个下标位置的数小于num，就记录下这个下标为答案，看后面还能不能向右推高这个答案
            } else if (num > arr[mid]) {
                // 继续二分右部分，看后面能否推高小于num的最右下标答案
                l = mid + 1;
                ans = mid;
            }
        }
        return ans;
    }
}
