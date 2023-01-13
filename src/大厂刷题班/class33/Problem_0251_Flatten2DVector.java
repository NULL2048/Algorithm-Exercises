package 大厂刷题班.class33;

// 数据结构设计题   设计迭代器    数据结构设计题外企考的多
// https://leetcode.cn/problems/flatten-2d-vector/
public class Problem_0251_Flatten2DVector {
    // 该迭代器就是可以实现按照从左到右，从上到下顺序着遍历二维数组
    public static class Vector2D {
        private int[][] matrix;
        // 当前遍历到的一维和二维坐标
        private int row;
        private int col;
        // 标识当前遍历到的位置是否已经输出过了
        private boolean curUse;

        public Vector2D(int[][] v) {
            matrix = v;
            row = 0;
            col = -1;
            curUse = true;
            hasNext();
        }

        public int next() {
            int ans = matrix[row][col];
            curUse = true;
            hasNext();
            return ans;
        }

        public boolean hasNext() {
            if (row == matrix.length) {
                return false;
            }
            if (!curUse) {
                return true;
            }
            // (row，col)用过了
            if (col < matrix[row].length - 1) {
                col++;
            } else {
                col = 0;
                do {
                    row++;
                } while (row < matrix.length && matrix[row].length == 0);
            }
            // 新的(row，col)
            if (row != matrix.length) {
                curUse = false;
                return true;
            } else {
                return false;
            }
        }

    }

}

