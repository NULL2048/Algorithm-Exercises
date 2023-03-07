package 体系学习班.class40;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.cn/problems/spiral-matrix/
public class Code05_PrintMatrixSpiralOrder {
    // 1、直接输出的版本
    public static void spiralOrderPrint(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    public static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
        if (tR == dR) {
            for (int i = tC; i <= dC; i++) {
                System.out.print(m[tR][i] + " ");
            }
        } else if (tC == dC) {
            for (int i = tR; i <= dR; i++) {
                System.out.print(m[i][tC] + " ");
            }
        } else {
            int curC = tC;
            int curR = tR;
            while (curC != dC) {
                System.out.print(m[tR][curC] + " ");
                curC++;
            }
            while (curR != dR) {
                System.out.print(m[curR][dC] + " ");
                curR++;
            }
            while (curC != tC) {
                System.out.print(m[dR][curC] + " ");
                curC--;
            }
            while (curR != tR) {
                System.out.print(m[curR][tC] + " ");
                curR--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);

    }

    // 2、将输出的答案存储到List的版本，这个可以直接提交力扣
    public List<Integer> spiralOrder(int[][] matrix) {
        // 表示每一层左上角和右下角的坐标（tR, tC）和（dR, dC）
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        List<Integer> ans = new ArrayList<>();
        // 从外层依次向内层移动，所以左上角和右下角的下标需要同步加1和减1
        // 因为这里并不一定是正方形矩阵，所以条件要限定tR <= dR && tC <= dC，因为有可能长 > 宽，也有可能宽 > 长
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--, ans);
        }

        return ans;
    }

    public static void printEdge(int[][] m, int tR, int tC, int dR, int dC, List<Integer> ans) {
        // 这道题也是用一个具体的例子，来根据输出的下标变化来找规律的，普遍情况下就就是写四个并列的循环就可以了，但是需要讨论一下最后一层凑不出来一个环的情况
        // 最后一层只剩下一行了
        if (tR == dR) {
            for (int i = tC; i <= dC; i++) {
                ans.add(m[tR][i]);
            }
        // 最后一层只剩下一列了
        } else if (tC == dC) {
            for (int i = tR; i <= dR; i++) {
                ans.add(m[i][tC]);
            }
        // 普通的情况
        } else {
            int curC = tC;
            int curR = tR;
            // 四个并列的循环，把一个边一起输出
            while (curC != dC) {
                ans.add(m[tR][curC]);
                curC++;
            }
            while (curR != dR) {
                ans.add(m[curR][dC]);
                curR++;
            }
            while (curC != tC) {
                ans.add(m[dR][curC]);
                curC--;
            }
            while (curR != tR) {
                ans.add(m[curR][tC]);
                curR--;
            }
        }
    }

}
