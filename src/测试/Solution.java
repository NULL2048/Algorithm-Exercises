package 测试;

import java.util.*;

class Solution {
    public static int minCut(String str) {
        char[] s = str.toCharArray();

        return process(s, 0);
    }

    public static int process(char[] s, int index) {
        if (index == s.length) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int i = index; i < s.length; i++) {
            if (ispalindrome(s, index, i)) {
                min = Math.min(min, process(s, i + 1));
            }
        }
        return min + 1;
    }

    public static boolean ispalindrome(char[] s, int l, int r) {
        while (l < r) {
            if (s[l++] != s[r--]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] grid = {{1,2,7},{3,6,7}};

        int[] nums = {2,3,6,7};
        int[] nums2 = {2,2,2,2,2};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "aab";

        System.out.println(minCut(str2));

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