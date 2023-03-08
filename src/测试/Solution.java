package 测试;

import java.util.*;

class Solution {
    public static int[] getMaxMatrix(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        int[] ans  = new int[4];

        for (int k = 0; k < matrix.length; k++) {
            int[] arr = new int[matrix[0].length];

            for (int i = k; i < matrix.length; i++) {
                int[] dp = new int[matrix[0].length];
                int start = 0;
                arr[0] += matrix[i][0];
                dp[0] = arr[0];

                if (dp[0] > max) {
                    max = dp[0];
                    ans[0] = k;
                    ans[1] = 0;
                    ans[2] = i;
                    ans[3] = 0;
                }

                for (int j = 1; j < matrix[0].length; j++) {
                    arr[j] += matrix[i][j];

                    dp[j] = dp[j - 1] > 0 ? dp[j - 1] + arr[j] : arr[j];
                    if (dp[j - 1] <= 0) {
                        start = j;
                    }

                    if (dp[0] > max) {
                        max = dp[0];
                        ans[0] = k;
                        ans[1] = start;
                        ans[2] = i;
                        ans[3] = j;
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        int[][] grid = {{9,-8,1,3,-2},{-3,7,6,-2,4},{6,-4,-4,8,-7}};

        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {3,4,5,1,2};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        int[] ans = getMaxMatrix(grid);
        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + ' ');
        }
        //System.out.println(getMaxMatrix(grid));


//        String ans = minWindow(nums, n);
//        for (Integer a : ans) {
//            System.out.print(a + " ");
//        }

    }
}