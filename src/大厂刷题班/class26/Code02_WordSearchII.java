package 大厂刷题班.class26;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

// 这道题leetcode给的数据量也说明其实这道题可以直接用暴力解，但是这里我们做了很多优化
// 前缀树  回溯  递归
// 本题测试链接 : https://leetcode.cn/problems/word-search-ii/
public class Code02_WordSearchII {

    // 1、下面是左神的代码，我觉得他写的思路最清晰，条理最清楚，所以复习的时候直接看它的代码即可。
    // 前缀树节点
    public static class TrieNode {
        // 该节点的子路线
        public TrieNode[] nexts;
        // 记录该节点被不同单词通过的次数
        public int pass;
        // 记录该节点是否是某个单词的结尾
        public boolean end;
        public TrieNode() {
            nexts = new TrieNode[26];
            pass = 0;
            end = false;
        }
    }
    // 将word加入前缀树
    public static void fillWord(TrieNode head, String word) {
        // 先给前缀树的根节点pass++
        head.pass++;
        char[] chs = word.toCharArray();
        int index = 0;
        TrieNode node = head;
        for (int i = 0; i < chs.length; i++) {
            index = chs[i] - 'a';
            if (node.nexts[index] == null) {
                node.nexts[index] = new TrieNode();
            }
            // node向下移动一个位置
            node = node.nexts[index];
            // 将下一个node的pass也加1
            node.pass++;
        }
        // 标记单词结尾节点
        node.end = true;
    }
    // 将字符List转换为String
    public static String generatePath(LinkedList<Character> path) {
        char[] str = new char[path.size()];
        int index = 0;
        for (Character cha : path) {
            str[index++] = cha;
        }
        // 利用String的方法，将字符数组转化为String
        return String.valueOf(str);
    }
    // 主函数
    public static List<String> findWords(char[][] board, String[] words) {
        // 前缀树的根节点
        TrieNode head = new TrieNode();
        // 记录前缀树中的所有字符串，做去重
        HashSet<String> set = new HashSet<>();
        // 利用单词表构造前缀树
        for (String word : words) {
            // 相同的单词就去重
            if (!set.contains(word)) {
                fillWord(head, word);
                set.add(word);
            }
        }
        // 要返回的答案
        List<String> ans = new ArrayList<>();
        // 递归沿途走过的字符，收集起来，存在path里
        LinkedList<Character> path = new LinkedList<>();
        // 尝试以每一个位置作为起始点，看看能不能找到一个单词能和前缀树中的单词匹配上
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                // 枚举在board中的所有位置
                // 每一个位置出发的情况下，答案都收集
                process(board, row, col, path, head, ans);
            }
        }
        return ans;
    }
    // 从board[row][col]位置的字符出发，
    // 之前的路径上，走过的字符，记录在path里
    // 传入的cur是此时已经来到的前缀树节点，我们要去检查传入的cur的nexts中有没有board[row][col]这个字符的路线，
    // 如果有的话，就算是匹配上了一个字符，就再去尝试匹配board[row][col]上下左右的字符，看能不能继续匹配。
    // 如果连board[row][col]都匹配不上，也就没有继续匹配的必要了，直接返回
    // 如果成功在矩阵中找到words中的某个str，就记录在 res里
    // 返回值，从(row,col)出发，一共找到了多少个str
    public static int process(
            char[][] board, int row, int col,
            LinkedList<Character> path, TrieNode cur,
            List<String> res) {
        // 当前我们要匹配的字符
        char cha = board[row][col];
        // 如果cha为0，说明矩阵中这个位置我们已经走过了，直接返回，不要再重复走了
        if (cha == 0) { // 这个row col位置是之前走过的位置
            // 找到0个单词
            return 0;
        }
        // 执行到这里说明(row,col) 不是回头路 cha 有效
        // 计算一下board[row][col]这个字符应该在nexts中的下标，然后去找一下当前的前缀树节点cur是否存在这个字符的路线
        int index = cha - 'a';
        // 如果没路，或者这条路上最终的字符串之前加入过结果里，那么就直接返回
        if (cur.nexts[index] == null || cur.nexts[index].pass == 0) {
            // 找到0个单词
            return 0;
        }

        // 没有走回头路且矩阵中的board[row][col]能够在前缀树中匹配上，那么我们就在前缀树上继续遍历到下一个节点，准备后面的匹配工作
        // 此时匹配上的字符board[row][col]对应在前缀树的路线下表是index，将cur移动到cur.nexts[index]上
        cur = cur.nexts[index];
        // 将匹配成功的board[row][col]字符加到路径里去
        path.addLast(cha);

        // 记录从row和col位置出发，后续一共搞定了多少答案
        int fix = 0;
        // 我们成功匹配到了(row,col)位置，如果这个位置的字符是一个单词的结尾，那么就说明搞定了一个单词了
        if (cur.end) {
            // 将path中找到的这个单词加入到答案中
            res.add(generatePath(path));
            // 该单词已经被收集了，为了不重复收集，将结尾节点的end设置为false
            cur.end = false;
            // 找到的单词数量++
            fix++;
        }
        // 继续往(row,col)的上、下、左、右，四个方向尝试
        // 先将board[row][col]设置为0，标记已经走过了，不要重复走
        board[row][col] = 0;
        // 下面要保证走的位置不能越界
        if (row > 0) {
            fix += process(board, row - 1, col, path, cur, res);
        }
        if (row < board.length - 1) {
            fix += process(board, row + 1, col, path, cur, res);
        }
        if (col > 0) {
            fix += process(board, row, col - 1, path, cur, res);
        }
        if (col < board[0].length - 1) {
            fix += process(board, row, col + 1, path, cur, res);
        }
        // 恢复现场
        board[row][col] = cha;
        // 将本层循环加入到字符弹出，因为还会有别的分支需要用这个位置来放别的字母
        path.pollLast();
        // 将此时正在尝试匹配的前缀树节点的pass减fix，就相当于将所有成功找到单词的通过次数一次性减掉，做到了剪枝优化，以后就不会重复走相同的路线了
        cur.pass -= fix;
        // 返回本轮找到的单词数
        return fix;
    }


    // 2、下面是我自己写的代码，起始性能和左神的是一样的，但是我觉得左神写的条例更清晰，以后复习直接看左神的就行
    /*
    public List<String> findWords(char[][] board, String[] words) {
        // 记录前缀树中的所有字符串，做去重
        HashSet<String> trieSet = new HashSet<>();
        // 前缀树的根节点
        TrieNode head = new TrieNode();
        // 利用单词表构造前缀树
        for (String word : words) {
            // 相同的单词就去重
            if (!trieSet.contains(word)) {
                addTrieNode(head, word);
                trieSet.add(word);
            }
        }

        // 记录递归轨迹中走过的字符
        LinkedList<Character> path = new LinkedList<>();
        // 要返回的答案
        List<String> ans = new ArrayList<>();
        // 尝试以每一个位置作为起始点，看看能不能找到一个单词能和前缀树中的单词匹配上
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                process(board, i, j, head, path, ans);
            }
        }

        return ans;
    }

    public int process(char[][] board, int row, int col, TrieNode node, LinkedList<Character> path, List<String> ans) {
        // 如果矩阵中该位置为0，说明该位置已经走过了，不要重复走了，直接返回0
        if (board[row][col] == 0 ) {
            return 0;
        }

        char cha = board[row][col];
        // 如果当前矩阵中的字符在前缀树中没有相应的路线，也返回0
        if (node.next[cha - 'a'] == null || node.next[cha - 'a'].pass == 0) {
            return 0;
        }

        TrieNode next = node.next[cha - 'a'];
        board[row][col] = 0;
        path.addLast(cha);
        int cnt = 0;

        // 如果来到了一个单词的结尾字符，就说明找到了一个单词，将该单词加入到ans中
        if (next.end == true) {
            ans.add(charListToString(path));
            next.end = false;
            cnt++;
        }

        // 开始尝试四个方向，并且需要保证不越界
        if (row + 1 < board.length) {
            //node.pass--;
            cnt += process(board, row + 1, col, next, path, ans);
        }

        if (row - 1 >= 0) {

            //node.pass--;
            cnt += process(board, row - 1, col, next, path, ans);

        }

        if (col + 1 < board[0].length) {
            //node.pass--;
            cnt += process(board, row, col + 1, next, path, ans);

        }

        if (col - 1 >= 0) {
            //node.pass--;
            cnt += process(board, row, col - 1, next, path, ans);
        }

        // 恢复现场
        board[row][col] = cha;
        path.pollLast();
        next.pass -= cnt;
        return cnt;
    }

    // 将字符List转换为String
    public String charListToString(LinkedList<Character> path) {
        StringBuilder sb = new StringBuilder();

        for (Character c : path) {
            sb.append(c);
        }

        return sb.toString();
    }

    // 前缀树节点类
    class TrieNode {
        public TrieNode[] next;
        // 记录该节点被不同单词通过的次数
        public int pass;
        // 该节点是否为单词结束位置
        public boolean end;

        public TrieNode() {
            next = new TrieNode[26];
            pass = 0;
            end = false;
        }
    }

    // 将word加入前缀树
    public void addTrieNode(TrieNode head, String word) {
        char[] w = word.toCharArray();

        TrieNode node = head;
        node.pass++;
        for (int i = 0; i < w.length; i++) {
            if (node.next[w[i] - 'a'] == null) {
                node.next[w[i] - 'a'] = new TrieNode();
            }
            // node向下移动一个位置
            node = node.next[w[i] - 'a'];
            // 将下面的node的pass也加1
            node.pass++;

        }
        // 标记单词结尾节点
        node.end = true;
    }
     */
}
