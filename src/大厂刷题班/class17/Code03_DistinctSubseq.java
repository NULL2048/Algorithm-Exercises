package 大厂刷题班.class17;

// 样本对应模型   样本对应模型可能性根据结尾位置分
// 测试链接：https://leetcode.cn/problems/21dk04/
// 测试连接：https://leetcode.cn/problems/distinct-subsequences/
// 上面两个测试连接的题是一样的，用哪个都可以
public class Code03_DistinctSubseq {
    // 暴力递归
    public static int numDistinct1(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        return process(s, t, s.length, t.length);
    }

    public static int process(char[] s, char[] t, int i, int j) {
        if (j == 0) {
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        int res = process(s, t, i - 1, j);
        if (s[i - 1] == t[j - 1]) {
            res += process(s, t, i - 1, j - 1);
        }
        return res;
    }

    // 2、动态规划   最优解
    public int numDistinct(String sStr, String tStr) {
        if (sStr == null || tStr == null || sStr.length() == 0 || tStr.length() == 0) {
            return 0;
        }

        char[] s = sStr.toCharArray();
        char[] t = tStr.toCharArray();

        // dp[i][j] : s只拿前i个字符做子序列，有多少个子序列，字面值等于T的前j个字符的前缀串
        int[][] dp = new int[s.length][t.length];

        // 第0行，除了dp[0][0]，如果S的0下标等于T的0下标，那么dp[0][0] = 1，否则等于0。其余第0行位置都是0，因为一个字符不可能和多个字符组成的字符串相等。
        dp[0][0] = s[0] == t[0] ? 1 : 0;
        // dp[0][j] = s只拿前0个字符做子序列, T前j个字符
        // 从i=0开始向下遍历，只要是遍历到的格子对应的s字符串的字符位置，s的这个字符等于T字符串0下标位置的字符的话，当前格子就是上面格子的数+1，不相等，当前格子就等于上面的格子。其实就是相当于找S字符串上有多少个等于T字符串0下标的字符。
        for (int i = 1; i < s.length; i++) {
            dp[i][0] = s[i] == t[0] ? (dp[i - 1][0] + 1) : dp[i - 1][0];
        }

        // S[...i]的所有子序列中，包含多少个字面值等于T[...j]这个字符串的子序列，记为dp[i][j]
        // 可能性1）S[...i]的所有子序列中，都不以s[i]结尾，则dp[i][j]肯定包含dp[i-1][j]
        // 可能性2）S[...i]的所有子序列中，都必须以s[i]结尾，
        // 这要求S[i] == T[j]，则dp[i][j]包含dp[i-1][j-1]
        for (int i = 1; i < s.length; i++) {
            // 不能超过t的最长范围，也不能超过此时s字符串0~i的范围长度（t字符串0~j前缀长度如果超过了s字符串0~i的长度，那么就不可能存在相等字面值的情况），所以就从i和t.length - 1中取最小值，i和t.length-1都是值的下标，所以j都是小于等于他们
            for (int j = 1; j <= Math.min(i, t.length - 1); j++) {
                // 情况1
                dp[i][j] = dp[i - 1][j];
                // 情况2
                if (s[i] == t[j]) {
                    // 情况1和情况2加和
                    dp[i][j] += dp[i - 1][j - 1];
                }
            }
        }
        return dp[s.length - 1][t.length - 1];
    }

    // 3、数组压缩
    public static int numDistinct3(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[] dp = new int[t.length + 1];
        dp[0] = 1;
        for (int j = 1; j <= t.length; j++) {
            dp[j] = 0;
        }
        for (int i = 1; i <= s.length; i++) {
            for (int j = t.length; j >= 1; j--) {
                dp[j] += s[i - 1] == t[j - 1] ? dp[j - 1] : 0;
            }
        }
        return dp[t.length];
    }
}
