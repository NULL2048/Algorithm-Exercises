package 体系学习班.class08;

import java.util.HashMap;

public class Code01_TrieTree {
    // 前缀树节点类型
    public static class Node1 {
        public int pass;
        public int end;
        // 存储每一个节点可能指向的子节点指针
        public Node1[] nexts;

        // 构造方法
        public Node1() {
            pass = 0;
            end = 0;
            // 树数字依次表示小写字母
            // 0    a
            // 1    b
            // 2    c
            // ..   ..
            // 25   z
            // 如果char tmp = 'b'  那么想要找b的路线，就用(tmp - 'a')得出来的数就是对应的下标
            // nexts[i] == null   i方向的路不存在
            // nexts[i] != null   i方向的路存在
            // 这里我们就假设字符只有26个小写字母，所以这里创建26长度的数组就足够了
            nexts = new Node1[26];
        }
    }

    public static class Trie1 {
        private Node1 root;

        // 构造方法
        public Trie1() {
            // 创建根节点
            root = new Node1();
        }

        // 向前缀树中加入新的字符串
        public void insert(String word) {
            // 字符串为空，直接返回
            if (word == null) {
                return;
            }

            // 将String转换成字符数组
            char[] str = word.toCharArray();
            // node当前遍历到的节点，从根节点开始
            Node1 node = root;
            // 根节点pass值++，因为添加进字符串根节点的pass一定会增加的
            node.pass++;
            // 当前应该再往下走哪条路线
            int path = 0;
            // 从左往右遍历字符，遍历完所有字符之后就完成了字符串向前缀树中的添加操作
            for (int i = 0; i < str.length; i++) {
                // 根据字符计算出路线的下标值
                path = str[i] - 'a';
                // 如果下一个节点为空，则新建节点
                if (node.nexts[path] == null) {
                    node.nexts[path] = new Node1();
                }
                // 将当前节点向下走一个位置
                node = node.nexts[path];
                // 并且将这个位置pass++，因为已经有一个字符串经过了这个节点了
                node.pass++;
            }
            // 将最后一个位置的节点end++
            node.end++;
        }

        // 将指定字符串从前缀树中删除一次，注意如果该字符串加入过多次的话，不是直接全部删除，而是只删除一次
        public void delete(String word) {
            // 先查一下前缀树是否存在这个word字符串
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node1 node = root;
                // 将根节点pass--
                node.pass--;
                int path = 0;
                // 遍历所有的字符，并且将沿途节点的pass都减1
                for (int i = 0; i < chs.length; i++) {
                    path = chs[i] - 'a';
                    // 如果发现当前遍历到的节点的下一个位置的节点pass减1之后变成0了，说明该字符串已经被从前缀树中彻底删除了
                    if (--node.nexts[path].pass == 0) {
                        // 将指向下一个位置的指针设置为null，这样后面的节点都变成了不可达对象，会被gc回收
                        node.nexts[path] = null;
                        // 返回
                        return;
                    }
                    // 将当前节点向下走一个位置
                    node = node.nexts[path];
                }
                // 如果走到这里，说明该字符串并没有被从前缀树中完全删除，则将最后一个节点的end也减1
                node.end--;
            }
        }

        // 查找指定字符串向前缀树中加入了几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            // 字符串转成字符数组
            char[] chs = word.toCharArray();
            // 从根节点开始遍历
            Node1 node = root;
            // 下一个路线下标
            int index = 0;
            // 循环结束之后，node就指向了该字符串的最后一个位置
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                // 如果还没有遍历到字符串的最后一个字符就发现前缀树已经没有路了，说明该前缀树中就没有加入这个字符串，直接返回0
                if (node.nexts[index] == null) {
                    return 0;
                }
                // 将当前节点向下走一个位置
                node = node.nexts[index];
            }
            // 最后一个节点的end值就是该字符串在前缀树中出现的次数
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            // 这个流程和search()一样，只是最后返回的是pass值
            return node.pass;
        }
    }

    // 如果字符不再是只有26个小写字母，而是比较多的话，我们就可以用HashMap结构来代替之前的数组结构来标识指向后续节点的指针
    // 除此之外，其他的流程都是一样的
    public static class Node2 {
        public int pass;
        public int end;
        // 使用HashMap存储后续指向的指针
        public HashMap<Integer, Node2> nexts;

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int index = 0;
                for (int i = 0; i < chs.length; i++) {
                    index = (int) chs[i];
                    if (--node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    node = node.nexts.get(index);
                }
                node.end--;
            }
        }

        // word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
        }
    }

    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie1 trie1 = new Trie1();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");

    }
}
