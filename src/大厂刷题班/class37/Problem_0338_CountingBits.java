package 大厂刷题班.class37;
// 位运算
// https://leetcode.cn/problems/counting-bits/
public class Problem_0338_CountingBits {
    public int[] countBits(int n) {
        // 记录每一个数中二进制1有多少个
        int[] bits = new int[n + 1];

        // 统计0~n每一个数的二进制1个数
        for (int i = 0; i <= n; i++) {
            int rightOne = 0;
            int num = i;
            while(num != 0) {
                // 1的个数++
                bits[i]++;
                // 只保留此时最右边的1，其他位都变0
                rightOne = num & (-num);
                // 抹除掉当前这一轮n中最右侧的1
                num ^= rightOne;
            }
        }

        return bits;
    }
}
