package 测试;

import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static int minAbsDifference(int[] nums, int goal) {
        if (nums == null || nums.length == 0) {
            return goal;
        }
        int n = nums.length;
        int mid = (n - 1) >> 1;
        // int[] sumL = new int[2 << mid];
        // int[] sumR = new int[2 << (mid + 1)];
        TreeSet<Integer> sumL = new TreeSet<>();
        TreeSet<Integer> sumR = new TreeSet<>();

        process(0, mid, 0, nums, sumL);
        process(mid + 1, n - 1, 0, nums, sumR);

        int min = goal;
        for (Integer l : sumL) {
            int rest = goal - l;

            Integer r1 = sumR.floor(rest);
            Integer r2 = sumR.ceiling(rest);

            if (r1 != null) {
                min = Math.min(min, Math.abs(rest - r1));
            } else {
                min = Math.min(min, Math.abs(rest));
            }

            if (r2 != null) {
                min = Math.min(min, Math.abs(rest - r2));
            } else {
                min = Math.min(min, Math.abs(rest));
            }
        }

        return min;
    }

    public static void process(int index, int end, int sum, int[] nums, TreeSet<Integer> arrSum) {
        if (index == end + 1) {
            arrSum.add(sum);
            return;
        }

        process(index + 1, end, sum + nums[index], nums, arrSum);
        process(index + 1, end, sum, nums, arrSum);
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1,1},{1,0,1},{1,1,1}};
        int[] nums = {7,-9,15,-2};
        String str = "abcabcbb";
        System.out.println(minAbsDifference(nums, -5));
    }


}