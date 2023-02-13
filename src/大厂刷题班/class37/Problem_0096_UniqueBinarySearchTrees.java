package 大厂刷题班.class37;
// 卡特兰数  找规律  排列组合  最大公因数  辗转相除法
// https://leetcode.cn/problems/unique-binary-search-trees/
public class Problem_0096_UniqueBinarySearchTrees {
    public int numTrees(int n) {
        return compute(n);
    }

    public int compute(int N) {
        // 过滤特殊值
        if (N < 0) {
            return 0;
        }
        if (N < 2) {
            return 1;
        }

        long a = 1;
        long b = 1;
        long c = 0;
        // 2n
        long limit = N << 1;
        // 1、计算c(2N, N) = b / a
        for (long j = 1, i = N + 1; j <= N && i <= limit; j++, i++) {
            // 计算a：从1累乘到n
            a *= j;
            // 计算b：从n+1一直累乘到2n
            b *= i;

            // 求a和b的最大公因数，用来对a和b进行分数化简，避免在计算过程中溢出。
            // 如果这里不进行化简的话，当N比较大的时候就会出现数据溢出情况
            c = gcd(a, b);
            a /= c;
            b /= c;
        }

        // 2、计算公式3的计算结果
        // 公式3：k(n)= c(2n, n) / (n + 1)
        // c(n, m) = n(n-1)...(n-m+1) / m!
        // b：2n * (2n - 1) * ... * (2n - n + 1)   也就是从n+1一直累乘到2n
        // a：1 * 2 * ... * (n - 1) * n   也就是从1一直累乘到n
        // c(2N, N) = b / a
        return (int) ((b / a) / (N + 1));
    }

    // 辗转相除法求最大公因数
    public long gcd(long a, long b) {
        long c = a % b;
        if (c != 0) {
            return gcd(b, c);
        } else {
            return b;
        }
    }
}
