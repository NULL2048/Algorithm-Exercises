package 体系学习班.class20;

public class Code01_PalindromeSubsequence {
    // 1、暴力递归
    public static int lpsl1(String s) {
        // 判空
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 转为字符数组
        char[] str = s.toCharArray();
        // 递归入口
        return f(str, 0, str.length - 1);
    }
    // str[L..R]最长回文子序列长度返回
    public static int f(char[] str, int L, int R) {
        // basecase 当只剩下一个字符的时候，那么回文串的长度肯定就是1
        if (L == R) {
            return 1;
        }
        // basecase，当还剩下两个字符的时候，回文串的长度需要分情况讨论。如果这两个字符是相同的，那么这两个字符就组成了一个回文串，长度为2。如果这两个字符不相同，那么只能是单独把某一个字符当成是一个回文串，长度为1。
        if (L == R - 1) {
            return str[L] == str[R] ? 2 : 1;
        }
        /**
         普遍位置，就分出来四种情况
         1、最长公共回文串既不以L开头，也不以R结尾
         2、最长公共回文串以L开头，不以R结尾
         3、最长公共回文串不以L开头，以R结尾
         4、最长公共回文串既以L开头，也以R结尾   这个需要分一下情况讨论，如果L和R位置的字符相等，那么回文串肯定就是str[L+1 ... R+1]的最长回文串长度 + 2
         */
        int p1 = f(str, L + 1, R - 1);
        int p2 = f(str, L, R - 1);
        int p3 = f(str, L + 1, R);
        int p4 = str[L] != str[R] ? 0 : (2 + f(str, L + 1, R - 1));
        // 取最大值返回
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    // 2、动态规划
    public static int lpsl2(String s) {
        // 判空
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        // 创建dp数组
        int N = str.length;
        int[][] dp = new int[N][N];
        // 根据暴力递归中的basecase去赋初值   第一个basecase显示对角线上的都是1，这里我们先给右下角赋值1，这样能在后续的赋值中剩下一个判断
        dp[N - 1][N - 1] = 1;
        // 下面就根据暴力递归中的两个basecase进行赋初值
        for (int i = 0; i < N - 1; i++) {
            // 对角线都是1
            dp[i][i] = 1;
            // 两个相邻的字符如果相等，那么就设置为2，如果不相等，就设置为1。这个也是一个对角线，但是需要根据不同情况判断一下。
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1; // 这里i是遍历到N-2，因为在这个判断中得保证i+1不越界
        }
        // 赋值完初值之后，就开始对普遍的位置进行赋值
        // 利用暴力递归中找到的四种情况抽象出位置依赖关系来进行赋值
        // 这里我们发现每一个位置的值取决于自己的左边、下边、和左下，再结合着我们已经赋初值的情况，所以这里的复制方向我们可以看找从下到上，从左向右的顺序。（当然也可以沿着对角线去赋值，我们这里就换一种新的方向）
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                // 通过分析，可以将暴力递归中的p1情况去掉，这个情况其实是重复的。
                // 从左，下，左下三个位置中选出一个最大值来进行赋值
                dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
                // 这里记得要做一个判断
                if (str[L] == str[R]) {
                    dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);
                }
            }
        }
        // 根据递归入口传入的参数，可以知道要返回dp哪个位置的结果
        return dp[0][N - 1];
    }
}

