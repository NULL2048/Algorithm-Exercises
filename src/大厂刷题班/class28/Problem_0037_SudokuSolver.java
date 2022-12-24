package 大厂刷题班.class28;
// 暴力递归  尝试  深度优先遍历
// https://leetcode.cn/problems/sudoku-solver/
public class Problem_0037_SudokuSolver {
    public void solveSudoku(char[][] board) {
        // row[x][y]：y这个数在第x行是否出现过
        boolean[][] row = new boolean[9][10];
        // col[x][y]：y这个数在第x列是否出现过
        boolean[][] col = new boolean[9][10];
        // bucket[x][y]：y这个数在第x号宫格内是否出现过
        boolean[][] bucket = new boolean[9][10];
        // 初始化上面的三个数组
        initMaps(board, row, col, bucket);

        // 开始暴力递归尝试所有答案，只要找到了一种答案就返回
        process(board, 0, 0, row, col, bucket);
    }

    // 初始化三个辅助数组
    public void initMaps(char[][] board, boolean[][] row, boolean[][] col, boolean[][] bucket) {
        // 开始遍历数独表，构造辅助数组
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    int bid = (i / 3) * 3 + (j / 3);
                    // 标记该数出现在的位置
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                }
            }
        }
    }

    // DFS
    // 当前来到(i,j)这个位置，
    // 如果已经有数字，跳到下一个位置上
    // 如果没有数字，尝试1~9，不能和row、col、bucket冲突
    public boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket) {
        // 如果行数已经越界，说明已经尝试完了所有位置，返回true
        if (i > 8) {
            return true;
        }

        // 当离开(i，j)，应该去哪？(nexti, nextj)
        // 要通过j是否走到结尾了来决定是不是i要向下走一行，j是否要重新回到0
        int nexti = j < 8 ? i : i + 1;
        int nextj = j < 8 ? j + 1 : 0;
        // 如果当前位置不是空，就继续尝试下一个位置
        if (board[i][j] != '.') {
            return process(board, nexti, nextj, row, col, bucket);
            // 当前位置是空，我们来尝试1~9
        } else {
            // 计算当前数字所在的3*3宫格的编号
            int bid = (i / 3) * 3 + (j / 3);
            // 在该位置尝试填写1~9，看能不能符合要求
            for (int num = 1; num <= 9; num++) {
                // 如果尝试的数字在该行、列、宫格内从未出现，说明符合要求
                if (!row[i][num] && !col[j][num] && !bucket[bid][num]) {
                    // 可以尝试num
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                    // 将num填到board中
                    board[i][j] = (char)(num + '0');

                    // 继续递归尝试下一个位置，如果后面都是成立的，就说明找到了一种解法，直接返回true。不再执行后续的尝试，因为题目保证了输入数独仅有一个解
                    if (process(board, nexti, nextj, row, col, bucket)) {
                        return true;
                    }

                    // 恢复现场
                    row[i][num] = false;
                    col[j][num] = false;
                    bucket[bid][num] = false;
                    board[i][j] = '.';
                }
            }
        }

        // 如果尝试完了1~9也没有返回true，说明没有符合条件的解，直接返回false
        return false;
    }
}
