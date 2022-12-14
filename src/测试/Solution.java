package 测试;

import java.util.*;

class Solution {
    public static List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        wordList.add(beginWord);
        HashSet<String> wordSet = new HashSet(wordList);
        HashMap<String, List<String>> neighborMap = getNeighborMap(wordSet, beginWord);
        HashMap<String, Integer> distanceMap = getDistanceMap(beginWord, neighborMap);

        HashMap<String, Integer> distanceMap2 = getDistanceMap(endWord, neighborMap);

        LinkedList<String> path = new LinkedList<>();
        List<List<String>> ans = new ArrayList<>();
        getShortestPath(beginWord, endWord, neighborMap, distanceMap, path, ans, 0, distanceMap2);

        return ans;
    }

    public static HashMap<String, List<String>> getNeighborMap(HashSet<String> wordSet, String beginWord) {
        HashMap<String, List<String>> neighborMap = new HashMap<>();

        //neighborMap.put(beginWord, getNexts(beginWord, wordSet));
        for (String word : wordSet) {
            neighborMap.put(word, getNexts(word, wordSet));
        }

        return neighborMap;
    }

    public static List<String> getNexts(String word, HashSet<String> wordSet) {
        List<String> nexts = new ArrayList<String>();
        char[] w = word.toCharArray();

        for (int i = 0; i < w.length; i++) {
            for (char cha = 'a'; cha <= 'z'; cha++) {
                if (cha != w[i]) {
                    char temp = w[i];
                    w[i] = cha;

                    if (wordSet.contains(String.valueOf(w))) {
                        nexts.add(String.valueOf(w));
                    }

                    w[i] = temp;
                }

            }
        }

        return nexts;
    }

    public static HashMap<String, Integer> getDistanceMap(String beginWord, HashMap<String, List<String>> neighborMap) {
        Queue<String> queue = new LinkedList<>();
        queue.add(beginWord);

        HashMap<String, Integer> distanceMap = new HashMap<>();
        distanceMap.put(beginWord, 0);

        HashSet<String> set = new HashSet<>();
        set.add(beginWord);
        while (!queue.isEmpty()) {
            String temp = queue.poll();
            //if (neighborMap.containsKey(temp)) {
            for (String next : neighborMap.get(temp)) {
                if (!set.contains(next)) {
                    distanceMap.put(next, distanceMap.get(temp) + 1);
                    queue.add(next);
                    set.add(next);
                }

            }
            //}

        }

        return distanceMap;
    }


    public static void getShortestPath(String nowWord, String endWord, HashMap<String, List<String>> neighborMap, HashMap<String, Integer> distanceMap, LinkedList<String> path, List<List<String>> ans, int nowDistance, HashMap<String, Integer> distanceMap2) {
        // if (nowWord == null) {
        //     return;
        // }

        path.add(nowWord);

        if (nowWord.equals(endWord)) {
            // 不能直接将path加入，要单独创建一个地址空间加入到ans中，因为path的地址在所有的递归层中是共用的，在后面的递归中可能会改变path中的值，如果直接将path加入到ans中，就会导致ans中的内容会随着递归操作一起变化
            ans.add(new LinkedList<String>(path));
        } else {
            for (String next : neighborMap.get(nowWord)) {
                if (distanceMap.get(next) == distanceMap.get(nowWord) + 1 &&
                        distanceMap2.get(next) == distanceMap2.get(nowWord) - 1) {
                    getShortestPath(next, endWord, neighborMap, distanceMap, path, ans, nowDistance + 1, distanceMap2);
                }
            }
        }


        path.pollLast();
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1},{3,2},{5,3},{4,1},{2,3},{1,4}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {1,2,3,6};
        int n = 3;

//        Arrays.sort(nums);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        //System.out.println(nums);

//        "ADOBECODEBANC"
//        "ABC"

        String str1 = "hit";
        String str2 = "cog";
        String[] strs = {"hot","dot","dog","lot","log"};
        List<String> strList = new ArrayList<String>();
        for (String s : strs) {
            strList.add(s);
        }

        System.out.println(findLadders(str1, str2, strList));

//        System.out.println("hesitxyplovdqfkz");
//        System.out.println("hesitxyplovdqfkz".equals(removeDuplicateLetters(str1)));

//        int[] ans = maxSumOfThreeSubarrays(nums, n);
//        for (int a : ans) {
//            System.out.print(a + " ");
//        }
    }
}