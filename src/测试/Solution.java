package 测试;

import java.util.*;

class Solution {
    public static int search(int[] nums, int target) {
        // 设置左右边界指针
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int mid = (l + r) >> 1;

            if (nums[mid] == target) {
                return mid;
            }


            if (nums[l] < nums[mid]) {
                if (target < nums[mid] && target >= nums[l]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else if (nums[r] > nums[mid]) {
                if (target > nums[mid] && target <= nums[r]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            } else if (l == r) {
                break;
            }


        }


        // 如果整个二分过程没有找到target，就说明数组中没有target，返回-1
        return -1;
    }

    public static void main(String[] args) {
        int[][] grid = {{1,2,7},{3,6,7}};

        int[] nums = {2,3,6,7};
        int[] nums2 = {4,5,6,7,0,1,2};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        System.out.println(search(nums2, 3));

//        for (int i = 0; i < nums2.length; i++) {
//            System.out.print(nums2[i] + ' ');
//        }
        //System.out.println(getMaxMatrix(grid));


//        String ans = minWindow(nums, n);
//        for (Integer a : ans) {
//            System.out.print(a + " ");
//        }

    }
}