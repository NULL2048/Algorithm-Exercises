package 体系学习班.class23;

public class Code01_SplitSumClosed {
    // 1、暴力递归
    public static int right(int[] arr) {
        // 判空
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // 求原集合的累加和
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        // 递归入口，这里传入原集合累加和的一半
        return process(arr, 0, sum / 2);
    }
    // arr[i...]可以自由选择，请返回累加和尽量接近rest，但不能超过rest的情况下，最接近的累加和是多少？
    public static int process(int[] arr, int i, int rest) {
        // basecase  当遍历完所有位置的时候，此时也没办法去决策了，因为已经没有数了，所以直接返回0
        if (i == arr.length) {
            return 0;
            // 还有数，arr[i]这个数
        } else {
            // 可能性1，不使用arr[i]，所以这个决策的加和结果就是其下层决策的结果，不用加当前这个数
            int p1 = process(arr, i + 1, rest);
            // 可能性2，要使用arr[i]
            int p2 = 0;
            // 这个值不能超过rest（还剩下的要接近的数）
            if (arr[i] <= rest) {
                // 既然选择了这个数字，那么当前这个决策的加和结果，就是其下层决策返回上来的结果加上当前这个数
                p2 = arr[i] + process(arr, i + 1, rest - arr[i]);
            }
            // 从两种决策结果中选最大的返回，因为要尽可能接近rest，前面有限定了结果肯定不可能大于rest，所以这里直接选取最大值即可
            return Math.max(p1, p2);
        }
    }
    // 2、动态规划
    public static int dp(int[] arr) {
        // 判空
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // 求原数组的累加和
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        // 根据暴力递归有两个可变参数得知，创建dp二维数组，并且根据从两个可变参数的取值范围来设置数组大小
        sum /= 2;
        int N = arr.length;
        // i在暴力递归中的范围是0~n，rest的范围是0~sum
        int[][] dp = new int[N + 1][sum + 1];
        // 通过basecase得知，dp数组的最后一行都是0
        // 再结合位置依赖关系，知道每个各自都是依赖其下面一行左侧的各自，我们就可以知道普遍位置的赋值方向应该是从下到上，从左到右
        // 根据这一点我们就可以写出两层for循环应该怎么写了，至于for循环内的赋值代码，直接用暴力递归的改就行，就是把递归调用位置改成dp数组，把返回位置改成对dp数组赋值即可
        for (int i = N - 1; i >= 0; i--) {
            for (int rest = 0; rest <= sum; rest++) {
                // 可能性1，不使用arr[i]
                int p1 = dp[i + 1][rest];
                // 可能性2，要使用arr[i]
                int p2 = 0;
                if (arr[i] <= rest) {
                    p2 = arr[i] + dp[i + 1][rest - arr[i]];
                }
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        // 根据递归入口知道要返回哪个dp元素
        return dp[0][sum];
    }
    // for test
    public static int[] randomArray(int len, int value) {
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * value);
        }
        return arr;
    }
    // for test
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    // for test
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 50;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            int[] arr = randomArray(len, maxValue);
            int ans1 = right(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
