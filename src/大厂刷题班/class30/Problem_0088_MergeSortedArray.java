package 大厂刷题班.class30;
// 双指针   数组
// https://leetcode.cn/problems/merge-sorted-array/
public class Problem_0088_MergeSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 从后往前填（先填大的，再填小的），为了避免数组还需要向后移动。
        int index1 = m - 1;
        int index2 = n - 1;
        int index = m + n - 1;

        // 整体的思路和合并两个有序链表是完全一样的
        // 其实就是依次找到两个数组中还没有放置好的数中最大的数，然后放置到nums1尾部对应的位置。利用两个数组的有序性去做。
        while (index1 >= 0 && index2 >= 0) {
            if (nums1[index1] >= nums2[index2]) {
                nums1[index--] = nums1[index1--];
            } else {
                nums1[index--] = nums2[index2--];
            }
        }

        while (index1 >= 0) {
            nums1[index--] = nums1[index1--];
        }

        while (index2 >= 0) {
            nums1[index--] = nums2[index2--];
        }
    }
}
