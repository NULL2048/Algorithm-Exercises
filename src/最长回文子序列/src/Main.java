package 最长回文子序列.src;

import java.io.StreamTokenizer;

public class Main {
    public static void main(String[] args) {
        System.out.println(longestPalindromeSubseq("aabaa"));
    }

    /**
     * 这个题很明显是一个动规解决的问题，因为它有最优子结构的性质，就是说每一个最优解肯定包含了子问题的最优解
     *
     *
     * @param s
     * @return
     */
    public static int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        // dp[i][j] = n 标识i-j的区间内的回文串长的为n
        int dp[][] = new int[1000][1000];

        for (int i = s.length() - 1; i >= 0; i--) {
            dp[i][i] = 1;

            for (int j = i + 1; j < s.length(); j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                }
            }
        }

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }

        return dp[0][s.length() - 1];
    }

}
