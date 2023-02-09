package 大厂刷题班.class36;
// 这道题和之前讲过的那个牛羊吃草问题很像，就是打表找规律章节的第二题：牛羊吃N份青草谁会赢

// 这道题不能打表找规律，找不到规律     这道题是动态规划   从左往右的尝试模型

// 来自哈喽单车
// 本题是leetcode原题 : https://leetcode.cn/problems/stone-game-iv/
public class Code10_StoneGameIV {
    // 1、暴力递归
    // 返回当前的先手会不会赢
    public static boolean winnerSquareGame1(int n) {
        // 如果轮到当前这个人时，剩余的石子为0个，那么这个人一定输了，返回false
        if (n == 0) {
            return false;
        }
        // 当前的先手，会尝试所有的情况，1，4，9，16，25，36....
        for (int i = 1; i * i <= n; i++) {
            // 当前的先手，决定拿走 i * i 这个平方数
            // 它的对手会不会赢？ winnerSquareGame1(n - i * i)
            // 如果对手输了，就说明自己赢了，返回true。后手输，先手就赢
            if (!winnerSquareGame1(n - i * i)) {
                return true;
            }
        }
        // 只要是没发现自己能赢得情况，就返回false
        return false;
    }

    // 2、记忆化搜索
    public static boolean winnerSquareGame2(int n) {
        // dp[i]:总共i个石子时,先手会不会赢
        // 0:初始值  1:赢   -1:输
        int[] dp = new int[n + 1];
        // 0个石子，一定是先手输
        dp[0] = -1;
        return process2(n, dp);
    }
    // n个石子，先手能不能赢
    // 直接用暴力递归改的记忆化搜索
    public static boolean process2(int n, int[] dp) {
        // 之前已经计算出结果了，直接返回
        if (dp[n] != 0) {
            return dp[n] == 1 ? true : false;
        }
        boolean ans = false;
        for (int i = 1; i * i <= n; i++) {
            // 后手输，先手就赢
            if (!process2(n - i * i, dp)) {
                ans = true;
                break;
            }
        }
        dp[n] = ans ? 1 : -1;
        return ans;
    }

    // 3、动态规划
    // 直接将递归改成了迭代
    public static boolean winnerSquareGame3(int n) {
        // dp[i]:总共i个石子时,先手会不会赢
        boolean[] dp = new boolean[n + 1];
        // 尝试所有可能剩余的石子数
        for (int i = 1; i <= n; i++) {
            // 尝试先手所有可能拿的石子数量
            for (int j = 1; j * j <= i; j++) {
                // 当前的先手，决定拿走 i * i 这个平方数
                // 它的对手会不会赢？ dp[i - j * j]
                // 如果对手输了，就说明自己赢了，返回true。后手输，先手就赢
                if (!dp[i - j * j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        // 看递归入口传入的是n，所以这里就返回dp[n]
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 10000000;
        System.out.println(winnerSquareGame3(n));
    }
}
