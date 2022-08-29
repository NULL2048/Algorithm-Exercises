package 体系学习班.class24;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Code04_MinCoinsOnePaper {
    //==================使用原始数组======================
    // 1、暴力递归
    public static int minCoins(int[] arr, int aim) {
        // 递归入口
        return process(arr, 0, aim);
    }
    // 返回值就是凑出rest的钱，需要用多少张钞票
    public static int process(int[] arr, int index, int rest) {
        // basecase1 当剩余要凑出来的钱数小于0的时候，这一次尝试凑多了，所以直接返回到上一层
        if (rest < 0) {
            // 返回系统最大值，用来后续比较获得最小值，也是表示当前这个分支的凑法凑不出来
            return Integer.MAX_VALUE;
        }
        // basecase2 当已经尝试完所有的面值之后，判断是否已经已经凑出来目标钱数
        if (index == arr.length) {
            // 如果此时rest == 0，说明正好凑出来了目标钱数，则返回0，这个0表示需要0张钱凑出剩余的rest，此时rest已经等于0了，所以肯定只需要0张钱凑出它了。
            // 如果rest != 0，说明凑不出来，直接返回系统最大值表示当前这个分支的凑法凑不出来
            return rest == 0 ? 0 : Integer.MAX_VALUE;
            // 还没有尝试完所有面值，则继续向下尝试递归
        } else {
            // 一共就两种决策，要或者不要当前的钱
            // 1、不用当前的钱
            // 这里要注意p1是不需要每次再单独判断是否为Integer.MAX_VALUE，然后再自增的。因为这个分支本来就是不选钱的，就不能再去对其加1
            // 后面那个自增，是在确定了下层递归的尝试可以凑出来aim目标钱数后（也就是返回值不是Integer.MAX_VALUE），然后将本层使用的这张钞票累加到总张数上
            int p1 = process(arr, index + 1, rest);
            // 2、用当前的钱   rest就减掉当前使用的这张钱的面值
            int p2 = process(arr, index + 1, rest - arr[index]);
            // 如果下层的尝试可以凑出来aim，本层的p2是选择了当前层的这张钞票，所以还要对总张数加1
            if (p2 != Integer.MAX_VALUE) {
                p2++;
            }
            // 从两种张数中选最小值
            return Math.min(p1, p2);
        }
    }
    // 2、改成动态规划
    // dp1时间复杂度为：O(arr长度 * aim)
    public static int dp1(int[] arr, int aim) {
        // 过滤无效参数
        if (aim == 0) {
            return 0;
        }
        // 创建dp数组，暴力递归方法参数中，有两个可变参数，所以dp数组是一个二维数组
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        // 根据basecase2，对dp数组赋初值
        dp[N][0] = 0;
        // N行的数，除了dp[N][0]为0，其他位置都是Integer.MAX_VALUE
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        // 根据递归代码，对dp数组普遍位置进行复制
        // 已知最后一行已经有了初值，然后也知道依赖方向是每一行的数据都去依赖下一行，并且依赖的是左下位置的格子
        // 通过这两个信息，就知道了赋值方向是从下到上，从左到右
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 赋值代码直接赋值暴力递归的改改就可以
                int p1 = dp[index + 1][rest];
                // 这里不一样的是还需要根据basecase1要判断一下如果选定了这个钞票是不是就小于0了，如果选定了之后小于零了，说明此时不能再选这张钞票了，因为选了就无法正好凑出aim了
                int p2 = rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : Integer.MAX_VALUE;
                if (p2 != Integer.MAX_VALUE) {
                    p2++;
                }
                dp[index][rest] = Math.min(p1, p2);
            }
        }
        // 根据递归入口得知要返回dp[0][aim]
        return dp[0][aim];
    }
    //==================使用压缩后的数组，一个数组表示面值，一个数组表示对应面值的钞票数======================
    // 压缩数组
    public static class Info {
        // 表示面值
        public int[] coins;
        // 表示张数
        public int[] zhangs;
        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }
    // 构造压缩数组
    public static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }
    // 这里直接改动态规划
    // dp2时间复杂度为：O(arr长度) + O(货币种数 * aim * 每种货币的平均张数)
    public static int dp2(int[] arr, int aim) {
        // 过滤无效参数
        if (aim == 0) {
            return 0;
        }
        // 得到info时间复杂度O(arr长度)
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        // 构造dp数组
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        // 这三层for循环，时间复杂度为O(货币种数 * aim * 每种货币的平均张数)
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                // 这一层就是枚举过程，找最小值的枚举过程还有优化掉的空间
                for (int zhang = 1; zhang * coins[index] <= aim && zhang <= zhangs[index]; zhang++) {
                    if (rest - zhang * coins[index] >= 0
                            && dp[index + 1][rest - zhang * coins[index]] != Integer.MAX_VALUE) {
                        dp[index][rest] = Math.min(dp[index][rest], zhang + dp[index + 1][rest - zhang * coins[index]]);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    // dp3时间复杂度为：O(arr长度) + O(货币种数 * aim)   如果有大量重复面值的钞票时，这个方法的时间复杂度就比前面的方法低非常多了
    // 优化需要用到窗口内最小值的更新结构
    public static int dp3(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        // 得到info时间复杂度O(arr长度)
        Info info = getInfo(arr);
        int[] c = info.coins;
        int[] z = info.zhangs;
        int N = c.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        // 虽然是嵌套了很多循环，但是时间复杂度为O(货币种数 * aim)
        // 因为用了窗口内最小值的更新结构
        for (int i = N - 1; i >= 0; i--) {
            for (int mod = 0; mod < Math.min(aim + 1, c[i]); mod++) {
                // 当前面值 X
                // mod  mod + x   mod + 2*x   mod + 3 * x
                // 构造滑动窗口最小值的更新结构
                LinkedList<Integer> w = new LinkedList<>();
                w.add(mod);
                dp[i][mod] = dp[i + 1][mod];
                // 遍历涉及到的数   这里的遍历步长是c[i]，滑动窗口是从左向右滑动的，因为赋值方向也是从左向右的，右边的数依赖左边的书，所以需要先把左边的数求出来
                // 整个过程的本质就是将我们要求的位置所依赖的那一行所有的可能涉及到的数组成的窗口，都通过滑动窗口算出最小值，这样我们就可以推出我们要求的位置的值了
                for (int r = mod + c[i]; r <= aim; r += c[i]) {
                    // compensate(w.peekLast(), r, c[i])：这个用来求补偿的张数
                    while (!w.isEmpty() && (dp[i + 1][w.peekLast()] == Integer.MAX_VALUE
                            || dp[i + 1][w.peekLast()] + compensate(w.peekLast(), r, c[i]) >= dp[i + 1][r])) {
                        w.pollLast();
                    }
                    w.addLast(r);
                    int overdue = r - c[i] * (z[i] + 1);
                    if (w.peekFirst() == overdue) {
                        w.pollFirst();
                    }
                    // 每次执行到这，就存储的是当前窗口的最小值所在的下标位置
                    dp[i][r] = dp[i + 1][w.peekFirst()] + compensate(w.peekFirst(), r, c[i]);
                }
            }
        }
        return dp[0][aim];
    }
    public static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }


    // 为了测试
    public static int[] randomArray(int N, int maxValue) {
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
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            int ans4 = dp3(arr, aim);
            if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("功能测试结束");
        System.out.println("==========");
        int aim = 0;
        int[] arr = null;
        long start;
        long end;
        int ans2;
        int ans3;
        System.out.println("性能测试开始");
        maxLen = 30000;
        maxValue = 20;
        aim = 60000;
        arr = randomArray(maxLen, maxValue);
        start = System.currentTimeMillis();
        ans2 = dp2(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp2答案 : " + ans2 + ", dp2运行时间 : " + (end - start) + " ms");
        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3答案 : " + ans3 + ", dp3运行时间 : " + (end - start) + " ms");
        System.out.println("性能测试结束");
        System.out.println("===========");
        System.out.println("货币大量重复出现情况下，");
        System.out.println("大数据量测试dp3开始");
        maxLen = 20000000;
        aim = 10000;
        maxValue = 10000;
        arr = randomArray(maxLen, maxValue);
        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3运行时间 : " + (end - start) + " ms");
        System.out.println("大数据量测试dp3结束");
        System.out.println("===========");
        System.out.println("当货币很少出现重复，dp2比dp3有常数时间优势");
        System.out.println("当货币大量出现重复，dp3时间复杂度明显优于dp2");
        System.out.println("dp3的优化用到了窗口内最小值的更新结构");
    }
}

