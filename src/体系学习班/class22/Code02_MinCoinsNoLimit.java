package 体系学习班.class22;

public class Code02_MinCoinsNoLimit {
    // 1、暴力递归
    public static int minCoins(int[] arr, int aim) {
        // 递归入口
        return process(arr, 0, aim);
    }
    // arr[index...]面值，每种面值张数自由选择，
    // 搞出rest正好这么多钱，返回最小张数
    // 拿Integer.MAX_VALUE标记怎么都搞定不了
    public static int process(int[] arr, int index, int rest) {
        // basecase  当遍历完了所有的面值，如果此时剩余的rest钱数为0，那么只需要0张钞票就可以凑出来剩余的目标钱数rest了，所以返回0
        // 如果rest!=0，但是此时已经没有钞票了，说明没办法凑出rest的目标数，直接返回系统最大值
        // 这里就和之前求方法数的题不一样了，求方法数的是只要是能凑出来就返回1。
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            // 向下递归暴力枚举
            // 用系统最大值，以为我们是在数据中找最小值，这里用系统最大值做最初的比较参考数不会影响后续的结果
            int ans = Integer.MAX_VALUE;
            // 将每一个面值的钱所有可能的情况都遍历尝试一遍，向下递归。
            // 这里循环要限定不能让使用当前面值的张数 * 面值大小 > 剩余目标数，这样就不符合题意了
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                // 确定当前面值用几张，然后再去向后遍历别的面值的情况，这就是一种情况的分支
                int next = process(arr, index + 1, rest - zhang * arr[index]);
                // 如果next返回值不是系统最大值，说明当前这个分支可以凑出来目标数，就取用最小值，在所有循环出来的每一个分支的结果中取最小值
                if (next != Integer.MAX_VALUE) {
                    ans = Math.min(ans, zhang + next);
                }
            }
            // 经过上面的循环，就将面值为arr[index]时，所有可能的情况中使用张数最小的情况找到了。
            return ans;
        }
    }
    // 2、动态规划
    public static int dp1(int[] arr, int aim) {
        // 判空
        if (aim == 0) {
            return 0;
        }
        // 通过递归位置的参数中有两个可变参数可以知道，我们要创建二维dp数组
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        // 根据暴力递归的basecase，我们可以给二维数组最下面的一行赋初值
        dp[N][0] = 0;
        // 最下面一行的其他值都是系统最大
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        // 知道了最初值是在二维数组的最下面一行，然后位置依赖关系又是每一行都依赖其下一行的数据（可以从递归调用处得知，入参是index+1）
        // 通过这两点已知信息，我们就可以推断出对dp的赋值方向应该是从下向上，再通过另一个维度的依赖关系直到，横向的赋值方向是从左到右
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 直接按暴力递归的代码改写
                int ans = Integer.MAX_VALUE;
                // 这里有枚举行为，还可以进行优化
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    int next = dp[index + 1][rest - zhang * arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, zhang + next);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        // 根据暴力递归入口可以知道应该返回dp[0][aim]的结果
        return dp[0][aim];
    }
    // 3、斜率优化
    public static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 根据严格位置依赖关系，去进行斜率优化。这个通过画图能很直观的找到优化的方法，可以将枚举过程省略掉
                // 先将要赋值位置的值设置为其正下方位置的值，用来后续和其他依赖位置的值比较取最小值
                dp[index][rest] = dp[index + 1][rest];
                // 去判断dp[index][rest]左边位置dp[index][rest - arr[index]]是否符合我们要判断的要求
                // 需要rest - arr[index] >=0，因为数组下标不能小于0
                // 并且dp[index][rest - arr[index]] != Integer.MAX_VALUE，因为如果这个位置不能凑出目标数，那就没有比较的必要了
                if (rest - arr[index] >= 0
                        && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    // 如果符合条件，就从dp[index + 1][rest]（此时dp[index][rest]就等于dp[index + 1][rest]）和dp[index][rest - arr[index]]之前找一个最小值，赋值给dp[index][rest]
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }
    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < N; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }
    // 为了测试
    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    // 为了测试
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
    }
}
