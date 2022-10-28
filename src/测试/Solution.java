package 测试;

import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return false;
        }

        char[] str1 = s2.toCharArray();
        char[] str2 = s1.toCharArray();
        int n = str1.length;
        int m = str2.length;
        int[] count = new int[256];
        int all = m;
        for (int i = 0; i < m; i++) {
            count[str2[i]]++;
            all++;
        }


        int l = 0;
        int r = 0;
        for (; r < m; r++) {
            if (count[str1[r]]-- > 0) {
                all--;
            }
        }


        for (; r < n; l++, r++) {
            if (all == 0) {
                return true;
            }

            if (count[str1[l]]++ >= 0) {
                all++;
            }

            if (count[str1[r]]-- > 0) {
                all--;
            }
        }



        return all == 0 ? true : false;
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1,1},{1,0,1},{1,1,1}};
        int[] nums = {7,-9,15,-2};

        String str1 = "ab";
        String str2 = "eidbaooo";
        System.out.println(checkInclusion(str1, str2));
    }


}