package 大厂刷题班.class32;

// 位运算
// https://leetcode.cn/problems/number-of-1-bits/
public class Problem_0191_NumberOf1Bits {
    // n的二进制形式，有几个1？
    public static int hammingWeight1(int n) {
        int bits = 0;
        int rightOne = 0;
        while(n != 0) {
            bits++;
            // 只保留此时最右边的1，其他位都变0
            rightOne = n & (-n);
            n ^= rightOne;
        }
        return bits;
    }

    // 这个方法了解一下就可以了，掌握上一种方法就够了
    public static int hammingWeight2(int n) {
        n = (n & 0x55555555) + ((n >>> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >>> 2) & 0x33333333);
        n = (n & 0x0f0f0f0f) + ((n >>> 4) & 0x0f0f0f0f);
        n = (n & 0x00ff00ff) + ((n >>> 8) & 0x00ff00ff);
        n = (n & 0x0000ffff) + ((n >>> 16) & 0x0000ffff);
        return n;
    }

}

