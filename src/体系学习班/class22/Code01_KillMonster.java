package 体系学习班.class22;

public class Code01_KillMonster {
    // 1、暴力递归
    /**
     * N：怪兽总血量
     * M：每一刀可能的伤害范围时0~M
     * K：一共可以砍K刀
     */
    public static double right(int N, int M, int K) {
        // 判空
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        // 这是所有可能的情况，就是一共砍K刀，每一刀有M+1中可能的情况，所以总的情况数就是(M+1) ^K
        long all = (long) Math.pow(M + 1, K);
        // 暴力递归，求出可以砍死怪兽的情况书
        long kill = process(K, M, N);
        // 计算砍死的概率
        return (double) ((double) kill / (double) all);
    }

    /**
     * 怪兽还剩hp点血
     * 每次的伤害在[0~M]范围上
     * 还有times次可以砍
     * 返回砍死的情况数！
     */
    public static long process(int times, int M, int hp) {
        // basecase  当砍完全部刀时，如果怪兽的血量小于等于0，说明怪兽已经被砍死了，此时就返回1；如果怪物没有死，就返回0
        if (times == 0) {
            return hp <= 0 ? 1 : 0;
        }
        // basecase   这个是在后续改写动态规划的时候，发现hp如果是负数值的话，没有办法作为dp数组的下标，如果再专门为附属情况开辟数组空间又有点浪费
        // 所以对hp小于等于0的情况，在暴力递归这单独拿出来讨论，进而减轻改写动态规划的难度
        // 当hp已经小于等于0，但是times还不为0，说明此时怪兽已经死了，但是还可以继续砍，这种情况下不管后续看多少次，每一个分支都是一次能砍死怪兽的计数
        // 所以我们就可以直接求后面的分支总数即可，直接用公式求 (M+1)^K
        if (hp <= 0) {
            return (long) Math.pow(M + 1, times);
        }
        // 去向下递归遍历所有的分支情况
        long ways = 0;
        // 每一刀都有可能砍掉0~M滴血，所以每砍一刀都会有M+1个情况分支，这里都需要遍历到
        // 遍历每一种可能的伤害
        for (int i = 0; i <= M; i++) {
            // times - 1：剩余刀数减一
            // hp - i：如果这一刀砍掉i滴血，怪兽还剩下多少滴血
            // 将每一种可能的伤害分支返回的砍死怪兽次数累加
            ways += process(times - 1, M, hp - i);
        }
        // 返回结果
        return ways;
    }
    // 2、动态规划
    /**
     * N：怪兽总血量
     * M：每一刀可能的伤害范围时0~M
     * K：一共可以砍K刀
     */
    public static double dp1(int N, int M, int K) {
        // 判空
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        // 所有可能的情况
        long all = (long) Math.pow(M + 1, K);
        // 由递归调用处发现有了两个可变参数，所以dp数组设置为二维数组，范围是0~K和0~N
        long[][] dp = new long[K + 1][N + 1];
        // 根据暴力递归的第一个basecase，给dp赋初值，当剩余的刀数为0，并且hp为0，记为一次砍死的记录
        // 第一行除了第0列是1，剩余的都是0
        dp[0][0] = 1;
        // 利用位置依赖关系，从上向下，从左到右对dp数组进行复制，这个是根据位置依赖关系，发现每一个数据都是以来其上一行，和左边的数据，并且最初的初值就是赋值的第一行的，进而我们就能知道赋值方向应该怎么设置了。
        for (int times = 1; times <= K; times++) {
            // 这个是根据暴力递归的第二个basecase，当hp<=0时，它的值可以直接用公式求解，因为下标没有负数，所以这里我们献给hp为0的时候赋值
            // 负数情况我们单独进行讨论计算，因为我们的本意只是求出dp[K][N]位置的结果就可以了，每一个下标大于0的元素值，肯定是由其依赖的数据加和得到的
            // 如果一个数据依赖的位置有负数位置，但是我们的dp数组有没有办法表示负数位置，我们就自己独立的计算出后续负数情况的所有结合结果，然后累加到要求的非负数情况的元素上即可
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                long ways = 0;
                // 有一个枚举过程，还有优化空间
                for (int i = 0; i <= M; i++) {
                    // 这里就是根据暴力递归的递归调用位置改写的
                    if (hp - i >= 0) {
                        ways += dp[times - 1][hp - i];
                        // 单独讨论了如果hp - i出现负数情况时，我们直接用公式求解累加到dp数组即可，因为负数情况没办法在dp数组中体现，就不向dp数组中进行赋值了
                    } else {
                        ways += (long) Math.pow(M + 1, times - 1);
                    }
                }
                // 将累加结果赋值给对应的dp元素
                dp[times][hp] = ways;
            }
        }
        // 根据暴力递归入口处的入参，我们就知道了需要的是dp[K][N]位置的结果
        long kill = dp[K][N];
        // 计算概率并返回
        return (double) ((double) kill / (double) all);
    }
    // 3、对枚举进行斜率优化
    public static double dp2(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                // 这里，我们通过过利用每个格子之间的严格位置依赖关系，去将不固定的循环枚举过程优化成固定的依赖公式
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                // 如果要减去的那种情况下标是大于0的，就按照在笔记中讲的依赖关系，减去掉多出来的位置
                if (hp - 1 - M >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - M];
                    // 如果这个多出来的位置是负数情况，我们就直接减去对应的公式解即可，注意这里公式套用的是多出来的那个位置的下标
                } else {
                    dp[times][hp] -= Math.pow(M + 1, times - 1);
                }
            }
        }
        long kill = dp[K][N];
        return (double) ((double) kill / (double) all);
    }
    // for test
    public static void main(String[] args) {
        int NMax = 10;
        int MMax = 10;
        int KMax = 10;
        int testTime = 200;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * NMax);
            int M = (int) (Math.random() * MMax);
            int K = (int) (Math.random() * KMax);
            double ans1 = right(N, M, K);
            double ans2 = dp1(N, M, K);
            double ans3 = dp2(N, M, K);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}

