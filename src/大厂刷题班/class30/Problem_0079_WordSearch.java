package 大厂刷题班.class30;
// 回溯   递归  深度优先遍历   矩阵   数组
// https://leetcode.cn/problems/word-search/
public class Problem_0079_WordSearch {
    public boolean exist(char[][] board, String word) {
        // 字符串转换成字符数组，方便操作
        char[] w = word.toCharArray();
        // 尝试以矩阵中每一个字符作为开始位置，看能不能走出word这个单词
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 只要是找到了一种可以走出word这个单词的情况，直接返回true
                if (dfs(board, i, j, w, 0)) {
                    return true;
                }
            }
        }
        // 如果所有情况都尝试了一遍，还是没有返回true，就说明无法走出word，返回false
        return false;
    }

    // 深度递归
    // 目前到达了board[i][j]，word[index....]
    // 从board[i][j]出发，能不能搞定word[index....]
    // 能搞定word就返回true，否则就false
    public boolean dfs(char[][] board, int i, int j, char[] w, int index) {
        // basecase
        // 已经将w[0~w.length-1]都匹配出来了，说明已经凑出word来了，返回true
        if (index == w.length) {
            return true;
        }

        // w还有字符没有匹配出来，但是此时(i,j)越界了，直接返回false，说明此路不通
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return false;
        }

        // 如果当前来到的位置和要匹配的字符w[index]不一样，说明当前这个路线也匹配不出word，返回false
        if (board[i][j] != w[index]) {
            return false;
            // board[i][j] == w[index]，就继续向后尝试匹配w[index+1...]后缀字符串
        } else {
            // 我们需要先将board[i][j]设置为0，为了避免走回头路
            char temp = board[i][j];
            // 标记已经走过的路
            board[i][j] = 0;
            // 尝试四个方向的路线，只要有一个为true，那么整个结果就是true
            boolean ans = dfs(board, i, j + 1, w, index + 1) || dfs(board, i, j - 1, w, index + 1) ||
                    dfs(board, i + 1, j, w, index + 1) || dfs(board, i - 1, j, w, index + 1);
            // 尝试完了之后要恢复现场，因为还会有以别的位置为开始点的尝试
            board[i][j] = temp;
            // 返回答案
            return ans;
        }
    }
}
