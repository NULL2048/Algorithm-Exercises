package 大厂刷题班.class31;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 宽度优先遍历
// https://leetcode.cn/problems/word-ladder/
public class Problem_0127_WordLadder {
    // 下面都是左神的代码，值得学习

    // 1、左神的优秀解
    // start，出发的单词
    // to, 目标单位
    // list, 列表
    // to 一定属于list
    // start未必
    // 返回变幻的最短路径长度
    public static int ladderLength1(String start, String to, List<String> list) {
        list.add(start);

        // key : 列表中的单词，每一个单词都会有记录！
        // value : key这个单词，有哪些邻居！
        HashMap<String, ArrayList<String>> nexts = getNexts(list);
        // abc  出发     abc  -> abc  0
        //
        // bbc  1
        HashMap<String, Integer> distanceMap = new HashMap<>();
        distanceMap.put(start, 1);
        HashSet<String> set = new HashSet<>();
        set.add(start);
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            Integer distance = distanceMap.get(cur);
            for (String next : nexts.get(cur)) {
                if (next.equals(to)) {
                    return distance + 1;
                }
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                    distanceMap.put(next, distance + 1);
                }
            }

        }
        return 0;
    }

    public static HashMap<String, ArrayList<String>> getNexts(List<String> words) {
        HashSet<String> dict = new HashSet<>(words);
        HashMap<String, ArrayList<String>> nexts = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            nexts.put(words.get(i), getNext(words.get(i), dict));
        }
        return nexts;
    }

    // 应该根据具体数据状况决定用什么来找邻居
    // 1)如果字符串长度比较短，字符串数量比较多，以下方法适合
    // 2)如果字符串长度比较长，字符串数量比较少，以下方法不适合
    public static ArrayList<String> getNext(String word, HashSet<String> dict) {
        ArrayList<String> res = new ArrayList<String>();
        char[] chs = word.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            for (char cur = 'a'; cur <= 'z'; cur++) {
                if (chs[i] != cur) {
                    char tmp = chs[i];
                    chs[i] = cur;
                    if (dict.contains(String.valueOf(chs))) {
                        res.add(String.valueOf(chs));
                    }
                    chs[i] = tmp;
                }
            }
        }
        return res;
    }


    // 2、左神优化解，这个相对于上面的代码是常数优化，但是也算是最优解了
    public static int ladderLength2(String beginWord, String endWord, List<String> wordList) {
        HashSet<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) {
            return 0;
        }
        HashSet<String> startSet = new HashSet<>();
        HashSet<String> endSet = new HashSet<>();
        HashSet<String> visit = new HashSet<>();
        startSet.add(beginWord);
        endSet.add(endWord);
        for (int len = 2; !startSet.isEmpty(); len++) {
            // startSet是较小的，endSet是较大的
            HashSet<String> nextSet = new HashSet<>();
            for (String w : startSet) {
                // w -> a(nextSet)
                // a b c
                // 0
                //   1
                //     2
                for (int j = 0; j < w.length(); j++) {
                    char[] ch = w.toCharArray();
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c != w.charAt(j)) {
                            ch[j] = c;
                            String next = String.valueOf(ch);
                            if (endSet.contains(next)) {
                                return len;
                            }
                            if (dict.contains(next) && !visit.contains(next)) {
                                nextSet.add(next);
                                visit.add(next);
                            }
                        }
                    }
                }
            }
            // startSet(小) -> nextSet(某个大小)   和 endSet大小来比
            startSet = (nextSet.size() < endSet.size()) ? nextSet : endSet;
            endSet = (startSet == nextSet) ? endSet : nextSet;
        }
        return 0;
    }


    // 3、这是我自己写的提交给力扣的代码，完全是只用宽度优先遍历实现的，和左神的方法一是相同的思路
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        wordList.add(beginWord);
        HashSet<String> wordSet = new HashSet<>(wordList);
        // 先生成邻接表
        HashMap<String, List<String>> neighborMap = getNeighborMap(wordSet);

        // 再利用宽度优先搜索构造距离表，并且在构造距离表的过程中来找到最短路径的长度
        return getShortestPathLength(beginWord, endWord, neighborMap);
    }

    // 构造邻接表
    public HashMap<String, List<String>> getNeighborMap(HashSet<String> wordSet) {
        HashMap<String, List<String>> neighborMap = new HashMap<>();

        for (String word : wordSet) {
            List<String> list = new ArrayList<>();
            char[] w = word.toCharArray();
            for (int i = 0; i < w.length; i++) {
                for (char cha = 'a'; cha <= 'z'; cha++) {
                    if (cha != w[i]) {
                        char temp = w[i];
                        w[i] = cha;

                        if (wordSet.contains(String.valueOf(w))) {
                            list.add(String.valueOf(w));
                        }
                        w[i] = temp;
                    }
                }
            }

            neighborMap.put(word, list);
        }

        return neighborMap;
    }

    // 宽度优先遍历找到最短路径的长度
    public int getShortestPathLength(String beginWord, String endWord, HashMap<String, List<String>> neighborMap) {
        Queue<String> queue = new LinkedList<String>();
        HashSet<String> set = new HashSet<>();
        HashMap<String, Integer> distanceMap = new HashMap<>();
        queue.add(beginWord);
        set.add(beginWord);
        // 这里初始的距离是1，不是0，因为只要有一个单词也算是路径有1个长度了
        distanceMap.put(beginWord, 1);

        // 经典BFS
        while (!queue.isEmpty()) {
            String cur = queue.poll();

            for (String str : neighborMap.get(cur)) {
                // 不走回头路
                if (!set.contains(str)) {
                    // 因为整个流程是从起点一层一层的往外遍历，所以第一次遍历到结尾单词时找到的路径长度一定就是最短的，因为每一次的长度都是严格加1
                    if (str.equals(endWord)) {
                        return distanceMap.get(cur) + 1;
                    } else {
                        queue.add(str);
                        set.add(str);
                        distanceMap.put(str, distanceMap.get(cur) + 1);
                    }
                }

            }
        }

        return 0;
    }
}
