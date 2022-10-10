package 大厂刷题班.class04;

// 本题测试链接 : https://leetcode.cn/problems/interleaving-string/
public class Code07_InterleavingString {
    public boolean isInterleave(String s1, String s2, String s3) {
        // 如果存在为null的字符串，直接返回false
        if (s1 == null || s2 == null || s3 == null) {
            return false;
        }
        // 如果s1+s2的长度!=s3的长度，那么s1和s2不可能组成s3。直接返回false
        if (s1.length() + s2.length()  != s3.length()) {
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();

        // dp[i][j]：str1只拿前i个字符，和str2只拿前j个字符，能否组成str总字符串只拿前i+j的字符,能为true，否为false
        // 注意，这里i和j表示的是前缀个数，不是下标范围
        boolean dp[][] = new boolean[str1.length + 1][str2.length + 1];
        // 将0,0设置为true
        dp[0][0] = true;
        // str1不要，str2一个字符，两个，三个字符能不能搞定str3的一个,两个,三个..字符。一旦有False后面全是false，这样我们可以把第一列都赋上初值。
        for (int j = 1; j <= str2.length; j++) {
            if (dp[0][j - 1]) {
                dp[0][j] = str3[j - 1] == str2[j - 1] ? true : false;
            } else {
                dp[0][j] = false;
            }
        }

        // 同样，str2不同要，把str1一个、两个、三个看能不能搞定str3的一个、两个、三个。一旦有False后面全是false，这样我们可以把第一行都赋上初值。
        for (int i = 1; i <= str1.length; i++) {
            if (dp[i - 1][0]) {
                dp[i][0] = str3[i - 1] == str1[i - 1] ? true : false;
            } else {
                dp[i][0] = false;
            }
        }

        // 赋值其他位置
        for (int i = 1; i <= str1.length; i++) {
            for (int j = 1; j <= str2.length; j++) {
                // 一共只有两种情况：
                // 1、str3字符串的最后一个字符可能来自str1的最后一个字符。
                //      这种情况str3字符串的下标i+j-1字符等于str1的下标i-1字符，并且dp[i - 1][j]为true，也就是str1的前i-1个字符和str2的前j个字符能组成str3的前i+j-1个字符才能成立，因为如果dp[i-1][j]都不成立，那么后面再怎么样也都凑不出来了。
                // 2、str3的最后一个字符可能来自str2的最后一个字符
                //      str3字符串的下标i+j-1字符等于str2的下标j-1字符，并且dp[i - 1][j]为true才成立
                if (str3[i + j - 1] == str1[i - 1] && dp[i - 1][j] || str3[i + j - 1] == str2[j - 1] && dp[i][j - 1]) {
                    dp[i][j] = true;
                } else {
                    dp[i][j] = false;
                }
            }
        }

        // 返回str1的前str1.length个字符和str2的前str2.length个字符能够组成str3的前str1.length+str2.length个字符
        return dp[str1.length][str2.length];
    }
}
