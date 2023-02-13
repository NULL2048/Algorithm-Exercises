package 测试;

import java.util.*;

class Solution {
    public static int numTrees(int n) {
        return compute(n);
    }

    public static int compute(int N) {
        // 过滤特殊值
        if (N < 0) {
            return 0;
        }
        if (N < 2) {
            return 1;
        }

        long a = 1;
        long b = 1;
        // 2n
        long limit = N << 1;
        for (long i = 1; i <= limit; i++) {
            // 计算a：从1累乘到n
            if (i <= N) {
                a *= i;
            // 计算b：从n+1一直累乘到2n
            } else {
                b *= i;
            }
        }
        // 公式3：k(n)= c(2n, n) / (n + 1)
        // c(n, m) = n(n-1)...(n-m+1) / m!
        // b：2n * (2n - 1) * ... * (2n - n + 1)   也就是从n+1一直累乘到2n
        // a：1 * 2 * ... * (n - 1) * n   也就是从1一直累乘到n
        long c = b / a;
        return (int) ((c) / (N + 1));
    }
    public static void main(String[] args) {
        int[][] grid = {{0,1,2,0},{3,4,5,2},{1,3,1,5}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {3,2,1,5,6,4};
        int n = 19;



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

        System.out.println(numTrees(14));
//        System.out.println("hesitxyplovdqfkz".equals(removeDuplicateLetters(str1)));

//        int[] ans = maxSumOfThreeSubarrays(nums, n);
//        for (int a : ans) {
//            System.out.print(a + " ");
//        }
    }
}