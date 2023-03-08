package 大厂刷题班.class03;

// 预处理数组+模拟
// 本题测试链接 : https://leetcode.cn/problems/largest-1-bordered-square/
public class Code05_Largest1BorderedSquare {
    public int largest1BorderedSquare(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // 记录从一个点开始向下/向右遍历最多能数多少个为1的连续位置，包括自己
        // 这里要创建n + 1*m + 1大小，因为要多创建一行一列，用来在最下面一行和最右边一列构造预处理数组的时候不用去考虑边界。
        // downInfo[1][1] = 3 表示从（1，1）位置向下有连续的三个1
        int[][] downInfo = new int[n + 1][m + 1];
        // rightInfo[1][1] = 3 表示从（1，1）位置向右有连续的三个1
        int[][] rightInfo = new int[n + 1][m + 1];

        // 构造downInfo和rightInfo的预处理结构  只需要求右边和下面的长度就好了，不需要求左边和上边的长度，因为这个信息也可以利用右边和下边的长度求出来，只是变换一下要求位置即可（左下角和右上角）
        getMInfo(grid, downInfo, rightInfo);

        int maxSize = 0;
        // 遍历每一个位置，去判断每一个位置的最大符合要求的正方形
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 如果当前位置是1，就说明它可以作为符合要求的正方形的顶点
                if (grid[i][j] == 1) {
                    // 找到这个位置向下和向右连续1长度的最小值，因为要以最小值为边才有可能组成符合要求的正方形
                    int size = Math.min(downInfo[i][j], rightInfo[i][j]);
                    // 并不是说找到了最小值的边就一定能组成符合要求的，因为可能别的位置的顶点没有这么长的连续为1的边
                    // 所以要从大到小都尝试一边，只要能找到符合要求的其他两个位置的顶点，就直接更新最大尺寸并退出循环
                    for (int k = size; k > 0; k--) {
                        // (i，j)是正方形左上角顶点
                        // 下面是要找到对应的左下角和右上角顶点，去分辨判断它们右边连续的1和下面连续的1是不是大于等于k，只有这样才能组合成符合要求的正方形
                        if (downInfo[i][j + k - 1] >= k && rightInfo[i + k - 1][j] >= k) {
                            // 更新maxSize
                            maxSize = Math.max(maxSize, k * k);
                            // 结束循环
                            break;
                        }
                    }
                }
            }
        }

        return maxSize;
    }

    // 构造预处理数组，记录每个位置右边和下面连续的1的长度
    public void getMInfo(int[][] grid, int[][] downInfo, int[][] rightInfo) {
        int n = grid.length;
        int m = grid[0].length;

        // 从下到上，从右到左遍历，因为右下角的右边和下面连续的1长度是最好求的，后面可以利用以前的值来求出现在的值
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                // 如果当前位置是1，就利用他们下面位置的连续最长1和右边位置的连续最长1的长度再加1，就能得到当前位置的结果
                if (grid[i][j] == 1) {
                    downInfo[i][j] = downInfo[i + 1][j] + 1;
                    rightInfo[i][j] = rightInfo[i][j + 1] + 1;
                    // 如果当前位置不是1，直接长度设置为0
                } else {
                    downInfo[i][j] = 0;
                    rightInfo[i][j] = 0;
                }
            }
        }
    }
}
