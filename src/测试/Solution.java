package 测试;

import java.util.*;

class Solution {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();

        int[] path = new int[candidates.length];
        process(path, 0, 0, candidates, 0, target, ans);
        return ans;
    }

    public static void  process(int[] path, int pi, int index, int[] candidates, int sum, int target, List<List<Integer>> ans) {
        if (sum == target) {
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < pi; i++) {
                list.add(path[i]);
            }
            ans.add(list);
        }

        if (index == candidates.length) {
            return;
        }

        int tempSum = 0;
        for (int i = 0; i < candidates.length && sum + tempSum <= target; i++) {
            path[pi + i] = candidates[index];
            tempSum += candidates[index];
            process(path, pi + i + 1, index + 1, candidates, sum + tempSum, target, ans);
        }

        process(path, pi, index + 1, candidates, sum, target, ans);

    }

    public static void main(String[] args) {
        int[][] grid = {{1,2,7},{3,6,7}};

        int[] nums = {2,3,6,7};
        int[] nums2 = {1,1,2,1,2,2,1};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        System.out.println(combinationSum(nums, 7));
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