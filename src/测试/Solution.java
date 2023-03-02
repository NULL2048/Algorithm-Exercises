package 测试;

import java.util.*;

class Solution {
    public static int longestSubstring(String str, int k) {
        char[] s = str.toCharArray();
        int n = s.length;

        int max = 0;
        for (int kinds = 1; kinds <= 26; kinds++) {
            int[] count = new int['z' + 1];
            int nowKinds = 0;
            int satisfyKinds = 0;
            int r = 0;

            for (int l = 0; l < n; l++) {
                while (r < n) {
                    if (nowKinds < kinds || (nowKinds == kinds && count[s[r]] != 0)) {
                        if (count[s[r]] == 0) {
                            nowKinds++;
                        }
                        count[s[r]]++;
                        if (count[s[r]] == k) {
                            satisfyKinds++;
                        }
                        r++;
                    } else {
                        max = Math.max(max, r - l);
                        break;
                    }
                }
                if (count[s[l]] == k) {
                    satisfyKinds--;
                }
                if (count[s[l]] == 1) {
                    nowKinds--;
                }
                count[s[l]]--;
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



        String str1 = "ababacb";
        String str2 = "ABC";

        System.out.println(longestSubstring(str1, n));


//        String ans = minWindow(nums, n);
//        for (Integer a : ans) {
//            System.out.print(a + " ");
//        }

    }
}