package 大厂刷题班.class33;
// https://leetcode.cn/problems/perfect-squares/
// 这种题其实在现在的面试上出现的越来越少了，因为没有区分度
// 数学结论   找规律    或者    动态规划
// 用数学的方法太难想了，最好还是直接用自然智慧，用动态规划做出来，课程讲了一下动态规划的解，但是讲的不细致，不行的话就去看力扣的题解帖子，或者先自己试着写一下
public class Problem_0279_PerfectSquares {
    // 1、暴力解
    public static int numSquares1(int n) {
        int res = n, num = 2;
        while (num * num <= n) {
            int a = n / (num * num), b = n % (num * num);
            res = Math.min(res, a + numSquares1(b));
            num++;
        }
        return res;
    }

    // 2、找规律
    // 1 : 1, 4, 9, 16, 25, 36, ...
    // 4 : 7, 15, 23, 28, 31, 39, 47, 55, 60, 63, 71, ...
    // 规律解
    // 规律一：个数不超过4
    // 规律二：如果这个数可以直接开平方，那么就直接返回1个
    // 规律三：任何数 % 8 == 7，那么一定是4个
    // 规律四：任何数消去4的因子之后，剩下rest，如果rest % 8 == 7，那么一定是4个
    public static int numSquares2(int n) {
        int rest = n;
        // 规律四
        while (rest % 4 == 0) {
            rest /= 4;
        }
        // 规律三和规律四
        if (rest % 8 == 7) {
            return 4;
        }
        // 规律二
        int f = (int) Math.sqrt(n);
        if (f * f == n) {
            return 1;
        }
        // 执行到这里，就排除了1个和4个的情况
        // 先去判断是不是2个，就尝试所有可能的左部分，进而再去验证剩余的右部分是不是完全平方数，找到了一种可行的方案就说明是2个
        // first：尝试其中的一个平方数为first * first
        for (int first = 1; first * first <= n; first++) {
            // 将剩余的数（n - first * first）开平方
            int second = (int) Math.sqrt(n - first * first);
            // 看两部分数first * first和second * second加和是否能得到n，能得到就说明由2个完全平方数组成，返回2
            if (first * first + second * second == n) {
                return 2;
            }
        }
        // 如果执行到这里，就只剩下3个的情况了，直接返回3
        return 3;
    }

    // 3、数学解
    // 1）四平方和定理
    // 2）该定理的结论和上面找规律得到的结论一致
    public static int numSquares3(int n) {
        while (n % 4 == 0) {
            n /= 4;
        }
        if (n % 8 == 7) {
            return 4;
        }
        for (int a = 0; a * a <= n; ++a) {
            // a * a +  b * b = n
            int b = (int) Math.sqrt(n - a * a);
            if (a * a + b * b == n) {
                return (a > 0 && b > 0) ? 2 : 1;
            }
        }
        return 3;
    }

    // 4、动态规划
    public int numSquares4(int n) {
        // 默认初始化值都为0
        int[] dp = new int[n + 1];
        // 求i的最少完全平方数个数
        for (int i = 1; i <= n; i++) {
            // 最坏的情况就是每次+1，所以i这个数最坏情况下的平方和个数就是i个，也就是1+1+1...=i
            dp[i] = i;
            // 尝试所有的完全平方数
            for (int j = 1; i - j * j >= 0; j++) {
                // 动态转移方程   这个也比较好理解，个数就是从dp[i]和dp[i - j * j] + 1选一个最小值
                // dp[i - j * j] + 1的含义：就是先看dp[i - j*j]所需要的最少的完全平方数格式，然后加1是指加的j*j这个数
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        for (int i = 1; i < 1000; i++) {
            System.out.println(i + " , " + numSquares1(i));
        }
    }
}

