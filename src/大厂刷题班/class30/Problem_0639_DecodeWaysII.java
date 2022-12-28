package 大厂刷题班.class30;
// 从左往右的尝试模型
// https://leetcode.cn/problems/decode-ways-ii/
public class Problem_0639_DecodeWaysII {
    // 下面是我自己写的版本
    public static long MOD = 1000000007;

    // 1、暴力递归
    public int numDecodings1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        return (int) process1(str.toCharArray(), 0);
    }

    public long process1(char[] s, int i) {
        if (i == s.length) {
            return 1;
        }

        if (s[i] == '0') {
            return 0;
        }

        if (s[i] != '*') {
            long p1 = 1 * process1(s, i + 1);
            if (i + 1 == s.length) {
                return p1 % MOD;
            }

            long p2 = 0;

            if (s[i + 1] != '*') {
                int num = (s[i] - '0') * 10 + (s[i + 1] - '0');
                if (num < 27) {
                    p2 = 1 * process1(s, i + 2);
                }
            } else {
                if (s[i] < '3') {
                    p2 = (s[i] == '1' ? 9 : 6) * process1(s, i + 2);
                }
            }


            return (p1 + p2) % MOD;
        } else {
            long p1 = 9 * process1(s, i + 1);
            if (i + 1 == s.length) {
                return p1 % MOD;
            }

            long p2 = 0;
            if (s[i + 1] != '*') {
                p2 = (s[i + 1] < '7' ? 2 : 1) * process1(s, i + 2);
            } else {
                p2 = 15 * process1(s, i + 2);
            }

            return (p1 + p2) % MOD;
        }
    }


    // 2、记忆化搜索
    public int numDecodings2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        long[] dp = new long[str.length()];
        return (int) process2(str.toCharArray(), 0, dp);
    }

    public long process2(char[] s, int i, long[] dp) {
        if (i == s.length) {
            return 1;
        }

        if (s[i] == '0') {
            return 0;
        }

        if (dp[i] != 0) {
            return dp[i];
        }

        // 当前位置的字符不是*
        if (s[i] != '*') {
            // 情况1：当前位置的数字单独转换
            long p1 = 1 * process2(s, i + 1, dp);
            if (i + 1 == s.length) {
                dp[i] = p1 % MOD;
                return dp[i];
            }

            // 情况2：当前位置的数字和下一个位置的数字合并在一起进行转换
            long p2 = 0;
            // 下一个位置是*
            if (s[i + 1] != '*') {
                int num = (s[i] - '0') * 10 + (s[i + 1] - '0');
                if (num < 27) {
                    p2 = 1 * process2(s, i + 2, dp);
                }
            // 下一个位置不是*
            } else {
                if (s[i] < '3') {
                    p2 = (s[i] == '1' ? 9 : 6) * process2(s, i + 2, dp);
                }
            }

            dp[i] = (p1 + p2) % MOD;
            return dp[i];
        // 当前位置的字符是*
        } else {
            // 情况1：当前位置的数字单独转换
            long p1 = 9 * process2(s, i + 1, dp);
            if (i + 1 == s.length) {
                dp[i] = p1 % MOD;
                return dp[i];
            }

            // 情况2：当前位置的数字和下一个位置的数字合并在一起进行转换
            long p2 = 0;
            if (s[i + 1] != '*') {
                p2 = (s[i + 1] < '7' ? 2 : 1) * process2(s, i + 2, dp);
            } else {
                p2 = 15 * process2(s, i + 2, dp);
            }

            dp[i] = (p1 + p2) % MOD;
            return dp[i];
        }
    }


    // 下面是左神的代码
    public static long mod = 1000000007;

    // 1、暴力递归
    public static int numDecodings0(String str) {
        return (int) f(str.toCharArray(), 0);
    }

    public static long f(char[] str, int i) {
        if (i == str.length) {
            return 1;
        }
        if (str[i] == '0') {
            return 0;
        }
        // str[index]有字符且不是'0'
        if (str[i] != '*') {
            // str[index] = 1~9
            // i -> 单转
            long p1 = f(str, i + 1);
            if (i + 1 == str.length) {
                return p1 % mod;
            }
            if (str[i + 1] != '*') {
                int num = (str[i] - '0') * 10 + str[i + 1] - '0';
                long p2 = 0;
                if (num < 27) {
                    p2 = f(str, i + 2);
                }
                return (p1 + p2) % mod;
            } else { // str[i+1] == '*'
                // i i+1 -> 一起转 1* 2* 3* 9*
                long p2 = 0;
                if (str[i] < '3') {
                    p2 = f(str, i + 2) * (str[i] == '1' ? 9 : 6);
                }
                return (p1 + p2) % mod;
            }
        } else { // str[i] == '*' 1~9
            // i 单转 9种
            long p1 = 9 * f(str, i + 1);
            if (i + 1 == str.length) {
                return p1 % mod;
            }
            if (str[i + 1] != '*') {
                // * 0 10 20
                // * 1 11 21
                // * 2 12 22
                // * 3 13 23
                // * 6 16 26
                // * 7 17
                // * 8 18
                // * 9 19
                long p2 = (str[i + 1] < '7' ? 2 : 1) * f(str, i + 2);
                return (p1 + p2)% mod;
            } else { // str[i+1] == *
                // **
                // 11~19 9
                // 21 ~26 6
                // 15
                long p2 = 15 * f(str, i + 2);
                return (p1 + p2)% mod;
            }
        }
    }

    // 2、记忆化搜索   他这个记忆化搜索的代码是根据暴力递归精简处理过的，并不是直接由暴力递归的代码改出来的，但是整个思路还是暴力递归的思路，代码一看也能看懂
    public static int numDecodings11(String str) {
        long[] dp = new long[str.length()];
        return (int) ways1(str.toCharArray(), 0, dp);
    }

    public static long ways1(char[] s, int i, long[] dp) {
        if (i == s.length) {
            return 1;
        }
        if (s[i] == '0') {
            return 0;
        }
        if (dp[i] != 0) {
            return dp[i];
        }
        long ans = ways1(s, i + 1, dp) * (s[i] == '*' ? 9 : 1);
        if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
            if (i + 1 < s.length) {
                if (s[i + 1] == '*') {
                    ans += ways1(s, i + 2, dp) * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                } else {
                    if (s[i] == '*') {
                        ans += ways1(s, i + 2, dp) * (s[i + 1] < '7' ? 2 : 1);
                    } else {
                        ans += ((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? ways1(s, i + 2, dp) : 0;
                    }
                }
            }
        }
        ans %= mod;
        dp[i] = ans;
        return ans;
    }

    // 3、动态规划
    public static int numDecodings22(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        long[] dp = new long[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] != '0') {
                dp[i] = dp[i + 1] * (s[i] == '*' ? 9 : 1);
                if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
                    if (i + 1 < n) {
                        if (s[i + 1] == '*') {
                            dp[i] += dp[i + 2] * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                        } else {
                            if (s[i] == '*') {
                                dp[i] += dp[i + 2] * (s[i + 1] < '7' ? 2 : 1);
                            } else {
                                dp[i] += ((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? dp[i + 2] : 0;
                            }
                        }
                    }
                }
                dp[i] %= mod;
            }
        }
        return (int) dp[0];
    }

    // 4、压缩空间的动态规划
    public static int numDecodings3(String str) {
        char[] s = str.toCharArray();
        int n = s.length;
        long a = 1;
        long b = 1;
        long c = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] != '0') {
                c = b * (s[i] == '*' ? 9 : 1);
                if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
                    if (i + 1 < n) {
                        if (s[i + 1] == '*') {
                            c += a * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
                        } else {
                            if (s[i] == '*') {
                                c += a * (s[i + 1] < '7' ? 2 : 1);
                            } else {
                                c += a * (((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? 1 : 0);
                            }
                        }
                    }
                }
            }
            c %= mod;
            a = b;
            b = c;
            c = 0;
        }
        return (int) b;
    }
}
