package 体系学习班.class43;

public class Code03_PavingTile {
    // 1、暴力递归
    /*
     * 2*M铺地的问题非常简单，这个是解决N*M铺地的问题
     */
    public static int ways1(int N, int M) {
        // 过滤无效参数
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        // 如果整个要铺的面值长或宽是1，那么肯定就只有一种铺法
        if (N == 1 || M == 1) {
            return 1;
        }
        // pre代表递归过程中当前行的上一行的状况，0表示空着，1表示已经铺上地砖了
        int[] pre = new int[M];
        // 最开始pre表示的是-1行的状态，-1行假设就是全都铺满的
        for (int i = 0; i < pre.length; i++) {
            pre[i] = 1;
        }
        // 返回全部铺满的总方法数
        return process(pre, 0, N);
    }
    // pre：表示level-1行的状态
    // level：表示正在level行做决定
    // N：表示一共有多少行。常量
    // 整个递归函数，我们认为level-2行及其之上所有行，都摆满砖了
    // 返回值：在level做决定后，让整个M*N区域都满的方法数
    // 这个题其实也是设计简化外部信息的动态规划，通过对暴力递归函数潜台词的设计，来简化需要传入递归函数的参数，达到简化尾部信息的效果
    // 该递归函数的意思就是当前要在level层上铺地砖，此时level层是全空的，level-1层并不要求全空，可能全铺满了，也可能还有空位置，然后level-1上面的全部层都认为已经全部铺满了
    // 之所以这里要留两层开递归讨论，是因为地砖长度是2，所以至少要留两层才能把所有的可能摆放位置都尝试出来
    public static int process(int[] pre, int level, int N) {
        // base case
        // level到了N，说明已经越界了，此时pre表示N-1最后一行的情况
        if (level == N) {
            // 如果发现N-1最后一行所有的位置都是1，那么说明就铺满了，返回1表示成功找到一种方法。如果存在0就说明没有铺满，返回0，说明当前尝试无法铺满
            for (int i = 0; i < pre.length; i++) {
                if (pre[i] == 0) {
                    return 0;
                }
            }
            return 1;
        }
        // 没到终止行，可以选择在当前的level行摆瓷砖
        int[] op = getOp(pre);
        return dfs(op, 0, level, N);
    }
    // op[i] == 0 可以考虑摆砖
    // op[i] == 1 只能竖着向上
    // 这个深度优先遍历是在当前行上去做递归，上面的preocess是对不同层之间的递归
    // 这个递归就将这一层所有可能的选择都尝试一遍
    public static int dfs(int[] op, int col, int level, int N) {
        // 在列上自由发挥，玩深度优先遍历，当col来到终止列，i行的决定做完了
        // 如果当前行已经从左向右遍历完了，则就去向下一层尝试递归，轮到i+1行，做决定
        if (col == op.length) {
            // 向下一行开始递归
            return process(op, level + 1, N);
        }
        // 当前行的所有可行的摆放方法数
        int ans = 0;
        // 选择一：col位置不横摆（不横摆的话，这个位置的铺法肯定是确定唯一的，这个位置是空着还是竖着完全取决于上一层对应位置是否填满）   col+1说明这是从当前行从左向右递归的
        ans += dfs(op, col + 1, level, N); // col位置上不摆横转
        // 选择二：col位置横摆, 向右   横摆的前提条件是当前位置的上面不是空（op[col] == 0），并且紧挨着右边的格子正上面也不是空（op[col + 1] == 0）
        // op[]记录的是当前行位置是否能考虑不竖着摆砖，当前行一定是空的，所以如果想要横着摆，必须要正相邻的两个位置的上一行对应的位置都已经被填满了，如果没有填满那么当前行这个位置就必须竖着填
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            // 横着摆
            op[col] = 1;
            op[col + 1] = 1;
            // 继续向右边递归  col+2
            ans += dfs(op, col + 2, level, N);
            // 还原现场
            op[col] = 0;
            op[col + 1] = 0;
        }
        // 返回这一层的总方法数
        return ans;
    }
    // 根据上一层判断当前层可选择的铺砖方案
    public static int[] getOp(int[] pre) {
        int[] cur = new int[pre.length];
        for (int i = 0; i < pre.length; i++) {
            // 异或相同为0，不同为1。所以如果当前层上一层pre[i]如果已经铺了地砖为1了，那么当前层cur[i]可以考虑竖着摆，也可以考虑横着摆
            // 但是如果上一层pre[i]为0，说明上面这个是空的没有贴地砖，那么当前层cur[i]只能考虑竖着摆，因为如果这个时候不把上一层补全，等下再向下一层就再也没机会补全了
            cur[i] = pre[i] ^ 1;
        }
        // 所以每一行每一个位置只有三个选择，要么必须竖着摆，要么可以从不摆或者横摆中选择一个方法
        // 返回生成的当前层的可选择的铺砖方案
        return cur;
    }

    // 2、状态压缩的暴力递归
    // 整体思路和上面暴力递归完全一致，是不过就是用二进制位来表示每一个位置的状态，将数组给省略掉了
    // Min (N,M) 不超过 32
    public static int ways2(int N, int M) {
        // 过滤无效参数
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        // 如果整个要铺的面值长或宽是1，那么肯定就只有一种铺法
        if (N == 1 || M == 1) {
            return 1;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        // 记录上一层的状态，一开始pre表示-1层，就认为-1层全部铺满了，每一个二进制位都是1。
        int pre = (1 << min) - 1;
        // 开始按层进行尝试递归
        return process2(pre, 0, max, min);
    }
    // 上一行的状态是pre。limit = (1 << M) - 1是用来对齐的，固定参数不用管
    // 当前来到i行，一共N行，返回填满的方法数
    public static int process2(int pre, int i, int N, int M) {
        // base case
        if (i == N) {
            return pre == ((1 << M) - 1) ? 1 : 0;
        }
        // 根据上一层判断当前层可选择的铺砖方案
        int op = ((~pre) & ((1 << M) - 1));
        // 在当前层中开始递归，尝试所有可能的选择
        return dfs2(op, M - 1, i, N, M);
    }
    public static int dfs2(int op, int col, int level, int N, int M) {
        // basecase
        if (col == -1) {
            return process2(op, level + 1, N, M);
        }
        int ans = 0;
        // 选择一：当前位置不横铺
        ans += dfs2(op, col - 1, level, N, M);
        // 选择二：当前位置横铺
        if ((op & (1 << col)) == 0 && col - 1 >= 0 && (op & (1 << (col - 1))) == 0) {
            ans += dfs2((op | (3 << (col - 1))), col - 2, level, N, M);
        }
        return ans;
    }
    // 3、记忆化搜索的解
    // 就是将上面的思路改成记忆化搜索
    // Min(N,M) 不超过 32
    public static int ways3(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int max = Math.max(N, M);
        int min = Math.min(N, M);
        int pre = (1 << min) - 1;
        // 暴力递归有两个变量，pre和i   根据这两个来创建dp   dp[pre][i]：表示当前层i，在上一层为pre的状态下，将N*M全部铺满的所有可能的摆放方法数
        int[][] dp = new int[1 << min][max + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process3(pre, 0, max, min, dp);
    }
    // dp缓存就在这一里使用，这里是按层递归
    public static int process3(int pre, int i, int N, int M, int[][] dp) {
        if (dp[pre][i] != -1) {
            return dp[pre][i];
        }
        int ans = 0;
        if (i == N) {
            ans = pre == ((1 << M) - 1) ? 1 : 0;
        } else {
            int op = ((~pre) & ((1 << M) - 1));
            ans = dfs3(op, M - 1, i, N, M, dp);
        }
        dp[pre][i] = ans;
        return ans;
    }
    // 这里实在每一层中递归，缓存不在这里使用
    public static int dfs3(int op, int col, int level, int N, int M, int[][] dp) {
        if (col == -1) {
            return process3(op, level + 1, N, M, dp);
        }
        int ans = 0;
        ans += dfs3(op, col - 1, level, N, M, dp);
        if (col > 0 && (op & (3 << (col - 1))) == 0) {
            ans += dfs3((op | (3 << (col - 1))), col - 2, level, N, M, dp);
        }
        return ans;
    }
    // 4、严格位置依赖的动态规划解
    public static int ways4(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int big = N > M ? N : M;
        int small = big == N ? M : N;
        int sn = 1 << small;
        int limit = sn - 1;
        int[] dp = new int[sn];
        dp[limit] = 1;
        int[] cur = new int[sn];
        for (int level = 0; level < big; level++) {
            for (int status = 0; status < sn; status++) {
                if (dp[status] != 0) {
                    int op = (~status) & limit;
                    dfs4(dp[status], op, 0, small - 1, cur);
                }
            }
            for (int i = 0; i < sn; i++) {
                dp[i] = 0;
            }
            int[] tmp = dp;
            dp = cur;
            cur = tmp;
        }
        return dp[limit];
    }
    public static void dfs4(int way, int op, int index, int end, int[] cur) {
        if (index == end) {
            cur[op] += way;
        } else {
            dfs4(way, op, index + 1, end, cur);
            if (((3 << index) & op) == 0) { // 11 << index 可以放砖
                dfs4(way, op | (3 << index), index + 1, end, cur);
            }
        }
    }
    public static void main(String[] args) {
        int N = 8;
        int M = 6;
        System.out.println(ways1(N, M));
        System.out.println(ways2(N, M));
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));
        N = 10;
        M = 10;
        System.out.println("=========");
        System.out.println(ways3(N, M));
        System.out.println(ways4(N, M));
    }
}