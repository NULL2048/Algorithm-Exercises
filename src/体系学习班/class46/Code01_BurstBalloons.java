package 体系学习班.class46;

public class Code01_BurstBalloons {
    // 1、暴力递归
    public int maxCoins(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        // 我们将数组扩大两个位置，用来表示原本左右两个边界，这样可以在写代码的过程中减少很多的边界条件讨论
        int[] arr = new int[nums.length + 2];
        // 将左边界设置为1
        arr[0] = 1;
        // 将右边界设置为1
        arr[nums.length + 1] = 1;
        // 将原数组中的数转移到新数组
        for (int i = 1; i <= nums.length; i++) {
            arr[i] = nums[i - 1];
        }
        return process(1, nums.length, arr);
    }
    // 打爆arr[L..R]范围上的所有气球，返回最大的分数
    // 假设arr[L-1]和arr[R+1]一定没有被打爆
    public int process(int l, int r, int[] arr) {
        // 如果arr[L..R]范围上只有一个气球，直接打爆即可
        if (l == r) {
            return arr[l - 1] * arr[l] * arr[r + 1];
        }

        // 选择一：让l位置最后爆    当l最后爆的时候，它左右两边距离最近的并且没有爆掉的就是l-1和r+1，因为这个函数保证了l-1和r+1一定没有爆。并且在l+1~r这个范围内，能保证左边界l和右边界r+1一定没有爆，这样就可以调preocess函数了。
        int coins1 = arr[l - 1] * arr[l] * arr[r + 1] + process(l + 1, r, arr);
        // 选择二：让r位置最后爆
        int coins2 = arr[l - 1] * arr[r] * arr[r + 1] + process(l, r - 1, arr);
        int max = coins1 > coins2 ? coins1 : coins2;
        // 选择三：尝试让l~r之间的位置最后爆
        for (int i = l + 1; i < r ; i++) {
            // 这个最后爆的位置就将整个区间分成了两份，并且左右两个分出来的区间都能满足左右两个边界没有爆，所以可以调用preocess函数
            int coins3 = arr[l - 1] * arr[i] * arr[r + 1] + process(l, i - 1, arr) +  process(i + 1, r, arr);
            max = coins3 > max ? coins3 : max;
        }
        // 将所有可能的选择方案中选择得分最多的一个返回
        return max;
    }
    // 2、记忆化搜索
    public int maxCoins1(int[] nums) {
        // 我们将数组扩大两个位置，用来表示原本左右两个边界，这样可以在写代码的过程中减少很多的边界条件讨论
        int[] arr = new int[nums.length + 2];
        // 将左边界设置为1
        arr[0] = 1;
        // 将右边界设置为1
        arr[nums.length + 1] = 1;
        // 将原数组中的数转移到新数组
        for (int i = 1; i <= nums.length; i++) {
            arr[i] = nums[i - 1];
        }
        // 加缓存
        int dp[][] = new int[nums.length + 2][nums.length + 2];
        return process1(1, nums.length, arr, dp);
    }

    // 打爆arr[L..R]范围上的所有气球，返回最大的分数
    // 假设arr[L-1]和arr[R+1]一定没有被打爆
    public int process1(int l, int r, int[] arr, int[][] dp) {
        // 如果已经计算过这个结果，直接返回
        if (dp[l][r] != 0) {
            return dp[l][r];
        }
        // 如果arr[L..R]范围上只有一个气球，直接打爆即可
        if (l == r) {
            return arr[l - 1] * arr[l] * arr[r + 1];
        }

        // 选择一：让l位置最后爆    当l最后爆的时候，它左右两边距离最近的并且没有爆掉的就是l-1和r+1，因为这个函数保证了l-1和r+1一定没有爆。并且在l+1~r这个范围内，能保证左边界l和右边界r+1一定没有爆，这样就可以调preocess函数了。
        int coins1 = arr[l - 1] * arr[l] * arr[r + 1] + process1(l + 1, r, arr, dp);
        // 选择二：让r位置最后爆
        int coins2 = arr[l - 1] * arr[r] * arr[r + 1] + process1(l, r - 1, arr, dp);
        int max = coins1 > coins2 ? coins1 : coins2;
        // 选择三：尝试让l~r之间的位置最后爆
        for (int i = l + 1; i < r ; i++) {
            // 这个最后爆的位置就将整个区间分成了两份，并且左右两个分出来的区间都能满足左右两个边界没有爆，所以可以调用preocess函数
            int coins3 = arr[l - 1] * arr[i] * arr[r + 1] + process1(l, i - 1, arr, dp) +  process1(i + 1, r, arr, dp);
            max = coins3 > max ? coins3 : max;
        }
        // 记录下结果缓存
        dp[l][r] = max;
        // 将所有可能的选择方案中选择得分最多的一个返回
        return max;
    }

    // 3、动态规划
    public static int maxCoins2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }
        int N = arr.length;
        int[] help = new int[N + 2];
        help[0] = 1;
        help[N + 1] = 1;
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        int[][] dp = new int[N + 2][N + 2];
        for (int i = 1; i <= N; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
        }
        for (int L = N; L >= 1; L--) {
            for (int R = L + 1; R <= N; R++) {
                int ans = help[L - 1] * help[L] * help[R + 1] + dp[L + 1][R];
                ans = Math.max(ans, help[L - 1] * help[R] * help[R + 1] + dp[L][R - 1]);
                for (int i = L + 1; i < R; i++) {
                    ans = Math.max(ans, help[L - 1] * help[i] * help[R + 1] + dp[L][i - 1] + dp[i + 1][R]);
                }
                dp[L][R] = ans;
            }
        }
        return dp[1][N];
    }
}
