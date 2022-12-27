package 大厂刷题班.class29;
// 数组   矩阵   模拟
// https://leetcode.cn/problems/set-matrix-zeroes/
public class Problem_0073_SetMatrixZeroes {
    // 方法三：最优解
    public void setZeroes(int[][] matrix) {
        // 单独标记第0列是否要全都变为0
        boolean col0 = false;

        // 后面我们就用col0标记第0列是否全变为0
        // matrix[0~n-1][0]标记0~n-1行是否全变为0
        // matrix[0][1~n-1]标记1~n-1列是否全变为0

        // 遍历矩阵
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                // 如果一个位置的数为0
                if (matrix[i][j] == 0) {
                    // 先去标记所在行是否全部变为0
                    // 这个数所在的行一定全都变为0，将0列对应的行设置为0
                    matrix[i][0] = 0;

                    // 再去标记所在列是否全部变为0
                    // 如果当前遍历的是第0列的，就用col变量单独标记
                    if (j == 0) {
                        col0 = true;
                        // 如果当前遍历的不是第0列的数，就用 matrix[0][1~n-1]去标记
                    } else {
                        matrix[0][j] = 0;
                    }
                }
            }
        }

        // 先按照matrix[0~n-1][0]和matrix[0][1~n-1]的标记结果，将0~n-1行和1~n-1列去根据标记情况都设置为0
        // 注意需要从下往上变0，因为第0行是用来标记所在列是否都变为0的，如果从上往下变，一开始就会将第0行的标记信息抹掉，就会导致结果错误，第0行的标记要保留到最后
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // 单独处理第0列
        if (col0) {
            // 将第0列都变为0
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    // 方法二：次优解，但是是最好理解，代码最容易写的版本
    public static void setZeroes1(int[][] matrix) {
        boolean row0Zero = false;
        boolean col0Zero = false;
        int i = 0;
        int j = 0;
        for (i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                row0Zero = true;
                break;
            }
        }
        for (i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                col0Zero = true;
                break;
            }
        }
        for (i = 1; i < matrix.length; i++) {
            for (j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (i = 1; i < matrix.length; i++) {
            for (j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (row0Zero) {
            for (i = 0; i < matrix[0].length; i++) {
                matrix[0][i] = 0;
            }
        }
        if (col0Zero) {
            for (i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
