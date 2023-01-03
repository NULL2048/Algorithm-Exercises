package 大厂刷题班.class31;

import java.util.List;
import java.util.Set;

// 从左往右的尝试模型    可用前缀树优化，只要是涉及到前缀的问题，都有可能能够使用前缀树优化
// https://leetcode.cn/problems/word-break/
// lintcode也有测试，数据量比leetcode大很多 : https://www.lintcode.com/problem/107/
public class Problem_0139_WordBreak {
    // 1、记忆化搜索    这个是我自己写的代码，可以过力扣，但是过不了LintCode，因为LintCode数据量大，用记忆化搜索递归会导致栈溢出，还是要改成动态规划才行
    // 前缀树节点
    class Node {
        // 前缀树节点路线，26个小写字母
        public Node nexts[];
        // 当前节点是否为一个字符串的结尾
        public boolean end;

        public Node() {
            // 初始化长度为26的数组长度
            this.nexts = new Node[26];
            // 默认false
            this.end = false;
        }
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        // 创建前缀树根节点
        Node root = new Node();
        // 将列表中的单词构建前缀树
        // 遍历列表中的单词
        for (String str : wordDict) {
            // 将单词转化为字符数组
            char[] strChar = str.toCharArray();
            // 从前缀树根节点开始构造
            Node node = root;
            // 遍历当前单词的字符
            for (int i = 0; i < strChar.length; i++) {
                // 计算当前字符对应的下标
                int index = strChar[i] - 'a';
                // 如果没有该字符的路线，就不能复用了，需要自己创建一个节点
                if (node.nexts[index] == null) {
                    // 创建节点
                    node.nexts[index] = new Node();
                }
                // 将node节点向下移动
                node = node.nexts[index];
            }
            // 将单词构造完之后，将最后一个节点的end设置为true
            node.end = true;
        }

        // 将要分解的字符串转化为字符数组
        char[] sc = s.toCharArray();
        // 创建dp数组。dp[i]：表示s字符串中从i位置开始及其之后的字符组成的字符串是否能被列表中的单词分解，能分解为1，不能分解为-1。dp[i]为0表示该位置的数还没有赋值
        int[] dp = new int[sc.length + 1];
        return process(sc, 0, root, dp);
        // 这里直接返回dp[0]也是正确的，取决于递归入口的参数就是0
        //return dp[0] == 1;
    }

    // 此时字符串sc的i下标之前的前缀已经确定可以被列表中的单词进行分解，当前要去找i下标位置及其之后的字符串是否能够利用列表中的单词进行分解
    public boolean process(char[] sc, int i, Node root, int[] dp) {
        // 如果dp[i]已经有值了，直接从里面取答案
        if (dp[i] != 0) {
            return dp[i] == 1;
        }

        // basecase
        // 如果此时i == sc.length，说明已经遍历玩整个字符串了，能一直执行到这里说明整个字符串都可以被分解，直接返回true
        if (i == sc.length) {
            dp[i] = 1;
            return true;
        }

        // 从前缀树根节点开始匹配
        Node cur = root;
        // 从j=i开始尝试，尝试i~j范围上的字符串能否被列表中的单词分解
        // 这个循环就是枚举所有以i为开始的前缀字符串，看列表中的单词有没有能和其匹配上的，能匹配上就说明这个前缀字符串可以被分解
        for (int j = i; j < sc.length; j++) {
            // 随着循环遍历，也开始沿着前缀树匹配
            // 如果前缀树没有sc[j]这个字符的分治，说明无法再继续匹配了，后面也不可能找到以i未开始的能匹配上的前缀串了，直接返回false，结束循环
            if (cur.nexts[sc[j] - 'a'] == null) {
                dp[i] = -1;
                return false;
            }
            // 在前缀树中匹配上了sc[j]字符
            // 将前缀树节点向下移动一格位置
            cur = cur.nexts[sc[j] - 'a'];

            // 如果此时节点为一个单词的结束节点，说明找到了一个匹配上的字符串，以i~j范围上的字符串在列表中存在一个单词和其相同
            if (cur.end) {
                // 继续从j+1开始，找以j+1为开始的前缀串中是否能在列表中找到一个可以匹配上的字符串（要调用这个递归的一个大前提是0...j范围上的字符串已经找到了分解方案了，否则是不能再去调用这个递归的）
                if (process(sc, j + 1, root, dp)) {
                    // 如果后面的递归也返回的是true，说明找到了分解方法，返回true
                    dp[i] = 1;
                    // 这里只要求返回能否分解，不要求将所有的分解方案都找到，所以只要找到了一个true，直接返回就可以了，不用再继续向下执行了。
                    return true;
                }
            }
            // 如果执行到这里，说明以i~j范围上的字符串进行分割找不到能够将整个字符串分解的方案（有可能是i~j本身就无法分解，也有可能i~j能被分解，但是后面的字符串无法被分解）
            // 所以需要将j++,继续尝试别的范围的字符串，看能否找到分解方案
        }
        // 如果尝试完了所有情况也没有找到分解方案，就会执行到这里
        // 直接返回false
        dp[i] = -1;
        return false;
    }

    // 2、动态规划   这个也是我自己写的代码，根据记忆化搜索的代码改写的动态规划，这个是可以直接在lintcode上提交通过，和LeetCode代码唯一的不同就是入参列表是Set不是List
    public boolean wordBreak(String s, Set<String> wordSet) {
        // 创建前缀树根节点
        Node root = new Node();
        // 将列表中的单词构建前缀树
        // 遍历列表中的单词
        for (String str : wordSet) {
            // 将单词转化为字符数组
            char[] strChar = str.toCharArray();
            // 从前缀树根节点开始构造
            Node node = root;
            // 遍历当前单词的字符
            for (int i = 0; i < strChar.length; i++) {
                // 计算当前字符对应的下标
                int index = strChar[i] - 'a';
                // 如果没有该字符的路线，就不能复用了，需要自己创建一个节点
                if (node.nexts[index] == null) {
                    // 创建节点
                    node.nexts[index] = new Node();
                }
                // 将node节点向下移动
                node = node.nexts[index];
            }
            // 将单词构造完之后，将最后一个节点的end设置为true
            node.end = true;
        }

        // 将要分解的字符串转化为字符数组
        char[] sc = s.toCharArray();
        // 创建dp数组。dp[i]：表示s字符串中从i位置开始及其之后的字符组成的字符串是否能被列表中的单词分解，能分解为1，不能分解为-1。dp[i]为0表示该位置的数还没有赋值
        boolean[] dp = new boolean[sc.length + 1];
        // 根据暴力递归的basecase赋初值
        dp[sc.length] = true;

        // 对dp数组进行赋值
        for (int i = sc.length - 1; i >= 0; i--) {
            // 从前缀树根节点开始匹配
            Node cur = root;
            // 从j=i开始尝试，尝试i~j范围上的字符串能否被列表中的单词分解
            // 这个循环就是枚举所有以i为开始的前缀字符串，看列表中的单词有没有能和其匹配上的，能匹配上就说明这个前缀字符串可以被分解
            for (int j = i; j < sc.length; j++) {
                //String tempStr = s.substring(i, j + 1);
                // 随着循环遍历，也开始沿着前缀树匹配
                // 如果前缀树没有sc[j]这个字符的分治，说明无法再继续匹配了，后面也不可能找到以i未开始的能匹配上的前缀串了，直接返回false，结束循环
                if (cur.nexts[sc[j] - 'a'] == null) {
                    dp[i] = false;
                    break;
                }
                // 在前缀树中匹配上了sc[j]字符
                // 将前缀树节点向下移动一格位置
                cur = cur.nexts[sc[j] - 'a'];

                // 如果此时节点为一个单词的结束节点，说明找到了一个匹配上的字符串，以i~j范围上的字符串在列表中存在一个单词和其相同
                if (cur.end) {
                    // 继续从j+1开始，找以j+1为开始的前缀串中是否能在列表中找到一个可以匹配上的字符串（要调用这个递归的一个大前提是0...j范围上的字符串已经找到了分解方案了，否则是不能再去调用这个递归的）
                    if (dp[j + 1]) {
                        // 如果后面的递归也返回的是true，说明找到了分解方法，返回true
                        dp[i] = true;
                        // 这里只要求返回能否分解，不要求将所有的分解方案都找到，所以只要找到了一个true，直接返回就可以了，不用再继续向下执行了。
                        break;
                    }
                }
                // 如果执行到这里，说明以i~j范围上的字符串进行分割找不到能够将整个字符串分解的方案（有可能是i~j本身就无法分解，也有可能i~j能被分解，但是后面的字符串无法被分解）
                // 所以需要将j++,继续尝试别的范围的字符串，看能否找到分解方案
            }
        }

        // 这里直接返回dp[0]也是正确的，取决于递归入口的参数就是0
        return dp[0];
    }


    // 下面是左神写的代码
    // 返回能否拆分   LeetCode上可提交通过的代码
    public boolean wordBreak1(String s, List<String> wordDict) {
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
            node.end = true;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        boolean[] dp = new boolean[N + 1];
        dp[N] = true; // dp[i]  word[i.....] 能不能被分解
        // dp[N] word[N...]  -> ""  能不能够被分解
        // dp[i] ... dp[i+1....]
        for (int i = N - 1; i >= 0; i--) {
            // i
            // word[i....] 能不能够被分解
            // i..i    i+1....
            // i..i+1  i+2...
            Node cur = root;
            for (int end = i; end < N; end++) {
                cur = cur.nexts[str[end] - 'a'];
                if (cur == null) {
                    break;
                }
                // 有路！
                if (cur.end) {
                    // i...end 真的是一个有效的前缀串  end+1....  能不能被分解
                    dp[i] |= dp[end + 1];
                }
                if (dp[i]) {
                    break;
                }
            }
        }
        return dp[0];
    }


    // 返回多少种拆分方法
    public int wordBreak2(String s, List<String> wordDict) {
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
            node.end = true;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            Node cur = root;
            for (int end = i; end < N; end++) {
                cur = cur.nexts[str[end] - 'a'];
                if (cur == null) {
                    break;
                }
                if (cur.end) {
                    dp[i] += dp[end + 1];
                }
            }
        }
        return dp[0];
    }
}
