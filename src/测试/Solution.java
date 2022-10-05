package 测试;

import java.util.HashMap;
class Solution {
    public static int largest1BorderedSquare(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] downInfo = new int[n + 1][m + 1];
        int[][] leftInfo = new int[n + 1][m + 1];

        getMInfo(grid, downInfo, leftInfo);

        int maxSize = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; m < m; m++) {
                if (grid[i][j] == 1) {
                    int size = Math.min(downInfo[i][j], leftInfo[i][j]);

                        maxSize = Math.max(maxSize, size * size);
                }
            }
        }

        return maxSize;
    }

    public static void getMInfo(int[][] grid, int[][] downInfo, int[][] leftInfo) {
        int n = grid.length;
        int m = grid[0].length;

        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (grid[i][j] == 1) {
                    downInfo[i][j] = downInfo[i + 1][j] + 1;
                    leftInfo[i][j] = leftInfo[i][j + 1] + 1;
                } else {
                    downInfo[i][j] = 0;
                    leftInfo[i][j] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1,1},{1,0,1},{1,1,1}};
        String str = "abcabcbb";
        System.out.println(largest1BorderedSquare(grid));
    }


}