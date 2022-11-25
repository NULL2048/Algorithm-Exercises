package 大厂刷题班.class20;

// 回文串的题大多都是范围上的尝试模型
// 范围上尝试模型  子序列  回文串
public class Code04_PalindromeWays {
    // 1、暴力解
    public static int ways1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] s = str.toCharArray();
        char[] path = new char[s.length];
        return process(str.toCharArray(), 0, path, 0);
    }

    public static int process(char[] s, int si, char[] path, int pi) {
        if (si == s.length) {
            return isP(path, pi) ? 1 : 0;
        }
        int ans = process(s, si + 1, path, pi);
        path[pi] = s[si];
        ans += process(s, si + 1, path, pi + 1);
        return ans;
    }

    public static boolean isP(char[] path, int pi) {
        if (pi == 0) {
            return false;
        }
        int L = 0;
        int R = pi - 1;
        while (L < R) {
            if (path[L++] != path[R--]) {
                return false;
            }
        }
        return true;
    }

    // 2、动态规划
    public static int ways2(String str) {
        // 过滤无效参数
        if (str == null || str.length() == 0) {
            return 0;
        }
        // 转成字符串数组
        char[] s = str.toCharArray();
        int n = s.length;
        // 在字符串中从L...R中所有子序列能搞出几个回文来，空串不算。注意dp数组的L和R并不要求必须要选择L或R位置的字符，知识范围在L~R范围上选，至于选不选L或R位置上的字符并不强制要求。
        int[][] dp = new int[n][n];
        // 对角线只有一个字符的情况，就是一个回文串，都赋值为1
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1;
        }
        // 对角线的上面的拿一条斜线是两个字符，如果两个字符一样，就是回文串，就赋值为3，如果不是，那么就两个字符各自是一个回文串，赋值为2
        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = s[i] == s[i + 1] ? 3 : 2;
        }
        // 根据我们的状态转移来进行赋值  从下到上，从左到右
        for (int L = n - 3; L >= 0; L--) {
            for (int R = L + 2; R < n; R++) {
                // 先把情况1、2、3加上
                dp[L][R] = dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1];
                // s[L] == s[R]时，才有情况4
                if (s[L] == s[R]) {
                    dp[L][R] += dp[L + 1][R - 1] + 1;
                }
            }
        }
        // 返回右上角的结果
        return dp[0][n - 1];
    }

    // for test
    public static String randomString(int len, int types) {
        char[] str = new char[len];
        for (int i = 0; i < str.length; i++) {
            str[i] = (char) ('a' + (int) (Math.random() * types));
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int N = 10;
        int types = 5;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int len = (int) (Math.random() * N);
            String str = randomString(len, types);
            int ans1 = ways1(str);
            int ans2 = ways2(str);
            if (ans1 != ans2) {
                System.out.println(str);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

}

