package 体系学习班.class40;
// https://leetcode.cn/problems/rotate-image/
public class Code06_RotateMatrix {

    public static void rotate(int[][] matrix) {
        // 表示每一层左上角和右下角的坐标（a, b）和（c, d）
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        // 从外层依次向内层移动，所以左上角和右下角的下标需要同步加1和减1
        while (a < c) {
            rotateEdge(matrix, a++, b++, c--, d--);
        }
    }

    public static void rotateEdge(int[][] m, int a, int b, int c, int d) {
        int tmp = 0;
        // 这道题最简单的找出交换下标公式的方法就是直接用样例，列出来几个代表性位置的交换的下标变换，观察下标变换的规律就能总结出下标转换公式了。
        // 下面这个循环里面的下标变化公式，其实就是通过具体的例子总结出来的，通过具体例子我们就能发现下标交换不管是什么位置的数，只要是同一层的，他们横坐标或者纵坐标加或减的数都是同一个
        // d - b表示每一层要交换的次数（d - b的结果等于边长减1），假设此时这一层边长为n，那么每一组数字交换完一圈就需要交换n-1次
        for (int i = 0; i < d - b; i++) {
            // 虽然题目要求是顺时针旋转，但是我们在交换的时候是按照逆时针的顺序来进行交换操作的
            tmp = m[a][b + i];
            // 通过一个坐标加或者减i，就可以实现依次交换同一行或者同一列的所有位置的数
            m[a][b + i] = m[c - i][b];
            m[c - i][b] = m[c][d - i];
            m[c][d - i] = m[a + i][d];
            m[a + i][d] = tmp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);

    }

}