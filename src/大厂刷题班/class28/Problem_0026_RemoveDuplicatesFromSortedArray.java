package 大厂刷题班.class28;
// 双指针
// https://leetcode.cn/problems/remove-duplicates-from-sorted-array/
public class Problem_0026_RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 指向有效区的边界
        int fill = 1;
        for (int cur = 1; cur < nums.length; cur++) {
            // 如果当前遍历到的数不等于有效区边界的数，就将nums[cur]加入到有效区，有效区扩充
            if (nums[cur] != nums[fill - 1]) {
                nums[fill++] = nums[cur];
            }
        }

        // fill就是有效区的长度
        return fill;
    }
}
