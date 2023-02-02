package 大厂刷题班.class35;
// 滑动窗口   数组   计数
// https://leetcode.cn/problems/longest-substring-with-at-least-k-repeating-characters/
public class Problem_0395_LongestSubstringWithAtLeastKRepeatingCharacters {
    public int longestSubstring(String str, int k) {
        // 转为字符数组
        char[] s = str.toCharArray();
        // 记录符合题目要求的最长子串长度
        int max = 0;
        // 枚举窗口内所有的字符种类数
        for (int kinds = 1; kinds <= 26; kinds++) {
            // 当前这里一轮窗口内a~z出现的次数
            int[] count = new int[26];
            // 窗口右边界，每一轮都从头开始
            int r = -1;
            // 目前窗口内收集了几种字符了
            int nowKinds = 0;
            // 目前窗口内出现次数>=k次的字符，满足了几种
            int satisfyKinds = 0;
            // l要尝试每一个位置作为窗口的最左边界
            for (int l = 0; l < s.length; l++) { // 右边界不回退
                // 窗口右扩的条件：
                //  1) 窗口内字符种数不足
                //  2）某个字符出现不足K次
                // 循环执行条件：r右移一位不能导致越界  并且如果右移一位新加入窗口一个全新的字符，超过了我们要求的kinds个种数了，那也不可以右扩
                while (r < s.length - 1 && !(nowKinds == kinds && count[s[r + 1] - 'a'] == 0)) { // 下一个词频为0，就是新出现的，算进来就超了
                    // 窗口右扩
                    r++;
                    // 如果新加入窗口的字符是第一次进入窗口，则将窗口内的字符种数++
                    if (count[s[r] - 'a'] == 0) {
                        nowKinds++;
                    }
                    // 如果新加入窗口的字符已经在窗口内出现了k-1次了，那么又加入一个就变成了出现k次。则将满足窗口内出现次数>=k次的字符种数++
                    if (count[s[r] - 'a'] == k - 1) {
                        satisfyKinds++;
                    }
                    // 将新加入的字符出现次数在当前窗口的词频表中++
                    count[s[r] - 'a']++;
                }
                // 如果当前满足窗口内出现次数>=k次的字符种数 等于 本轮枚举要求的窗口内字符种数
                if (satisfyKinds == kinds) {
                    // 尝试更新符合题目要求的最长子串长度
                    max = Math.max(max, r - l + 1);
                }
                // 窗口左边界右移，弹出一个字符
                count[s[l] - 'a']--;
                // 如果弹出字符后，这个字符在窗口内就没有了，就将窗口内的字符种数--
                if (count[s[l] - 'a'] == 0) {
                    nowKinds--;
                }
                // 如果弹出字符后，这个字符在窗口内出现次数不是k次了，变成了k-1次，就将窗口内满足窗口内出现次数>=k次的字符种数--
                if (count[s[l] - 'a'] == k - 1) {
                    satisfyKinds--;
                }
            }
        }
        // 返回答案
        return max;
    }
}
