package 大厂刷题班.class33;
// 前缀树
// https://leetcode.cn/problems/implement-trie-prefix-tree/
public class Problem_0208_ImplementTriePrefixTree {
    class Trie {
        // 前缀树节点类
        public class Node {
            // 当前节点是否为一个字符串的结尾
            public boolean end;
            // 该节点的子路径
            public Node[] nexts;

            public Node() {
                this.end = false;
                // 子路径初始化26个位置，代表26个英文小写字母
                nexts = new Node[26];
            }
        }

        // 前缀树的根节点
        public Node root;

        // 初始化前缀树
        public Trie() {
            root = new Node();
        }

        // 将word字符串插入到前缀树中
        public void insert(String word) {
            char[] w = word.toCharArray();
            Node node = root;
            for (int i = 0; i < w.length; i++) {
                if (node.nexts[w[i] - 'a'] == null) {
                    node.nexts[w[i] - 'a'] = new Node();
                }

                node = node.nexts[w[i] - 'a'];
            }
            node.end = true;
        }
        // 查询前缀树中是否存在字符串word
        public boolean search(String word) {
            char[] w = word.toCharArray();
            Node node = root;
            for (int i = 0; i < w.length; i++) {
                if (node.nexts[w[i] - 'a'] == null) {
                    return false;
                }

                node = node.nexts[w[i] - 'a'];
            }
            // 只有最后的位置end为true，也就是确实是一个字符串的结尾才能返回true
            return node.end;
        }
        // 查询字符串prefix是否为前缀树中的一个前缀
        public boolean startsWith(String prefix) {
            char[] p = prefix.toCharArray();
            Node node = root;
            for (int i = 0; i < p.length; i++) {
                if (node.nexts[p[i] - 'a'] == null) {
                    return false;
                }

                node = node.nexts[p[i] - 'a'];
            }
            // 只要是找到了prefix的路径，就直接返回true
            return true;
        }
    }
}
