package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static int numDistinct(String sStr, String tStr) {
        if (sStr == null || tStr == null || sStr.length() == 0 || tStr.length() == 0) {
            return 0;
        }

        char[] s = sStr.toCharArray();
        char[] t = tStr.toCharArray();
        int[][] dp = new int[s.length][t.length];

        dp[0][0] = s[0] == t[0] ? 1 : 0;
        for (int i = 1; i < s.length; i++) {
            dp[i][0] = s[i] == t[0] ? (dp[i - 1][0] + 1) : dp[i - 1][0];
        }

        for (int i = 1; i < s.length; i++) {
            for (int j = 1; j < Math.min(i, t.length); j++) {
                dp[i][j] = dp[i - 1][j];
                if (s[i] == t[j]) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[s.length - 1][t.length - 1];
    }


    public static void main(String[] args) {
        int[][] grid = {{1,1,3,8,13},{4,4,4,8,18},{9,14,18,19,20},{14,19,23,25,25},{18,21,26,28,29}};
        int[] nums = {1,2,31,33};
        int[] nums2 = {2,5,6};
        int n = 13;

        String str1 = "rabbbit";
        String str2 = "rabbit";
        System.out.println(numDistinct(str1, str2));
    }
}