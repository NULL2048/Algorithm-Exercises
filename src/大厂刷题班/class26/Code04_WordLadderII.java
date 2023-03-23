package 大厂刷题班.class26;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
// 非常经典的一道题，也是一道很典型的递归练习题。
// 递归  宽搜  深搜
// 本题测试链接 : https://leetcode.cn/problems/word-ladder-ii/
public class Code04_WordLadderII {
    public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        // 记录所有符合条件的转换路径，作为答案返回
        List<List<String>> ans = new ArrayList<>();

        // beginWord并不保证一定在wordList中，但是我们在构造邻接表的时候也是要为beginWord构造邻接表的，所以在一开始我们可以手动把beginWrod加入到wordList中即可
        wordList.add(beginWord);
        // 为wordList创建一个Set，这样既可以做到去重，又可以在后面快速查找某个单词是否在单词表中
        HashSet<String> wordSet = new HashSet(wordList);

        // 为所有单词表中的单词构建邻接表，邻接表的直接相连的条件就是两个单词只能相差一个字母，并且这两个单词都是单词表中的才需要构建邻接表
        // key：单词表中的单词
        // value：是一个List<String>  存储的是所有和key直接相连的单词，这些单词一定和key只相差一个字母，并且也都是单词表中的
        // neighborMap中的key必然都是单词表中的，并且也包括beginWord
        HashMap<String, List<String>> neighborMap = getNeighborMap(wordSet);
        // 再根据neighborMap邻接表构造每个单词的距离表，记录每一个单词表中的字符串(包括beginWrod)离beginWord的距离
        HashMap<String, Integer> distanceMap = getDistanceMap(beginWord, neighborMap);
        // 需要保证距离表中存在key为endWord的数据，即有endWord到startWord的距离，只有距离表中存在这个数据，才说明在宽度优先遍历的时候成功从startWrod到达了endWord，如果startWrod压根到达不了endWord，那么一定是没有符合条件的转化路径的，直接返回空ans。
        if (!distanceMap.containsKey(endWord)) {
            return ans;
        }

        // 还需要创建一个所有的单词到endWord的距离表，为了在后面判断转化路径严格距离加1的时候，做两个方向的判断，确保是最短路径
        // 记录每一个单词表中的字符串(包括endWord)离endWord的距离
        HashMap<String, Integer> distanceMap2 = getDistanceMap(endWord, neighborMap);
        // 记录递归过程中的递归转换路径
        LinkedList<String> path = new LinkedList<>();

        // 主流程，开始深度优先遍历找符合条件的转换路径
        getShortestPath(beginWord, endWord, neighborMap, distanceMap, path, ans, distanceMap2);

        return ans;
    }

    // 构造每一个单词的邻接表
    public static HashMap<String, List<String>> getNeighborMap(HashSet<String> wordSet) {
        // 创建邻接表
        HashMap<String, List<String>> neighborMap = new HashMap<>();

        // 为单词表中的每一个单词构造邻接表，也就是找到和他们直接相连的单词，这些单词只和他们相差1个字母
        for (String word : wordSet) {
            // 调用getNexts(word, wordSet)，去找到所有和word只相差一个字符，并且是单词表中的单词
            neighborMap.put(word, getNexts(word, wordSet));
        }

        return neighborMap;
    }

    // 找到所有和word只相差一个字符，并且是单词表中的单词，将他们构造成List返回
    public static List<String> getNexts(String word, HashSet<String> wordSet) {
        // 要返回的List
        List<String> nexts = new ArrayList<String>();
        // 将word转换成字符数组
        char[] w = word.toCharArray();

        // 遍历word的每一个字符，然后尝试和它只相差一个字符的所有情况，然后在从所有情况中选出在单词表中的单词加入到List中
        for (int i = 0; i < w.length; i++) {
            // 尝试所有的字符，每次只变一个位置
            for (char cha = 'a'; cha <= 'z'; cha++) {
                // 尝试的字符不能不能和原字符相同
                if (cha != w[i]) {
                    // 改变一个字符
                    char temp = w[i];
                    w[i] = cha;
                    // 如果当前这个单词在单词表中，就将其加入nexts，这个就是和word直接相连的单词
                    if (wordSet.contains(String.valueOf(w))) {
                        nexts.add(String.valueOf(w));
                    }
                    // 恢复现场
                    w[i] = temp;
                }

            }
        }

        return nexts;
    }

    // 通过宽度优先遍历利用邻接表来构造距离表
    // 从startWord开始宽度优先遍历，将所有能够遍历到的单词构造距离表，计算单词到startWord的距离
    public static HashMap<String, Integer> getDistanceMap(String startWord, HashMap<String, List<String>> neighborMap) {
        // 创建队列用于宽度优先遍历
        Queue<String> queue = new LinkedList<>();
        // 先将startWord加入到队列中
        queue.add(startWord);
        // 创建距离表
        // key：字符串
        // value：key到startWord的距离
        HashMap<String, Integer> distanceMap = new HashMap<>();
        // startWord到startWord的距离是0
        distanceMap.put(startWord, 0);

        // 标记哪些节点已经遍历到了，防止BFS时走回头路
        HashSet<String> set = new HashSet<>();
        // 将startWord加入Set
        set.add(startWord);
        // 开始宽度优先遍历
        while (!queue.isEmpty()) {
            // 队列中弹出队头
            String temp = queue.poll();
            // 获取temp所有直接相连的字符串，将其加入到队列中
            for (String next : neighborMap.get(temp)) {
                // 不走回头路
                if (!set.contains(next)) {
                    // next到达startWord的距离比他的上级节点temp到startWord的距离多1，这里直接用temp的距离加1
                    distanceMap.put(next, distanceMap.get(temp) + 1);
                    // 将next加入队列
                    queue.add(next);
                    // 将next加入set
                    set.add(next);
                }
            }
        }

        return distanceMap;
    }

    // nowWord：当前来到的字符串 可变
    // endWord：目标字符串，固定参数
    // neighborMap：每一个字符串的邻居表
    // distanceMap距离表：nowWord到beginWord的距离
    // distanceMap2距离表：nowWord到endWord的距离
    // path : 来到nowWord之前，深度优先遍历之前的历史路径是什么
    // ans : 要返回的答案
    // 通过深度优先遍历来找符合条件的路径，需要用两个距离表来知道我们走正确路径的深度优先遍历
    public static void getShortestPath(String nowWord, String endWord, HashMap<String, List<String>> neighborMap, HashMap<String, Integer> distanceMap, LinkedList<String> path, List<List<String>> ans, HashMap<String, Integer> distanceMap2) {
        // 先将当前到达加入到path中
        path.add(nowWord);

        // 如果当前的nowWord就是endWord，说明找到了一条符合条件的最短转换路径，将答案加入到ans中，向上层返回
        if (nowWord.equals(endWord)) {
            // 不能直接将path加入，要单独创建一个地址空间加入到ans中，因为path的地址在所有的递归层中是共用的，在后面的递归中可能会改变path中的值，如果直接将path加入到ans中，就会导致ans中的内容会随着递归操作一起变化
            ans.add(new LinkedList<String>(path));
        // 还没有到递归出口
        } else {
            // 遍历nowWord所有直接相连的节点，找到到beginWord距离严格加1的节点，去深度遍历
            for (String next : neighborMap.get(nowWord)) {
                // 需要满足两个条件：
                // 1、next到beginWord的距离比其上一个节点nowWord严格加1
                // 2、next到endWord的距离比其上一个节点nowWord严格减1
                // 只有满足这两个条件，参能保证找到的路径是最短路径
                if (distanceMap.get(next) == distanceMap.get(nowWord) + 1 &&
                        distanceMap2.get(next) == distanceMap2.get(nowWord) - 1) {
                    // 遍历到next，继续深度优先遍历
                    getShortestPath(next, endWord, neighborMap, distanceMap, path, ans, distanceMap2);
                }
            }
        }
        // 还要将本层一开始加入的节点弹出，要恢复现场，不能影响其他的递归流程
        path.pollLast();
    }
}
