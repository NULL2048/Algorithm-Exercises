package 体系学习班.class47;

public class Code01_StrangePrinter {
    // 1、暴力递归
    public int strangePrinter1(String s) {
        if (s.length() == 1) {
            return 1;
        }
        // 转化为字符数组
        char[] str = s.toCharArray();
        return process1(0, str.length - 1, str);
    }
    // 要想刷出str[L...R]的样子，返回最少的转数
    // 我们规定最边缘处的一定是最先刷出来的，这样最少转数的答案不会被错过
    public int process1(int l, int r, char[] str) {
        // basecase  当l==r时，肯定只需要一转就能出来，返回1
        if (l == r) {
            return 1;
        }
        // 最大转数也不可能超过这个值，这个就相当于每一个字符都通过一转来得到
        int min = r - l + 1;
        // 尝试每一个分界点
        for (int i = l + 1; i <= r; i++) {
            // 将l~i-1去独立作为一部分去用打印机搞出来
            // 将i~r去独立作为一部分去用打印机搞出来
            // 因为每一部分边缘处的一定是最先刷出来的，最左边缘第一次刷完之后就不再动它了，所以如果下面划分的两个独立部分的最左边缘的字符是相等的话，那么在最开始就可以第一次将这两部分的左边缘字符直接一次刷出来，所以用两个递归求就相当于多算了一次，我们再减掉一次即可
            min = Math.min(min, process1(l, i - 1, str) + process1(i, r, str) - (str[l] == str[i] ? 1 : 0));
        }
        return min;
    }
    // 2、记忆化搜索
    public int strangePrinter2(String s) {
        if (s.length() == 1) {
            return 1;
        }
        char[] str = s.toCharArray();
        int n = str.length;
        // 引入缓存数组
        int[][] dp = new int[n][n];
        return process2(0, n - 1, str, dp);
    }
    // 要想刷出str[L...R]的样子，返回最少的转数
    // 我们规定最边缘处的一定是最先刷出来的，这样最少转数的答案不会被错过
    public int process2(int l, int r, char[] str, int[][] dp) {
        // 如果已经有记录了，就直接返回
        if (dp[l][r] != 0) {
            return dp[l][r];
        }
        if (l == r) {
            dp[l][r] = 1;
            return 1;
        }
        int min = r - l + 1;
        // 尝试每一个分界点，将整个范围划分成独立的两部分去单独用打印机打印出来，要把所有可能的分界点都试一遍
        for (int i = l + 1; i <= r; i++) {
            // 将l~i-1去独立作为一部分去用打印机搞出来
            // 将i~r去独立作为一部分去用打印机搞出来
            // 因为每一部分边缘处的一定是最先刷出来的，最左边缘第一次刷完之后就不再动它了，所以如果下面划分的两个独立部分的最左边缘的字符是相等的话，那么在最开始就可以第一次将这两部分的左边缘字符直接一次刷出来，所以用两个递归求就相当于多算了一次，我们再减掉一次即可
            min = Math.min(min, process2(l, i - 1, str, dp) + process2(i, r, str, dp) - (str[l] == str[i] ? 1 : 0));
        }
        // 将计算出来的结果计入缓存
        dp[l][r] = min;
        return min;
    }
    // 3、动态规划
    public static int strangePrinter3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 1 : 2;
        }
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                dp[L][R] = R - L + 1;
                for (int k = L + 1; k <= R; k++) {
                    dp[L][R] = Math.min(dp[L][R], dp[L][k - 1] + dp[k][R] - (str[L] == str[k] ? 1 : 0));
                }
            }
        }
        return dp[0][N - 1];
    }

}
