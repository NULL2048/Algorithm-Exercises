package 体系学习班.class40;

import java.io.*;

public class Code07_ZigZagPrintMatrix {
    public static void printMatrixZigZag(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = 0;
        int dC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean fromUp = false;
        while (tR != endR + 1) {
            printLevel(matrix, tR, tC, dR, dC, fromUp);
            tR = tC == endC ? tR + 1 : tR;
            tC = tC == endC ? tC : tC + 1;
            dC = dR == endR ? dC + 1 : dC;
            dR = dR == endR ? dR : dR + 1;
            fromUp = !fromUp;
        }
        System.out.println();
    }

    public static void printLevel(int[][] m, int tR, int tC, int dR, int dC, boolean f) {
        if (f) {
            while (tR != dR + 1) {
                System.out.print(m[tR++][tC--] + " ");
            }
        } else {
            while (dR != tR - 1) {
                System.out.print(m[dR--][dC++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        printMatrixZigZag(matrix);

    }


    // 下面是代码的另一种版本，我都写了注释
    private static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    private static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    /**
     * 之字形输出
     * @param arr
     */
    public static void printZhi(int[][] arr) {
        // 初始化A和B指向的初始值
        // 以后再有这种二维数组的题，一定要分清x和y在数组中到底指的是什么，不要把数组和数学中的坐标系混在一起，x表示的是行，是数组中的一维，y表示的是列，是数组中的二维
        int xa = 0;
        int ya = 0;
        int xb = 0;
        int yb = 0;
        // 表示当前输出是从A向B输出，还是从B向A输出
        boolean flag = false;
        while (true) {
            print(arr, xa, xb, ya, yb, flag);
            // 进行下标变换   A指向在没有到右边界之前一直向右移动，当碰到右边界后再向下移动
            // arr[0].length这种写法就是用来获得二维数组有多少列
            xa = ya == arr[0].length - 1 ? xa + 1 : xa;
            ya = ya == arr[0].length - 1 ? ya : ya + 1;
            // B指向在没有碰到下边界之前一直向下移动，当碰到下边界后再向右移动
            // arr.length这种写法就是用来获得二维数组有多少行
            yb = xb == arr.length - 1 ? yb + 1 : yb;
            xb = xb == arr.length - 1 ? xb : xb + 1;
            // 两个指向交会在一起一定走了相同的步数
            if (xa == xb && ya == yb) {
                out.print(arr[xa][ya]);
                break;
            }
            // 转换输出方向
            flag = !flag;
        }
    }

    /**
     * 输出
     * @param arr
     * @param xa
     * @param xb
     * @param ya
     * @param yb
     * @param flag
     */
    public static void print(int[][] arr, int xa, int xb, int ya, int yb, boolean flag) {
        if (flag) {
            // 当A指向已经越过B指向则结束输出
            while (ya >= yb && xa <= xb) {
                // 遍历输出，同时移动A指向
                out.print(arr[xa++][ya--] + " ");
            }
        } else {
            // 当B指向已经越过A指向则结束输出
            while (yb <= ya && xb >= xa) {
                // 遍历输出，同时移动B指向
                out.print(arr[xb--][yb++] + " ");
            }
        }
    }


}
