package 大厂刷题班.class27;
// 数学    防溢出
// https://leetcode.cn/problems/reverse-integer/
public class Problem_0007_ReverseInteger {
    public int reverse(int x) {
        boolean isNeg = x < 0 ? true : false;
        x = isNeg ? x : -x;
        int m = Integer.MIN_VALUE / 10;
        int o = Integer.MIN_VALUE % 10;
        int res = 0;

        while (x != 0) {
            // 如果res==m的话，res*10其实就是系统最小值把最后一位变成0，所以如果加的数的小于系统最小值最后一位这个数，那么加操作一定是会溢出的
            if (res < m || (res == m && x % 10 < o)) {
                return 0;
            }

            res = res * 10 + x % 10;
            x /= 10;
        }
        return isNeg ? res : -res;
    }
}
