package 大厂刷题班.class33;
// 双指针
// https://leetcode.cn/problems/move-zeroes/
public class Problem_0283_MoveZeroes {
    public void moveZeroes(int[] nums) {
        int n = nums.length;
        // l位置及其左侧的都是非0的数，l右侧的都是0
        int l = -1;
        // 从左往右遍历，将非0都加到++l位置，这样也能保证非0数的相对次序不变
        for (int cur = 0; cur < n; cur++) {
            if (nums[cur] != 0) {
                swap(nums, cur, ++l);
            }
        }
    }
    // 交换
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
