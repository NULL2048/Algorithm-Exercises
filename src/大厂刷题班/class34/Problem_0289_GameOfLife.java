package 大厂刷题班.class34;

// 有关这个游戏更有意思、更完整的内容：
// https://www.bilibili.com/video/BV1rJ411n7ri
// 也推荐这个up主
// 数组  矩阵  位运算
// https://leetcode.cn/problems/game-of-life/
public class Problem_0289_GameOfLife {
    public void gameOfLife(int[][] board) {
        int m = board[0].length;
        int n = board.length;
        // 遍历所有位置，根据每一个位置周围1的数量来判断这个细胞是活还是死
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int curOneCnt = getOneCnt(board, i, j);
                // 利用位信息，就可以不使用辅助数组了，同时保留旧信息和新信息
                // 如果周围有3个1，那么这个细胞下一步已经活
                if (curOneCnt == 3) {
                    // 用末位的前一位来标识下一步的状态信息，所以和2取或运算
                    board[i][j] |= 2;
                    // 如果当前位置细胞是活的，并且它的周围有2个活细胞，那么下一步他也是活的
                } else if (curOneCnt == 2 && board[i][j] == 1) {
                    board[i][j] |= 2;
                }

                // 除了上面的情况以外，其他全部都是死
            }
        }

        // 将每一个位置的信息右移一位，将旧信息抹去，只保留新信息
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                board[i][j] >>= 1;
            }
        }
    }

    // 统计(i,j)位置周围有多少个1
    public int getOneCnt(int[][] board, int i, int j) {
        return check(board, i - 1, j - 1) +
                check(board, i - 1, j) +
                check(board, i - 1, j + 1) +
                check(board, i, j - 1) +
                check(board, i, j + 1) +
                check(board, i + 1, j - 1) +
                check(board, i + 1, j) +
                check(board, i + 1, j + 1);
    }

    public int check(int[][] board, int i, int j) {
        int m = board[0].length;
        int n = board.length;
        // 如果当前位置不越界，并且这个位置的末位信息是1，那么就说明这个位置的细胞是活的，返回1，否则返回0
        return i >= 0 && i < n && j >= 0 && j < m && (board[i][j] & 1) == 1 ? 1 : 0;
    }
}
