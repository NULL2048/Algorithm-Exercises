package 大厂刷题班.class28;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// 把两个字符串按照字典序排个序，如果排完序后两个字符串是一样的，就说明原来的两个字符串是同一个类型
// 数组   字符串   排序   哈希表
// https://leetcode.cn/problems/group-anagrams/
public class Problem_0049_GroupAnagrams {
    // 解法一：哈希表
    public List<List<String>> groupAnagrams(String[] strs) {
        // key：字符串按字典序排序后的结果
        // value：所有排序后都为key的字符串几何
        HashMap<String, List<String>> map = new HashMap<>();

        // 遍历所有的字符串
        for (int i = 0; i < strs.length; i++) {
            char[] s = strs[i].toCharArray();
            // 将字符串按照字典序排序
            Arrays.sort(s);

            // 根据排序后的字符串是什么，来将原始字符串放入对应的键值对中
            String str = String.valueOf(s);
            if (map.containsKey(str)) {
                map.get(str).add(strs[i]);
            } else {
                List<String> values = new ArrayList<>();
                values.add(strs[i]);
                map.put(str, values);
            }
        }

        // 存放要返回的答案，相同类型的字符串放到同一个List中
        List<List<String>> ans = new ArrayList<>();
        // 相同的key，就是同一个类型的字符串，将这些同一个类型的字符串放到同一个List中
        for (List<String> values : map.values()) {
            ans.add(values);
        }
        return ans;
    }

    // 解法二
    public static List<List<String>> groupAnagrams1(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            int[] record = new int[26];
            for (char cha : str.toCharArray()) {
                record[cha - 'a']++;
            }
            StringBuilder builder = new StringBuilder();
            for (int value : record) {
                builder.append(String.valueOf(value)).append("_");
            }
            String key = builder.toString();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }
            map.get(key).add(str);
        }
        List<List<String>> res = new ArrayList<List<String>>();
        for (List<String> list : map.values()) {
            res.add(list);
        }
        return res;
    }
}
