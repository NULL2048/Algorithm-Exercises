package 测试;

import java.util.*;

class Solution {
    public static int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        int L = 0;
        int R = n - 1;
        int l = L - 1;
        int r = R + 1;
        k = n - k;

        while (true) {
            int v = nums[L + (int) (Math.random() * (R - L + 1))];
            //int v = nums[L];

            int cur = L;
            while (l < r && cur < r) {
                if (nums[cur] < v) {
                    swap(++l, cur, nums);
                } else if (nums[cur] > v) {
                    swap(--r, cur, nums);
                    continue;
                }
                cur++;
            }

            if (k > l && k < r) {
                return v;
            } else if (k <= l) {
                R = l;
                r = l + 1;
                l = L - 1;
            } else {
                L = r;
                l = L - 1;
                r = R + 1;
            }
        }
    }

    public static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    public static void main(String[] args) {
        int[][] grid = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {3,2,1,5,6,4};
        int n = 3;



//        Arrays.sort(nums);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        //System.out.println(nums);

//        "ADOBECODEBANC"
//        "ABC"

        String str1 = "aab";
        String str2 = "cog";
//        String[] strs = {"hot","dot","dog","lot","log"};
//        List<String> strList = new ArrayList<String>();
//        for (String s : strs) {
//            strList.add(s);
//        }


//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[0].length; j++) {
//                System.out.print(grid[i][j] + " ");
//            }
//            System.out.println();
//        }

//        for (List<String> a : ans) {
//            for (String s : a) {
//                System.out.print(s + " ");
//            }
//            System.out.println();
//        }

        System.out.println(findKthLargest(nums2, 2));
//        System.out.println("hesitxyplovdqfkz".equals(removeDuplicateLetters(str1)));

//        int[] ans = maxSumOfThreeSubarrays(nums, n);
//        for (int a : ans) {
//            System.out.print(a + " ");
//        }
    }
}