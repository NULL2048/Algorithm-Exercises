package 测试;

import java.util.*;

class Solution {
    public static String minWindow(String sStr, String tStr) {
        if (sStr.length() < tStr.length()) {
            return "";
        }
        char[] s = sStr.toCharArray();
        char[] t = tStr.toCharArray();

        int l = 0;
        int r = 0;
        int all = 0;
        int[] count = new int[256];

        for (int i = 0; i < t.length; i++) {
            count[t[i]]++;
            all++;
        }

        int[] ans = new int[2];
        int minLen = Integer.MAX_VALUE;
        while (l <= r && r < s.length) {
            if (count[s[r++]]-- > 0) {
                all--;
            }

            if (all == 0) {
                if (r - l < minLen) {
                    ans[0] = l;
                    ans[1] = r;
                    minLen = r - l;
                }


                while (all != 2 && l <= r) {
                    if (count[s[l++]]++ >= 0) {
                        all++;
                    }
                }
                l--;
                all--;
                count[s[l]]++;
            }
        }

        return sStr.substring(ans[0], ans[1]);
    }

    public static void main(String[] args) {
        int[][] grid = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};

        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {3,4,5,1,2};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "ADOBECODEBANC";
        String str2 = "ABC";

        System.out.println(minWindow(str1, str2));


//        String ans = minWindow(nums, n);
//        for (Integer a : ans) {
//            System.out.print(a + " ");
//        }

    }
}