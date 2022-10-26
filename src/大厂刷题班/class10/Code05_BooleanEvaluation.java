package 大厂刷题班.class10;

// 本题测试链接 : https://leetcode.cn/problems/boolean-evaluation-lcci/
// 范围尝试模型
public class Code05_BooleanEvaluation {
    // 通过一个类封装两个信息，作为递归的返回值
    public class Info {
        // 最终计算结果为true的逻辑符号结合的方法数
        private int tCnt;
        // 最终计算结果为false的逻辑符号结合的方法数
        private int fCnt;

        public Info(int t, int f) {
            this.tCnt = t;
            this.fCnt = f;
        }
    }

    // 1、暴力递归
    public int countEval1(String s, int result) {
        // 过滤无效参数
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 将字符串转换成字符数组
        char[] str = s.toCharArray();

        Info info = process1(str, 0, str.length - 1);
        // 当前要求最终的结果为result，返回result结果的逻辑符号结合的方法数
        return result == 1 ? info.tCnt : info.fCnt;
    }

    // 限制:
    // L...R上，一定有奇数个字符
    // L位置的字符和R位置的字符，非0即1，不能是逻辑符号！
    // 返回str[L...R]这一段，为true的方法数，和false的方法数
    public Info process1(char[] str, int l, int r) {
        // basecase   当前范围只有一个数
        if (l == r) {
            // 如果这个位置的数是1，那么true就有1个，false就有0个
            int t = str[l] == '1' ? 1 : 0;
            // 如果这个位置的数是0，那么true就有0个，false就有1个
            int f = str[l] == '0' ? 1 : 0;
            // 返回
            return new Info(t, f);
        } else {
            int t = 0;
            int f = 0;
            // 从范围上尝试所有的可能情况，i作为分割，将式子分成左右两个部分，然后分别去求不同左右两部分范围的结果，来累加出总的答案
            // i指向的就是式子中的运算逻辑符号，这里就是把当前范围上所有的逻辑符号都作为分隔位置尝试一边，将所有符合条件的结果都累加起来
            for (int i = l + 1; i < r; i += 2) {
                // 向下递归获取左右两部分的结果
                Info leftInfo = process1(str, l, i - 1);
                Info rightInfo = process1(str, i + 1, r);

                // 根据当前尝试的逻辑运算符号，来计算能让式子结果为true和false的方法数，并且累加到t和f中
                // &：左右两边都是true，最终结果才能是true；左右两边至少存在一个false或者两边都是false，最终结果就是false
                // |：左右两边都是至少存在一个true或者两边都是true，最终结果才能是true；左右两边至都是false，最终结果就是false
                // ^：左右两边结果不相同，最终结果才能是true；左右两边结果相同，最终结果就是false
                if (str[i] == '&') {
                    t += leftInfo.tCnt * rightInfo.tCnt;
                    f += leftInfo.tCnt * rightInfo.fCnt + leftInfo.fCnt * rightInfo.tCnt + leftInfo.fCnt * rightInfo.fCnt;
                } else if (str[i] == '|') {
                    t += leftInfo.tCnt * rightInfo.fCnt + leftInfo.fCnt * rightInfo.tCnt + leftInfo.tCnt * rightInfo.tCnt;
                    f += leftInfo.fCnt * rightInfo.fCnt;
                } else if (str[i] == '^') {
                    t += leftInfo.tCnt * rightInfo.fCnt + leftInfo.fCnt * rightInfo.tCnt;
                    f += leftInfo.fCnt * rightInfo.fCnt + leftInfo.tCnt * rightInfo.tCnt;
                }
            }
            // 返回结果
            return new Info(t, f);
        }
    }


    // 2、记忆化搜索
    public int countEval(String s, int result) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int n = str.length;
        // 引入Info类型的dp缓存
        Info[][] dp = new Info[n][n];

        Info info = process(str, 0, str.length - 1, dp);
        return result == 1 ? info.tCnt : info.fCnt;
    }

    public Info process(char[] str, int l, int r, Info[][] dp) {
        // 先查看答案是否在缓存中，有的话就直接返回
        if (dp[l][r] != null) {
            return dp[l][r];
        }
        // 后面就和暴力递归的流程一样，只不过要把返回结果提前存入dp数组中

        if (l == r) {
            int t = str[l] == '1' ? 1 : 0;
            int f = str[l] == '0' ? 1 : 0;
            // 将结果存入dp
            dp[l][r] = new Info(t, f);
            return dp[l][r];
        } else {
            int t = 0;
            int f = 0;
            for (int i = l + 1; i < r; i += 2) {
                Info leftInfo = process(str, l, i - 1, dp);
                Info rightInfo = process(str, i + 1, r, dp);

                if (str[i] == '&') {
                    t += leftInfo.tCnt * rightInfo.tCnt;
                    f += leftInfo.tCnt * rightInfo.fCnt + leftInfo.fCnt * rightInfo.tCnt + leftInfo.fCnt * rightInfo.fCnt;
                } else if (str[i] == '|') {
                    t += leftInfo.tCnt * rightInfo.fCnt + leftInfo.fCnt * rightInfo.tCnt + leftInfo.tCnt * rightInfo.tCnt;
                    f += leftInfo.fCnt * rightInfo.fCnt;
                } else if (str[i] == '^') {
                    t += leftInfo.tCnt * rightInfo.fCnt + leftInfo.fCnt * rightInfo.tCnt;
                    f += leftInfo.fCnt * rightInfo.fCnt + leftInfo.tCnt * rightInfo.tCnt;
                }
            }
            // 将结果存入dp
            dp[l][r] = new Info(t, f);
            return dp[l][r];
        }
    }



    // 3、动态规划   这个代码不存在枚举过程，时间复杂度和记忆化搜索一样，所以这道题写到记忆化搜索就够了
    public static int countEval2(String express, int desired) {
        if (express == null || express.equals("")) {
            return 0;
        }
        char[] exp = express.toCharArray();
        int N = exp.length;
        int[][][] dp = new int[2][N][N];
        // 赋初值
        dp[0][0][0] = exp[0] == '0' ? 1 : 0;
        dp[1][0][0] = dp[0][0][0] ^ 1;
        // 给普遍位置赋值，赋值代码基本和暴力递归一致
        for (int i = 2; i < exp.length; i += 2) {
            dp[0][i][i] = exp[i] == '1' ? 0 : 1;
            dp[1][i][i] = exp[i] == '0' ? 0 : 1;
            for (int j = i - 2; j >= 0; j -= 2) {
                for (int k = j; k < i; k += 2) {
                    if (exp[k + 1] == '&') {
                        dp[1][j][i] += dp[1][j][k] * dp[1][k + 2][i];
                        dp[0][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[0][k + 2][i] + dp[0][j][k] * dp[1][k + 2][i];
                    } else if (exp[k + 1] == '|') {
                        dp[1][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
                        dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i];
                    } else {
                        dp[1][j][i] += dp[0][j][k] * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
                        dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i] + dp[1][j][k] * dp[1][k + 2][i];
                    }
                }
            }
        }
        return dp[desired][0][N - 1];
    }
}
