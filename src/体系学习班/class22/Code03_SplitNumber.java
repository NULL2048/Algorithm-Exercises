package 体系学习班.class22;

public class Code03_SplitNumber {

    // 1、暴力递归
    // n为正数
    public static int ways(int n) {
        // 判空
        if (n < 0) {
            return 0;
        }
        // 特殊情况
        if (n == 1) {
            return 1;
        }
        // 递归入口
        return process(1, n);
    }

    // 上一个拆出来的数是pre
    // 还剩rest需要去拆
    // 返回拆解的方法数
    public static int process(int pre, int rest) {
        // basecase1  当rest剩下要拆的数字为0了，说明此时已经成功拆完了，所以方法数返回1
        if (rest == 0) {
            return 1;
        }
        // basecase2  当pre > rest的时候，显然此时无法满足右边的数不小于左边的数了，说明当前无法再拆了，直接返回0
        if (pre > rest) {
            return 0;
        }
        // 每一个分支向下递归
        int ways = 0;
        // 当前还剩下rest数的时候，遍历所有的可拆情况，从pre开始，因为不能小于pre，一直到rest结束，因为拆分出来的数字不可能大于rest
        for (int first = pre; first <= rest; first++) {
            // 将所有的情况分支都向下递递归，然后将所有可行的方法数累加，最后得到的就是当前pre和rest情况的最终结果
            ways += process(first, rest - first);
        }
        // 返回最终结果
        return ways;
    }

    // 2、动态规划
    public static int dp1(int n) {
        // 判空
        if (n < 0) {
            return 0;
        }
        // 特殊情况
        if (n == 1) {
            return 1;
        }

        // 有暴力递归的调用位置可知，有两个可变参数，创建二维dp，根据pre和rest的范围来确定数组大小
        int[][] dp = new int[n + 1][n + 1];
        // 根据两个basecase，以及尝试，来进行赋初值
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }

        // 根据位置依赖关系和初值有哪些来决定对dp数组的赋值方向，为从下向上，从右向左
        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                // 内部的赋值代码直接用暴力递归的改即可，这里要做的就是找到赋值方向，写外面的两层循环即可。
                int ways = 0;
                // 有枚举行为，可以进行优化
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }
        return dp[1][n];
    }

    // 斜率优化
    public static int dp2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                // 通过举一个具体例子，然后找到斜率优化的方法。
                // 因为这个题每个格子位置依赖的是左下方的格子，所以一般就去看要求位置的紧挨着左边和下边的格子，看看能不能看出优化方法
                // 如果不行再去看周围其他位置的格子，这道题就找到了可以用来替代枚举行为的格子，也就是其左边和紧挨着下边的格子，只依赖这两个位置就行了，替代了枚举循环行为
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest - pre];
            }
        }
        // 根据递归入口知道要返回哪个位置的值
        return dp[1][n];
    }

    public static void main(String[] args) {
        int test = 39;
        System.out.println(ways(test));
        System.out.println(dp1(test));
        System.out.println(dp2(test));
    }

}

