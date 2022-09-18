package 体系学习班.class32;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code03_AC {
    // 前缀树的节点
    public static class Node {
        // 如果一个node，end为空，则说明这个node不是敏感词汇的结尾
        // 如果end不为空，表示这个点是某个字符串的结尾，end的值就是这个字符串
        public String end;
        // 只有在上面的end变量不为空的时候，endUse才有意义
        // 表示这个字符串之前有没有加入过答案，为true就表示这个字符串已经加入过答案了，以后就不要重复加了
        public boolean endUse;
        // fail指针
        public Node fail;
        // 每一个节点连通的路线，这里的下标就是代表英文字母，26个字母就代表了1~26的下标
        public Node[] nexts;

        public Node() {
            endUse = false;
            end = null;
            fail = null;
            nexts = new Node[26];
        }
    }

    // AC自动机
    public static class ACAutomation {
        // AC自动机中前缀树的根节点
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        // 构建前缀树，向前缀树中插入候选串
        public void insert(String s) {
            // 将字符串转换为字符数组
            char[] str = s.toCharArray();
            // 从前缀树根节点开始
            Node cur = root;
            int index = 0;
            // 向后匹配前缀串，遍历完要插入的候选串后，也就将这个候选串插入到前缀树中了
            for (int i = 0; i < str.length; i++) {
                // 将字符转换为数组下标
                index = str[i] - 'a';
                // 如果发现后续有能匹配上的字符，就直接复用这个节点
                // 如果没有能匹配上的节点，就自己创建一个节点，追加到前缀树中去
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                // 向下层遍历
                cur = cur.nexts[index];
            }
            // 为这个字符串的结尾节点设置end，就将这个字符串赋值给结尾节点的end
            cur.end = s;
        }

        // 构造玩前缀树后，再去为每一个节点设置fail指针
        // 根节点的fail指针为Null，不用单独设置，因为每个节点fail属性默认值就是null
        // 根节点的直接子节点的fail都指向根节点，因为我们是宽度遍历，一定是先遍历到的根节点的所有子节点，我们代码中是先暂时将每一个节点的fail指向根节点，
        // 在最开始的时候，他们肯定都找不到符合条件的节点，所以最后fail指针就是指向默认的根节点了
        public void build() {
            // 辅助队列，用来实现宽度优先遍历
            Queue<Node> queue = new LinkedList<>();
            // 先将根节点加入队列
            queue.add(root);
            // 当前真正遍历到的前缀树节点的位置
            Node cur = null;
            // 去沿着当前节点的fail指针进行跳转，找到符合条件的节点，然后将当前节点的fail指向它
            Node cfail = null;
            while (!queue.isEmpty()) {
                // 弹出队头节点，是某个节点的父亲，cur节点
                cur = queue.poll();
                // 宽度优先遍历当前节点的所有的路
                for (int i = 0; i < 26; i++) {
                    // cur -> 父亲
                    // cur.nexts[i] -> i号儿子
                    // 这个过程就是必须把i号儿子的fail指针设置好！
                    // 如果真的有i号儿子
                    if (cur.nexts[i] != null) {
                        // 先将i号儿子的fail暂时指向根节点
                        cur.nexts[i].fail = root;
                        // 然后去沿着当前父节点的fail指针去遍历，注意这里用的是cfail指针，并不影响cur指针
                        cfail = cur.fail;
                        // 沿着fail指针跳转，直到为null
                        while (cfail != null) {
                            // 如果沿着fail指针跳转的沿途中，存在一个节点也连通着i字符的路线
                            // 也就是和cur父节点和i号儿子连通的路线一样的字符
                            // 那么这个cfail节点通过i字符路线直接相连的节点就是i号儿子fail指针要指向的节点
                            // cfail.nexts[i] != null就说明cfail这个节点有连通着i字符的路线，符合条件
                            if (cfail.nexts[i] != null) {
                                // 将cur的i号儿子的fail指针 指向 cfail节点通过i字符路线直接相连的子节点
                                cur.nexts[i].fail = cfail.nexts[i];
                                // 跳出循环
                                break;
                            }
                            // 如果上面没有找到符合条件的cfail，就继续沿着fail指针跳转，直到遍历到null为止。
                            cfail = cfail.fail;
                        }
                        // 按照层序遍历的方法，将每次遍历到的节点加入到队列中
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        // 查询大文章中出现过哪些敏感词汇
        // 大文章：content
        public List<String> containWords(String content) {
            // 将大文章转化为字符数组
            char[] str = content.toCharArray();
            // 从前缀树根节点开始匹配
            Node cur = root;
            // 沿着fail指针跳转时用的指针
            Node follow = null;
            // 大文章数组匹配到哪个位置了
            int index = 0;
            // 用来收集要返回的答案，不会收集到重复值
            List<String> ans = new ArrayList<>();
            // 遍历大文章数组
            for (int i = 0; i < str.length; i++) {
                // 将大文章上的字符传唤为节点下标
                index = str[i] - 'a'; // 路

                // cur.nexts[index] == null：如果当前字符在这条路上没配出来（匹配失败），就随着fail方向走向下条路径。
                // 如果能匹配上，就跳过下面这个while循环
                // 如果已经沿着fail来到根节点的，也直接跳出循环，说明以当前index为头，肯定没有能匹配上的敏感词汇了
                // 跳出循环有两种可能，要么找到了有连通index路线的节点，要么时遍历到了根节点
                while (cur.nexts[index] == null && cur != root) {
                    // cur沿着fail移动，找有连通index路线的节点
                    cur = cur.fail;
                }

                // 来到这个为止，有两种情况：
                // 1) 现在来到的路径，是可以继续匹配的   cur.nexts[index] != null
                // 2) 现在来到的节点，就是前缀树的根节点  cur == root
                // 如果此时cur是有连通着的index的路线，那么将cur指向可以连通的路径连接的节点
                // 如果没有能匹配上的路线，就将cur指向root根节点，从头开始进行匹配
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                // 沿着cur节点的fail转一圈，收集end为true的节点，这个就是我们要的答案
                follow = cur;
                // 当转到根节点时结束
                while (follow != root) {
                    // 如果follow指向的当前节点endUse为true，说明这个答案已经被手机了，直接结束循环，因为沿着这条fail后面的路径一定都已经遍历完一遍了，所有能收集的答案一定都被收集好了
                    if (follow.endUse) {
                        break;
                    }

                    // 如果有不同的需求，在这一段之间修改。其实也是可以实现统计每一个敏感词出现的次数的，只要把endUse去去掉就行了，只要碰到一个就重复遍历一遍即可，然后记录好每一个敏感词出现的次数
                    // 如果follow.end != null，并且follow.endUse==false，说明false节点是我们要的答案，并且还没有被收集
                    if (follow.end != null) {
                        // 收集答案
                        ans.add(follow.end);
                        // 将该节点endUse设置为true，相当于笔记中将节点改回白色
                        follow.endUse = true;
                    }
                    // 如果有不同的需求，在这一段之间修改
                    // 沿着fail指针转一圈
                    follow = follow.fail;
                }
            }
            return ans;
        }

    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }
    }
}
