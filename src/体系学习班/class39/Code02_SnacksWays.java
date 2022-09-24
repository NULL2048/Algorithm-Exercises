package 体系学习班.class39;

public class Code02_SnacksWays {
    // 1、经典解法
    public static int ways1(int[] arr, int w) {
        // arr[0...]
        return process(arr, 0, w);
    }
    // 暴力递归
    // 从左往右的经典模型
    // 还剩的容量是rest，arr[index...]自由选择，
    // 返回选择方案
    // index ： 0～N
    // rest : 0~w
    public static int process(int[] arr, int index, int rest) {
        // basecase
        if (rest < 0) { // 没有容量了
            // -1 无方案的意思
            return -1;
        }
        // rest>=0,
        if (index == arr.length) { // 无零食可选
            // 说明这个方法是可以的，返回1，表示1个方案
            return 1;
        }
        // rest >=0
        // 有零食index
        // 两种选择：index号零食，要 or 不要
        // index, rest
        // (index+1, rest)
        // (index+1, rest-arr[i])
        int next1 = process(arr, index + 1, rest); // 不要
        int next2 = process(arr, index + 1, rest - arr[index]); // 要
        // 要将两种分支的结果加和，next1不可能出现-1，因为Next1至少会有一个分支是什么都不选，就不可能超过背包大小导致-1
        return next1 + (next2 == -1 ? 0 : next2);
    }
    // 2、另一种动态规划写法
    public static int ways2(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N + 1][w + 1];
        for (int j = 0; j <= w; j++) {
            dp[N][j] = 1;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = dp[i + 1][j] + ((j - arr[i] >= 0) ? dp[i + 1][j - arr[i]] : 0);
            }
        }
        return dp[0][w];
    }
    // 3、另一种动态规划写法
    public static int ways3(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N][w + 1];
        for (int i = 0; i < N; i++) {
            dp[i][0] = 1;
        }
        if (arr[0] <= w) {
            dp[0][arr[0]] = 1;
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= w; j++) {
                dp[i][j] = dp[i - 1][j] + ((j - arr[i]) >= 0 ? dp[i - 1][j - arr[i]] : 0);
            }
        }
        int ans = 0;
        for (int j = 0; j <= w; j++) {
            ans += dp[N - 1][j];
        }
        return ans;
    }
    // 上面讲的三种解法，当零食的重量和体积都非常大，超过10^8时，就会超时了，需要用分治的方法
    public static void main(String[] args) {
        int[] arr = { 4, 3, 2, 9 };
        int w = 8;
        System.out.println(ways1(arr, w));
        System.out.println(ways2(arr, w));
        System.out.println(ways3(arr, w));
    }
}
