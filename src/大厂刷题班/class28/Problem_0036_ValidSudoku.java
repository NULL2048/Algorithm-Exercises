package 大厂刷题班.class28;
// 模拟   数组    矩阵
// https://leetcode.cn/problems/valid-sudoku/
public class Problem_0036_ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        // row[x][y]：y这个数在第x行是否出现过
        boolean[][] row = new boolean[9][10];
        // col[x][y]：y这个数在第x列是否出现过
        boolean[][] col = new boolean[9][10];
        // bucket[x][y]：y这个数在第x号宫格内是否出现过
        boolean[][] bucket = new boolean[9][10];

        // 开始遍历整个矩阵
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 当前遍历到的位置填写了数字
                if (board[i][j] != '.') {
                    // 取得该位置的数字
                    int num = board[i][j] - '0';
                    // 计算当前数字所在的3*3宫格的编号
                    int bid = (i / 3) * 3 + (j / 3); // 整个式子的含义是比较好理解的，计算出来的行要乘3，计算出来的列直接加进去
                    // 只要是列、行、宫格内曾经出现过这个数，直接返回false
                    if (row[i][num] || col[j][num] || bucket[bid][num]) {
                        return false;
                    }

                    // 记录上num这个数已经出现过了
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                }
            }
        }

        // 如果执行完了循环，则说明整个矩阵符合要求，返回true
        return true;
    }
}
