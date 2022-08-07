package 体系学习班.class21;

public class Code02_CoinsWayEveryPaperDifferent {
    // 1、暴力递归

    // 递归入口
    public static int coinWays(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    // arr[index....] 组成正好rest这么多的钱，有几种方法，index之前的不用管
    // 这里index和rest是可变参数
    public static int process(int[] arr, int index, int rest) {
        // basecase  当剩余的钱小于零了，那么这肯定是组合不出来的，直接返回0
        if (rest < 0) {
            return 0;
        }
        // basecase  当所有的钱都已经遍历完了，如果此时剩下的钱数rest为0，说明已经凑出来了，返回1，否则返回0
        if (index == arr.length) { // 没钱了！
            return rest == 0 ? 1 : 0;
        } else {
            // 暴力递归，每次一层都有两个决策，也就是使用当前这张钞票和不使用当前这张钞票，最后将两种决策的结果相加即可
            return process(arr, index + 1, rest) + process(arr, index + 1, rest - arr[index]);
        }
    }

    // 2、动态规划
    public static int dp(int[] arr, int aim) {
        // 判空，如果aim为0，直接返回0
        if (aim == 0) {
            return 1;
        }

        // 由暴力递归可以知道有两个可变参数，这里就创建一个二维的dp数组
        int N = arr.length;
        // 注意这里两个可变参数最大值分别到N和aim，但是数组的下标是从0开始的，所以如果想要让数组的下标到达N和aim，就需要创建到N+1和aim+1
        int[][] dp = new int[N + 1][aim + 1];
        // 根据暴力递归的basecase，我们知道了初值只有在index == N时，并且rest == 0的时候为1，如果rest不为0，就全都是0
        dp[N][0] = 1;
        // 根据位置依赖关系赋值
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        // 根据递归入口的入参返回对应的dp元素
        return dp[0][aim];
    }

    // 为了测试
    public static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
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
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = dp(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}

