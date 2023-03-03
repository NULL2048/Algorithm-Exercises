package 大厂刷题班.class23;

// 合并石头的最低成本
// 本题测试链接 : https://leetcode.cn/problems/minimum-cost-to-merge-stones/
// 这道题和分金子那道题不一样，没有贪心的解【贪心笔记中第三题：切金子】
// 【动态规划】【范围尝试模型】【业务限制模型】【前缀和】
public class Code04_MinimumCostToMergeStones {
    // 1、暴力递归
    public int mergeStones1(int[] stones, int k) {
        // 过滤无效参数
        if (stones == null || stones.length == 0 || k == 0) {
            return -1;
        }
        // 数组长度
        int n = stones.length;
        // 这个条件很重要，存在一些数组长度n和k的组合压根就合并不成1个，直接返回-1
        if ((n - 1) % (k - 1) > 0) {
            return -1;
        }
        // 构造前缀和数组，加速求任意区间累加和的速度
        int[] preSum = new int[n];
        preSum[0] = stones[0];
        for (int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + stones[i];
        }

        // 将数组0~n-1范围合并成1份，每一次将相邻的k个合并
        return process1(stones, 0, n - 1, 1, k, preSum);
    }

    // arr[l..r] 一定要弄出p份，返回最低代价
    // 固定参数是k和preSum，preSum是用来加快求累加和速度的
    public int process1(int[] stones, int l, int r, int p, int k, int[] preSum) {
        // basecase  如果此时l==r，说明本身就是1个数了，如果要划分的目标p也是1，那么就不需要再合并了，代价直接返回0，如果目标p不是1，但是因为此时l~r范围只有一个数了，肯定是合并不出来p目标了，直接返回-1
        if (l == r) {
            return p == 1 ? 0 : -1;
        }

        // 说明当前就是最后一次合并了，合并完就要求是1个，如果无法一次合并成1个，就返回-1
        if (p == 1) {
            // 当前是最后一次合并了，如果要想是最后一次合并，此时合并完只能有k个数，所以要判断当前的状况能不能合并出k份
            int next = process1(stones, l, r, k, k, preSum);
            // 如果不能合并成k份，那么一定就无法最后合并成一份，直接返回-1
            if (next == -1) {
                return -1;
            // 可以合并出k份
            } else {
                // 代价就是合并出k分所需要的总代价，加上l~r范围的累加和，因为将l~r范围上的数合并，合并代价就是这个范围中所有数字的累加和，直接用前缀和定律快速求解
                return next + (l == 0 ? preSum[r] : preSum[r] - preSum[l - 1]);
            }

        // 当前目标并不是分成1份，说明合并还没有到最后
        } else {
            // 先将最小代价初始化为系统最大
            int min = Integer.MAX_VALUE;
            // 开始遍历，尝试所有可能的左右部分划分情况，我们就规定左部分划分出1份，右部分划分出p-1份
            // 划分遍历步长就是k - 1
            for (int mid = l; mid <= r - (p - 1); mid += (k - 1)) {
                // 看左部分合并成1份最低代价
                int leftCost = process1(stones, l, mid, 1, k, preSum);
                int rightCost = -1;
                // 如果左部分确实可以划分出1份，就去看右部分能不能划分出p-1份
                if (leftCost != -1) {
                    // 右部分合并出p-1份的最低代价
                    rightCost = process1(stones, mid + 1, r, p - 1, k, preSum);
                }
                // 如果右部分也可以划分出来，则计算这两部分的合并代价，是否是l~r范围所有划分情况中最低的
                if (rightCost != -1) {
                    // 这里我们就是要将l~r范围合并出p份，并不会对这p份再做合并，所以这里的代价就是leftCost+rightCost，不需要加上l~r的累加和
                    min = Math.min(min, leftCost + rightCost);
                }
            }
            // 返回所有划分中合并代价最小的值
            return min;
        }
    }

    // 2、记忆化搜索
    public int mergeStones(int[] stones, int k) {
        // 过滤无效参数
        if (stones == null || stones.length == 0 || k == 0) {
            return -1;
        }
        // 数组长度
        int n = stones.length;
        // 这个条件很重要，存在一些数组长度n和k的组合压根就合并不成1个，直接返回-1
        if ((n - 1) % (k - 1) > 0) {
            return -1;
        }
        // 构造前缀和数组，加速求任意区间累加和的速度
        int[] preSum = new int[n];
        preSum[0] = stones[0];
        for (int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + stones[i];
        }
        // 构造三维dp缓存  第三维下标在递归中最多赋值就是k，所以长度开到k+1即可
        int[][][] dp = new int[n][n][k + 1];

        // 将数组0~n-1范围合并成1份，每一次将相邻的k个合并
        return process(stones, 0, n - 1, 1, k, preSum, dp);
    }

    // arr[l..r] 一定要弄出p份，返回最低代价
    // 固定参数是k和preSum，preSum是用来加快求累加和速度的
    public int process(int[] stones, int l, int r, int p, int k, int[] preSum, int[][][] dp) {
        // 先看是否有缓存，有的话直接返回
        if (dp[l][r][p] != 0) {
            return dp[l][r][p];
        }

        // basecase  如果此时l==r，说明本身就是1个数了，如果要划分的目标p也是1，那么就不需要再合并了，代价直接返回0，如果目标p不是1，但是因为此时l~r范围只有一个数了，肯定是合并不出来p目标了，直接返回-1
        if (l == r) {
            dp[l][r][p] = p == 1 ? 0 : -1;
            return dp[l][r][p];
        }

        // 说明当前就是最后一次合并了，合并完就要求是1个，如果无法一次合并成1个，就返回-1
        if (p == 1) {
            // 当前是最后一次合并了，如果要想是最后一次合并，此时合并完只能由k个数，所以要判断当前的状况能不能合并出k份
            int next = process(stones, l, r, k, k, preSum, dp);
            // 如果不能合并成k份，那么一定就无法最后合并成一份，直接返回-1
            if (next == -1) {
                dp[l][r][p] = -1;
                return dp[l][r][p];
                // 可以合并出三份
            } else {
                // 代价就是合并出三分所需要的总代价，加上l~r范围的累加和，因为将3份数合并，合并代价就是这三部分的累加和
                dp[l][r][p] = next + (l == 0 ? preSum[r] : preSum[r] - preSum[l - 1]);
                return dp[l][r][p];
            }
            // 当前目标并不是分成1份，说明合并还没有到最后
        } else {
            // 先将最小代价初始化为系统最大
            int min = Integer.MAX_VALUE;
            // 开始遍历，尝试所有可能的左右部分划分情况，我们就规定左部分划分出1份，右部分划分出p-1份
            // 划分遍历步长就是k - 1，l最多到r - (p - 1)，因为要保证右部分至少有p-1个数，不然就不可能划分出p-1个目标了
            for (int mid = l; mid <= r - (p - 1); mid += (k - 1)) {
                // 看左部分合并成1份最低代价
                int leftCost = process(stones, l, mid, 1, k, preSum, dp);
                int rightCost = -1;
                // 如果左部分确实可以划分出1份，就去看右部分能不能划分出p-1份。剪枝
                if (leftCost != -1) {
                    // 右部分合并出p-1份的最低代价
                    rightCost = process(stones, mid + 1, r, p - 1, k, preSum, dp);
                }
                // 如果右部分也可以划分出来，则计算这两部分的合并代价，是否是l~r范围所有划分情况中最低的
                if (rightCost != -1) {
                    // 这里我们就是要将l~r范围合并出p份，并不会对这p份再做合并，所以这里的代价就是leftCost+rightCost，不需要加上l~r的累加和
                    min = Math.min(min, leftCost + rightCost);
                }
            }
            // 返回所有划分中合并代价最小的值
            dp[l][r][p] = min;
            return dp[l][r][p];
        }
    }
}
