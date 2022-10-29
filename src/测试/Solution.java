package 测试;

import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        boolean isEven = (size & 1) == 1 ? false : true;

        if (nums1 != null && nums1.length > 0 && nums2 != null && nums2.length > 0) {
            int mid = size / 2;
            if (isEven) {
                return (findKthNum(nums1, nums2, mid) + findKthNum(nums1, nums2, mid + 1)) / 2.0;
            } else {
                return findKthNum(nums1, nums2, mid + 1);
            }
        } else if (nums1 == null || nums1.length == 0) {
            if (isEven) {
                int mid = (nums2.length >> 1) - 1;
                return (nums2[mid] + nums2[mid + 1]) / 2.0;
            } else {
                int mid = nums2.length >> 1;
                return nums2[mid];
            }
        } else if (nums2 == null || nums2.length == 0) {
            if (isEven) {
                int mid = (nums1.length >> 1) - 1;
                return (nums1[mid] + nums1[mid + 1]) / 2.0;
            } else {
                int mid = nums1.length >> 1;
                return nums1[mid];
            }
        } else {
            return 0;
        }
    }

    public static int findKthNum(int[] nums1, int[] nums2, int kth) {
        int[] shorts = nums1.length <= nums2.length ? nums1 : nums2;
        int[] longs = nums1.length > nums2.length ? nums1 : nums2;
        int shortsLen = shorts.length;
        int longsLen = longs.length;

        if (kth <= shortsLen) {
            return getUpMedian(shorts, 0, kth - 1, longs, 0, kth - 1);
        } else if (kth > shortsLen && kth <= longsLen) {
            int preIndex = kth - shortsLen - 1;
            int posIndex = kth - 1;

            if (longs[preIndex] >= shorts[shortsLen - 1]) {
                return longs[preIndex];
            } else {
                return getUpMedian(shorts, 0, shortsLen - 1, longs, preIndex + 1, posIndex);
            }
            // kth > longsLen
        } else {
            int longIndex = kth - shortsLen - 1;
            int shortIndex = kth - longsLen - 1;

            if (longs[longIndex] >= shorts[shortsLen - 1]) {
                return longs[longIndex];
            } else if (shorts[shortIndex] >= longs[longsLen - 1]) {
                return shorts[shortIndex];
            }

            return getUpMedian(shorts, shortIndex + 1, shortsLen - 1, longs, longIndex + 1, longsLen - 1);
        }
    }

    public static int getUpMedian(int[] nums1, int s1, int e1, int[] nums2, int s2, int e2) {
        // int m = nums1.length;
        // int n = nums2.length;

        // int s1 = 0;
        // int e1 = m - 1;
        // int s2 = 0;
        // int e2 = n - 1;
        while (s1 < e1) {
            int mid1 = (s1 + e1) >> 1;
            int mid2 = (s2 + e2) >> 1;

            if (nums1[mid1] == nums2[mid2]) {
                return nums1[mid1];
            }

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
        return Math.min(nums1[s1], nums2[s2]);
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1,1},{1,0,1},{1,1,1}};
        int[] nums = {1,3,4};
        int[] nums2 = {2,5,6};

        String str1 = "ab";
        String str2 = "eidbaooo";
        System.out.println(findMedianSortedArrays(nums, nums2));
    }


}