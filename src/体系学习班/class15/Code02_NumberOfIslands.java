package 体系学习班.class15;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

// 本题为leetcode原题
// 测试链接：https://leetcode.com/problems/number-of-islands/
// 所有方法都可以直接通过
public class Code02_NumberOfIslands {

    //================并查集：使用新建一个空类通过地址来区分都值都是1但是在不同位置的元素======================
    // 辅助空类，用来区分不同位置的元素
    public static class Dot { }
    public static class Node<V> {
        V value;
        // 如果这里的value传入的是char，那么因为char是值传递，那么无法区分值相同但是在二维数组不同位置的元素。
        // 比如a[1][2] = '1'   a[0][1] = '1'  两个明明不是同一个元素，但是如果值传递作为value创建Node的话，就无法单纯通过value来区分不同的Node了
        // 所以这里value是不能直接用char的，需要使用辅助类来利用其地址传递区分不同的元素。
        public Node(V v) {
            value = v;
        }
    }
    // 构造并查集
    public static class UnionFind1<V> {
        // 每一个元素都对应着一个Node
        public HashMap<V, Node<V>> nodes;
        // 记录每一个元素的父节点   经过路径压缩之后就记录的是每个元素的代表节点
        public HashMap<Node<V>, Node<V>> parents;
        // 记录每一个集合有多少个元素，key存储的是集合的代表节点
        public HashMap<Node<V>, Integer> sizeMap;
        // 初始化并查集
        public UnionFind1(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            // 在最开始，将每一个样本都看作是一个独立的集合
            for (V cur : values) {
                // 为样本创建对应的Node
                Node<V> node = new Node<>(cur);
                // 建立样本和node对样的对应关系，将二者存入HashMap中
                nodes.put(cur, node);
                // 最开始将自己设置为自己的父节点
                parents.put(node, node);
                // 每一个集合大小都是1
                sizeMap.put(node, 1);
            }
        }
        // 找到代表节点，并且进行路径压缩
        public Node<V> findFather(Node<V> cur) {
            // 辅助栈，用来进行路径压缩
            Stack<Node<V>> path = new Stack<>();
            // 沿着父节点路径找到自己的代表节点
            while (cur != parents.get(cur)) {
                // 将沿途的节点都加入到栈中
                path.push(cur);
                // 找到每一个节点的父节点
                cur = parents.get(cur);
            }
            // 路径压缩，将途径的所有的父节点都设置为他们的代表节点cur
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            // 返回代表节点
            return cur;
        }
        // 合并操作
        public void union(V a, V b) {
            // 找到两个集合的代表节点
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            // 如果两个集合的代表节点地址不同，说明两个节点不是同一个集合，则进行合并操作
            if (aHead != bHead) {
                // 获取两个集合的节点个数
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                // 分别找到大集合和小集合
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                // 将小集合挂到大集合上，将小集合的代表节点设置为大集合的代表节点
                parents.put(small, big);
                // 更新大集合的大小
                sizeMap.put(big, aSetSize + bSetSize);
                // 将小集合的大小从sizeMap中移除，因为小集合合并到大集合中，小集合已经不存在了，这个HashMap还需要用来统计集合个数，所以要将不存在的集合移除
                sizeMap.remove(small);
            }
        }
        // 返回并查集中有多少个独立的集合
        public int sets() {
            // 这里直接使用HashMap的size方法进行统计
            return sizeMap.size();
        }
    }

    /*
     * 使用并查集合并求解连通性问题
     */
    public static int numIslands1(char[][] board) {
        // 行数
        int row = board.length;
        // 列数
        int col = board[0].length;
        // 创建辅助空类，用来通过其地址区分不同位置的元素   创建相同大小的二维数组
        Dot[][] dots = new Dot[row][col];
        // 将对应的值是1的dot对象加入到该List中
        List<Dot> dotList = new ArrayList<>();
        // 从左到右，从上到下遍历二维数组，
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 将所有为1的元素在dots二维数组相同的位置创建Dot对象
                if (board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    // 将二维数组中的dot对象加入到一维List中，这样就把二维问题转换成了一维问题
                    // 因为并查集需要使用一维数组传入进行初始化，这里List只是在初始化并查集的时候用一下，别的地方没用
                    // 因为二维数组中我们只把值是1的元素看成是一个样本，其他不是1的元素是不能算是样本了，在初始化并查集的时候我们只需要将样本传入进行初始化即可
                    dotList.add(dots[i][j]);
                }
            }
        }
        // 创建并查集   将dotList传入进行初始化
        UnionFind1<Dot> uf = new UnionFind1<>(dotList);
        // 遍历二维数组board，对二维数组中的样本进行并查集合并操作
        // 这里的合并原则是：每遍历到一个位置，就检查自己是不是1，如果自己是1，则去看自己的上面和自己的左边是不是1，如果是1的话则进行合并
        // 并查集连通问题的关键点就是找到合并原则，这个一般就是根据题意想到的
        // 前面的两个循环是单独先合并一边第一行和第一列，因为第一行没有上面的节点，第一列没有左边的节点，我们把他们单独拿出来进行合并，这样就不用再考虑边界问题了
        // 遍历第一列，只判断自己右边的元素进行合并
        for (int j = 1; j < col; j++) {
            // (0,j)  (0,0)跳过了，因为(0,0)既没有上面的元素也没有左面的元素  (0,1) (0,2) (0,3)
            // 当右边的数也是1，自己也是1，说明两个可以进行合并
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                // 进行合并操作，在这里传入的并不是board二维数组元素，而是相同位置的dots二维数组元素
                // 因为在合并过程中，有一步是要判断两个样本的代表节点地址是不是相同，如果相同的话说明已经是在同一个集合中了，不需要合并了
                // 但是board是char类型的二维数组，char类型在Java中都是值传递，如果传入的是board数组元素，那么就将两个相同的字符'1'传入，就如发区分样本究竟是不是同一个样本了，也无法通过相同的字符'1'找到各自对应的不同Node对象
                // 所以这里我们就引入了Dot辅助空类，就是为了利用它的地址来区分不同的'1'
                uf.union(dots[0][j - 1], dots[0][j]);
            }
        }
        // 遍历第一行，只判断自己左边的元素进行合并
        for (int i = 1; i < row; i++) {
            // 当下边的数也是1，自己也是1，说明两个可以进行合并
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf.union(dots[i - 1][0], dots[i][0]);
            }
        }
        // 双层循环，遍历合并剩下位置的元素
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                // 合并前提是自己必须是1
                if (board[i][j] == '1') {
                    // 右面的元素是否为1，是则合并
                    if (board[i][j - 1] == '1') {
                        uf.union(dots[i][j - 1], dots[i][j]);
                    }
                    // 下面的元素是否为1，是则合并
                    if (board[i - 1][j] == '1') {
                        uf.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }
        // 执行完全部的合并操作之后，返回当前并查集中独立的集合个数，则为小岛数
        return uf.sets();
    }


    //================并查集：将二维数组的两个下标通过公式转换成一维数组的下标，以此来区分值都为1的不同元素======================


    // 构建并查集   使用数组实现，不适用HashMap
    public static class UnionFind2 {
        // 记录每个样本的父节点   路径压缩后记录的是代表节点
        private int[] parent;
        // 记录每个独立集合中有多少个成员
        // 这个数组只有下标是代表节点才有意义，其他不是代表节点的下标对应的值都是无效的，所以这个题不能通过直接统计这个数组的长度来获取并查集中集合的个数，需要自己单独去统计。
        // 不像上面的方法，直接用HashMap的size方法就可以
        private int[] size;
        // 用来进行路径压缩
        private int[] help;
        // 列数
        private int col;
        // 记录整个并查集中有多少个独立集合
        private int sets;
        // 构造方法，初始化并查集
        public UnionFind2(char[][] board) {
            // 记录有多少列
            col = board[0].length;
            // 初始化集合数
            sets = 0;
            // 行数和列数一致
            int row = board.length;
            // 二维数组总的元素数量
            int len = row * col;
            // 初始化并查集中的成员数组
            parent = new int[len];
            size = new int[len];
            help = new int[len];
            // 遍历传入的二维数组，初始化构建并查集
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    // 我们只需要将1的元素加入到并查集，其他不需要加入
                    if (board[r][c] == '1') {
                        // 需要将二维数组转换为一维数组，因为我们这个并查集的结构需要一维数组
                        // 记录各项数据的如size[]、parent[]都是一维数组，所以我们传入的数据下标也需要用一维数组的下标唯一标识，不然无法找到每个样本对应的数据
                        int i = index(r, c);
                        // 每个样本父节点都初始化成自己   注意，这里转换成了一维数组了，我们就将样本本身的值也用作一维数组下标表示了，这样每一个样本的值都是不同的，就可以进行区分了
                        parent[i] = i;
                        // 一开始将每一个样本都看作是一个独立的集合，集合大小为1
                        size[i] = 1;
                        // 设置并查集总的集合数
                        sets++;
                    }
                }
            }
        }
        // (r,c) -> i    这是一个公式，可以将二维数组的下标转换为以为数组的下标。原理很节点，就是把二维数组每一行的一维数组都排成一行，然后下标就依次累加即可
        // a[r][c] = b[r * col + c]   clo是二维数组的列数  a[r][c]表示此时这个元素上面已经有r行完整的行了，然后在它所在的行，它在第c列的位置，
        // 所以我们转换成一维下标，先求出来它上面已经有多少个元素了 r * col，col是二维数组的列数   然后在加上这个元素在所在行的第几列   r * col + c
        private int index(int r, int c) {
            return r * col + c;
        }
        // 找到指定样本的代表节点    原始位置 -> 下标
        private int find(int i) {
            int hi = 0;
            // 遍历找到代表节点
            while (i != parent[i]) {
                // 记录沿途的节点，用于后续的路径压缩
                help[hi++] = i;
                i = parent[i];
            }
            // 进行路径压缩
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            // 返回代表节点
            return i;
        }
        // 合并操作  合并操作是暴露在并查集类外部的，所以传入的参数是二维数组下标，需要在方法内部转化为一维数组下标，这样才能在并查集内部找到其对应的信息
        public void union(int r1, int c1, int r2, int c2) {
            // 转换为一维数组下标
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            // 通过一维数组下标找到样本对应的代表节点
            int f1 = find(i1);
            int f2 = find(i2);
            // 本并查集不在parent[]中记录字符'1'，而是直接用一维数组下标代替，这样我们就可以直接用值来区别不同的样本了，这里就可以指就判断两个样本的代表节点是不是相同了
            // 不像上一个并查集那样，还需要引入一个辅助空类，来使用地址区分不同的样本
            if (f1 != f2) {
                // 小集合挂到大集合上
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                // 并查集的集合数减1
                sets--;
            }
        }
        // 返回并查集的集合数
        public int sets() {
            return sets;
        }
    }

    public static int numIslands2(char[][] board) {
        // 获得入参二维数组的行数
        int row = board.length;
        // 获得入参二维数组的列数
        int col = board[0].length;
        // 创建并查集
        UnionFind2 uf = new UnionFind2(board);
        // 遍历所有的元素，与相邻的上面和左面的元素尝试进行合并（只有当两者都是'1'才可以进行合并）
        // 先合并第一行和第一列
        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                // 直接传入二维数组下标即可   方法内部会将二维下标转换为一维下标，并且可以唯一标识一个样本
                uf.union(0, j - 1, 0, j);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf.union(i - 1, 0, i, 0);
            }
        }
        // 合并剩余的样本
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(i, j - 1, i, j);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(i - 1, j, i, j);
                    }
                }
            }
        }
        // 返回合并后的并查集独立集合数量
        return uf.sets();
    }

    //================不使用并查集写法，速度最快======================

    /*
     * 这里就是使用搜索的方法，每碰到一个'1'的元素，就去将和其连通的所有为'1'的元素感染成0，
     * 这样就能保证对每一块连成片的岛屿中，指挥碰到一次其中的为'1'的元素，因为只要碰到一次，其他的连通的'1'就全都设置成0了，以后也不会再找到了
     * 使用的是递归的方法进行感染
     */
    public static int numIslands3(char[][] board) {
        // 记录岛屿数量
        int islands = 0;
        // 遍历全部的岛屿
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // 当是'1'的时候，开始检查这个位置一圈的元素是不是也是1，如果是1的话，就将相邻的元素感染为0
                if (board[i][j] == '1') {
                    // 找到一个是'1'的位置，就将岛屿数加1
                    islands++;
                    infect(board, i, j);
                }
            }
        }
        return islands;
    }

    // 从(i,j)这个位置出发，把所有练成一片的'1'字符，变成0
    public static void infect(char[][] board, int i, int j) {
        // 如果相邻的元素不是'1'，或者下标已经溢出了，则直接返回
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1') {
            return;
        }
        // 将相邻的所有为1的元素都感染为0
        board[i][j] = 0;
        infect(board, i - 1, j);
        infect(board, i + 1, j);
        infect(board, i, j - 1);
        infect(board, i, j + 1);
    }


    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }
    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }
    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;
        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);
        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");
        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + numIslands1(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");
        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");
        System.out.println();
        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);
        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");
        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");
    }
}