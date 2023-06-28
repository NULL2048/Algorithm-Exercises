package 测试;

import java.util.*;

class Solution {
        public static int lengthOfLIS(int[] nums) {
            int[] dp = new int[nums.length];
            dp[0] = 1;

            for (int i = 1; i < nums.length; i++) {
                int max = -1;
                for (int j = 0; j < i; j++) {
                    if (nums[i] > nums[j]) {
                        max = Math.max(max, dp[j]);
                    }
                }

                dp[i] = max != -1 ? max + 1 : 1;
            }


            return dp[nums.length - 1];
        }


        // public int process(int[] nums, int index) {
        //     if (index == 0) {
        //         return 1;
        //     }

        //     int p1 =
        // }

        // public int process(int[] nums, int index, int pre, HashMap<Integer, HashMap<Integer, Integer>> dp) {
        //     if (index == nums.length) {
        //         return 0;
        //     }

        //     if (dp.containsKey(index) && dp.get(index).containsKey(pre)) {
        //         return dp.get(index).get(pre);
        //     }

        //     int p1 = pre < nums[index] ? process(nums, index + 1, nums[index], dp) + 1 : 0;
        //     int p2 = process(nums, index + 1, pre, dp);

        //     if (!dp.containsKey(index)) {
        //         dp.put(index, new HashMap<Integer, Integer>());
        //     }

        //     dp.get(index).put(pre, Math.max(p1, p2));
        //     return Math.max(p1, p2);
        // }

    public static void main(String[] args) {
        int[][] grid = {{1,2,7},{3,6,7}};

        int[] nums = {2,3,6,7};
        int[] nums2 = {1,3,6,7,9,4,10,5,6};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        System.out.println(lengthOfLIS(nums2));

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