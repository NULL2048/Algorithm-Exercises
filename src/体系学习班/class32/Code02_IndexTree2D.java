package 体系学习班.class32;
// IndexTree
// 测试链接：https://leetcode.cn/problems/range-sum-query-2d-mutable
// 但这个题是付费题目
// 提交时把类名、构造函数名从Code02_IndexTree2D改成NumMatrix
public class Code02_IndexTree2D {
    private int[][] tree;
    // 记录哪些位置更新成了哪些数，在这道题中并没有用。
    private int[][] nums;
    private int N;
    private int M;

    public Code02_IndexTree2D(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        N = matrix.length;
        M = matrix[0].length;
        tree = new int[N + 1][M + 1];
        nums = new int[N][M];
        // 通过update方法构造IndexTree，利用原始数组中的数，构造IndexTree
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    // 求原数组matrix中以1行1列做左上角，i行j列做右下角这一整块的累加和
    private int sum(int row, int col) {
        // 记录累加和
        int sum = 0;
        // 利用我们笔记中讲到的二维求累加和的规律，将所有设计的数都类加进去
        for (int i = row + 1; i > 0; i -= i & (-i)) {
            for (int j = col + 1; j > 0; j -= j & (-j)) {
                sum += tree[i][j];
            }
        }
        return sum;
    }

    // 更新操作，原始数组的数更新，需要将所有受影响的辅助数组位置的数也进行相应的更新
    public void update(int row, int col, int val) {
        if (N == 0 || M == 0) {
            return;
        }
        // 这里我们就相当于把更新操作改为累加操作，看原数增加多少能得到最重要更新为的数
        int add = val - nums[row][col];
        nums[row][col] = val;
        // 修改辅助数组中受影响的位置
        for (int i = row + 1; i <= N; i += i & (-i)) {
            for (int j = col + 1; j <= M; j += j & (-j)) {
                tree[i][j] += add;
            }
        }
    }

    // 求任意左上角（row1,col1），到任意一个右下角（row2,col2）的点框住的所有值的累加和
    // 这道题最终是调用的这个方法，看这个方法的返回值对不对。
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (N == 0 || M == 0) {
            return 0;
        }
        // 就是剪掉两个多余部分后，还需要将多减的那一块再加回去。画图就能看出来，笔记中有画图。
        return sum(row2, col2) + sum(row1 - 1, col1 - 1) - sum(row1 - 1, col2) - sum(row2, col1 - 1);
    }

}

