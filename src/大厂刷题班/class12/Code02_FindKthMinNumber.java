package 大厂刷题班.class12;
// 二分  递归
// 本题测试链接 : https://leetcode.cn/problems/median-of-two-sorted-arrays/
public class Code02_FindKthMinNumber {
    public  double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // 记录两个数组的总长度
        int size = nums1.length + nums2.length;
        // 标记总长度是奇数还是偶数
        boolean isEven = (size & 1) == 1 ? false : true;

        // 如果两个数组都不为空
        if (nums1 != null && nums1.length > 0 && nums2 != null && nums2.length > 0) {
            // 总长度的中间位置
            int mid = size / 2;
            // 如果总长度是偶数，那么要返回的中位数就是上中位数和下中位数相加除以2（两个数组排序合并之后的上中位数和下中位数）
            if (isEven) {
                // 求第mid小和第mid+1小这两个数，然后相加除以2
                return (findKthNum(nums1, nums2, mid) + findKthNum(nums1, nums2, mid + 1)) / 2.0;
                // 如果总长度是奇数，那么要返回的中位数就是第mid个数
            } else {
                // 求第mid+1小这个数
                return findKthNum(nums1, nums2, mid + 1);
            }
            // 只有nums2不为空
        } else if (nums1 == null || nums1.length == 0) {
            // 如果nums2长度为偶数
            if (isEven) {
                // 因为nums2本身就是有序的，直接求其中位数即可
                // 注意位移运算符的优先级比较弱，所以它的计算要括上括号
                int mid = (nums2.length >> 1) - 1;
                return (nums2[mid] + nums2[mid + 1]) / 2.0;
                // 如果nums2长度为奇数
            } else {
                // 可直接算中位数
                int mid = nums2.length >> 1;
                return nums2[mid];
            }
            // 只有nums1不为空   同上一个分支
        } else if (nums2 == null || nums2.length == 0) {
            if (isEven) {
                int mid = (nums1.length >> 1) - 1;
                return (nums1[mid] + nums1[mid + 1]) / 2.0;
            } else {
                int mid = nums1.length >> 1;
                return nums1[mid];
            }
            // 这个分支其实没用，只是为了有个返回值而已，避免报错
        } else {
            return 0;
        }
    }

    // 进阶问题 : 在两个都有序的数组中，找整体第K小的数，两个数组不等长
    // 可以做到O(log(Min(M,N)))
    // 注意这里传入的kth是第kth小，并不是下标
    // 整个代码的思路就是笔记上的思路复现
    public  int findKthNum(int[] nums1, int[] nums2, int kth) {
        // 标记处短数组和长数组   注意这里要有一个写上等号，保证shorts和longs数组一定指向的是不同的数组
        int[] shorts = nums1.length <= nums2.length ? nums1 : nums2;
        int[] longs = nums1.length > nums2.length ? nums1 : nums2;
        int shortsLen = shorts.length;
        int longsLen = longs.length;

        // 如果kth小于shortsLen
        if (kth <= shortsLen) {
            // 那么在长数组中切除和短数组等长的前缀树组，两个等长数组求上中位数，就是答案。
            // 传入的两个数组长度相等，直接调用算法原型
            return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
            // 如果kth大于shortsLen小于等于longsLen
        } else if (kth > shortsLen && kth <= longsLen) {
            // 先找到要排除长数组哪些位置的数
            int preIndex = kth - shortsLen - 1;
            int posIndex = kth - 1;
            // 单独判断longs[preIndex]和shorts[shortsLen - 1]
            if (longs[preIndex] >= shorts[shortsLen - 1]) {
                return longs[preIndex];
                // 如果不满足上面的条件，就说明longs[preIndex]不可能是第kth小的数，直接抛弃，那么传入的两个数组长度就又相等了，直接调用算法原型
            } else {
                return getUpMedian(shorts, 0, shortsLen - 1, longs, preIndex + 1, posIndex);
            }
            // 如果kth大于longsLen
        } else {
            // 先找到段数组和长数组要排除的位置
            int longIndex = kth - shortsLen - 1;
            int shortIndex = kth - longsLen - 1;

            // 单独判断longs[longIndex]和shorts[shortsLen - 1]
            if (longs[longIndex] >= shorts[shortsLen - 1]) {
                return longs[longIndex];
                // 单独判断shorts[shortIndex]和longs[longsLen - 1]
            } else if (shorts[shortIndex] >= longs[longsLen - 1]) {
                return shorts[shortIndex];
            }
            // 舍弃掉一个不可能的数之后，传入的两个数组长度就相等了，调算法原型
            return getUpMedian(shorts, shortIndex + 1, shortsLen - 1, longs, longIndex + 1, longsLen - 1);
        }
    }

    // 第一问：在两个等长都有序的数组中找上中位数。这个方法是算法原型，整个大问题都是基于这个方法来求解
    // A[s1...e1]
    // B[s2...e2]
    // 一定等长！
    // 返回整体的，上中位数！8（4） 10（5） 12（6）
    public int getUpMedian(int[] nums1, int s1, int e1, int[] nums2, int s2, int e2) {
        // 整个代码很好理解，就是我们讲的算法流程。可以在纸上列一个具体的例子，就很容易写出这个代码
        while (s1 < e1) {
            int mid1 = (s1 + e1) >> 1;
            int mid2 = (s2 + e2) >> 1;

            if (nums1[mid1] == nums2[mid2]) {
                return nums1[mid1];
            }
            // 执行到这里说明两个中点一定不等！
            // 奇数长度
            if ((e1 - s1 + 1) % 2 == 1) {
                if (nums1[mid1] > nums2[mid2]) {
                    if (nums2[mid2] >= nums1[mid1 - 1]) {
                        return nums2[mid2];
                    }
                    e1 = mid1 - 1;
                    s2 = mid2 + 1;
                } else {
                    if (nums2[mid2 - 1] <= nums1[mid1]) {
                        return nums1[mid1];
                    }
                    e2 = mid2 - 1;
                    s1 = mid1 + 1;
                }
                // 偶数长度
            } else {
                if (nums1[mid1] > nums2[mid2]) {
                    e1 = mid1;
                    s2 = mid2 + 1;
                } else {
                    e2 = mid2;
                    s1 = mid1 + 1;
                }
            }
        }
        // 跳出上面循环后，只剩下两个数了，小的那个数就是上中位数。我感觉上面这个过程就和二分有点类似
        return Math.min(nums1[s1], nums2[s2]);
    }
}
