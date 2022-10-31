package 大厂刷题班.class12;
// 样本对应模型
// 测试链接 : https://leetcode.cn/problems/regular-expression-matching/
public class Code04_RegularExpressionMatch {
    public boolean isValid(char[] str, char[] exp) {
        // str中不能有'.' or '*'
        for (int i = 0; i < str.length; i++) {
            if (str[i] == '.' || str[i] == '*') {
                return false;
            }
        }
        // 开头的exp[0]不能是'*'，不能有相邻的'*'
        for (int i = 0; i < exp.length; i++) {
            if (exp[i] == '*' && (i == 0 || exp[i - 1] == '*')) {
                return false;
            }
        }
        return true;
    }
    // 1、暴力递归，不含斜率优化
    public boolean isMatch1(String s, String p) {
        char[] str = s.toCharArray();
        char[] exp = p.toCharArray();

        // 判断str和exp有效性
        if (!isValid(str, exp)) {
            return false;
        }
        return process1(str, exp, 0, 0);
    }
    // str[si.....] 能不能被 exp[ei.....]配出来！ true false
    public boolean process1(char[] str, char[] exp, int s, int e) {
        // basecase
        // 如果exp已经遍历完了，同时str也遍历完了，那么就说明配出来，返回true
        if (e == exp.length) {
            return s == str.length;
        }
        // 如果exp没有遍历到最后一个位置，并且当前e位置的下一个字符不是'*'（exp[e + 1] != '*'），这种情况下必须保证exp和str对应位置的字符相等或者exp这个位置为'.'才行，否则后面就不可能配出来了
        // 当然如果exp已经来到了最后一个位置了（(e == exp.length - 1），就一定能保证它下一个字符不是'*'，因为它后面已经没数了，所以这种情况如果想要配出来也必须保证exp和str对应位置的字符相等或者exp这个位置为'.'才行
        if (e == exp.length - 1 || exp[e + 1] != '*') {
            // str[si] 必须和 exp[ei] 能配上或者exp[e]为'.'
            if (s < str.length && (exp[e] == str[s] || exp[e] == '.')) {
                // str[s]的字符配出来了，str和exp同时向后遍历，去判断后面的能不能配出来
                return process1(str, exp, s + 1, e + 1);
                // 如果不满足上面的条件，就永远不可能配出来了，直接返回false
            } else {
                return false;
            }
        }

        // 如果没有进入到上面的if分支，那么exp肯定还没有到最后一个位置，并且紧挨着ei + 1位置的字符是'*'
        // 这里要判断一下s的范围s < str.length，如果str已经遍历完了，那么就直接把e和e+1两个位置的字符直接变成空即可，就不用进入到这个while循环了
        // 循环枚举，尝试将exp的e和e+1位置变成多个str[s]字符，看能不能给匹配出来，这里的条件就是exp[e]要等于str[s]，不然变出来的字符肯定不能和str[s]匹配上
        while (s < str.length && (exp[e] == str[s] || exp[e] == '.')) {
            // 只要有一种情况能够配出来，就返回true
            if (process1(str, exp, s, e + 2)) {
                return true;
            }
            // 增加变出来的字符数，也就是相当于将str遍历到的位置向后推，表示前面都配出来了
            s++;
        }
        // 这里就是如果上面循环没有找出来，就直接将e和e+1位置的两个字符变成空，然后寄希望于让e+1后面的字符串能和str的s位置及后面的字符配出来
        return process1(str, exp, s, e + 2);
    }

    // 2、记忆化搜索，无斜率优化
    public boolean isMatch2(String s, String p) {
        char[] str = s.toCharArray();
        char[] exp = p.toCharArray();
        // dp[i][j] = 0, 没算过！
        // dp[i][j] = -1 算过，返回值是false
        // dp[i][j] = 1 算过，返回值是true
        int[][] dp = new int[str.length + 1][exp.length + 1];
        return isValid(str, exp) && (process2(str, exp, 0, 0, dp) == 1);
    }
    // str[si.....] 能被 exp[ei.....]配出来返回1，不能配出来返回-1
    public int process2(char[] str, char[] exp, int s, int e, int[][] dp) {
        // 先看dp中是不是已经存在该结果了，如果已经计算过了直接返回已有答案
        if (dp[s][e] != 0) {
            return dp[s][e];
        }

        // 后面的代码和暴力递归基本一致

        // basecase
        if (e == exp.length) {
            // 返回1或-1
            return s == str.length ? 1 : -1;
        }

        // 如果exp没有遍历到最后一个位置，并且当前e位置的下一个字符不是'*'（exp[e + 1] != '*'），这种情况下必须保证exp和str对应位置的字符相等或者exp这个位置为'.'才行，否则后面就不可能配出来了
        // 当然如果exp已经来到了最后一个位置了（(e == exp.length - 1），就一定能保证它下一个字符不是'*'，因为它后面已经没数了，所以这种情况如果想要配出来也必须保证exp和str对应位置的字符相等或者exp这个位置为'.'才行
        if (e == exp.length - 1 || exp[e + 1] != '*') {
            // str[si] 必须和 exp[ei] 能配上或者exp[e]为'.'
            if (s < str.length && (exp[e] == str[s] || exp[e] == '.')) {
                // str[s]的字符配出来了，str和exp同时向后遍历，去判断后面的能不能配出来
                // 将答案记录到dp中再返回
                dp[s][e] = process2(str, exp, s + 1, e + 1, dp);
                return dp[s][e];
                // 如果不满足上面的条件，就永远不可能配出来了，直接返回-1
            } else {
                // 将答案记录到dp中再返回
                dp[s][e] = -1;
                return dp[s][e];
            }
        }
        // 如果没有进入到上面的if分支，那么exp肯定还没有到最后一个位置，并且紧挨着ei + 1位置的字符是'*'
        // 这里要判断一下s的范围s < str.length，如果str已经遍历完了，那么就直接把e和e+1两个位置的字符直接变成空即可，就不用进入到这个while循环了
        // 循环枚举，尝试将exp的e和e+1位置变成多个str[s]字符，看能不能给匹配出来，这里的条件就是exp[e]要等于str[s]，不然变出来的字符肯定不能和str[s]匹配上
        while (s < str.length && (exp[e] == str[s] || exp[e] == '.')) {
            // 只要有一种情况能够配出来，就返回1
            if (process2(str, exp, s, e + 2, dp) == 1) {
                // 记录到dp中
                dp[s][e] = 1;
                return dp[s][e];
            }
            // 增加变出来的字符数，也就是相当于将str遍历到的位置向后推，表示前面都配出来了
            s++;
        }
        // 这里就是如果上面循环没有找出来，就直接将e和e+1位置的两个字符变成空，然后寄希望于让e+1后面的字符串能和str的s位置及后面的字符配出来
        dp[s][e] = process2(str, exp, s, e + 2, dp);
        return dp[s][e];
    }

    // 3、记忆化搜索，加上斜率优化
    public boolean isMatch(String s, String p) {
        char[] str = s.toCharArray();
        char[] exp = p.toCharArray();

        int[][] dp = new int[str.length + 1][exp.length + 1];
        return isValid(str, exp) && (process(str, exp, 0, 0, dp) == 1);
    }
    public int process(char[] str, char[] exp, int s, int e, int[][] dp) {
        if (dp[s][e] != 0) {
            return dp[s][e];
        }
        // basecase
        if (e == exp.length) {
            return s == str.length ? 1 : -1;
        }

        if (e == exp.length - 1 || exp[e + 1] != '*') {

            if (s < str.length && (exp[e] == str[s] || exp[e] == '.')) {
                dp[s][e] = process(str, exp, s + 1, e + 1, dp);
                return dp[s][e];
            } else {
                dp[s][e] = -1;
                return dp[s][e];
            }
        }

        // 将循环枚举行为省略掉
        if (s < str.length && (exp[e] == str[s] || exp[e] == '.')) {
            // 能配出来
            if ((process(str, exp, s, e + 2, dp) == 1) || (process(str, exp, s + 1, e, dp) == 1)) {
                dp[s][e] = 1;
                return dp[s][e];
                // 不能配出来
            } else {
                dp[s][e] = -1;
                return dp[s][e];
            }
        }
        dp[s][e] = process(str, exp, s, e + 2, dp);
        return dp[s][e];
    }


    // 4、动态规划版本 + 斜率优化
    public boolean isMatch3(String str, String pattern) {
        if (str == null || pattern == null) {
            return false;
        }
        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();
        if (!isValid(s, p)) {
            return false;
        }
        int N = s.length;
        int M = p.length;
        boolean[][] dp = new boolean[N + 1][M + 1];
        dp[N][M] = true;
        for (int j = M - 1; j >= 0; j--) {
            dp[N][j] = (j + 1 < M && p[j + 1] == '*') && dp[N][j + 2];
        }
        // dp[0..N-2][M-1]都等于false，只有dp[N-1][M-1]需要讨论
        if (N > 0 && M > 0) {
            dp[N - 1][M - 1] = (s[N - 1] == p[M - 1] || p[M - 1] == '.');
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = M - 2; j >= 0; j--) {
                if (p[j + 1] != '*') {
                    dp[i][j] = ((s[i] == p[j]) || (p[j] == '.')) && dp[i + 1][j + 1];
                } else {
                    if ((s[i] == p[j] || p[j] == '.') && dp[i + 1][j]) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i][j + 2];
                    }
                }
            }
        }
        return dp[0][0];
    }
}
