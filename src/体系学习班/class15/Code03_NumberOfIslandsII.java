package 体系学习班.class15;

import java.util.ArrayList;
import java.util.List;

public class Code03_NumberOfIslandsII {
    // 返回每一轮输入的计算结果
    public static List<Integer> numIslands2(int m, int n, int[][] positions) {
        // 构建并查集
        UnionFind uf = new UnionFind(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            // 将每一次输入的参数传入connect方法，再将结果加入到List中
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    // 并查集
    public static class UnionFind {
        // 该题目的并查集需要的成员属性
        private int[] parent;
        // 这里我们就利用size[i]是否为0，来判断当前位置是不是从来还没有被赋值过'1'，因为根据题意一个位置一旦被赋值为'1'，就不可能再变回0，整个操作是不可逆的
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        // 构造方法   并查集初始化
        // 注意，这道题是动态输入1元素的位置，在最开始，整个二维数组都是0，
        // 所以在初始化并查集的时候，并不需要将任何元素加入并查集，因为此时所有的元素都是0
        public UnionFind(int m, int n) {
            // 行
            row = m;
            // 列
            col = n;
            // 并查集中集合数量
            sets = 0;
            int len = row * col;
            // 创建数组
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        // 将二维下标转换为一维下标
        private int index(int r, int c) {
            return r * col + c;
        }

        // 找到代表节点
        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            // 路径压缩
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        // 合并操作
        private void union(int r1, int c1, int r2, int c2) {
            // 数组越界直接返回
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                return;
            }
            // 将要合并的二维下标转换为一维下标
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            // 如果此时存在某个元素的size还没有被初始化，说明这个位置的一定还是0，是不可以进行合并的，直接返回
            // 以往我们判断能不能合并的操作都是在union方法外部进行的，只要能进入union的都是可以进行合并的样本，
            // 但是这里我们将判断能不能合并的判断放到了union内部，通过size[]来判断是不是当前位置还从来没有被赋值1，进而判断能不嫩进行合并
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
            // 找到代表节点
            int f1 = find(i1);
            int f2 = find(i2);
            // 如果两个不在同一个集合中，就进行合并
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                // 集合数量减1
                sets--;
            }
        }

        // 动态的进行合并操作
        public int connect(int r, int c) {
            // 将赋值为'1'的二维下标转换为一维下标
            int index = index(r, c);
            // 如果size[index] == 0，说明这个位置从来没有被赋值过1，这是它第一次被赋值为1，所以需要对其进行合并操作，并返回合并后的新集合数量
            // 如果size[index] != 0，说明该位置已经被赋值为1了，这里有进行重复赋值了，当前情况的sets已经被求出来了，这一次再求一边也没有什么意义，直接返回sets即可
            if (size[index] == 0) {
                // 初始化当前位置的父节点为其自身
                parent[index] = index;
                // 先将其看作是一个独立的集合
                size[index] = 1;
                // 将总的集合数加1
                sets++;
                // 和自己相邻的上下左右四个位置尝试进行合并，判断是否能合并的操作在union方法内部，经过一轮合并，如果有相邻的为'1'的样本，
                // 就可以并入现有的集合当中，进而可以计算出新的sets
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            // 返回并查集的集合数量
            return sets;
        }

    }
}
