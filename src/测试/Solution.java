package 测试;

import java.util.*;

class Solution {
    public static int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] lens = new int[n];
        int[] cnts = new int[n];
        lens[0] = 1;
        cnts[0] = 1;
        int allCnt = 1;
        int maxLen = 1;

        for (int i = 1; i < n; i++) {
            int preLen = 0;
            //
            int preCnt = 0;

            for (int j = 0; j < i; j++) {
                //
                if (nums[j] > nums[i] || lens[j] < preLen) {
                    continue;
                }

                if (lens[j] > preLen) {
                    preLen = lens[j];
                    preCnt = cnts[j];
                } else {
                    preCnt += cnts[j];
                }
            }

            lens[i] = preLen + 1;
            cnts[i] = preCnt;

            if (lens[i] > maxLen) {
                maxLen = lens[i];
                allCnt = cnts[i];
            } else if (lens[i] == maxLen) {
                allCnt += cnts[i];
            }
        }

        return allCnt;
    }

    public static void main(String[] args) {
        int[][] grid = {{1,2,7},{3,6,7}};

        int[] nums = {2,3,6,7};
        int[] nums2 = {2,2,2,2,2};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        System.out.println(findNumberOfLIS(nums2));

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