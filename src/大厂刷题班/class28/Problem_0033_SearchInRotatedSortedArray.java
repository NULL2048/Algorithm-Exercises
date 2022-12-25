package 大厂刷题班.class28;
// 二分
// https://leetcode.cn/problems/search-in-rotated-sorted-array/
public class Problem_0033_SearchInRotatedSortedArray {
    // arr，原本是有序数组，旋转过，而且左部分长度不知道（这道题规定了只会旋转一次）
    // 找num
    // num所在的位置返回
    public int search(int[] nums, int target) {
        // 设置左右边界指针
        int l = 0;
        int r = nums.length - 1;

        // 开始二分
        while (l <= r) {
            // 计算中间下标
            int m = (l + r) >> 1;

            // 如果中间值正好等于target，直接返回下标m
            if (nums[m] == target) {
                return m;
            }

            // 如果三个下标位置的数相等，并且他们又不等于target（[L] == [M] == [R] != target），这种情况是没有办法二分的，因为无法确定断电在左侧还是在右侧
            if (nums[l] == nums[m] && nums[m] == nums[r]) {
                // 这种情况下让L往下走，L++，直到遇到一个不是num[M]的位置，在当前L...R上继续二分
                while (l != m && nums[l] == nums[m]) {
                    l++;
                }

                // 执行到这里，有两种情况：
                // 1) L == M L...M都一路都相等
                // 2) 从L到M终于找到了一个不等的位置
                if (l == m) { // L...M 一路都相等
                    // L一直往右，到M了，一路都是num[M]，那么在M+1到R上二分
                    l = m + 1;
                    continue;
                }
            }

            // 执行到这里，arr[M]一定是 != num的
            // [L] [M] [R] 不都一样的情况, 如何二分的逻辑

            // [L] != [M]
            if (nums[l] != nums[m]) {
                // 如果[L] < [M]，说明左侧是有序的，那么断点应该在右侧
                if (nums[l] < nums[m]) {
                    // 如果target的范围就在左侧有序的范围内，那么我们就对左侧进行二分
                    if (target >= nums[l] && target < nums[m]) {
                        r = m - 1;
                        // 否则，target就应该在无序的右侧，我们就去右侧进行二分
                    } else {
                        l = m + 1;
                    }
                    // 这个情况就是右侧是有序的，断点在左侧
                } else {
                    // 如果target的范围就在右侧有序的范围内，那么我们就对右侧进行二分
                    if (target > nums[m] && target <= nums[r]) {
                        l = m + 1;
                        // 否则，target就应该在无序的左侧，我们就去左侧进行二分
                    } else {
                        r = m - 1;
                    }
                }
                // [M] != [R]
            } else {
                // 如果[M] < [R]，说明右侧是有序的，那么断点应该在左侧
                if (nums[m] < nums[r]) {
                    if (target > nums[m] && target <= nums[r]) {
                        l = m + 1;
                    } else {
                        r = m - 1;
                    }
                    // 这个情况就是左侧是有序的，断点在右侧
                } else {
                    if (target >= nums[l] && target < nums[m]) {
                        r = m - 1;
                    } else {
                        l = m + 1;
                    }
                }
            }
        }

        // 如果整个二分过程没有找到target，就说明数组中没有target，返回-1
        return -1;
    }
}
