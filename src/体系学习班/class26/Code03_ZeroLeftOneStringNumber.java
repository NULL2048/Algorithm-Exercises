package 体系学习班.class26;

public class Code03_ZeroLeftOneStringNumber {
    // 贴瓷砖
    // 1、使用传统的递推方法，通过递归去求斐波那契数列
    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }
    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
    }
    // 2、也使用过递推去求斐波那契数列，但是这个是直接用迭代，没用递归
    public static int getNum2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int pre = 1;
        int cur = 1;
        int tmp = 0;
        for (int i = 2; i < n + 1; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }
    // 3、使用优化的方法去求指定位置的斐波那契数列，时间复杂度O(logN)
    public static int getNum3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        // 这个斐波那契额需要的二阶矩阵我们可以自己手算出来，毕竟这个是一个固定值。矩阵就存到一个二维数组中
        int[][] base = { { 1, 1 }, { 1, 0 } };
        // 计算等号左边的两个位置的数组成的矩阵
        int[][] res = matrixPower(base, n - 2);
        // 从求出的两个数中，只取我们要求的那一个
        return 2 * res[0][0] + res[1][0];
    }
    // 求指定位置的斐波那契数列的值
    public static int[][] matrixPower(int[][] m, int p) {
        // 最终求出来的结果矩阵
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // tmp是那个固定的，要自己乘自己的矩阵
        int[][] tmp = m;
        // p是要求的次方数
        // 不断右移
        for (; p != 0; p >>= 1) {
            // 只要是二进制位是1，就说明需要用此时自己和自己相乘得到的结果
            if ((p & 1) != 0) {
                // 如果二进制位是1，就把当前的tmp结果乘进res中
                res = product(res, tmp);
            }
            // 矩阵自己和自己相乘
            tmp = product(tmp, tmp);
        }
        // 返回最终的矩阵结果
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

    //题目：由0和1两种字符构成的达标字符串
    /*
        思路：字符串的开头肯定1，这是确定的，然后第二位可以选择是0或者1，第三位可以在第二位选0的时候选1
        也就是说我们可以设置递归思路这样，当前位选了1，然后返回有多少达标的字符串
    */
    //返回第一位是1时，后面N-1个字符串能够排列出合法字符串的个数
    //可以多列几项，看看这一项可不可以由前几项加工出来
    public static int fi(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // 这个问题使用的矩阵和斐波那契数列是一样的
        int[][] base = { { 1, 1 },
                { 1, 0 } };
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    public static void main(String[] args) {
        for (int i = 0; i != 20; i++) {
            System.out.println(getNum1(i));
            System.out.println(getNum2(i));
            System.out.println(getNum3(i));
            System.out.println("===================");
        }
    }
}