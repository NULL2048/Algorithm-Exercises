package 大厂刷题班.class13;

// 并查集
// 本题测试链接 : https://leetcode.cn/problems/bricks-falling-when-hit/
public class Code04_BricksFallingWhenHit {
    public int[] hitBricks(int[][] grid, int[][] hits) {
        // 将炮弹达到对应的砖块上，并将该位置标记为2
        for (int i = 0; i < hits.length; i++) {
            // 只需要将有方块的地方标记为2，如果该位置是0，不用标记
            if (grid[hits[i][0]][hits[i][1]] == 1) {
                grid[hits[i][0]][hits[i][1]] = 2;
            }
        }

        // 用已经被炮弹打完的二维数组初始化构建并查集
        UnionFind unionFind = new UnionFind(grid);
        // 逆操作，逆向还原二维数组并统计坠落的砖块数
        int[] ans = new int[hits.length];
        for (int i = hits.length - 1; i >= 0; i--) {
            // 只有被炮弹打中的砖块才需要恢复
            if (grid[hits[i][0]][hits[i][1]] == 2) {
                // 恢复砖块，并且计算坠落砖块的数量
                ans[i] = unionFind.recoveryAndGetFallCount(hits[i][0], hits[i][1]);
            }
        }
        // 返回答案
        return ans;
    }

    public class UnionFind{
        // 二维数组的行数
        private int n;
        // 二维数组的列数
        private int m;
        // 二维数组，注意是地址传递
        private int[][] grid;
        // 此时连接着天花板的所有集合的总砖块数
        private int cellingAll;
        // 标记当前集合是否为连接着天花板的集合，下标为结合的代表节点数据才是有效的
        // isCellingSet[i] = true：i 是集合的代表，以i为代表节点的集合是天花板集合
        private boolean[] isCellingSet;
        // 父节点集合，记录每个位置的父节点，这个父节点有可能经历路线压缩后，就不再是直接父节点了
        // parents[cur] = x：cur的父节点为x
        private int[] parents;
        // 记录每个集合的大小，只有下标为代表节点的时候数据才是有效的
        // setSize[cur] = 10：代表节点为cur的集合大小为10
        private int[] setSize;
        // 用来进行路径压缩的栈。这个栈一定要在初始化并查集的时候就创建好，
        // 然后在后续的多次路径压缩的过程中就可以直接复用这个栈数组了，避免了多次申请创建数组的过程，可以大大减少性能损耗
        private int[] stack;

        public UnionFind(int[][] grid) {
            // 初始化并查集的成员属性
            this.grid = grid;
            this.n = grid.length;
            this.m = grid[0].length;
            // 二维数组一共有多少个格子
            int all = n * m;
            this.isCellingSet = new boolean[all];
            this.parents = new int[all];
            this.setSize = new int[all];
            this.cellingAll = 0;
            // 这里要注意，要在并查集初始化阶段提前将栈数组创建为成员变量，避免后续的路径压缩过程中反复的申请创建，降低代码性能
            this.stack = new int[n * m];

            // 将所有还没有被炮弹打到的砖块单独创建一个集合
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    // 将所有为1的砖块单独创建一个集合
                    if (grid[i][j] == 1) {
                        // 二位下表转为一维下标
                        int cur = i * m + j;
                        // 自己是自己的父节点
                        parents[cur] = cur;
                        // 自己的集合大小为1
                        setSize[cur] = 1;
                        // 如果是连接着天花板的，也记录为天花板集合，并且将连接天花板的砖块数量加1
                        if (i == 0) {
                            isCellingSet[cur] = true;
                            cellingAll++;
                        }
                    }

                }
            }

            // 初始完了所有集合之后，去进行每个独立集合的合并，将相互连通的集合进行合并
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    // 每个位置都尝试与自己上下左右四个位置的节点集合合并
                    union(i, j, i - 1, j);
                    union(i, j, i + 1, j);
                    union(i, j, i, j - 1);
                    union(i, j, i, j + 1);
                }
            }
        }

        // 查找指定节点所在集合的代表节点
        public int find(int row, int col) {
            // 标记栈顶
            int top = -1;
            // 二维转一维
            int cur = row * m + col;
            // 先找到记录在数组中的父节点，然后沿着父节点继续向上遍历
            int father = parents[cur];
            // 沿着父节点路线向上遍历，当遍历到的节点的父节点还是自己，说明找到了代表节点了
            while (cur != father) {
                // 将沿途的节点都记录到栈里
                stack[++top] = cur;
                // 向后遍历
                cur = father;
                father = parents[father];
            }
            // 将栈中刚刚途径的节点的父节点都设置成找到的代表节点，完成路径压缩
            while (top != -1) {
                parents[stack[top--]] = father;
            }
            // 返回代表节点
            return father;
        }

        // 将两个位置所在集合进行合并
        public void union(int r1, int c1, int r2, int c2) {
            if (valid(r1, c1) && valid(r2, c2)) {
                // 找到两个点所在集合的代表节点
                int representativeNode1 = find(r1, c1);
                int representativeNode2 = find(r2, c2);
                // 判断两个集合是否为同一个集合
                if (representativeNode1 != representativeNode2) {
                    // 获取集合大小以及该集合是否为连接着天花板的集合
                    int setSize1 = setSize[representativeNode1];
                    int setSize2 = setSize[representativeNode2];
                    boolean isCelling1 = isCellingSet[representativeNode1];
                    boolean isCelling2 = isCellingSet[representativeNode2];

                    // 将小集合并入到大集合中，也能压缩路径
                    if (setSize1 > setSize2) {
                        // 将小集合的代表节点设置为大集合的节点
                        parents[representativeNode2] = representativeNode1;
                        // 增加大集合数量
                        setSize[representativeNode1] += setSize2;

                        // 如果两个集合存在一个连接天花板的集合（两个集合的状态不同，就一定需要更新天花板集合信息），那么合并后的集合必然也是一个天花板集合
                        // 更新连接天花板集合的大小，并且将合并后的集合设置为天花板集合
                        if (isCelling1 ^ isCelling2) {
                            isCellingSet[representativeNode1] = true;
                            cellingAll += isCelling1 ? setSize2 : setSize1;
                        }
                    } else {
                        parents[representativeNode1] = representativeNode2;
                        setSize[representativeNode2] += setSize1;

                        if (isCelling1 ^ isCelling2) {
                            isCellingSet[representativeNode2] = true;
                            cellingAll += isCelling1 ? setSize2 : setSize1;
                        }
                    }
                }
            }
        }

        // 判断位置是否有效，其中包括判断该位置是否为1
        public boolean valid(int row, int col) {
            return row >= 0 && row < n && col >= 0 && col < m && grid[row][col] == 1;
        }

        // 恢复砖块，并且计算坠落砖块的数量
        public int recoveryAndGetFallCount(int row, int col) {
            // 将当前位置恢复为1
            grid[row][col] = 1;
            // 二维转一维
            int cur = row * m + col;
            // 如果恢复之后的砖块正好连着天花板，就将其设置为一个天花板集合
            if (row == 0) {
                cellingAll++;
                isCellingSet[cur] = true;
            }
            // 为其单独创建一个集合
            parents[cur] = cur;
            setSize[cur] = 1;
            // 将当前天花板集合的砖块总数量记录为preCnt
            int preCnt = cellingAll;
            // 将恢复的位置与其上下左右的集合进行合并
            union(row, col, row - 1, col);
            union(row, col, row + 1, col);
            union(row, col, row, col - 1);
            union(row, col, row, col + 1);
            // 记录合并完之后天花板集合的砖块总数量
            int nowCnt = cellingAll;

            // 如果恢复的位置就在天花板上
            if (row == 0) {
                return nowCnt - preCnt;
                // 如果恢复的位置不在天花板上
            } else {
                // 有可能恢复的位置是一个完全不连着天花板的砖块，所以有可能preCnt==nowCnt，这种情况直接返回0
                return preCnt == nowCnt ? 0 : nowCnt - preCnt - 1;
            }

        }

    }
}
