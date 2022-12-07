package 大厂刷题班.class24;
// 最短包含子串
// 滑动窗口(存在某种单调性，才可以使用滑动窗口)   欠账表模型
public class Code05_MinWindowLength {
    // 测试链接：https://leetcode.cn/problems/minimum-window-substring/
    // 这个是返回符合条件的最短子串，下面的代码是我写的版本
    public static String minWindow(String sStr, String tStr) {
        // 过滤无效参数
        if (sStr == null || sStr.length() == 0 || tStr == null || tStr.length() == 0) {
            return "";
        }
        // 转化为字符串数组
        char[] s = sStr.toCharArray();
        char[] t = tStr.toCharArray();
        // 创建欠帐表，记录每一个字符的欠账。这道题是允许子串有额外字符的，所以欠帐表是可以有负数的
        int[] map = new int['z' + 1];
        // 目前子串总的欠帐数
        int all = 0;
        // 先根据t初始化欠帐表map和all
        for (int i = 0; i < t.length; i++) {
            map[t[i]]++;
            all++;
        }
        // 窗口右边界
        int r = 0;
        // 最后答案的子串左右边界
        int ansL = -1;
        int ansR = -1;
        // 记录能够把t全部字符涵盖的最短子串长度
        int minLen = Integer.MAX_VALUE;
        // 一开始先将0~0的窗口还账
        map[s[0]]--;
        // 如果此时map[s[0]]，说明是一次有效还款，all--
        if (map[s[0]] >= 0) {
            all--;
        }
        // 尝试以所有位置为开始的子串，能将t的字符全部囊括的最短子串
        for (int l = 0; l < s.length; l++) {
            // 右扩窗口右边界
            while (r < s.length) {
                // 先判断all是否为0，如果此时all为0，说明所有的欠帐都不上了，一定都涵盖了t的全部字符了
                if (all == 0) {
                    // 判断当前字串是否比minLen更短，如果更短，就更新符合要求的最短子串
                    if (minLen > r - l + 1) {
                        // 更新字串范围和长度
                        ansL = l;
                        ansR = r;
                        minLen = ansR - ansL + 1;
                    }
                    // 如果以l为起始第一次找到了能涵盖t全部字符的子串，就比有必要再让r右扩了，因为这个就是以此时l为起始点的最短子串了，直接跳出循环，l++找以下一个位置为起始点的答案
                    break;
                }
                // 如果还没有完全涵盖t，那么继续让窗口r边界右扩
                r++;
                // 判断r不能越界
                if (r < s.length) {
                    // 将新加入的字符在欠帐表中减1
                    map[s[r]]--;
                    // 如果是有效还款，那么all--
                    if (map[s[r]] >= 0) {
                        all--;
                    }
                }

            }

            // 将l右移，开始尝试新的子串起始点
            // 首先需要将l位置的字符移出窗口，所以要在欠帐表中加1
            map[s[l]]++;
            // 如果是有效欠帐，就将all++    因为如果map[s[l]]还是负数，说明此时窗口中的s[l]比较多，即使少了一个也能把t中的s[l]字符全部涵盖
            if (map[s[l]] > 0) {
                all++;
            }
        }

        // 返回最短子串
        return minLen == Integer.MAX_VALUE ? "" : sStr.substring(ansL, ansR + 1);
    }


    // 这个不是力扣的题，但是其实题目完全一样，只不过这个是返回的最短子串长度
    public static int minLength(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return Integer.MAX_VALUE;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] map = new int[256]; // map[37] = 4 37 4次
        for (int i = 0; i != str2.length; i++) {
            map[str2[i]]++;
        }
        int all = str2.length;

        // [L,R-1] R
        // [L,R) -> [0,0)
        int L = 0;
        int R = 0;
        int minLen = Integer.MAX_VALUE;
        while (R != str1.length) {
            map[str1[R]]--;
            if (map[str1[R]] >= 0) {
                all--;
            }
            if (all == 0) { // 还完了
                while (map[str1[L]] < 0) {
                    map[str1[L++]]++;
                }
                // [L..R]
                minLen = Math.min(minLen, R - L + 1);
                all++;
                map[str1[L++]]++;
            }
            R++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }
}
