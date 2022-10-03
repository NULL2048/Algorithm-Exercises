package 大厂刷题班.class02;

// https://leetcode.cn/problems/factorial-trailing-zeroes/
public class Code07_Zeroes {
    public int trailingZeroes(int n) {
        int cnt = 0;
        // 找到参与阶乘中所有可能存在5的因子的数
        for (int i = 5; i <=n ; i+=5) {
            // 求该数一共有多少个5的因子
            for (int x = i; x % 5 == 0; x /= 5) {
                // 记录整个阶乘中5的因子总数
                cnt++;
            }
        }
        return cnt;
    }
}
