package 大厂刷题班.class03;

// 动态规划 空间压缩
// 本题测试链接 : https://leetcode.cn/problems/longest-substring-without-repeating-characters/
public class Code03_LongestSubstringWithoutRepeatingCharacters {
    // 1、暴力递归
    public int lengthOfLongestSubstring1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int[] preMap = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            preMap[i] = -1;
        }

        int[] temp = new int[256];
        for (int i = 0; i < 256; i++) {
            temp[i] = -1;
        }
        // 构造每一个位置的前面出现的位置表，预处理结构
        for (int i = 0; i < str.length; i++) {
            preMap[i] = temp[str[i]];
            temp[str[i]] = i;
        }

        int maxLen = Integer.MIN_VALUE;
        // 尝试以每一个位置为右边界起点，来尝试找到最长的无重复字符串
        for (int i = 0; i < str.length; i++) {
            maxLen = Math.max(maxLen, process1(i, str, preMap));
        }
        return maxLen;
    }

    // 从index向左开始，最长的没有重复字符的字符串长度    从右向左尝试
    public int process1(int index, char[] str, int[] preMap) {
        // basecase  当index==0时，长度肯定是1
        if (index == 0) {
            return 1;
        }

        // 因素一：当前字符上次出现的位置
        int p1 = preMap[index] + 1;
        // 因素二：当前index位置，前一个index-1位置往左推的距离
        int p2 = index - 1 - process1(index - 1, str, preMap) + 1;

        // 找到这两个因素中与index形成的长度最短的，并且在后面加1（加上index），作为最长的没有重复的字符串长度返回
        return index - Math.max(p1, p2) + 1;

    }


    // 2、记忆化搜索
    public int lengthOfLongestSubstring2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int[] preMap = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            preMap[i] = -1;
        }

        int[] temp = new int[256];
        for (int i = 0; i < 256; i++) {
            temp[i] = -1;
        }
        for (int i = 0; i < str.length; i++) {
            preMap[i] = temp[str[i]];
            temp[str[i]] = i;
        }

        // 不用给dp先初始化为-1，直接用0来表示还没有计算过即可，因为每一个位置的答案如果计算过的话最少也是长度1，不可能出现长度为0的情况，所以这里就默认用0表示还没有计算过即可。
        int[] dp = new int[str.length];
        int maxLen = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) {
            maxLen = Math.max(maxLen, process2(i, str, preMap, dp));
        }
        return maxLen;
    }

    public int process2(int index, char[] str, int[] preMap, int[] dp) {
        if (dp[index] != 0) {
            return dp[index];
        }

        if (index == 0) {
            dp[0] = 1;
            return 1;
        }

        int p1 = preMap[index] + 1;
        int p2 = index - 1 - process2(index - 1, str, preMap, dp) + 1;

        dp[index] = index - Math.max(p1, p2) + 1;
        return dp[index];

    }

    // 3、动态规划
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int[] preMap = new int[256];
        // 用来记录从左向右遍历，每一个字符的最近出现的位置
        for (int i = 0; i < 256; i++) {
            preMap[i] = -1;
        }

        // 不用给dp先初始化为-1，直接用0来表示还没有计算过即可，因为每一个位置的答案如果计算过的话最少也是长度1，不可能出现长度为0的情况，所以这里就默认用0表示还没有计算过即可。
        //int[] dp = new int[str.length];
        int maxLen = 1;
        // 空间压缩，记录i-1位置能去到的最长无重复字符串长度
        int preLen = 1;
        // 将0下标的字符提前写入，最近一次出现位置为0
        preMap[str[0]] = 0;

        // 从下表1开始向右遍历
        for (int i = 1; i < str.length; i++) {
            // 因素一
            int p1 = preMap[str[i]] + 1;
            // 因素二
            int p2 = i - preLen;
            // 将当前i的最长长度写入到preLen，用于下一轮循环
            preLen = i - Math.max(p1, p2) + 1;
            // 找到最大长度
            maxLen = Math.max(maxLen, preLen);
            // 更新i位置字符的最新出现的位置
            preMap[str[i]] = i;
        }
        return maxLen;
    }


    // 4、滑动窗口解法
    public int lengthOfLongestSubstring4(String str) {
        // 过滤特殊情况
        if (str.length() <= 1) {
            return str.length();
        }
        char[] s = str.toCharArray();
        // 窗口左右边界
        int l = 0;
        int r = 0;
        // 统计窗口内每一个字符的出现次数
        int[] count = new int[256];
        // 此时找到符合条件的最长子数组长度
        int max = 0;
        while (r < s.length) {
            // 要保证窗口内的每一个字符出现的次数不能超过1
            // 如果窗口内没有s[r]这个字符，就将其加入到窗口
            if (count[s[r]] == 0) {
                count[s[r++]]++;
                // 尝试用此时窗口的长度来更新max
                max = Math.max(max, r - l);
                // 如果此时窗口内已经有s[r]这个字符了，那么就不能再加入一个同样的字符到窗口内了
                // 此时我们需要右移左边界，将窗口内的字符弹出一个，看看弹出一个后能不能使右边界继续右括
            } else {
                count[s[l++]]--;
            }
        }
        return max;
    }

}
