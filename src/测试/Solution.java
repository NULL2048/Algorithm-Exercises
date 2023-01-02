package 测试;

import java.util.*;

class Solution {
    public static List<List<String>> partition(String s) {
        // 双层List，用来记录所有的分割方案
        List<List<String>> ans = new ArrayList<>();
        // 如果字符串只有一个字符，直接将其加入到答案中
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
        } else {
            // 先构造dp表
            char[] str = s.toCharArray();
            int N = str.length;
            // check[i][j]：i~j范围的字符串是回文串就为true，不是回文串就是false
            boolean[][] checkMap = createCheckMap(str, N);
            // // dp[i]：从i出发后面所有的字符串至少分成几个部分让每个部分都是回文
            // int[] dp = new int[N + 1];
            // dp[N] = 0;
            // for (int i = N - 1; i >= 0; i--) {
            //     if (checkMap[i][N - 1]) {
            //         dp[i] = 1;
            //     } else {
            //         int next = Integer.MAX_VALUE;
            //         for (int j = i; j < N; j++) {
            //             if (checkMap[i][j]) {
            //                 next = Math.min(next, dp[j + 1]);
            //             }
            //         }
            //         dp[i] = 1 + next;
            //     }
            // }

            // List<String> list = new ArrayList<>();
            // for (int i = 0; i < s.length(); i++) {
            //     list.add(String.valueOf(s.charAt(i)));
            // }
            // ans.add(list);

            // 递归回溯，找到所有的分割情况
            process(s, 0, 1, checkMap, ans, new ArrayList<String>());
        }
        return ans;
    }

    public static void process(String s, int i, int j, boolean[][] checkMap, List<List<String>> ans, List<String> path) {
        // s[i...N-1]   已经递归到结尾了
        if (j == s.length()) {
            if (checkMap[i][j - 1]) {
                path.add(s.substring(i, j));
                ans.add(copyStringList(path));
                // 恢复现场
                path.remove(path.size() - 1);
            }
            // s[i...j-1]   还没有递归到结尾
        } else {
            // 判断能否回溯的条件
            if (checkMap[i][j - 1] ) {
                // 找到一个可以向下走的分支,j和j+1
                path.add(s.substring(i, j));
                process(s, j, j + 1, checkMap, ans, path);
                // 恢复现场
                path.remove(path.size() - 1);
            }
            // 向下递归，到下一个划分点,i和j+1
            process(s, i, j + 1, checkMap, ans, path);
        }


//        if (i > s.length() || j >s.length() || i > j) {
//            return;
//        }
//
//        if (j == s.length()) {
//            ans.add(copyStringList(path));
//            path.remove(path.size() - 1);
//            return;
//        }
//
//
//        if (checkMap[i][j]) {
//            path.add(s.substring(i, j + 1));
//        }
//        for (int k = j + 1; k <= s.length(); k++) {
//            process(s, j + 1, k, checkMap, ans, path);
//        }
//        if (checkMap[i][j]) {
//            path.remove(path.size() - 1);
//        }

//        if (j == s.length() - 1) {
//            process(s, i + 1, i + 1, checkMap, ans, path);
//        }
    }







    // 构造回文串检查预处理结构
    public static boolean[][] createCheckMap(char[] str, int n) {
        // check[i][j]：i~j范围的字符串是回文串就为true，不是回文串就是false
        boolean[][] check = new boolean[n][n];
        // 先给对角线赋值赋值，只有一个字符肯定都是回文串
        for (int i = 0; i < n; i++) {
            check[i][i] = true;
        }
        // 再给第二条对角线赋值，两个字符，如果两个字符相等，那么就是回文串，如果两个字符不等，就不是回文串
        for (int i = 0; i < n - 1; i++) {
            check[i][i + 1] = str[i] == str[i + 1];
        }

        // 没有i > j的情况，dp数组左下半部分无效
        // 给普遍位置赋值
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                // 当str[i] == str[j]，并且i+1~j-1范围还是一个回文串，就说明i~j范围是一个回文串
                check[i][j] = str[i] == str[j] && check[i + 1][j - 1];
            }
        }

        return check;
    }

    // 将结果复制到新的List中返回
    public static List<String> copyStringList(List<String> list) {
        List<String> ans = new ArrayList<>();
        for (String str : list) {
            ans.add(str);
        }
        return ans;
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

        String str1 = "aab";
        String str2 = "cog";
//        String[] strs = {"hot","dot","dog","lot","log"};
//        List<String> strList = new ArrayList<String>();
//        for (String s : strs) {
//            strList.add(s);
//        }

        List<List<String>> ans = partition(str1);
//        for (int i = 0; i < grid.length; i++) {
//            for (int j = 0; j < grid[0].length; j++) {
//                System.out.print(grid[i][j] + " ");
//            }
//            System.out.println();
//        }

        for (List<String> a : ans) {
            for (String s : a) {
                System.out.print(s + " ");
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