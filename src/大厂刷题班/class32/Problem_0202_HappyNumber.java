package 大厂刷题班.class32;

import java.util.HashSet;
import java.util.TreeSet;
// 数学   硬背
// 有一个结论，只要是中途出现了4，那么后面一定就会开始循环，那么这个数一定不是快乐数。这个结论记下就可以了，不用知道数学证明
// 如果中途出现了1，那么这个数就是快乐数
// 整个流程中一定能遇到1或遇到4
// https://leetcode.cn/problems/happy-number/
public class Problem_0202_HappyNumber {
    // 暴力模拟
    public static boolean isHappy1(int n) {
        HashSet<Integer> set = new HashSet<>();
        while (n != 1) {
            int sum = 0;
            while (n != 0) {
                int r = n % 10;
                sum += r * r;
                n /= 10;
            }
            n = sum;
            if (set.contains(n)) {
                break;
            }
            set.add(n);
        }
        return n == 1;
    }

    // 最优解，使用上面说的结论
    public boolean isHappy(int n) {
        // 结论：只要是计算过程中出现了1和4，就结束循环。如果出现了1那么一定是快乐数，出现了4一定不是快乐数
        while (n != 1 && n != 4) {
            int sum = 0;
            while (n != 0) {
                // 求每一个数的平方
                sum += (n % 10) * (n % 10);
                // 取下一位
                n /= 10;
            }
            n = sum;
        }
        return n == 1;
    }
}
