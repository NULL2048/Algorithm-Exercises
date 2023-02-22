package 测试;

import java.util.*;

class Solution {
    public static int leastInterval(char[] tasks, int n) {
        int[] count = new int['Z' + 1];
        int maxCnt = 0;
        for (int i = 0; i < tasks.length; i++) {
            count[tasks[i]]++;
            maxCnt = Math.max(maxCnt, count[tasks[i]]);
        }
        int maxNumCnt = 0;
        for (int i = 0; i < tasks.length; i++) {
            if (count[tasks[i]] == maxCnt) {
                maxNumCnt++;
            }
        }

        int ans = 0;
        ans = maxNumCnt * maxCnt + (n - maxNumCnt + 1) * (maxCnt - 1);
        if (ans < tasks.length) {
            ans += (tasks.length - ans);
        }

        return ans;
    }
    public static void main(String[] args) {
        int[][] grid = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {3,2,1,5,6,4};
        int n = 2;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "aab";
        String str2 = "cog";

        System.out.println(leastInterval(task, n));

    }
}