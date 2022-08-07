package 体系学习班.class21;

public class Code03_CoinsWayNoLimit {

    // 1、暴力递归
    public static int coinsWay(int[] arr, int aim) {
        // 判空
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        // 递归入口
        return process(arr, 0, aim);
    }

    // arr[index....] 所有的面值，每一个面值都可以任意选择张数，组成正好rest这么多钱，方法数多少？
    public static int process(int[] arr, int index, int rest) {
        // basecase 没钱了
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        // 当前面值所有可行的选择方案数
        int ways = 0;
        // 讲当前面试可选的所有可能的张数向下进行递归，求最后的可能的方案数，进行累加
        // 只要是选择的面值数量 * 当前的面值 不超过当前剩下的目标总数rest即可
        // 所以这个题目在每一次递归是，都有一个枚举的枚举的过程，不同情况的递归枚举循环的次数也不相同，所以这就给了我们优化空间。看到这种情况就要去找严格位置依赖进而去找优化
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += process(arr, index + 1, rest - (zhang * arr[index]));
        }
        // 返回结果
        return ways;
    }

    // 2、经典动态规划
    public static int dp1(int[] arr, int aim) {
        // 判空
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        // 递归处有两个可变参数，将所有的可变参数都拿来做下标，创建二维dp数组
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        // 根据basecase赋初值
        dp[N][0] = 1;
        // 根据位置依赖关系赋初值
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 这里有枚举行为，我们还可以进一步优化
                int ways = 0;
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    ways += dp[index + 1][rest - (zhang * arr[index])];
                }
                // dp赋值
                dp[index][rest] = ways;
            }
        }
        // 根据递归入口返回相应的结果
        return dp[0][aim];
    }

    // 3、优化后的动态规划
    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 利用严格位置依赖关系，找到了消除掉枚举的办法。只需要依赖两个固定的位置即可求出当前的答案
                dp[index][rest] = dp[index + 1][rest];
                // 在依赖另一个位置的时候，也需要判断是不会超过rest的值才能去进行以来
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
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
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
