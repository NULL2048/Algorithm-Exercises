package 大厂刷题班.class06;

// 前缀树   贪心
// 测试链接 : https://leetcode.cn/problems/maximum-xor-with-an-element-from-array/
public class Code03_MaximumXorWithAnElementFromArray {
    public int[] maximizeXor(int[] nums, int[][] queries) {
        NumTrie tris = new NumTrie();
        for (int i = 0; i < nums.length; i++) {
            tris.add(nums[i]);
        }

        int[] max = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            max[i] = tris.getMaxXor(queries[i][0], queries[i][1]);
        }
        return max;

    }

    // 前缀树节点
    public class Node {
        // 记录每个前缀树节点的两条子路径next[0]表示0路径，next[1]表示1路径
        private Node[] next;
        private int min;

        public Node() {
            this.next = new Node[2];
            this.min = Integer.MAX_VALUE;
        }
    }

    public class NumTrie {
        // 前缀树头节点
        private Node head;

        public NumTrie() {
            this.head = new Node();
        }

        // 将数加入到前缀树中
        public void add(int num) {

            Node cur = head;
            head.min = Math.min(head.min, num);
            // 循环分解num的每一位，将其加入到前缀树中
            for (int move = 31; move >= 0; move--) {
                // 提取出num的每一位
                int curBit = (num >> move) & 1;

                // 如果当前位curbit在前缀树中已经存在了，则复用
                if (cur.next[curBit] != null) {
                    cur = cur.next[curBit];
                    cur.min = Math.min(cur.min, num);
                    // 不存在，则新建
                } else {
                    cur.next[curBit] = new Node();
                    cur = cur.next[curBit];
                    cur.min = Math.min(cur.min, num);

                }
            }
        }

        // 这个结构中，已经收集了一票数字
        // 请返回哪个数字与X异或的结果最大，返回最大结果
        // 但是，只有<=m的数字，可以被考虑
        public int getMaxXor(int num, int m) {
            if (head.min > m) {
                return -1;
            }

            Node cur = head;
            // 要返回的答案
            int ans = 0;

            for (int move = 31; move >= 0; move--) {
                // 提取每一位
                int curBit = (num >> move) & 1;
                // 当前这一位和什么数异或能最大，肯定是和它不一样的数是最大的，这样才能尽量保证高位是1
                // 这里考虑负数，在31位符号位的时候，和自己本身异或才能拿到最大值，因为如果是负数的话，和自己异或就可以把负数变成正数
                int best =  move == 31 ? curBit : curBit ^ 1;

                // 如果当前向下的分支中正好存在我们想要的分支best，则将该分支添加到ans中
                if (cur.next[best] != null && cur.next[best].min <= m) {
                    // 将该分支的异或结果添加到ans中
                    ans |= ((best ^ curBit)  << move);
                    // 向下层遍历
                    cur = cur.next[best];
                    // 如果当前向下的分支中不存在我们想要的分支best，但是另外一条分支是存在的，那么就只能走另外一条分支了，因为没得可选了
                } else if (cur.next[curBit] != null && cur.next[curBit].min <= m){
                    // 将该分支的异或结果添加到ans中
                    ans |= ((curBit ^ curBit) << move);
                    // 向下层遍历
                    cur = cur.next[curBit];
                }
            }
            return ans;
        }
    }



    // 课程上老师的代码版本，指的学习一下他的coding技巧
//    public static int[] maximizeXor(int[] nums, int[][] queries) {
//        int N = nums.length;
//        NumTrie trie = new NumTrie();
//        for (int i = 0; i < N; i++) {
//            trie.add(nums[i]);
//        }
//        int M = queries.length;
//        int[] ans = new int[M];
//        for (int i = 0; i < M; i++) {
//            ans[i] = trie.maxXorWithXBehindM(queries[i][0], queries[i][1]);
//        }
//        return ans;
//    }
//
//    public static class Node {
//        public int min;
//        public Node[] nexts;
//
//        public Node() {
//            min = Integer.MAX_VALUE;
//            nexts = new Node[2];
//        }
//    }
//
//    public static class NumTrie {
//        public Node head = new Node();
//
//        public void add(int num) {
//            Node cur = head;
//            head.min = Math.min(head.min, num);
//            for (int move = 30; move >= 0; move--) {
//                int path = ((num >> move) & 1);
//                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
//                cur = cur.nexts[path];
//                cur.min = Math.min(cur.min, num);
//            }
//        }
//
//        // 这个结构中，已经收集了一票数字
//        // 请返回哪个数字与X异或的结果最大，返回最大结果
//        // 但是，只有<=m的数字，可以被考虑
//        public int maxXorWithXBehindM(int x, int m) {
//            if (head.min > m) {
//                return -1;
//            }
//            // 一定存在某个数可以和x结合
//            Node cur = head;
//            int ans = 0;
//            for (int move = 30; move >= 0; move--) {
//                int path = (x >> move) & 1;
//                // 期待遇到的东西
//                int best = (path ^ 1);
//                best ^= (cur.nexts[best] == null || cur.nexts[best].min > m) ? 1 : 0;
//                // best变成了实际遇到的
//                ans |= (path ^ best) << move;
//                cur = cur.nexts[best];
//            }
//            return ans;
//        }
//    }

}
