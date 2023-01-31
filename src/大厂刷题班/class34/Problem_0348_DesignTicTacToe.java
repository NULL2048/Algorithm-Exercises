package 大厂刷题班.class34;

// 模拟
// https://leetcode.cn/problems/design-tic-tac-toe/
public class Problem_0348_DesignTicTacToe {
    class TicTacToe {
        // 记录每个人在每一行上下了几个
        private int[][] rows;
        // 记录每个人在每一列上下了几个
        private int[][] cols;
        // 记录每个人在左对角线下了几个
        private int[] leftUp;
        // 记录每个人在右对角线下了几个
        private int[] rightUp;
        // 棋盘
        private boolean[][] matrix;
        // 棋盘大小 N*N
        private int N;

        public TicTacToe(int n) {
            // rows[a][1] : 1这个人，在a行上，下了几个
            // rows[b][2] : 2这个人，在b行上，下了几个
            rows = new int[n][3]; //0 1 2
            cols = new int[n][3];
            // leftUp[2] = 7 : 2这个人，在左对角线上，下了7个
            leftUp = new int[3];
            // rightUp[1] = 9 : 1这个人，在右对角线上，下了9个
            rightUp = new int[3];
            matrix = new boolean[n][n];
            N = n;
        }

        // player号玩家在（row,col）位置下了一个位置后，返回谁能赢
        public int move(int row, int col, int player) {
            // 如果这个位置已经有棋了，则直接返回0，这一次是无效的
            if (matrix[row][col]) {
                return 0;
            }
            // 将棋盘该位置设置为true，标识上面已经放了棋了
            matrix[row][col] = true;
            // 修改player的下棋数信息
            rows[row][player]++;
            cols[col][player]++;
            // 如果下棋的位置是对角线，也要修改player玩家的对角线下棋信息
            if (row == col) {
                leftUp[player]++;
            }
            if (row + col == N - 1) {
                rightUp[player]++;
            }
            // 如果player玩家在任何一个行、列、对角线都下满了（下的棋子数到达N了），就说明这个玩家赢了
            if (rows[row][player] == N || cols[col][player] == N || leftUp[player] == N || rightUp[player] == N) {
                return player;
            }
            // 否则返回0。只有当前下棋的人才有可能赢，除此之外只可能是暂时没人赢
            return 0;
        }
    }
}
