package 体系学习班.class21;


public class Code05_BobDie {

    // 1、暴力递归
    public static double livePosibility1(int row, int col, int k, int N, int M) {
        // 用生存的方法数 除以 总的方法数
        return (double) process(row, col, k, N, M) / Math.pow(4, k);
    }

    // 目前在row，col位置，还有rest步要走，走完了如果还在棋盘中就获得1个生存点，返回总的生存点数
    public static long process(int row, int col, int rest, int N, int M) {
        // basecase 如果走出范围了，就直接死了，返回0
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        // 还在棋盘中！这是一个可以生存的走法，返回1
        if (rest == 0) {
            return 1;
        }
        // 还在棋盘中！还有步数要走.一共四个方向，去向下递归。我们发现依赖关系都是rest - 1，也就是以来的都是自己上面一层的数据
        long up = process(row - 1, col, rest - 1, N, M);
        long down = process(row + 1, col, rest - 1, N, M);
        long left = process(row, col - 1, rest - 1, N, M);
        long right = process(row, col + 1, rest - 1, N, M);
        // 加和返回
        return up + down + left + right;
    }

    // 2、动态规划
    public static double livePosibility2(int row, int col, int k, int N, int M) {
        // 创建dp  最多有k步，所以创建的数组是k + 1，因为数组下标从0开始
        long[][][] dp = new long[N][M][k + 1];
        // 赋初值
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }
        // 利用位置依赖关系赋值
        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    dp[r][c][rest] = pick(dp, N, M, r - 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r + 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c - 1, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c + 1, rest - 1);
                }
            }
        }
        // 返回结果  这里dp是根据暴力递归的入口来的值要用哪一个的
        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    // 在赋值阶段，去判断是不是超过范围了，如果是的话直接返回0。这个方法和象棋那个题一样，因为我们需要在复制过程中去判断是不是已经超过范围了
    public static long pick(long[][][] dp, int N, int M, int r, int c, int rest) {
        if (r < 0 || r == N || c < 0 || c == M) {
            return 0;
        }
        return dp[r][c][rest];
    }

    public static void main(String[] args) {
        System.out.println(livePosibility1(6, 6, 10, 50, 50));
        System.out.println(livePosibility2(6, 6, 10, 50, 50));
    }

}
