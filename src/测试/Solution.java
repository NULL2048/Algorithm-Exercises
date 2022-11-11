package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static int kthSmallest(int[][] matrix, int k) {
        // 矩阵行数
        int n = matrix.length;
        // 矩阵列数
        int m = matrix[0].length;
        // 有序矩阵的最小值，即矩阵左上角的值
        int left = matrix[0][0];
        // 有序矩阵的最大值，即矩阵右下角的值
        int right = matrix[n - 1][m - 1];
        // 矩阵中第k小的数
        int ans = 0;
        // 记录小于等于mid这个数的信息，包含小于等于mid的数有多少个和小于等于mid并且最接近mid的数是什么
        Info equalOrLessMidInfo = null;
        // 利用二分法，找到小于等于mid的数正好有k个的信息
        while (left <= right) {
            // 二分
            int mid = left + ((right - left) >> 1);

            // 获取矩阵中小于等于mid的Info信息，包括小于等于mid的个数，和小于等于mid的数中最接近mid的数是什么
            equalOrLessMidInfo = getequalOrLessNumInfo(matrix, mid);
            // 如果小于等于mid的数少于k个，意味着我们还需要提高mid的值
            if (equalOrLessMidInfo.equalOrLessNumCnt < k) {
                // 取右半部分
                left = mid + 1;
                // 如果小于等于mid的数大于k个，意味着我们还需要减少mid的值
            } else if (equalOrLessMidInfo.equalOrLessNumCnt >= k) {
                ans = equalOrLessMidInfo.nearNum;
                right = mid - 1;
            }
        }

        return ans;
    }

    public static class Info {
        int nearNum;
        int equalOrLessNumCnt;

        public Info(int nearNum, int equalOrLessNumCnt) {
            this.nearNum = nearNum;
            this.equalOrLessNumCnt = equalOrLessNumCnt;
        }
    }

    public static Info getequalOrLessNumInfo(int[][] matrix, int num) {
        int row = 0;
        int col = matrix[0].length - 1;
        int cnt = 0;
        int ans = Integer.MIN_VALUE;

        while (row <= matrix[0].length - 1 && col >= 0) {
            // if (matrix[row][col] == num) {
            //     ans = matrix[row][col];
            //     cnt += (col + 1);
            //     return new Info(ans, cnt);
            // }

            if (matrix[row][col] <= num) {
                ans = Math.max(ans, matrix[row][col]);
                cnt += (col + 1);
                row++;
            } else if (matrix[row][col] > num) {
                col--;
            }
        }

        return new Info(ans, cnt);
    }


    public static void main(String[] args) {
        int[][] grid = {{1,1,3,8,13},{4,4,4,8,18},{9,14,18,19,20},{14,19,23,25,25},{18,21,26,28,29}};
        int[] nums = {1,2,31,33};
        int[] nums2 = {2,5,6};
        int n = 13;

        String str1 = "ab";
        String str2 = "eidbaooo";
        System.out.println(kthSmallest(grid, n));
    }


}