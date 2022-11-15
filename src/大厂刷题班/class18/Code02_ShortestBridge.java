package 大厂刷题班.class18;

// 宽度优先遍历
// 本题测试链接: https://leetcode.cn/problems/shortest-bridge/
public class Code02_ShortestBridge {
    public int shortestBridge(int[][] grid) {
        // 矩阵的行数
        int n = grid.length;
        // 矩阵的列数
        int m = grid.length;
        // 矩阵一共有几个位置
        int all = n * m;
        // 将矩阵二维坐标转化为一维坐标，然后记录每个位置到最初连成一片的1的距离是多少
        // distanceRecord[0][i]：表示矩阵中的二维坐标转化为一维坐标i，这个位置到0号的那一片1距离是多少
        // distanceRecord[1][i]：表示矩阵中的二维坐标转化为一维坐标i，这个位置到1号的那一片1距离是多少
        // 一共就两片1，所以第一维就开两个空间即可。
        int[][] distanceRecord = new int[2][all];
        // 记录当前一轮宽度优先遍历要遍历的位置
        // curs[i] = v：v表示的是二维转一维的下标
        int[] curs = new int[all];
        // 记录下一轮宽度优先遍历要遍历的位置
        int[] nexts = new int[all];
        // 当前要统计到哪一片1的距离，一共就两片1，所以islandNo只会是0或1
        int islandNo = 0;

        // 遍历整个矩阵，去找最初始的两片1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 下面这个if分支在整个循环过程中只会进入两次
                // 如果当前遍历的位置是1，说明找到了一片1，进入到分支中
                if (grid[i][j] == 1) {
                    // 首先要进行初始化，将record和curs初始化出来，将这一片1中的位置的距离都设置为1，存入到record，也就相当于标记他们已经被遍历过了
                    // 然后还要将这一片1都加入到curs队列中，表示第一轮宽度优先遍历要从curs中的这些位置开始向外遍历一层
                    // 返回值是当前curs已经填充到的位置，也可以认为是此时curs队列中有效数据的数量
                    int cursIndex = infect(n, m, i, j, grid, 0, distanceRecord[islandNo], curs);
                    // 表示当前这一轮宽度优先遍历要设置的到islandNo这一片1的距离是多少，在下面每一轮宽度优先遍历都要将distance++
                    int distance = 2;
                    // 开始进行宽度优先遍历，只要curs还有有效数据，就说明当前还有位置要宽度优先遍历
                    while (cursIndex != 0) {
                        // 宽度优先遍历，返回的是nexts队列已经填充到的位置，也可以认为是此时nexts队列中有效数据的数量
                        int nextsIndex = bfs(n, m, grid, distanceRecord[islandNo], curs, cursIndex, nexts, distance++);

                        // 当前nexts队列在下一轮宽度优先遍历的时候就就会变成下一轮的curs
                        // 下面的就是要将nexts的地址赋值给curs上，这里要注意还要讲curs的地址赋值给next，就相当于两个引用交换了指向的地址空间
                        // 如果不交换，只将nexts赋值给curs，就会导致最后curs和nexts指向的是同一个地址空间，就会出现错误
                        int[] temp = curs;
                        curs = nexts;
                        nexts = temp;
                        // 将nextsIndex也赋值给cursIndex
                        cursIndex = nextsIndex;
                    }

                    // 将islandNo加1，当下一次再次进入到这个循环之后，就是另一片1了
                    islandNo++;
                }
            }
        }

        // 完成了循环之后，distanceRecord中就存储了所有位置到那两片1的距离是多少

        // 找到到两片1距离的加和最小的位置
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < distanceRecord[0].length; i++) {
            // 就计算当前位置i到两片1的距离加和，看能否将min推低，找到其中的最小值
            min = Math.min(distanceRecord[0][i] + distanceRecord[1][i], min);
        }

        // 因为统计出来重复的，重复了1个，然后又因为我们的距离是从1开始算的，其实也是多算了1个，所以最后要减3
        return min - 3;
    }

    // 将这一片1都感染为2，并且初始化curs队列和distanceRecord。就是一个宽度优先遍历的感染过程，当这个过程完成之后，这一片1就都会设置为2
    // 当前来到grid[i][j] , 总行数是N，总列数是M
    // grid[i][j]感染出去(找到这一片岛所有的1),把每一个1的坐标，放入到int[] curs队列！
    // 1 (a,b) -> curs[index++] = (a * M + b)
    // 1 (c,d) -> curs[index++] = (c * M + d)
    // 二维已经变成一维了， 1 (a,b) -> a * M + b
    // 设置距离record[a * M +b ] = 1，默认初始化的距离都是1
    public int infect(int n, int m, int i, int j, int[][] grid, int index, int[] distanceRecord, int[] curs) {
        // 保证当前位置不越界，并且这个位置在矩阵中的值为1
        if (i < 0 || i >= n || j < 0 || j >= m || grid[i][j] != 1) {
            return index;
        }

        // m[i][j] 不越界，且m[i][j] == 1
        // 将其感染为2，这就保证了shortestBridge()方法中的循环中的if分支只会进入到两次，因为当这个过程完成之后，这一片1就都会设置为2，后面即使遍历到了这一片1的位置也不会进入到那个if分支了
        grid[i][j] = 2;
        // 当前位置二维坐标转化为一维坐标
        int curIndex = i * m + j;
        // 将当前位置的距离设置为1
        distanceRecord[curIndex] = 1;
        // 将当前位置加入到curs队列
        curs[index++] = curIndex;

        // 注意下面的过程是依次执行的，所以在执行过程中是要更新index的，因为每一次都有可能将新的位置加入到curs中，推高index的指向
        // 所以每一次执行传入的index也都是最新的
        index = infect(n, m, i + 1, j, grid, index, distanceRecord, curs);
        index = infect(n, m, i - 1, j, grid, index, distanceRecord, curs);
        index = infect(n, m, i, j + 1, grid, index, distanceRecord, curs);
        index = infect(n, m, i, j - 1, grid, index, distanceRecord, curs);

        // 返回当前curs已经填充到的位置
        return index;
    }

    // 宽度优先遍历，本轮宽度优先遍历是从curs中的位置开始，然后向外遍历一层，向外遍历到的新位置就会加入到nexts中，下一轮的宽度优先遍历就要从nexts中的位置开始
    // 二维原始矩阵中，N总行数，M总列数
    // distance：当前要遍历到的这一层到那一片1的距离
    // cursSize：当前curs队列中的有效位置数量
    // curs：curs中存储的是位置
    // record里面拿距离，如果遍历到的位置对应的record值不是0，说明这个位置已经遍历过了，直接跳过
    public int bfs(int n, int m, int[][] grid, int[] distanceRecord, int[] curs, int cursSize, int[] nexts, int distance) {
        // 从nexts的0位置开始填充
        int nextsIndex = 0;
        // 遍历curs中的位置，开始宽度优先遍历
        for (int i = 0; i < cursSize; i++) {
            // 向当前位置左右上下都遍历一层，先判断位置合法性
            // 先去判断当前位置的左右上下是否还有位置，如果没有了，就设置为-1，如果有就计算出对应的位置。
            int leftIndex = curs[i] % m == 0 ? -1 : curs[i] - 1;
            int rightIndex = curs[i] % m == m - 1 ? -1 : curs[i] + 1;
            int upIndex = curs[i] / m == 0 ? -1 : curs[i] - m;
            int downIndex = curs[i] / m == n - 1 ? -1 : curs[i] + m;

            // 如果确实有左右上下的位置，并且该位置没有被遍历过（distanceRecord[leftIndex] == 0）
            if (leftIndex != -1 && distanceRecord[leftIndex] == 0) {
                // 设置该位置到那一片1的距离
                distanceRecord[leftIndex] = distance;
                // 将该位置加入到nexts中
                nexts[nextsIndex++] = leftIndex;
            }
            if (rightIndex != -1 && distanceRecord[rightIndex] == 0) {
                distanceRecord[rightIndex] = distance;
                nexts[nextsIndex++] = rightIndex;
            }
            if (upIndex != -1 && distanceRecord[upIndex] == 0) {
                distanceRecord[upIndex] = distance;
                nexts[nextsIndex++] = upIndex;
            }
            if (downIndex != -1 && distanceRecord[downIndex] == 0) {
                distanceRecord[downIndex] = distance;
                nexts[nextsIndex++] = downIndex;
            }
        }

        // 返回nexts填充到的位置
        return nextsIndex;
    }
}
