package 大厂刷题班.class06;

// 前缀树  贪心
// 本题测试链接 : https://leetcode.cn/problems/maximum-xor-of-two-numbers-in-an-array/
public class Code02_MaximumXorOfTwoNumbersInAnArray {
    // 前缀树节点
    public class Node {
        // 记录每个前缀树节点的两条子路径next[0]表示0路径，next[1]表示1路径
        private Node[] next;

        public Node() {
            this.next = new Node[2];
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
            // 循环分解num的每一位，将其加入到前缀树中
            for (int move = 31; move >= 0; move--) {
                // 提取出num的每一位
                int curBit = (num >> move) & 1;

                // 如果当前位curbit在前缀树中已经存在了，则复用
                if (cur.next[curBit] != null) {
                    cur = cur.next[curBit];
                    // 不存在，则新建
                } else {
                    cur.next[curBit] = new Node();
                    cur = cur.next[curBit];
                }
            }
        }

        // 得到前缀树中和num异或结果最大的数
        public int getMaxXor(int num) {
            Node cur = head;
            // 要返回的答案
            int ans = 0;
            for (int move = 31; move >= 0; move--) {
                // 提取每一位
                int curBit = (num >> move) & 1;
                // 当前这一位和什么数异或能最大，肯定是和它不一样的数是最大的，这样才能尽量保证高位是1
                // 这里考虑负数，在31位符号位的时候，和自己本身异或才能拿到最大值，因为如果是负数的话，和自己异或就可以把负数变成正数
                int best = move == 31 ? curBit : curBit ^ 1;

                // 如果当前向下的分支中正好存在我们想要的分支best，则将该分支添加到ans中
                if (cur.next[best] != null) {
                    // 将该分支的数添加到ans中
                    ans |= (best << move);
                    // 向下层遍历
                    cur = cur.next[best];
                    // 如果当前向下的分支中不存在我们想要的分支best，但是另外一条分支是存在的，那么就只能走另外一条分支了，因为没得可选了
                } else if (cur.next[curBit] != null){
                    // 将该分支添加到ans中
                    ans |= (curBit << move);
                    // 向下层遍历
                    cur = cur.next[curBit];
                    // 如果遍历到两个分支都没有了，也就是遍历到叶子节点了，就说明此时已经完成了找异或结果最大的数，直接返回结果，不用再进行后续的遍历了
                } else {
                    return ans;
                }
            }
            return ans;
        }
    }

    public int findMaximumXOR(int[] nums) {
        // 考虑页数情况，如果长度为1，自己和自己异或就得0
        if (nums == null || nums.length == 1) {
            return 0;
        }

        NumTrie trie = new NumTrie();
        // 先将第一个数加进去
        trie.add(nums[0]);
        int max = Integer.MIN_VALUE;
        // 去尝试每一个数在前缀树中找到能异或取得最大值的结果，在这些结果中取最大值
        for (int i = 1; i < nums.length; i++) {
            // 注意这里要将getMaxXor(nums[i])的结果再和nums[i]异或一下，因为这个方法只是返回参与异或运算的数，并不是异或运算的最大结果
            max = Math.max(max, nums[i] ^ trie.getMaxXor(nums[i]));
            // 将当前数加入到前缀树中
            trie.add(nums[i]);
        }
        return max;
    }
}
