package 体系学习班.class18;

/**
 * 暴力递归到动态规划（自顶向下的动态规划）
 *
 * 假设有排成一行的N个位置记为1~N，N一定大于或等于2
 * 开始时机器人在其中的M位置上(M一定是1~N中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到N-1位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，返回方法数
 */
public class Code01_RobotWalk {
    // 这道题我们分三种方法去些，来一步步的将暴力递归改成动态规划，有助于启发我们去学习动态规划的解题方法

    // 方法一：暴力递归解法
    public static int ways1(int N, int start, int aim, int K) {
        // 边界条件判断，异常入参直接返回-1
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        return process1(start, K, aim, N);
    }

    // 机器人当前来到的位置是cur，
    // 机器人还有rest步需要去走，
    // 最终的目标是aim，
    // 有哪些位置？1~N
    // 返回：机器人从cur出发，走过rest步之后，最终停在aim的方法数，是多少？
    public static int process1(int cur, int rest, int aim, int N) {
        // 如果已经不需要走了，走完了！   递归出口 base case
        if (rest == 0) {
            // 如果放剩余步数为零时，当前位置正好在目标位置上，说明这是一条符合要求的走法，返回1，否则返回0
            return cur == aim ? 1 : 0;
        }
        // 当前位置在左边界，机器人只能向右走到2位置
        if (cur == 1) { // 1 -> 2
            return process1(2, rest - 1, aim, N);
        }
        // 当前位置在右边界，机器人只能向左走到N-1位置
        if (cur == N) { // N-1 <- N
            return process1(N - 1, rest - 1, aim, N);
        }
        // 其他的位置，机器人既可以向右走，也可以向左走
        return process1(cur - 1, rest - 1, aim, N) + process1(cur + 1, rest - 1, aim, N);
    }

    // 方法二：加缓存方法
    public static int ways2(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        // 将每一种情况的结果存入到dp数组中
        int[][] dp = new int[N + 1][K + 1];
        // 先将每一个位置都初始化成-1
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        // dp就是缓存表
        // dp[cur][rest] == -1 -> process1(cur, rest)之前没算过！
        // dp[cur][rest] != -1 -> process1(cur, rest)之前算过！返回值，dp[cur][rest]
        // N+1 * K+1
        return process2(start, K, aim, N, dp);
    }

    // cur 范: 1 ~ N
    // rest 范：0 ~ K
    // 整体的思路还是暴力递归，只不过在暴力递归的过程中将计算出来的结果存入到dp数组中，这样如果以后会用到相同的结果，就直接取用即可，避免重复计算。
    public static int process2(int cur, int rest, int aim, int N, int[][] dp) {
        // 在一开始先看一下数组中是不是已经存有当前这个情况的数据，如果有则直接返回
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        // 如果没有，就计算一遍
        // 计算过程还是暴力递归
        int ans = 0;
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(2, rest - 1, aim, N, dp);
        } else if (cur == N) {
            ans = process2(N - 1, rest - 1, aim, N, dp);
        } else {
            ans = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
        }
        // 只不过每次计算完都将结果存入到dp数组中
        dp[cur][rest] = ans;
        return ans;

    }

    // 方法三：经典动态规划
    // 在一开始就将整个题目所涉及到的所有可能都提前求出来存入dp中，然后再根据入参直接取出相应的结果返回，这就是经典动态规划和加缓存法的一个重要区别
    public static int ways3(int N, int start, int aim, int K) {
        // 异常入参判断
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        // 将计算结果存入dp中
        int[][] dp = new int[N + 1][K + 1];
        // 当剩余步数为0，当前位置在aim上时，就是一个符合条件的走法，这个位置即为1，这个是自顶向上的动归，所以以最终结果状态为起始点开始向下分裂，分裂出所有可能得到最终结果的情况
        dp[aim][0] = 1;
        // 将所有可能的情况提前算出来存入到dp数组中
        // 这里是一列一列的去计算，从上到下，从左到右填充。
        for (int rest = 1; rest <= K; rest++) {
            // 先去计算第1行  也就是当前位置在左边界时
            dp[1][rest] = dp[2][rest - 1];
            // 再去计算中间行，普通的情况，即可能向左走，也可能向右走
            for (int cur = 2; cur < N; cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            // 再去计算最后一行   也就是当前位置在右边界时
            dp[N][rest] = dp[N - 1][rest - 1];
        }
        // 根据传参，直接从dp数组中取到相应的结果返回。
        return dp[start][K];
    }

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }

}
