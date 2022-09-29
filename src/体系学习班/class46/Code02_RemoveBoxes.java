package 体系学习班.class46;

public class Code02_RemoveBoxes {
    // 1、暴力递归
    public int removeBoxes1(int[] boxes) {
        if (boxes.length == 1) {
            return 1;
        }
        return process1(boxes, 0, boxes.length - 1, 0);
    }
    // arr[L...R]消除，而且前面跟着K个boxes[L]这个数
    // 返回：所有东西都消掉，最大得分
    public int process1(int[] boxes, int l, int r, int k) {
        if (l > r) {
            return 0;
        }
        // 选择一：将l~r前面的k个boxes[l]和boxes[l]一起合并，消除的得分是(k + 1) * (k + 1)
        // 后面的l+1~r这个范围的数自己区合并，前面一定是就假设没有boxes[l]了，所以k=0，只有当还想和前面的数合并的时候k才大于0，调用函数是process(boxes, l + 1, r, 0)
        int p1 = (k + 1) * (k + 1) + process1(boxes, l + 1, r, 0);
        // 选择二：先将l+1~i-1范围内的数自己消掉，不和别人合并，函数调用为process(boxes, l + 1, i - 1, 0)
        // 当l+1~i-1合并完之后，这样在l前面的k个boxes[l]和i~r范围的数字，这两部分就相邻了，让这两部分一起合并，调用函数为process(boxes, i, r, k + 1)
        // 注意boxes[i]一定是等于boxes[l]，所以process(boxes, i, r, k + 1)也是满足递归函数要求的，前面的k个boxes[l]和boxes[i]是相等的
        int p2 = Integer.MIN_VALUE;
        // 尝试所有符合要求的i位置，来找到所有可能情况的最大得分
        for (int i = l + 1; i <= r; i++) {
            int temp = 0;
            if (boxes[i] == boxes[l]) {
                temp = process1(boxes, l + 1, i - 1, 0) + process1(boxes, i, r, k + 1);
            }
            p2 = Math.max(p2, temp);
        }
        return Math.max(p1, p2);
    }

    // 2、记忆化搜索
    public int removeBoxes2(int[] boxes) {
        int n = boxes.length;
        if (n == 1) {
            return 1;
        }
        // 上面有三个可变参数，所以创建一个三维dp表
        int[][][] dp = new int[n][n][n];
        return process2(boxes, 0, n - 1, 0, dp);
    }
    // arr[L...R]消除，而且前面跟着K个boxes[L]这个数
    // 返回：所有东西都消掉，最大得分
    public int process2(int[] boxes, int l, int r, int k, int[][][] dp) {
        if (l > r) {
            return 0;
        }
        // 如果已经有缓存了，直接将答案返回。注意这个要写到上面的basecase下面，否则会出现越界
        if (dp[l][r][k] != 0) {
            return dp[l][r][k];
        }
        int p1 = (k + 1) * (k + 1) + process2(boxes, l + 1, r, 0, dp);
        int p2 = Integer.MIN_VALUE;
        for (int i = l + 1; i <= r; i++) {
            int temp = 0;
            if (boxes[i] == boxes[l]) {
                temp = process2(boxes, l + 1, i - 1, 0, dp) + process2(boxes, i, r, k + 1, dp);
            }
            p2 = Math.max(p2, temp);
        }
        // 给dp缓存赋值
        dp[l][r][k] = Math.max(p1, p2);
        return dp[l][r][k];
    }

    // 3、记忆化搜索——优化
    public int removeBoxes3(int[] boxes) {
        int n = boxes.length;
        if (n == 1) {
            return 1;
        }
        int[][][] dp = new int[n][n][n];
        return process3(boxes, 0, n - 1, 0, dp);
    }
    // arr[L...R]消除，而且前面跟着K个boxes[L]这个数
    // 返回：所有东西都消掉，最大得分
    public int process3(int[] boxes, int l, int r, int k, int[][][] dp) {
        if (l > r) {
            return 0;
        }
        if (dp[l][r][k] != 0) {
            return dp[l][r][k];
        }
        // 常数优化点
        // 找到l+1~r范围上前面连续等于boxes[l]的最后的位置
        int last = l;
        for (int i = l + 1; i <= r; i++) {
            if (boxes[i] != boxes[i - 1]) {
                break;
            }
            last++;
        }
        // 统计算上l前面的k个boxes[l]，再加上l+1~r的连续等于boxes[l]的最长前缀的个数，算一共有多少个连续前缀boxes[l]。注意这里没有算l位置的这个数，后面要自己加1
        int pre = k + (last - l);
        // 选择一：将l前面k个boxes[l]和l~last的连续的boxes[l]合并，得分是(pre + 1) * (pre + 1)
        // 然后将后面的last+1~r范围的数自己合并，递归调用为process(boxes, last + 1, r, 0, dp)
        int p1 = (pre + 1) * (pre + 1) + process3(boxes, last + 1, r, 0, dp);
        // 选择二：将lasr+1~i-1范围内的数自己区合并，递归调用为process(boxes, last + 1, i - 1, 0, dp)
        // 当lasr+1~i-1范围合并全都消掉之后，last+1前面的连续相同的pre+1个boxes[l]就和i~r范围的数相邻了，然后就将这两个分去合并消除，递归调用为process(boxes, i, r, pre + 1, dp)
        int p2 = Integer.MIN_VALUE;
        // 尝试所有符合要求的i位置，来找到所有可能情况的最大得分
        for (int i = last + 1; i <= r; i++) {
            int temp = 0;
            if (boxes[i] == boxes[l]) {
                temp = process3(boxes, last + 1, i - 1, 0, dp) + process3(boxes, i, r, pre + 1, dp);
            }
            p2 = Math.max(p2, temp);
        }
        /**
         这两种选择就是以前面连续相同的boxes[l]作为基准，要么就前缀连续相同的数自己合并，再将剩下部分自己合并
         要么将整个范围中间的部分去合并消除，中间消除之后再将两边部分的数在一起合并消除
         这两种选择就可以将所有可能的情况都涵盖了，不会漏掉
         */
        dp[l][r][k] = Math.max(p1, p2);
        return dp[l][r][k];
    }

}
