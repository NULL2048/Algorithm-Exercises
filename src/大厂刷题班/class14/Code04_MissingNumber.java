package 大厂刷题班.class14;
// 字节面试  有点像快速排序的那种思路  双指针
// 测试链接：https://leetcode.cn/problems/first-missing-positive/
public class Code04_MissingNumber {
    public int firstMissingPositive(int[] nums) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return 1;
        }

        // l左边都是有效区的数。有效区的所有书都满足nums[i] = i + 1
        int l = 0;
        // r及其r右边的数都是垃圾区。永远不可能加入到有效区的数，就加入垃圾区
        int r = nums.length;
        while (l != r) {
            // 1、如果l位置的数等于l+1，说明这个数是有效区的数，加入有效区
            if (nums[l] == l + 1) {
                // 有效区右扩
                l++;
                // 2、如果l位置的数已经存在在有效区了（nums[l] <= l）  或者  l位置的数大于r   或者在nums[l] - 1已经存在符合有效区规则的nums[l]了，就将l位置的数加入到垃圾区
            } else if (nums[l] <= l || nums[l] > r || nums[nums[l] - 1] == nums[l]) {
                // 将l交换到垃圾区，并且将垃圾区左扩
                swap(nums, l, --r);
                // 如果nums[nums[l] - 1] != nums[l]，就将l和nums[l] - 1位置的数交换
            } else {
                swap(nums, l, nums[l] - 1);
            }
        }

        // r+1就是缺少的最小的正数
        return r + 1;
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
