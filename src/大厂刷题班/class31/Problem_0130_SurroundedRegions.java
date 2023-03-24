package 大厂刷题班.class31;
// 深度优先遍历    递归   矩阵    这道题据类似于体系学习班15节并查集章节里讲过的岛问题，核心技巧就是感染过程怎么写
// https://leetcode.cn/problems/surrounded-regions/
public class Problem_0130_SurroundedRegions {
//	// m -> 二维数组， 不是0就是1     感染函数
//	public static void infect(int[][] m, int i, int j) {
//		if (i < 0 || i == m.length || j < 0 || j == m[0].length || m[i][j] != 1) {
//			return;
//		}
//		// m[i][j] == 1
//		m[i][j] = 2;
//		infect(m, i - 1, j);
//		infect(m, i + 1, j);
//		infect(m, i, j - 1);
//		infect(m, i, j + 1);
//	}

    // 1、暴力解    左神的代码
    public static void solve1(char[][] board) {
        boolean[] ans = new boolean[1];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') {
                    ans[0] = true;
                    can(board, i, j, ans);
                    board[i][j] = ans[0] ? 'T' : 'F';
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                char can = board[i][j];
                if (can == 'T' || can == 'F') {
                    board[i][j] = '.';
                    change(board, i, j, can);
                }
            }
        }

    }

    public static void can(char[][] board, int i, int j, boolean[] ans) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
            ans[0] = false;
            return;
        }
        if (board[i][j] == 'O') {
            board[i][j] = '.';
            can(board, i - 1, j, ans);
            can(board, i + 1, j, ans);
            can(board, i, j - 1, ans);
            can(board, i, j + 1, ans);
        }
    }

    public static void change(char[][] board, int i, int j, char can) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length) {
            return;
        }
        if (board[i][j] == '.') {
            board[i][j] = can == 'T' ? 'X' : 'O';
            change(board, i - 1, j, can);
            change(board, i + 1, j, can);
            change(board, i, j - 1, can);
            change(board, i, j + 1, can);
        }
    }


    // 2、最优解   我自己写的代码
    // 从边界开始感染的方法，比第一种方法更好
    public void solve(char[][] board) {
        // 将连到上下两个边界的连成一片的O都感染成F
        for (int j = 0; j < board[0].length; j++) {
            if (board[0][j] == 'O') {
                infect(board, 0, j);
            }

            if (board[board.length - 1][j] == 'O') {
                infect(board, board.length - 1, j);
            }
        }

        // 将连到左右两个边界的连成一片的O都感染成F
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                infect(board, i, 0);
            }

            if (board[i][board[0].length - 1] == 'O') {
                infect(board, i, board[0].length - 1);
            }
        }

        // 执行到这里，所有连接到边界的连成一片的O都改成了F，此时矩阵中仍然为O的，一定都是被X包围的

        // 遍历矩阵，将此时矩阵中为O的改成X，为F的再改回O，完成题目要求
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'F') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    // 通过递归来进行感染过程，从(i,j)开始将连成一片的O改成F
    public void infect(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != 'O') {
            return;
        }

        board[i][j] = 'F';
        infect(board, i + 1, j);
        infect(board, i - 1, j);
        infect(board, i, j + 1);
        infect(board, i, j - 1);
    }
}
