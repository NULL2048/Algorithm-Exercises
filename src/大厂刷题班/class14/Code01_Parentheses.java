package 大厂刷题班.class14;

// s只由(和)组成
// 求最长有效括号子串长度
// 从左往右的尝试模型
// 本题测试链接 : https://leetcode.cn/problems/longest-valid-parentheses/
public class Code01_Parentheses {
    public int longestValidParentheses(String str) {
        if (str == null || str.length() < 2) {
            return 0;
        }

        char[] s = str.toCharArray();
        // dp[i] : 子串必须以i位置结尾的情况下，往左最远能扩出多长的有效区域
        // dp[0] = 0; （  ）
        int[] dp = new int[s.length];
        int ans = -1;
        int pre = 0;
        for (int i = 1; i < s.length; i++) {
            // 只有右括号可以讨论，左括号的话直接为0，因为右括号还有机会被左边的左括号调平，但是如果是左括号的话，永远都不可能被左边的括号调平
            if (s[i] == ')') {
                // 当前谁和i位置的)，去匹配
                pre = i - dp[i - 1] - 1; // 与str[i]配对的左括号的位置 pre
                // 如果pre并没有越界并且pre位置的字符是左括号，那么就说明i找到了何其配对的最括号
                if (pre >= 0 && s[pre] == '(') {
                    // 此时以i位置结尾的情况下，至少能向左扩dp[i-1]+2个长度，dp[i-1]是i和pre之间有效括号长度，2指的就是i和pre组成的两个括号长度
                    // 然后再去看pre左边的括号是否还能继续向左扩，如果可以扩就将左边的有效长度累加到dp[i]上，需要先判断pre>0，保证pre-1不会越界，如果pre已经到了0位置，那么左边就没有有效字符了。
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            // 取以i位置结尾的情况下最大的有效括号长度
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
