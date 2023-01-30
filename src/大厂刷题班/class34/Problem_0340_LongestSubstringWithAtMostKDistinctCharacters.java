package 大厂刷题班.class34;
// 滑动窗口
// 存在单调性，可以用窗口，窗口的范围和所含字母的种类数之间是存在单调性的
// https://leetcode.cn/problems/longest-substring-with-at-most-k-distinct-characters/
public class Problem_0340_LongestSubstringWithAtMostKDistinctCharacters {
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        // 过滤无效参数
        if (s == null || s.length() == 0 || k < 1) {
            return 0;
        }
        // 转换为字符数组
        char[] str = s.toCharArray();
        int N = str.length;
        // 用来统计当前窗口内每一种字符的个数
        int[] count = new int[256];
        // 记录当前窗口内字符的种数
        int diff = 0;
        // R 窗口的右边界
        int R = 0;
        int ans = 0;
        // 尝试收集以i开头的答案，遍历所有的位置作为i，i就相当于窗口的左边界
        for (int i = 0; i < N; i++) {
            // 如果窗口右边界没有越界，并且此时窗口内的字符种数没有超过k，就将窗口继续右扩
            while (R < N && (diff < k || (diff == k && count[str[R]] > 0))) {
                // 修改窗口内的字符种数
                diff += count[str[R]] == 0 ? 1 : 0;
                // 修改count数组中的窗口内每种字符的个数
                count[str[R++]]++;
            }
            // R 来到违规的第一个位置
            // 更新当前找到的最大长度
            ans = Math.max(ans, R - i);
            // 缩小窗口，减少diff和count[str[i]]
            diff -= count[str[i]] == 1 ? 1 : 0;
            count[str[i]]--;
        }
        // 返回答案
        return ans;
    }
}
