package 大厂刷题班.class24;

// 去除重复字母
// 贪心  双指针
// 本题测试链接 : https://leetcode.cn/problems/remove-duplicate-letters/
public class Code06_RemoveDuplicateLettersLessLexi {
    // 我自己写的代码，迭代版本
    public static String removeDuplicateLetters(String str) {
        // 过滤无效参数
        if (str == null || str.length() == 0) {
            return "";
        }
        // 特殊情况
        if (str.length() == 1) {
            return str;
        }

        if (str.length() == 2) {
            return str.charAt(0) == str.charAt(1) ? str.substring(0, 1) : str;
        }

        // 字符串转字符数组
        char[] s = str.toCharArray();
        // 词频表，一共只有26个小写字母
        int[] map = new int[26];
        // 初始化词频表
        // 小写字母ascii码值范围[97~122]，所以用长度为26的数组做次数统计
        // 如果map[i] > -1，则代表ascii码值为i的字符的出现次数
        // 如果map[i] == -1，则代表ascii码值为i的字符不再考虑
        for (int i = 0; i < s.length; i++) {
            map[s[i] - 'a']++;
        }

        // 双指针
        int l = 0;
        int r = 0;
        // 记录要保留的字符
        StringBuilder sb = new StringBuilder();
        // r开始向右滑动，每滑倒一个字符，就将这个字符在词频表中减1，如果第一次出现一个字符在次品表中为空了，说明这个双指针之外的区域已经没有这个字符了
        // 必须要在l~r之间选一个字符保留了，不然后面就会遗漏字符
        while (r < s.length) {
            // map次品表中为-1，说明这个字符已经被保留了，以后不用考虑他的。
            // 将r划过的字符词频减1：--map[s[r] - 'a']
            // 如果当前字符是不再考虑的，直接跳过
            // 如果当前字符的出现次数减1之后，后面还能出现，那就继续r右移，如果不能出现了，就需要进入下一个分支开始选择要保留的字符
            if ( (map[s[r] - 'a'] == -1 || --map[s[r] - 'a'] > 0)) {
                // 如果还没有碰到词频为0的，就继续r右移
                r++;
                // 当前字符需要考虑并且之后不会再出现了
            } else {
                // l~r范围上字典序最小的下标位置
                int minCharacterIndex = -1;
                // 查找l~r范围字典序最小的下标位置，如果有多个最小的字典序字符，那么我们就选择第一个出现的
                for (int i = l; i <= r; i++) {
                    // 只查找此时还没有保留过的字符
                    if (map[s[i] - 'a'] != -1 && (minCharacterIndex == -1 || s[minCharacterIndex] > s[i])) {
                        minCharacterIndex = i;
                    }

                }

                // 将字典序最小的加入到答案中
                sb.append(s[minCharacterIndex]);
                // 至此minCharacterIndex下标左边的所有字符就都可以丢弃了，继续看它右边的字符，有相同的流程找保留哪个字符

                // 在上面的for循环中，s[l..r]范围上每种字符的出现次数都减少了
                // 需要把s[minCharacterIndex + 1..r]上每种字符的出现次数加回来，因为后面的操作还会继续减1，如果不给他们恢复现场，就会重复减掉1
                for (int i = minCharacterIndex + 1; i <= r; i++) {
                    // 将还没有保留的字符都加1
                    if (map[s[i] - 'a'] != -1) {
                        map[s[i] - 'a']++;
                    }
                }

                // 将保留的字符词频表设置为-1，标记上，以后不再考虑
                map[s[minCharacterIndex] - 'a'] = -1;
                // 更新双指针，l指向minCharacterIndex + 1，r指向l
                l = minCharacterIndex + 1;
                r = l;
            }
        }

        return sb.toString();
    }

    // 左神写的代码，递归版本
    // 在str中，每种字符都要保留一个，让最后的结果，字典序最小 ，并返回
    public static String removeDuplicateLetters1(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        int[] map = new int[256];
        for (int i = 0; i < str.length(); i++) {
            map[str.charAt(i)]++;
        }
        int minACSIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
            if (--map[str.charAt(i)] == 0) {
                break;
            }
        }
        // 0...break(之前) minACSIndex
        // str[minACSIndex] 剩下的字符串str[minACSIndex+1...] -> 去掉str[minACSIndex]字符 -> s'
        // s'...
        return String.valueOf(str.charAt(minACSIndex)) + removeDuplicateLetters1(
                str.substring(minACSIndex + 1).replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
    }

    // 左神写的代码，迭代版本
    public static String removeDuplicateLetters2(String s) {
        char[] str = s.toCharArray();
        // 小写字母ascii码值范围[97~122]，所以用长度为26的数组做次数统计
        // 如果map[i] > -1，则代表ascii码值为i的字符的出现次数
        // 如果map[i] == -1，则代表ascii码值为i的字符不再考虑
        int[] map = new int[26];
        for (int i = 0; i < str.length; i++) {
            map[str[i] - 'a']++;
        }
        char[] res = new char[26];
        int index = 0;
        int L = 0;
        int R = 0;
        while (R != str.length) {
            // 如果当前字符是不再考虑的，直接跳过
            // 如果当前字符的出现次数减1之后，后面还能出现，直接跳过
            if (map[str[R] - 'a'] == -1 || --map[str[R] - 'a'] > 0) {
                R++;
            } else { // 当前字符需要考虑并且之后不会再出现了
                // 在str[L..R]上所有需要考虑的字符中，找到ascii码最小字符的位置
                int pick = -1;
                for (int i = L; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1 && (pick == -1 || str[i] < str[pick])) {
                        pick = i;
                    }
                }
                // 把ascii码最小的字符放到挑选结果中
                res[index++] = str[pick];
                // 在上一个的for循环中，str[L..R]范围上每种字符的出现次数都减少了
                // 需要把str[pick + 1..R]上每种字符的出现次数加回来
                for (int i = pick + 1; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1) { // 只增加以后需要考虑字符的次数
                        map[str[i] - 'a']++;
                    }
                }
                // 选出的ascii码最小的字符，以后不再考虑了
                map[str[pick] - 'a'] = -1;
                // 继续在str[pick + 1......]上重复这个过程
                L = pick + 1;
                R = L;
            }
        }
        return String.valueOf(res, 0, index);
    }
}
