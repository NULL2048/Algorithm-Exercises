package 大厂刷题班.class17;

import java.util.HashMap;

// 从左往右的尝试模型
// 本题测试链接 : https://leetcode.cn/problems/distinct-subsequences-ii/
public class Code04_DistinctSubseqValue {
    public static int distinctSubseqII(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        // 求余数
        int mod = 1000000007;
        char[] s = str.toCharArray();
        // all是当前所有的子集数量（去重后的数量）
        // 需要将all初始化为1，否则整个流程无法推进下去
        int all = 1;
        // 记录当前以index+'a'字符结尾的子序列有多少个
        int[] count = new int[26];
        // 从左向右
        for (int i = 0; i < s.length; i++) {
            // 当前遍历到的字符
            int index = s[i] - 'a';
            // 加上这个字符一共会增加的子集数量，剔除掉之前已经重复出现的子集
            // 如果不算重复的话，本轮一共新增的子集数量就是all个，因为是在之前all个子集的基础上，在他们的后面追加一个s[i]，自然新增加的子集数量就是all个
            // 但是有可能新增的在之前已经存在了，这就出现重复了，需要去重
            // 这一轮重复出现的子集，在之前中为什么会出现呢，一定是因为之前遍历到的字符追加到之前的子集后面来构造出来的，也就一共构造出来count[index]个，而这一轮又遍历到了s[i]，因为之前构造出来的所有子集都会保留，所以这一轮一定也会再次重复构造出来count[index]个相同的以s[i]结尾的子集，所以这里我们就减掉count[index]就可以了
            // 这里注意要加m，然后再%m。因为curAll和map.get(x)都是被模处理过的，他们两个相减有可能是负数，所以要先加一个m保证不是负数，然后再模m，并不影响正确结果
            int add =  (all - count[index] + mod) % mod;
            // 计算本轮新的all，以前的all加上去重后的新增数量
            all = (all + add) % mod;
            // 以s[i]结尾的子集增加了all（已去重），累加到count数组中
            count[index] = (count[index] + add) % mod;
        }
        // 不算空集，要减去1
        // 上述的所有过程中都要跟着取模操作，这是题目要求，为了避免数据溢出
        return (all - 1 + mod) % mod;
    }

    // 使用Map的写法
    public static int zuo(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int m = 1000000007;
        char[] str = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        int all = 1; // 一个字符也没遍历的时候，有空集
        for (char x : str) {
            int newAdd = all;
//			int curAll = all + newAdd - (map.containsKey(x) ? map.get(x) : 0);
            int curAll = all;
            curAll = (curAll + newAdd) % m;
            // 这里注意要加m，然后再%m。因为curAll和map.get(x)都是被模处理过的，他们两个相减有可能是负数，所以要先加一个m保证不是负数，然后再模m，并不影响正确结果
            curAll = (curAll - (map.containsKey(x) ? map.get(x) : 0) + m) % m;
            all = curAll;
            map.put(x, newAdd);
        }
        // 力扣不算空集，但是这里我们算空集了，所以提交的时候这里要改成all-1，还要记得取模
        return all;
    }

    public static void main(String[] args) {
        String s = "bccaccbaabbc";
        System.out.println(distinctSubseqII(s) + 1);
        System.out.println(zuo(s));
    }
}
