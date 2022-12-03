package 大厂刷题班.class23;

import java.util.HashSet;

// 二叉树节点间最近公共祖先的批量查询问题
// 高频题   二叉树   这个题可以放在二叉树的章节 ，然后给树链剖分和并查集章节一个链接即可
// 方法一：树链剖分   方法二：Tarjan算法，这个算法需要利用并查集
public class Code05_LCATarjanAndTreeChainPartition {
    // 给定数组tree大小为N，表示一共有N个节点
    // tree[i] = j 表示点i的父亲是点j，tree一定是一棵树而不是森林
    // queries是二维数组，大小为M*2，每一个长度为2的数组都表示一条查询
    // [4,9], 表示想查询4和9之间的最低公共祖先
    // [3,7], 表示想查询3和7之间的最低公共祖先
    // tree和queries里面的所有值，都一定在0~N-1之间
    // 返回一个数组ans，大小为M，ans[i]表示第i条查询的答案

    // 1、暴力方法
    public static int[] query1(int[] father, int[][] queries) {
        int M = queries.length;
        int[] ans = new int[M];
        HashSet<Integer> path = new HashSet<>();
        // 遍历所有的查询
        for (int i = 0; i < M; i++) {
            int jump = queries[i][0];
            // 查询的其中一个节点向上层遍历，将路径保存到Set中
            while (father[jump] != jump) {
                path.add(jump);
                jump = father[jump];
            }
            path.add(jump);
            jump = queries[i][1];
            // 再将另一个节点向上遍历，只要遍历到一个节点在Set路径中存在，就说明这个节点是两个查询的最近公共祖先，将其加入到答案
            while (!path.contains(jump)) {
                jump = father[jump];
            }
            ans[i] = jump;
            path.clear();
        }
        return ans;
    }

    // 2、线批量查询最优解 -> Tarjan + 并查集   上课重点讲的这个方法，一般在笔试中也是以离线查询的方法来考察的，面试可能会考在线查询
    // 如果有M条查询，时间复杂度O(N + M)
    // 但是必须把M条查询一次给全，不支持在线查询
    public static int[] query2(int[] father, int[][] queries) {
        int N = father.length;
        int M = queries.length;
        // 记录每一个父节点又多少个直接孩子   help[i] = x   表示i节点有x个直接孩子
        int[] help = new int[N];
        int h = 0;
        for (int i = 0; i < N; i++) {
            if (father[i] == i) {
                h = i;
            } else {
                help[father[i]]++;
            }
        }
        // mt[][]数组是整棵树，标记每个节点都是那些孩子节点。 例如：求head下方有哪些点（head所有的直接孩子节点），mt[head] = {a,b,c,d}，head的直接孩子是a、b、c、d
        int[][] mt = new int[N][];
        for (int i = 0; i < N; i++) {
            // 根据i有多少个直接孩子来创建mt[i]数组大小
            mt[i] = new int[help[i]];
        }
        // 遍历father数组，将每一个节点的直接孩子节点加入到对应的数组中
        for (int i = 0; i < N; i++) {
            if (i != h) {
                mt[father[i]][--help[father[i]]] = i;
            }
        }

        // 此时help数组都有清为0了

        // 遍历问题数组，将每一个节点的问题数都加1
        for (int i = 0; i < M; i++) {
            // 剔除掉问题的两个节点是同一个节点的情况
            if (queries[i][0] != queries[i][1]) {
                // 两个节点格子的问题书都加1
                help[queries[i][0]]++;
                help[queries[i][1]]++;
            }
        }
        // 创建问题和答案数组
        int[][] mq = new int[N][];
        int[][] mi = new int[N][];
        // 根据help数组来创建每一个节点问题列表的大小
        for (int i = 0; i < N; i++) {
            mq[i] = new int[help[i]];
            mi[i] = new int[help[i]];
        }
        // 将每一个节点的所有问题都加入mq数组中
        // 并且将mi中每个位置要放哪个问题的答案，把对应位置放上问题的编号i
        for (int i = 0; i < M; i++) {
            if (queries[i][0] != queries[i][1]) {
                mq[queries[i][0]][--help[queries[i][0]]] = queries[i][1];
                mi[queries[i][0]][help[queries[i][0]]] = i;
                mq[queries[i][1]][--help[queries[i][1]]] = queries[i][0];
                mi[queries[i][1]][help[queries[i][1]]] = i;
            }
        }
        // 答案数组
        int[] ans = new int[M];
        // 构造并查集
        UnionFind uf = new UnionFind(N);
        // 开始二叉树递归遍历，并且计算答案
        process(h, mt, mq, mi, uf, ans);
        // 将问题的两个节点是相同的问题答案直接设置成两个中的任意一个节点即可
        for (int i = 0; i < M; i++) {
            // 查询的两个节点是同一个节点
            if (queries[i][0] == queries[i][1]) {
                ans[i] = queries[i][0];
            }
        }
        // 返回答案
        return ans;
    }

    // 当前来到head点
    // mt[][]数组是整棵树，标记每个节点都是那些孩子节点。 例如：求head下方有哪些点（head所有的孩子节点），mt[head] = {a,b,c,d}，head的孩子是a、b、c、d
    // mq[][]数组问题列表，标记head有哪些查询任务，例如：mq[head] = {x,y,z}，表示head和三个节点存在查询任务(head，x) (head，y) (head z)
    // mi[][]存储问题的答案，一维下标表示是哪个节点的查询任务，二维下标表示是几号查询，mi和mq的下表是对应的，例如(head，x)(head，y) (head z)三个查询的答案就依次填写，mi[head] = {6, 12, 34}
    // uf 并查集
    public static void process(int head, int[][] mt, int[][] mq, int[][] mi, UnionFind uf, int[] ans) {
        // 遍历head节点的所有孩子
        for (int next : mt[head]) {
            // 就是按照二叉树递归遍历，构造出孩子节点们的答案
            process(next, mt, mq, mi, uf, ans);
            // 遍历完又返回到head节点后，就将head节点和next孩子节点的结合合并，合并集合的代表节点就是head
            uf.union(head, next);
            // 并将合并后集合的tag设置为当前的根节点head
            uf.setTag(head, head);
        }
        // 解决head的查询问题
        int[] q = mq[head];
        int[] i = mi[head];
        // 遍历所有的问题
        for (int k = 0; k < q.length; k++) {
            // 通过q[k]，遍历查看head和谁有查询问题。问题答案填在i[k]中
            // 查看当前和head存在查询问题的节点q[k]的tag
            int tag = uf.getTag(q[k]);
            // 如果tag不为-1，说明q[k]节点已经遍历过了
            if (tag != -1) {
                // 已经便利过q[k]的话，这个i[k]问题的答案就是q[k]的tag
                ans[i[k]] = tag;
            }
        }
    }

    // 并查集
    public static class UnionFind {
        private int[] f; // father -> 并查集里面father信息，i -> i的father
        private int[] s; // size[] -> 集合 --> i size[i]
        private int[] t; // tag[] -> 集合 ---> tag[i] = ?   数组下标是集合的代表节点
        private int[] h; // 栈？并查集搞扁平化

        public UnionFind(int N) {
            f = new int[N];
            s = new int[N];
            t = new int[N];
            h = new int[N];
            for (int i = 0; i < N; i++) {
                f[i] = i;
                s[i] = 1;
                // 一开始tag都初始化为-1
                t[i] = -1;
            }
        }

        // 找到i的代表节点
        private int find(int i) {
            int j = 0;
            // 路径压缩
            // i -> j -> k -> s -> a -> a
            while (i != f[i]) {
                h[j++] = i;
                i = f[i];
            }
            // i -> a
            // j -> a
            // k -> a
            // s -> a
            while (j > 0) {
                h[--j] = i;
            }
            // a
            return i;
        }

        // 合并两个节点
        public void union(int i, int j) {
            int fi = find(i);
            int fj = find(j);
            // 小节点合并到大节点上
            if (fi != fj) {
                int si = s[fi];
                int sj = s[fj];
                int m = si >= sj ? fi : fj;
                int l = m == fi ? fj : fi;
                f[l] = m;
                s[m] += s[l];
            }
        }

        // 集合的某个元素是i，请把整个集合打上统一的标签，tag
        public void setTag(int i, int tag) {
            // t数组中存储的是集合的代表节点
            t[find(i)] = tag;
        }

        // 集合的某个元素是i，请把整个集合的tag信息返回
        public int getTag(int i) {
            // t数组中存储的是集合的代表节点
            return t[find(i)];
        }

    }

    // 3、在线查询最优解 -> 树链剖分
    // 空间复杂度O(N), 支持在线查询，单次查询时间复杂度O(logN)
    // 如果有M次查询，时间复杂度O(N + M * logN)
    public static int[] query3(int[] father, int[][] queries) {
        TreeChain tc = new TreeChain(father);
        int M = queries.length;
        int[] ans = new int[M];
        for (int i = 0; i < M; i++) {
            // x y ?
            // x x x
            if (queries[i][0] == queries[i][1]) {
                ans[i] = queries[i][0];
            } else {
                ans[i] = tc.lca(queries[i][0], queries[i][1]);
            }
        }
        return ans;
    }

    public static class TreeChain {
        private int n;
        private int h;
        private int[][] tree;
        private int[] fa;
        private int[] dep;
        private int[] son;
        private int[] siz;
        private int[] top;

        public TreeChain(int[] father) {
            initTree(father);
            dfs1(h, 0);
            dfs2(h, h);
        }

        private void initTree(int[] father) {
            n = father.length + 1;
            tree = new int[n][];
            fa = new int[n];
            dep = new int[n];
            son = new int[n];
            siz = new int[n];
            top = new int[n--];
            int[] cnum = new int[n];
            for (int i = 0; i < n; i++) {
                if (father[i] == i) {
                    h = i + 1;
                } else {
                    cnum[father[i]]++;
                }
            }
            tree[0] = new int[0];
            for (int i = 0; i < n; i++) {
                tree[i + 1] = new int[cnum[i]];
            }
            for (int i = 0; i < n; i++) {
                if (i + 1 != h) {
                    tree[father[i] + 1][--cnum[father[i]]] = i + 1;
                }
            }
        }

        private void dfs1(int u, int f) {
            fa[u] = f;
            dep[u] = dep[f] + 1;
            siz[u] = 1;
            int maxSize = -1;
            for (int v : tree[u]) {
                dfs1(v, u);
                siz[u] += siz[v];
                if (siz[v] > maxSize) {
                    maxSize = siz[v];
                    son[u] = v;
                }
            }
        }

        private void dfs2(int u, int t) {
            top[u] = t;
            if (son[u] != 0) {
                dfs2(son[u], t);
                for (int v : tree[u]) {
                    if (v != son[u]) {
                        dfs2(v, v);
                    }
                }
            }
        }

        public int lca(int a, int b) {
            a++;
            b++;
            while (top[a] != top[b]) {
                if (dep[top[a]] > dep[top[b]]) {
                    a = fa[top[a]];
                } else {
                    b = fa[top[b]];
                }
            }
            return (dep[a] < dep[b] ? a : b) - 1;
        }
    }

    // 为了测试
    // 随机生成N个节点树，可能是多叉树，并且一定不是森林
    // 输入参数N要大于0
    public static int[] generateFatherArray(int N) {
        int[] order = new int[N];
        for (int i = 0; i < N; i++) {
            order[i] = i;
        }
        for (int i = N - 1; i >= 0; i--) {
            swap(order, i, (int) (Math.random() * (i + 1)));
        }
        int[] ans = new int[N];
        ans[order[0]] = order[0];
        for (int i = 1; i < N; i++) {
            ans[order[i]] = order[(int) (Math.random() * i)];
        }
        return ans;
    }

    // 为了测试
    // 随机生成M条查询，点有N个，点的编号在0~N-1之间
    // 输入参数M和N都要大于0
    public static int[][] generateQueries(int M, int N) {
        int[][] ans = new int[M][2];
        for (int i = 0; i < M; i++) {
            ans[i][0] = (int) (Math.random() * N);
            ans[i][1] = (int) (Math.random() * N);
        }
        return ans;
    }

    // 为了测试
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 为了测试
    public static boolean equal(int[] a, int[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 1000;
        int M = 200;
        int testTime = 50000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * N) + 1;
            int ques = (int) (Math.random() * M) + 1;
            int[] father = generateFatherArray(size);
            int[][] queries = generateQueries(ques, size);
            int[] ans1 = query1(father, queries);
            int[] ans2 = query2(father, queries);
            int[] ans3 = query3(father, queries);
            if (!equal(ans1, ans2) || !equal(ans1, ans3)) {
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }
}
