package 大厂刷题班.class29;

// 两种方法
// 方法一：动态规划
// 方法二：数学    排列组合（算阶乘的时候注意数据溢出问题）
// 这里只讲方法二
// https://leetcode.cn/problems/unique-paths/
public class Problem_0062_UniquePaths {
    // m 行
    // n 列
    // 下：m-1
    // 右：n-1
    public int uniquePaths(int m, int n) {
        // 下面这两个数举一个具体例子就能想出来是怎么算的
        // 向右一共需要走多少步
        int right = n - 1;
        // 从左上角到右下角一共需要走多少步
        int all = m + n - 2;

        // 求排列组合公式中的分子，为了避免溢出，这里用long
        long o1 = 1;
        // 求排列组合公式中的分母
        long o2 = 1;
        // 我们通过举一个具体的例子化简就可以知道，公式中分子是从right+1开始累乘的
        // 分母是从1开始累乘的，并且o1乘进去的个数 一定等于o2乘进去的个数
        // 计算C(right, all)
        for (int i = 1, j = right + 1; j <= all; i++, j++) {
            // 计算分子
            o1 *= j;
            // 计算坟墓
            o2 *= i;

            // 在计算阶乘的过程中要一直进行约分化简，因为分子是有可能溢出的
            // 得到o1和o2的最大公约数
            long gcd = gcd(o1, o2);
            // o1和o2同时除以他们的最大公约数，就相当于分子和分母进行约分
            o1 /= gcd;
            o2 /= gcd;
        }

        // 最后分母o2一定会被约分成1，所以最终返回分子o1就是排列组合公式的答案。注意要转换为int
        // 这里其实返回(int)(o1 / o2)也是对的
        return (int)o1;
    }

    // 辗转相除法求a和b的最大公约数
    public long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}
