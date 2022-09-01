package 体系学习班.class26;

public class Code02_FibonacciProblem {

    // 1、使用传统的递推方法，通过递归去求斐波那契数列
    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return f1(n - 1) + f1(n - 2);
    }

    // 2、也使用过递推去求斐波那契数列，但是这个是直接用迭代，没用递归
    public static int f2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int res = 1;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    // 3、使用优化的方法去求指定位置的斐波那契数列，时间复杂度O(logN)
    public static int f3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // 这个斐波那契额需要的二阶矩阵我们可以自己手算出来，毕竟这个是一个固定值。矩阵就存到一个二维数组中
        // [ 1 ,1 ]
        // [ 1, 0 ]
        int[][] base = {
                { 1, 1 },
                { 1, 0 }
        };
        // 计算等号左边的两个位置的数组成的矩阵
        int[][] res = matrixPower(base, n - 2);
        // 从求出的两个数中，只取我们要求的那一个
        return res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] m, int p) {
        // 最终求出来的结果矩阵
        int[][] res = new int[m.length][m[0].length];
        // 计算单位矩阵
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // 此时res = 矩阵中的1

        // t是那个固定的，要自己乘自己的矩阵
        int[][] t = m;// 矩阵1次方
        // p是要求的次方数
        // 不断右移
        for (; p != 0; p >>= 1) {
            // 只要是二进制位是1，就说明需要用此时自己和自己相乘得到的结果
            if ((p & 1) != 0) {
                // 如果二进制位是1，就把当前的t结果乘进res中
                res = product(res, t);
            }
            // 矩阵自己和自己相乘
            t = product(t, t);
        }
        return res;
    }

    // 两个矩阵乘完之后的结果返回
    public static int[][] product(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length; // a的列数同时也是b的行数
        int[][] ans = new int[n][m];
        // 矩阵相乘的步骤
        for(int i = 0 ; i < n; i++) {
            for(int j = 0 ; j < m;j++) {
                for(int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }


    // 后面是别的例子，只看上面斐波那契数列的即可
    public static int s1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        return s1(n - 1) + s1(n - 2);
    }

    public static int s2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int res = 2;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    public static int s3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = { { 1, 1 }, { 1, 0 } };
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static int c1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return c1(n - 1) + c1(n - 3);
    }

    public static int c2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int res = 3;
        int pre = 2;
        int prepre = 1;
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 4; i <= n; i++) {
            tmp1 = res;
            tmp2 = pre;
            res = res + prepre;
            pre = tmp1;
            prepre = tmp2;
        }
        return res;
    }

    public static int c3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int[][] base = {
                { 1, 1, 0 },
                { 0, 0, 1 },
                { 1, 0, 0 } };
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    public static void main(String[] args) {
        int n = 19;
        System.out.println(f1(n));
        System.out.println(f2(n));
        System.out.println(f3(n));
        System.out.println("===");

        System.out.println(s1(n));
        System.out.println(s2(n));
        System.out.println(s3(n));
        System.out.println("===");

        System.out.println(c1(n));
        System.out.println(c2(n));
        System.out.println(c3(n));
        System.out.println("===");

    }

}

