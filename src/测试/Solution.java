package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class Solution {
    public static String minWindow(String sStr, String tStr) {
        if (sStr == null || sStr.length() == 0 || tStr == null || tStr.length() == 0) {
            return "";
        }

        char[] s = sStr.toCharArray();
        char[] t = tStr.toCharArray();

        int[] map = new int['z' + 1];
        int all = 0;
        for (int i = 0; i < t.length; i++) {
            map[t[i]]++;
            all++;
        }

        int r = 0;
        int ansL = -1;
        int ansR = -1;
        int minLen = Integer.MAX_VALUE;

        map[s[0]]--;
        if (map[s[0]] >= 0) {
            all--;
        }
        for (int l = 0; l < s.length; l++) {
            while (l <= r && r < s.length) {
                if (all == 0) {
                    if (minLen > r - l + 1) {
                        ansL = l;
                        ansR = r;
                        minLen = ansR - ansL + 1;
                        break;
                    }
                }
                r++;
                if (r < s.length) {
                    map[s[r]]--;
                    if (map[s[r]] >= 0) {
                        all--;
                    }
                }

            }

            map[s[l]]++;
            if (map[s[l]] > 0) {
                all++;
            }

//            if (all == 0) {
//                if (minLen > r - l + 1) {
//                    ansL = l;
//                    ansR = r;
//                    minLen = ansR - ansL + 1;
//
//                    map[s[l]]++;
//                    if (map[s[l]] > 0) {
//                        all++;
//                    }
//                    continue;
//                }
//            }

        }

        return minLen == -1 ? "" : sStr.substring(ansL, ansR + 1);
    }

    public static void main(String[] args) {

        int[][] grid = {{1, 1, -1}, {1, -1, 1}, {-1, 1, 1}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {1,2,3,6};
        int n = 3;

//        Arrays.sort(nums);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        //System.out.println(nums);

//        "ADOBECODEBANC"
//        "ABC"
        String str1 = "ADOBECODEBANC";
        String str2 = "ABC";
        System.out.println(minWindow(str1, str2));

//        int[] ans = maxSumOfThreeSubarrays(nums, n);
//        for (int a : ans) {
//            System.out.print(a + " ");
//        }
    }
}