package 测试;

import java.util.*;

class Solution {
    public static int lengthOfLongestSubstring(String str) {
        if (str.length() <= 1) {
            return str.length();
        }

        char[] s = str.toCharArray();
        int l = 0;
        int r = 0;
        int[] count = new int[256];
        int max = 0;
        while (r < s.length) {
            if (count[r] == 0) {
                count[r++]++;

                max = Math.max(max, r - l);
            } else {
                count[l--]--;
            }
        }

        return max;
    }

    public static void main(String[] args) {
        int[][] grid = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};

        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {3,4,5,1,2};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        System.out.println(lengthOfLongestSubstring(str1));


//        String ans = minWindow(nums, n);
//        for (Integer a : ans) {
//            System.out.print(a + " ");
//        }

    }
}