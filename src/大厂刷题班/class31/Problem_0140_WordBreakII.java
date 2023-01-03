package 大厂刷题班.class31;

import java.util.ArrayList;
import java.util.List;

// 根据动态规划表生成路径 回溯  DFS  从左往右的尝试模型    前缀树
// https://leetcode.cn/problems/word-break-ii/
public class Problem_0140_WordBreakII {
    // 前缀树节点
    public static class Node {
        // 如果一个节点是一个单词的结尾（end为true），那么就将这个单词记录在这个节点的path属性中
        // 如果一个节点不是一个单词的末尾，这个属性就为null
        public String path;
        // 当前节点是否为一个字符串的结尾
        public boolean end;
        // 前缀树节点路线，26个小写字母
        public Node[] nexts;

        public Node() {
            // 默认为null
            path = null;
            // 默认false
            end = false;
            // 初始化长度为26的数组长度
            nexts = new Node[26];
        }
    }

    public static List<String> wordBreak(String s, List<String> wordDict) {
        // 将要拆分的字符串转化为字符数组
        char[] str = s.toCharArray();
        // 将列表中的字符串构造为前缀树，返回前缀树的根节点
        Node root = gettrie(wordDict);
        // 通过动态规划得到dp数组。
        // dp[i]：表示s字符串中从i位置开始及其之后的字符组成的字符串是否能被列表中的单词分解，能分解为true，不能分解为false。
        boolean[] dp = getdp(s, root);
        // 此时有了dp数组，下面就要利用dp数组，将路径回溯出来，得到所有的划分情况
        // 存储递归路径
        ArrayList<String> path = new ArrayList<>();
        // 存储所有的划分方案
        List<String> ans = new ArrayList<>();
        // 利用dp表递归回溯生成路径
        process(str, 0, root, dp, path, ans);
        // 返回答案
        return ans;
    }

    // 将单词表中的字符串构造前缀树
    public static Node gettrie(List<String> wordDict) {
        Node root = new Node();
        for (String str : wordDict) {
            char[] chs = str.toCharArray();
            Node node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    node.nexts[index] = new Node();
                }
                node = node.nexts[index];
            }
            // 将单词的字符串存储到这个单词的末尾节点的path属性中
            node.path = str;
            node.end = true;
        }
        return root;
    }

    // 利用动态规划构造dp数组。整个过程和LeetCode 139题的dp数组构造过程完全一致
    public static boolean[] getdp(String s, Node root) {
        char[] str = s.toCharArray();
        int N = str.length;
        boolean[] dp = new boolean[N + 1];
        dp[N] = true;
        for (int i = N - 1; i >= 0; i--) {
            Node cur = root;
            for (int end = i; end < N; end++) {
                int path = str[end] - 'a';
                if (cur.nexts[path] == null) {
                    break;
                }
                cur = cur.nexts[path];
                if (cur.end && dp[end + 1]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp;
    }

    // str[index.....] 是此时要搞定的字符串
    // dp[i]：表示s字符串中从i位置开始及其之后的字符组成的字符串是否能被列表中的单词分解，能分解为true，不能分解为false。
    // root 单词表所有单词生成的前缀树头节点
    // path str[0..index-1]做过决定了，做的决定放在path里
    // 整个流程基本的逻辑思路其实和构造dp数组差不多，这个方法是这道题重点要学习的
    public static void process(char[] str, int index, Node root, boolean[] dp, ArrayList<String> path,
                               List<String> ans) {
        // 已经完成了整个字符串的递归遍历，说明已经找到了一种合法的路径，也就是一种划分方案，将path中的路径加入到ans中
        if (index == str.length) {
            // 将path中的路径单词生成一个长句子，加入到ans中
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < path.size() - 1; i++) {
                builder.append(path.get(i) + " ");
            }
            builder.append(path.get(path.size() - 1));
            ans.add(builder.toString());
        // 还没有遍历完字符串，此时0~index-1字符串已经得到了路径，下面去尝试找str[index.....]这一部分字符串的路径
        } else {
            // 开始从前缀树中匹配以index为开始的前缀串，看能否找到能匹配上的单词，找到一个就相当于找到了路径中的一个单词，继续向下层递归
            Node cur = root;
            // 尝试找index~end这个单词是否能在前缀树中找到相应的单词
            // 尝试所有可能的end情况，找到所有可能的路径
            for (int end = index; end < str.length; end++) {
                // str[i..end] （能不能拆出来）
                int road = str[end] - 'a';
                // 如果发现出现了一个字符在前缀树中匹配不下去了，直接结束循环，因为后面的也肯定匹配不下去了，没有必要继续循环了
                if (cur.nexts[road] == null) {
                    break;
                }
                // 执行到这里说明字符匹配上了，前缀树向下移动一个位置
                cur = cur.nexts[road];
                // 如果当前到了一个单词的末尾，说明index~end这个单词匹配上前缀树上的单词了，并且需要保证str[end+1...]也是有合法的拆分方案（如果不保证后面的有合法的拆分方案，就没有去向下递归的必要，因为我们追求的是对整个字符串进行拆分）
                // 满足了这两个条件，就算是找到了路径中的一个单词，将其加入到path中
                if (cur.end && dp[end + 1]) {
                    // [i...end] 前缀串
                    // str.subString(i,end+1)  [i..end]
                    // 匹配上的单词加入到path中
                    path.add(cur.path);
                    // 开始到end+1去继续尝试
                    process(str, end + 1, root, dp, path, ans);
                    // 恢复现场，需要再去尝试别的方案
                    path.remove(path.size() - 1);
                }
            }
        }
    }
}
