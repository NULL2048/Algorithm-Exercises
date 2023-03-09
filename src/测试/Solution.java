package 测试;

import java.util.*;

class Solution {
    public static void wiggleSort(int[] nums) {
        Arrays.sort(nums);

        int mid = nums.length >> 1;
        if ((nums.length & 1) == 1) {
            reverse(nums, 0, mid);
            reverse(nums, mid + 1, nums.length - 1);
        } else {
            reverse(nums, 0, mid - 1);
            reverse(nums, mid, nums.length - 1);
        }


        int[] a = null;
        if ((nums.length & 1) == 1) {
            a = new int[mid + 1];
        } else {
            a = new int[mid];
        }
        int[] b = new int[mid];


        for (int i = 0; i < a.length; i++) {
            a[i] = nums[i];
        }
        for (int i = 0; i < b.length; i++) {
            b[i] = nums[i + mid];
        }

        int index = 0;
        int i = 0;
        while (index < nums.length) {
            if (i < a.length) {
                nums[index++] = a[i];
            }

            if (i < b.length) {
                nums[index++] = b[i++];
            }
        }
    }

    public static void reverse(int[] nums, int l, int r) {
        while (l < r) {
            swap(nums, l++, r--);
        }
    }

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[][] grid = {{9,-8,1,3,-2},{-3,7,6,-2,4},{6,-4,-4,8,-7}};

        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {1,1,2,1,2,2,1};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        wiggleSort(nums2);
        for (int i = 0; i < nums2.length; i++) {
            System.out.print(nums2[i] + ' ');
        }
        //System.out.println(getMaxMatrix(grid));


//        String ans = minWindow(nums, n);
//        for (Integer a : ans) {
//            System.out.print(a + " ");
//        }

    }
}