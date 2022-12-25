package 大厂刷题班.class29;
// 数学  快速幂    位运算（Java数据类型的范围处理，防止溢出，注意区分负数和正数的数据范围区别，负数的范围比正数范围多一个）
// https://leetcode.cn/problems/powx-n/
public class Problem_0050_PowXN {
    // 1、只考虑正整数的题解
    public static int pow(int a, int n) {
        int ans = 1;
        int t = a;
        while (n != 0) {
            if ((n & 1) != 0) {
                ans *= t;
            }
            t *= t;
            n >>= 1;
        }
        return ans;
    }

    // 2、x的n次方，n可能是负数，x为double   下面这个代码是力扣的解
    public double myPow(double x, int n) {
        // n==0，直接返回1
        if (n == 0) {
            return 1D;
        }

        // 先将n取绝对值，因为如果n为负数，我们需要先按照正数的求解方法求出一个答案，然后在最后用1/ans   这里需要考虑n为系统最小值的情况，因为系统最小值取绝对值还会是自己，所以当n为系统最小值的时候，给n加1，让其不再是系统最小值，这样就可以正常求出来一个绝对值，我们只需要在最后将少乘的那个x再累乘进答案即可
        int pow = Math.abs(n == Integer.MIN_VALUE ? n + 1 : n);
        // 记录答案
        double ans = 1D;
        // 累乘x的次幂
        double t = x;
        // 开始快速幂求解，直到pow右移为0
        while (pow > 0) {
            // 只保留对应的位为1时，将t累乘进ans
            if ((pow & 1) == 1) {
                ans *= t;
            }

            // 对t滚动累乘
            t *= t;
            // pow右移
            pow = pow >> 1;
        }

        // 如果n是系统最小，上面的流程就少累乘了依次，这里再将其补上
        if (n == Integer.MIN_VALUE) {
            ans *= x;
        }

        // 返回答案，如果n为负数，那么答案就是1/ans
        return n < 0 ? 1 / ans : ans;

    }
}
