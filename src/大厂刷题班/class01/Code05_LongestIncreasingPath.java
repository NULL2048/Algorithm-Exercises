package 大厂刷题班.class01;

public class Code05_LongestIncreasingPath {
    // 1、暴力递归   错误写法
    public int longestIncreasingPath1(int[][] matrix) {
        if (matrix.length == 1) {
            return 1;
        }

        int maxCnt = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int up = process1(i, j, i - 1, j, matrix);
                int down = process1(i, j, i + 1, j, matrix);
                int left = process1(i, j, i, j - 1, matrix);
                int right = process1(i, j, i, j + 1, matrix);
                maxCnt = Math.max(maxCnt, Math.max(Math.max(up, down), Math.max(left, right)) + 1);
            }
        }
        return maxCnt;
    }

    // 这个暴力递归方法是对的，但是这种递归模型没有办法改记忆化搜索
    // 因为可变参数已经有4个了，一般不会有这么高维度的dp，我们一般设置dp不能超过三维，超过三维大概率就是暴力递归想错了，需要换一种思路
    // 从(x1,y1)跳到(x2,y2)
    public int process1(int x1, int y1, int x2, int y2, int[][] matrix) {
        if (x2 < 0 || x2 >= matrix.length || y2 < 0 || y2 >= matrix[0].length) {
            return 0;
        }

        if (matrix[x2][y2] > matrix[x1][y1]) {
            int up = process1(x2, y2, x2 - 1, y2, matrix);
            int down = process1(x2, y2, x2 + 1, y2, matrix);
            int left = process1(x2, y2, x2, y2 - 1, matrix);
            int right = process1(x2, y2, x2, y2 + 1, matrix);

            return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
        } else {
            return 0;
        }
    }

    // 2、暴力递归   正确的方法
    public int longestIncreasingPath2(int[][] matrix) {
        int maxCnt = 0;
        // 尝试从每一个位置开始，能找到的最长递增链
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                maxCnt = Math.max(maxCnt, process2(i, j, matrix));
            }
        }
        return maxCnt;
    }

    // 从m[i][j]开始走，走出来的最长递增链，返回递增链的最长长度
    public int process2(int x, int y, int[][] matrix) {
        // 在每一次递归调用是，都要去判断不能越界
        // 如果不是递增了，就直接返回0
        int up = !(x - 1 < 0 || x - 1 >= matrix.length) && matrix[x][y] < matrix[x - 1][y] ? process2(x - 1, y, matrix) : 0;
        int down = !(x + 1 < 0 || x + 1 >= matrix.length) && matrix[x][y] < matrix[x + 1][y] ? process2(x + 1, y, matrix) : 0;
        int left = !(y - 1 < 0 || y - 1 >= matrix[0].length) && matrix[x][y] < matrix[x][y - 1] ? process2(x, y - 1, matrix) : 0;
        int right = !(y + 1 < 0 || y + 1 >= matrix[0].length) && matrix[x][y] < matrix[x][y + 1] ? process2(x, y + 1, matrix) : 0;

        // 需要加1
        return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
    }

    // 2、记忆化搜索   直接根据暴力递归改
    public int longestIncreasingPath3(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];

        int maxCnt = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxCnt = Math.max(maxCnt, process3(i, j, matrix, dp));
            }
        }
        return maxCnt;
    }

    public int process3(int x, int y, int[][] matrix, int[][] dp) {
        if (dp[x][y] != 0) {
            return dp[x][y];
        }

        int up = !(x - 1 < 0 || x - 1 >= matrix.length) && matrix[x][y] < matrix[x - 1][y] ? process3(x - 1, y, matrix, dp) : 0;
        int down = !(x + 1 < 0 || x + 1 >= matrix.length) && matrix[x][y] < matrix[x + 1][y] ? process3(x + 1, y, matrix, dp) : 0;
        int left = !(y - 1 < 0 || y - 1 >= matrix[0].length) && matrix[x][y] < matrix[x][y - 1] ? process3(x, y - 1, matrix, dp) : 0;
        int right = !(y + 1 < 0 || y + 1 >= matrix[0].length) && matrix[x][y] < matrix[x][y + 1] ? process3(x, y + 1, matrix, dp) : 0;

        dp[x][y] = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
        return dp[x][y];
    }
}
