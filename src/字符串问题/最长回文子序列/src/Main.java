package 字符串问题.最长回文子序列.src;

/**
 * 最长回文子序列和上一题最长回文子串的区别是：
 * 子串是字符串中连续的一个序列
 * 而子序列是字符串中保持相对位置的字符序列
 * 例如，"bbbb"可以是字符串"bbbab"的子序列但不是子串。
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(longestPalindromeSubseq("aabaa"));
    }

    /**
     * 这个题很明显是一个动规解决的问题，因为它有最优子结构的性质，就是说每一个最优解肯定包含了子问题的最优解
     * 因为一段字符串中包含的最大回文子序列长度，一定是包含在它的父字符串的最大回文子序列长度中的
     *
     * 这个问题也有重叠子问题性质，因为我们在求各个区间的最大回文子序列长度时候，可定会在不同的区间多次用到以前求出来的最大长度
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

        // 这里要注意，要从最右端开始遍历，因为动态规划需要自底向上来，如果从最左边开始遍历我们是用不到之前已经求出来的最优解的
        // 比如从i=0开始遍历，当内层完成一次计算之后得到的是dp[0][j]以0为开头的各个最优解，但是当i=1的时候求得都是dp[1][j]得最优解，是用不到之前求出来的dp[0][j]的
        // 但是如果从右向左遍历就不会有问题了
        for (int i = s.length() - 1; i >= 0; i--) {
            // 初始值
            dp[i][i] = 1;

            for (int j = i + 1; j < s.length(); j++) {
                // 如果左右两端的值一样，说明又多出来了两个可以组成回文串的字符，直接dp[i + 1][j - 1] + 2
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                // 如果没有一样的话直接从两种最大子情况中去一个Max
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

        // 最后的结果
        return dp[0][s.length() - 1];
    }

}
