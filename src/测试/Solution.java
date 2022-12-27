package 测试;

import java.util.*;

class Solution {
    public static void setZeroes(int[][] matrix) {
        boolean col0 = false;
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {


                        matrix[i][0] = 0;
                        matrix[0][j] = 0;

                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                col0 = true;
//                for (int j = 0; j < matrix[0].length; j++) {
//                    matrix[i][j] = 0;
//                }
            }
        }

        for (int j = 0; j < matrix[0].length; j++) {
            if ((j == 0 && col0) || (j != 0 && matrix[0][j] == 0)) {
                for (int i = 0; i < matrix.length; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {1,2,3,6};
        int n = 3;

//        Arrays.sort(nums);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        //System.out.println(nums);

//        "ADOBECODEBANC"
//        "ABC"

        String str1 = "hit";
        String str2 = "cog";
        String[] strs = {"hot","dot","dog","lot","log"};
        List<String> strList = new ArrayList<String>();
        for (String s : strs) {
            strList.add(s);
        }

        setZeroes(grid);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }

//        System.out.println("hesitxyplovdqfkz");
//        System.out.println("hesitxyplovdqfkz".equals(removeDuplicateLetters(str1)));

//        int[] ans = maxSumOfThreeSubarrays(nums, n);
//        for (int a : ans) {
//            System.out.print(a + " ");
//        }
    }
}