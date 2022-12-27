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

    // 2、也使用过递推去求斐波那契数列，但是这个是直接用迭代，没用递归，线性求解方法
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

    // 3、使用优化的方法去求指定位置的斐波那契数列，时间复杂度O(logN)。（矩阵+快速幂）
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
                {1, 1},
                {1, 0}
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
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }


    // 后面是别的例子
    // 下面这道题是跳台阶问题的解题方法，和斐波那契数列差不多
    // https://leetcode.cn/problems/climbing-stairs/   这道题就是很经典的爬楼梯问题，可以直接用斐波那契数列的代码提交
    // 1、利用递推关系，通过递归求
    public static int s1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        return s1(n - 1) + s1(n - 2);
    }

    // 2、利用递推关系，线性求解方法
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

    // 3、利用优化后的方法求
    /**
     * 思路：这里先进行推理：
     * 1.  一个台阶只有一种爬法
     * 2.  两个台阶有两种爬法
     * 3.  三个台阶等于先爬到第一个台阶然后直接爬两步到第三级台阶加上先爬到第二个台阶然后直接爬异步到第三级台阶的和
     *     那就是1+2=3
     *          ....
     * n. F(N) = F(N-1)+F(N-2),这就是和斐波那契数列一样的二阶问题
     */
    public static int s3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    // 下面这个就是奶牛生小牛问题的解题方法
    // 1、利用递推关系，通过递归求
    public static int c1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return c1(n - 1) + c1(n - 3);
    }

    // 2、利用递推关系，通过迭代求
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

    // 3、利用优化后的方法求
    /*
        思路：先进行推理
        第一年：A(成熟)-->1个
        第二年：A(成熟)，B-->2个
        第三年：A(成熟)，B，C-->3个
        第四年：A(成熟)，B(成熟)，C，D-->4个
        第五年：A(成熟)，B(成熟)，C(成熟)，D，E，F-->6个
        第六年：A(成熟)，B(成熟)，C(成熟)，D(成熟)，E，F，G，H，I-->9个
        经过推演，可以发现第N年的牛的个数F(N)=F(N-1)+0*F(N-2)+F(N-3)
        就是去年出生的今年还会活着，而三年前就已经出生的，现在肯定是成熟的，可以生新的小牛
        |F(N),F(N-1),F(N-2)| = |F(3),F(2),F(1)|*|3*3|^(N-3)
        这是一个三阶问题
        我发现在计算base矩阵的时候，那些个矩阵中的值不是0就是1，有时候可以合理猜测节省解方程时间
    */
    public static int c3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        // 这个就是这个递推式求出来的矩阵，这个矩阵是需要我们自己手算的，列几个等式解方程
        int[][] base = {
                {1, 1, 0},
                {0, 0, 1},
                {1, 0, 0}};
        int[][] res = matrixPower(base, n - 3);
        // 根据等式来求出第n项的值
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

