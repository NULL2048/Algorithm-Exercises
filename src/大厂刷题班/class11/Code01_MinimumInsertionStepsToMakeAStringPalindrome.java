package 大厂刷题班.class11;

import java.util.ArrayList;
import java.util.List;

public class Code01_MinimumInsertionStepsToMakeAStringPalindrome {
    // 本题第一问
    // 范围尝试模型
    // 本题测试链接 : https://leetcode.cn/problems/minimum-insertion-steps-to-make-a-string-palindrome/
    public int minInsertions(String s) {
        // 过滤无效参数
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int n = str.length;
        // dp[i][j]：i...j范围上最少要填几个字符串让整体变成回文。
        int[][] dp = new int[n][n];
        // 赋初始值
        // 左下半区没用，因为不可能让左边界L大于右边界R。对角线都是0，因为只有一个数的话，它本身就是回文串了。
        // 倒数第二条对角线 L==R-1，也就是只有两个字符的情况，两个字符相等就填0个（本身就是回文串了），不等就填1个。
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }

        // 填表顺序，从下到上，从左到右。这个是根据下面的这些位置依赖关系来确定的，每个格子依赖他左边，下面和左下三个格子，那么自然我们就要按照从下到上，从左到右的顺序来填dp表。
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                // 情况1 dp[i][j - 1] + 1：去看i...j-1范围上需要加几个字符能让i...j-1范围上变成回文串，这样我们在i-1位置加一个和j位置一样的字符，就让这个大范围变成回文串了。
                // 情况2 dp[i + 1][j] + 1：这个和上一种情况同理，先看i+1...j范围变成回文串至少需要加几个字符。然后我们再在j+1位置加一个和i位置一样的字符，就能让大范围变成回文字符串了。
                dp[i][j] = Math.min(dp[i + 1][j] + 1, dp[i][j - 1] + 1);
                // 情况3 dp[i + 1][j - 1]：如果i位置的字符等于j位置的字符，那么我们只要保证i+1...j-1范围上变成回文串了，就能使整个大范围变成回文串。
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }

        // 返回最终答案
        return dp[0][n - 1];
    }


    // 本题第二问，返回其中一种结果
    public static String minInsertionsOneWay(String s) {
        // 构造dp表
        if (s == null || s.length() < 2) {
            return s;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
        }
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                if (str[i] == str[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }

        // 完成dp表的构造，开始回溯，构造一种可能性结果

        // 从dp表右上角结果位置开始回溯
        int L = 0;
        int R = N - 1;
        // 记录一种可能性结果
        char[] ans = new char[N + dp[L][R]];
        // 标记结果的左边已经填充到了什么位置
        int ansl = 0;
        // 标记结果的左右边已经填充到了什么位置
        int ansr = ans.length - 1;
        while (L < R) {
            // 这里我们先去判断可能性1和可能性2，如果可能性1和可能性2是可以的，直接就近到他们的分支了。
            // 如果两个都不行，那必然就是可能性3了，就省略了判断L和R位置的字符是否相等的步骤了。这道题也是只要求返回一个结果即可
            // 正常的线路就是找左边，下遍，左下三个格子中，哪些格子的值等于dp[L][R] - 1，那么这个格子就是符合要求的回溯路径上的格子
            // 当然也可以按照最直接的思路找回溯路线，就是找那个格子的值比较小，就选小的回溯。同事还要判断L和R位置的字符是否相等，确定有没有可能性3
            if (dp[L][R - 1] == dp[L][R] - 1) {
                // 填充结果数组
                ans[ansl++] = str[R];
                ans[ansr--] = str[R--];
            } else if (dp[L + 1][R] == dp[L][R] - 1) {
                ans[ansl++] = str[L];
                ans[ansr--] = str[L++];
            } else {
                ans[ansl++] = str[L++];
                ans[ansr--] = str[R--];
            }
        }
        if (L == R) {
            ans[ansl] = str[L];
        }
        // 返回结果字符串
        return String.valueOf(ans);
    }



    // 本题第三问，返回所有可能的结果
    public static List<String> minInsertionsAllWays(String s) {
        // 构造dp表
        // 要返回的全部结果
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            int[][] dp = new int[N][N];
            for (int i = 0; i < N - 1; i++) {
                dp[i][i + 1] = str[i] == str[i + 1] ? 0 : 1;
            }
            for (int i = N - 3; i >= 0; i--) {
                for (int j = i + 2; j < N; j++) {
                    dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                    if (str[i] == str[j]) {
                        dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                    }
                }
            }
            int M = N + dp[0][N - 1];
            char[] path = new char[M];
            // 通过深度优先遍历，将所有的可能性结果分支都走一遍。
            process(str, dp, 0, N - 1, path, 0, M - 1, ans);
        }
        return ans;
    }

    // 当前来到的动态规划中的格子，(L,R)
    // path ....  [pl....pr] ....
    public static void process(char[] str, int[][] dp, int L, int R, char[] path, int pl, int pr, List<String> ans) {
        // 递归出口   L > R和L==R情况表示当前的分支找完了
        if (L >= R) {
            // 判断是否已经填完，如果L==R说明还差一个位置，填一下
            if (L == R) {
                path[pl] = str[L];
            }
            // 将当前分支的结果加入ans
            ans.add(String.valueOf(path));
        } else {
            // 这次用的不是if else分支了，全部都是用的if分支，这样就能保证每一个分支都会去深度遍历
            // 可能性1
            if (dp[L][R - 1] == dp[L][R] - 1) {
                path[pl] = str[R];
                path[pr] = str[R];
                // 继续向下递归
                process(str, dp, L, R - 1, path, pl + 1, pr - 1, ans);
            }
            // 可能性2
            if (dp[L + 1][R] == dp[L][R] - 1) {
                path[pl] = str[L];
                path[pr] = str[L];
                process(str, dp, L + 1, R, path, pl + 1, pr - 1, ans);
            }
            // 可能性3    L == R - 1是因为有的对角线边界位置就没有dp[L + 1][R - 1] == dp[L][R]这种关系
            if (str[L] == str[R] && (L == R - 1 || dp[L + 1][R - 1] == dp[L][R])) {
                path[pl] = str[L];
                path[pr] = str[R];
                process(str, dp, L + 1, R - 1, path, pl + 1, pr - 1, ans);
            }
        }
    }

    public static void main(String[] args) {
        String s = null;
        String ans2 = null;
        List<String> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "mbadm";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "leetcode";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);

        s = "aabaa";
        ans2 = minInsertionsOneWay(s);
        System.out.println(ans2);
        System.out.println("本题第二问，返回其中一种结果测试结束");

        System.out.println();

        System.out.println("本题第三问，返回所有可能的结果测试开始");
        s = "mbadm";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "leetcode";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();

        s = "aabaa";
        ans3 = minInsertionsAllWays(s);
        for (String way : ans3) {
            System.out.println(way);
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能的结果测试结束");
    }
}
