package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static int minPatches(int[] nums, int n) {
        int N = nums.length;
        Arrays.sort(nums);
        int range = 0;
        int cnt = 0;
        int i = 0;
        while (i < N) {
            if (range >= n) {
                return cnt;
            }
            if (nums[i] <= range + 1) {
                range += nums[i];
                i++;
            } else {
                range += (range + 1);
                cnt++;
            }

        }
//95  cnt 3
//        n >= range + 1
//                range + 1 <= n
        while (range + 1 <= n) {
            range += (range + 1);
            cnt++;
        }
        return cnt;
    }


    public static int minPatches2(int[] arr, int aim) {
        int patches = 0; // 缺多少个数字
        long range = 0; // 已经完成了1 ~ range的目标
        Arrays.sort(arr);
        for (int i = 0; i != arr.length; i++) {
            // arr[i]
            // 要求：1 ~ arr[i]-1 范围被搞定！
            while (arr[i] - 1 > range) { // arr[i] 1 ~ arr[i]-1
                range += range + 1; // range + 1 是缺的数字
                patches++;
                if (range >= aim) {
                    return patches;
                }
            }
            // 要求被满足了！
            range += arr[i];
            if (range >= aim) {
                return patches;
            }
        }
        while (aim >= range + 1) {
            range += range + 1;
            patches++;
        }
        return patches;
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1,1},{1,0,1},{1,1,1}};
        int[] nums = {1,2,31,33};
        int[] nums2 = {2,5,6};
        int n = 2147483647;

        String str1 = "ab";
        String str2 = "eidbaooo";
        System.out.println(minPatches(nums, n));
    }


}