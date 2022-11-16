package 大厂刷题班.class18;

// 牛客的测试链接：
// https://www.nowcoder.com/questionTerminal/8ecfe02124674e908b2aae65aad4efdf
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 把如下的全部代码拷贝进java编辑器
// 把文件大类名字改成Main，可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

// 动态规划   样本对应模型
// 力扣测试链接：https://leetcode.cn/problems/cherry-pickup/
public class Code03_CherryPickup {
    public static int cherryPickup(int[][] grid) {
        // 矩阵的行数
        int n = grid.length;
        // 矩阵的列数
        int m = grid[0].length;
        // dp缓存
        // dp[i][j][k]：表示A来到（i，j），B来到（k，i + j - k）时，能收集到的最多樱桃数量
        int[][][] dp = new int[n][m][n];
        // 先给dp缓存赋初值，这里设置为系统最小值，用来表示当前位置的值还从来没有计算过
        // 其实只要是赋值小于-1的数用来标记该位置的值还没有计算过就可以，因为题目中有意义的值都是大于等于-1的
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }

        // 开始递归，A和B同时从左上角出发
        int ans =  process(0, 0, 0, grid, n, m, dp);
        // 如果返回-1，就说明根本无法从起点走到终点再走回去，这种情况就认为能拿到0个樱桃
        return ans == -1 ? 0 : ans;
    }

    // 此时A来到（ai，aj），B来到（bi，ai + aj - bi），求此时已经能拿到的最大樱桃数。返回-1表示根本无法从起点走到终点再走回去
    // 这里只用了三个可变参数表示A和B的坐标，因为A和B都是只能向下或者向右走，并且他们是同步移动的，所以他们两个走的步数一定一直都是一样的，所以只要是知道了三个坐标数，就可以推出来下一个
    public static int process(int ai, int aj, int bi, int[][] grid, int n, int m, int[][][] dp) {
        // 省一个参数，因为A和B是同步走的，所以他们走过的步数一定是一样的，又因为他们只能向下走或者向右走，所以他们走的步数就是纵坐标+横坐标。
        // 所以他们的坐标满足a+b=c+d的关系，也就是说只要是知道了三个数，就可以推出第四个数，这就省掉了一个可变参数，提高了效率。
        // 计算B的列下标
        int bj = ai + aj - bi;

        // 判断此时位置是否越界，如果越界直接返回系统最小值，表示这个位置根本就是无效的，也就不用为其赋值
        if (ai >= n || bi >= n || aj >= m || bj >= m) {
            return Integer.MIN_VALUE;
        }

        // 如果该位置在缓存中已经有了结果了，直接取出来返回
        if (dp[ai][aj][bi] != Integer.MIN_VALUE) {
            return dp[ai][aj][bi];
        }

        // 如果此时两个点已经到达了右下角重点，此时两个点一定是同时到达的，所以只能取一次樱桃，将这个位置的樱桃树复制到dp，并返回
        // 其实这里只判断ai == n - 1 && aj == m - 1即可，因为A和B一定能同时到达终点
        if (ai == n - 1 && aj == m - 1 && bi == n - 1 && bj == m - 1) {
            dp[ai][aj][bi] = grid[ai][aj];
            return dp[ai][aj][bi];
        }

        // 尝试全部有可能的四种移动方式，通过递归得到这几种方式能拿到的最大樱桃树
        int p1 = process(ai + 1, aj, bi + 1, grid, n, m, dp);
        int p2 = process(ai, aj + 1, bi, grid, n, m, dp);
        int p3 = process(ai + 1, aj, bi, grid, n, m, dp);
        int p4 = process(ai, aj + 1, bi + 1, grid, n, m, dp);

        // 从这四种结果中取最大值，作为后续的走法能拿到的最大樱桃树
        int next = Math.max(p1, Math.max(p2, Math.max(p3, p4)));

        // 记录当前已经能拿到的最大樱桃树
        int cur = 0;
        // 如果此时A、B所在位置或者后续返回上来的数有一个是-1，就说明此时这个走法一定是走不通的，不可能走到终点，那么就将该位置也设置为-1，返回
        if (grid[ai][aj] == -1 || grid[bi][bj] == -1 || next == -1) {
            // 上面三个数存在-1，要么表示当前位置会被-1挡住（也就是去或者回的路会被当前遇到的-1挡住），要么表示后续走过的路会被-1挡住，不管哪一种情况，都意味着当前这个走法是走不通的
            dp[ai][aj][bi] = -1;
            return dp[ai][aj][bi];
        // 走到这个分支，表示当前的路线能走通
        } else {
            // 如果此时A和B的位置不同，说明两个位置的樱桃出都要累加到当前能拿到的樱桃数中
            if (ai != bi || aj != bj) {
                cur = grid[ai][aj] + grid[bi][bj];
            // 如果A和B此时在同一位置，那么就只能拿一次樱桃
            } else {
                cur = grid[ai][aj];
            }
        }

        // 将当前能拿到的最大樱桃数量加上后续步骤能拿到的最大樱桃数量
        dp[ai][aj][bi] = cur + next;
        return dp[ai][aj][bi];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int N = (int) in.nval;
            in.nextToken();
            int M = (int) in.nval;
            int[][] matrix = new int[N][M];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    in.nextToken();
                    matrix[i][j] = (int) in.nval;
                }
            }
            out.println(cherryPickup(matrix));
            out.flush();
        }
    }
}
