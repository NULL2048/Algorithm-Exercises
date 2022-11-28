package 大厂刷题班.class22;

import java.util.PriorityQueue;
// 接雨水这个题，其实就是单点思维的题目，单点思维在贪心笔记里的超级洗衣机里有讲
// 小根堆   小根堆很重要，如果此时小根堆里最小值都超过max了，那么说明此时小根堆里的所有值都超过max了，也就是此时已经来到了一个全新的内弧了，不再是以前那个max作为瓶颈的内弧了，现在遍历来到了一个全新的内弧
// 本题测试链接 : https://leetcode.cn/problems/trapping-rain-water-ii/
public class Code03_TrappingRainWaterII {
    class Node {
        // 坐标
        public int x;
        public int y;
        // 该位置的的高度
        public int value;

        public Node(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    public int trapRainWater(int[][] heightMap) {
        // 小根堆
        PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> a.value - b.value);
        // 矩阵的行数
        int n = heightMap.length;
        // 矩阵的列数
        int m = heightMap[0].length;
        // 内弧的瓶颈
        int max = 0;
        // 标志当前位置是否已经加入到过小根堆，true表示已经加入过小根堆
        boolean[][] isVisited = new boolean[n][m];
        // 先将矩阵的四周的都加入到堆中，四周一定是存不下水的
        for (int i = 0; i < m; i++) {
            heap.add(new Node(0, i, heightMap[0][i]));
            isVisited[0][i] = true;
        }
        for (int i = 1; i < n; i++) {
            heap.add(new Node(i, m - 1, heightMap[i][m - 1]));
            isVisited[i][m - 1] = true;
        }
        for (int i = m - 2; i >= 0; i--) {
            heap.add(new Node(n - 1, i, heightMap[n - 1][i]));
            isVisited[n - 1][i] = true;
        }
        for (int i = n - 2; i >= 1; i--) {
            heap.add(new Node(i, 0, heightMap[i][0]));
            isVisited[i][0] = true;
        }

        // 记录总的水量
        int water = 0;
        while (!heap.isEmpty()) {
            // 将堆顶弹出
            Node cur = heap.poll();
            int row = cur.x;
            int col = cur.y;
            // 用堆顶尝试更新瓶颈Max
            max = Math.max(max, cur.value);

            // 将弹出位置的上、下、左、右还没有加入过堆的位置加入到堆中，同时结算加入堆的位置的水量
            if (row > 0 && !isVisited[row - 1][col]) {
                heap.add(new Node(row - 1, col, heightMap[row - 1][col]));
                isVisited[row - 1][col] = true;
                water += (Math.max(0, max - heightMap[row - 1][col]));
            }

            if (row < n - 1 && !isVisited[row + 1][col]) {
                heap.add(new Node(row + 1, col, heightMap[row + 1][col]));
                isVisited[row + 1][col] = true;
                water += (Math.max(0, max - heightMap[row + 1][col]));
            }

            if (col > 0 && !isVisited[row][col - 1]) {
                heap.add(new Node(row, col - 1, heightMap[row][col - 1]));
                isVisited[row][col - 1] = true;
                water += (Math.max(0, max - heightMap[row][col - 1]));
            }

            if (col < m - 1 && !isVisited[row][col + 1]) {
                heap.add(new Node(row, col + 1, heightMap[row][col + 1]));
                isVisited[row][col + 1] = true;
                water += (Math.max(0, max - heightMap[row][col + 1]));
            }

        }

        // 返回总水量
        return water;
    }
}
