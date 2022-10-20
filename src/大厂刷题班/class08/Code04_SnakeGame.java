package 大厂刷题班.class08;

import java.util.Arrays;

// 动态规划的题目，有的时候直接看代码比看笔记图解更好理解，这道题就直接看代码反而更好理解
// 这道题是那种在递归调用入口就是要加一层循环，来收集所有位置的结果的动态规划
public class Code04_SnakeGame {
    // 1、暴力递归    上课现场写的版本
    public static int zuo(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                // 调用递归，看蛇从最左列的任意位置走到（i，j）结束能得到的最大值是多少
                Info cur = f(matrix, i, j);
                // 将在所有位置终止时能获得的最大值比较一下，求出最大值来就是答案
                ans = Math.max(ans, Math.max(cur.no, cur.yes));
            }
        }
        return ans;
    }

    // 递归返回两个信息，封装到Info类中
    public static class Info {
        // 不使用能力能获得的最大值
        public int no;
        // 使用一次能力能获得的最大值
        public int yes;

        public Info(int n, int y) {
            no = n;
            yes = y;
        }
    }

    // 蛇从某一个最左列，且最优的空降点降落
    // 沿途走到(i,j)必须停！
    // 返回，一次能力也不用，获得的最大成长值
    // 返回，用了一次能力，获得的最大成长值
    // 蛇的最大成长值不可能是负数，哪怕是这个matrix矩阵中都是负数，蛇还能用一次能力来让一个负数变成正数，然后蛇就不走了，就以此为最大成长值，所以下面我们可以将走不到某位置时，将返回值的yea和no都设置为-1
    // 如果蛇从某一个最左列，且最优的空降点降落，不用能力，怎么都到不了(i,j)，那么no = -1
    // 如果蛇从某一个最左列，且最优的空降点降落，用了一次能力，怎么都到不了(i,j)，那么yes = -1
    // 注意整个递归过程是逆序的，也就是从结束位置逆着向最左列的开始位置推
    public static Info f(int[][] matrix, int i, int j) {
        // basecase   到达最左列了，那么我们就认为到了空降起点了，开始返回
        if (j == 0) {
            // 计算使用能力和不使用能力能拿到的最大值
            // 如果他们得到的数小于等于-1，说明就走不到规定的结束位置，直接设置为-1
            int no = Math.max(matrix[i][0], -1);
            int yes = Math.max(-matrix[i][0], -1);
            // 返回
            return new Info(no, yes);
        }

        // j > 0 不在最左列，还没有到起点
        int preNo = -1;
        int preYes = -1;
        // 选择一：向左走
        Info pre = f(matrix, i, j - 1);
        // 收到向左走使用能力和不使用能力的最大值，然后分别比较取最大
        preNo = Math.max(pre.no, preNo);
        preYes = Math.max(pre.yes, preYes);
        // 选择二：如果上面还有路可走，就向左上走
        if (i > 0) {
            pre = f(matrix, i - 1, j - 1);
            // 收集向左上走使用能力和不使用能力的最大值，然后和之前的最大值比较
            preNo = Math.max(pre.no, preNo);
            preYes = Math.max(pre.yes, preYes);
        }
        // 选择三：如果下面还有路可走，就向左下走
        if (i < matrix.length - 1) {
            pre = f(matrix, i + 1, j - 1);
            // 收集向左下走使用能力和不使用能力的最大值，然后和之前的最大值比较
            preNo = Math.max(pre.no, preNo);
            preYes = Math.max(pre.yes, preYes);
        }

        // 将三个方向上收集上来的不使用能力和使用能力的最大值作为走到（i,j）位置结束能拿到的最大值
        // 1、走到（i,j）位置结束不使用能力能获得的最大值，直接用前面收集到的不使用能力的最大值加上matrix[i][j]即可
        int no = preNo == -1 ? -1 : (Math.max(-1, preNo + matrix[i][j]));
        // 2、走到（i,j）位置结束使用能力能获得的最大值。
        // 这个有两种情况：
        // （1）一个是在前面的位置已经使用了能力了，所以当前计算使用能力的最大值就用前面位置使用能力的最大值preYes加上matrix[i][j]原本的值
        // （2）另一个情况是前面的位置没有用能力，是在当前这个位置使用的能力，所以计算当前使用能力的最大值就是用前面位置不使用能力的最大值加上matrix[i][j]的相反数
        // 能力只有一次，是之前用的！
        int p1 = preYes == -1 ? -1 : (Math.max(-1, preYes + matrix[i][j]));
        // 能力只有一次，就当前用！
        int p2 = preNo == -1 ? -1 : (Math.max(-1, preNo - matrix[i][j]));
        // 取使用能力的两种情况的最大值
        int yes = Math.max(Math.max(p1, p2), -1);
        return new Info(no, yes);
    }


    // 2、暴力递归
    public static int walk1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int[] ans = process(matrix, i, j);
                res = Math.max(res, Math.max(ans[0], ans[1]));
            }
        }
        return res;
    }


    // 从假想的最优左侧到达(i,j)的旅程中
    // 0) 在没有使用过能力的情况下，返回路径最大和，没有可能到达的话，返回负
    // 1) 在使用过能力的情况下，返回路径最大和，没有可能到达的话，返回负
    public static int[] process(int[][] m, int i, int j) {
        if (j == 0) { // (i,j)就是最左侧的位置
            return new int[] { m[i][j], -m[i][j] };
        }
        int[] preAns = process(m, i, j - 1);
        // 所有的路中，完全不使用能力的情况下，能够到达的最好长度是多大
        int preUnuse = preAns[0];
        // 所有的路中，使用过一次能力的情况下，能够到达的最好长度是多大
        int preUse = preAns[1];
        if (i - 1 >= 0) {
            preAns = process(m, i - 1, j - 1);
            preUnuse = Math.max(preUnuse, preAns[0]);
            preUse = Math.max(preUse, preAns[1]);
        }
        if (i + 1 < m.length) {
            preAns = process(m, i + 1, j - 1);
            preUnuse = Math.max(preUnuse, preAns[0]);
            preUse = Math.max(preUse, preAns[1]);
        }
        // preUnuse 之前旅程，没用过能力
        // preUse 之前旅程，已经使用过能力了
        int no = -1; // 之前没使用过能力，当前位置也不使用能力，的最优解
        int yes = -1; // 不管是之前使用能力，还是当前使用了能力，请保证能力只使用一次，最优解
        if (preUnuse >= 0) {
            no = m[i][j] + preUnuse;
            yes = -m[i][j] + preUnuse;
        }
        if (preUse >= 0) {
            yes = Math.max(yes, m[i][j] + preUse);
        }
        return new int[] { no, yes };
    }

    // 2、动态规划
    public static int walk2(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        // 之所以是三维表，是因为虽然有两个可变参数，但是需要求出一个使用能力的和不使用能力的，也就相当于有两个二维表
        int[][][] dp = new int[matrix.length][matrix[0].length][2];
        // 赋初值
        for (int i = 0; i < dp.length; i++) {
            dp[i][0][0] = matrix[i][0];
            dp[i][0][1] = -matrix[i][0];
            max = Math.max(max, Math.max(dp[i][0][0], dp[i][0][1]));
        }
        // 给普遍位置赋值
        for (int j = 1; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                int preUnuse = dp[i][j - 1][0];
                int preUse = dp[i][j - 1][1];
                if (i - 1 >= 0) {
                    preUnuse = Math.max(preUnuse, dp[i - 1][j - 1][0]);
                    preUse = Math.max(preUse, dp[i - 1][j - 1][1]);
                }
                if (i + 1 < matrix.length) {
                    preUnuse = Math.max(preUnuse, dp[i + 1][j - 1][0]);
                    preUse = Math.max(preUse, dp[i + 1][j - 1][1]);
                }
                dp[i][j][0] = -1;
                dp[i][j][1] = -1;
                if (preUnuse >= 0) {
                    dp[i][j][0] = matrix[i][j] + preUnuse;
                    dp[i][j][1] = -matrix[i][j] + preUnuse;
                }
                if (preUse >= 0) {
                    dp[i][j][1] = Math.max(dp[i][j][1], matrix[i][j] + preUse);
                }
                max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
            }
        }
        return max;
    }

    // for test
    public static int[][] generateRandomArray(int row, int col, int value) {
        int[][] arr = new int[row][col];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int N = 7;
        int M = 7;
        int V = 10;
        int times = 1000000;
        for (int i = 0; i < times; i++) {
            int r = (int) (Math.random() * (N + 1));
            int c = (int) (Math.random() * (M + 1));
            int[][] matrix = generateRandomArray(r, c, V);
            int ans1 = zuo(matrix);
            int ans2 = walk2(matrix);
            if (ans1 != ans2) {
                for (int j = 0; j < matrix.length; j++) {
                    System.out.println(Arrays.toString(matrix[j]));
                }
                System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
                break;
            }
        }
        System.out.println("finish");
    }

}

