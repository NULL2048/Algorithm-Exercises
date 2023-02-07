package 大厂刷题班.class35;

import java.util.PriorityQueue;
// A*寻路算法（A*算法，最小寻路算法），也就是优先级队列结合的遍历

// 来自网易
// map[i][j] == 0，代表(i,j)是海洋，渡过的话代价是2
// map[i][j] == 1，代表(i,j)是陆地，渡过的话代价是1
// map[i][j] == 2，代表(i,j)是障碍，无法渡过
// 每一步上、下、左、右都能走，返回从左上角走到右下角最小代价是多少，如果无法到达返回-1
public class Code04_WalkToEnd {
    // 重点要理解Node的含义
    public static class Node {
        // 当前到达(row, col)位置
        public int row;
        public int col;
        // 当前到达(row, col)位置所使用的最优总代价。这个指的是当前从左上角最开始位置到(row, col)位置的最小总代价是多少
        public int cost;

        public Node(int a, int b, int c) {
            row = a;
            col = b;
            cost = c;
        }
    }

    public static int minCost(int[][] map) {
        // 过滤无效参数
        if (map[0][0] == 2) {
            return -1;
        }
        // 记录长 宽
        int n = map.length;
        int m = map[0].length;
        // 根据到达位置的最优总代价设置小根堆
        PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        // 标记每一个位置是否加入过小根堆
        boolean[][] visited = new boolean[n][m];
        // 先将左上角起始位置加入小根堆   总代价为0
        add(map, 0, 0, 0, heap, visited);
        // 当堆为空，结束循环
        while (!heap.isEmpty()) {
            // 每次从小根堆中弹出堆顶，这样就能保证每次都是先找总代价最小的节点尝试走，如果小的尝试走不通，才会去尝试更大代价的路径
            // 注意整个过程弹出的位置可能并不是相邻、连贯的，但是没有关系，我们弹出的Node都是从它自己的最优代价开始算起的，和上一次弹出的节点没有任何关系
            // 这就需要我们理解cost属性的含义了
            Node cur = heap.poll();
            // 如果走到了右下角结束位置，直接返回从左上角开始位置走到cur位置的最优总代价cur.cost
            if (cur.row == n - 1 && cur.col == m - 1) {
                return cur.cost;
            }
            // 将cur四周的合法位置都加入堆中
            add(map, cur.row - 1, cur.col, cur.cost, heap, visited);
            add(map, cur.row + 1, cur.col, cur.cost, heap, visited);
            add(map, cur.row, cur.col - 1, cur.cost, heap, visited);
            add(map, cur.row, cur.col + 1, cur.cost, heap, visited);
        }
        // 走不到就会跳出循环，返回-1
        return -1;
    }

    // 走到(i,j)位置，判断是不是可以将这个位置的Node加入堆
    // pre是走到(i,j)位置前已经有的最优总代价（从左上角起始位置开始算起的总代价）
    public static void add(int[][] m, int i, int j, int pre, PriorityQueue<Node> heap, boolean[][] visited) {
        // 不能越界  不能是障碍物   不能加入过小根堆
        if (i >= 0 && i < m.length && j >= 0 && j < m[0].length && m[i][j] != 2 && !visited[i][j]) {
            // 这里用pre + (m[i][j] == 0 ? 2 : 1)，计算的就是从左上角开始位置到(i,j)位置的最优总代价
            heap.add(new Node(i, j, pre + (m[i][j] == 0 ? 2 : 1)));
            visited[i][j] = true;
        }
    }
}

