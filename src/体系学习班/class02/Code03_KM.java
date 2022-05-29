package 体系学习班.class02;


import java.util.HashMap;
import java.util.HashSet;

// 输入一定能够保证，数组中所有的数都出现了M次，只有一种数出现了K次
// 1 <= K < M
// 返回这种数
public class Code03_KM {
    // 请保证arr中，只有一种数出现了K次，其他数都出现了M次
    public static int km(int[] arr, int k, int m) {
        int[] help = new int[32];
        // help[0] 0位置的1出现了几个
        // help[i] i位置的1出现了几个
        // 下面这个循环的时间复杂度依然是O(N)，因为内层循环是固定循环次数的，它是作为常数系数，可以直接省略
        for (int num : arr) {
            for (int i = 0; i < 32; i++) {
                help[i] += (num >> i) & 1;
            }
        }
        int ans = 0;
        // 如果这个出现了K次的数，就是0
        // 那么下面代码中的 : ans |= (1 << i);
        // 就不会发生
        // 那么ans就会一直维持0，最后返回0，也是对的！
        for (int i = 0; i < 32; i++) {
            // 将每一位上1出现的次数与m取模
            help[i] %= m;
            // 如果模不是0，说明这一位上1出现的次数不是m的倍数，也就是说出现k次的这个数在这一位上的数就是1
            if (help[i] != 0) {
                // 将1添加到ans对应的这一位种，这里是取或，这个原理很好理解，自己看一下应该就能懂
                ans |= 1 << i;
            }
        }
        return ans;
    }
}