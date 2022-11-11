package 大厂刷题班.class17;
// 测试链接：https://leetcode.cn/problems/search-a-2d-matrix-ii/submissions/
// 二分 矩阵  数组  模拟
// 从右上角或者左下角开始走都可以
public class Code01_FindNumInSortedMatrix {
    public boolean searchMatrix(int[][] matrix, int target) {
        // 从右上角开始
        int row = 0;
        int col = matrix[0].length - 1;

        // 如果下标越界了还没有找到target，说明矩阵中没有这个数
        while (col >= 0 && row < matrix.length) {
            // 找到返回true
            if (matrix[row][col] == target) {
                return true;
            }

            // 大于target就向左移动一个位置
            if (matrix[row][col] > target) {
                col--;
            // 小于target就向下移动一个位置
            } else {
                row++;
            }
        }

        return false;
    }
}
