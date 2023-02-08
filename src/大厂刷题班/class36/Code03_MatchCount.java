package 大厂刷题班.class36;

// KMP

// 来自美团   美团这道笔试题当时提交暴力都可以过
// 给定两个字符串s1和s2
// 返回在s1中有多少个子串等于s2
public class Code03_MatchCount {

    public static int sa(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return count(str1, str2);
    }

    // 改写kmp为这道题需要的功能
    public static int count(char[] str1, char[] str2) {
        // x表示此时s1已经遍历比较到哪个位置了
        int x = 0;
        // y表示此时s2已经遍历比较到哪个位置了
        int y = 0;
        // 统计s1中所有与s2相等的子串数量
        int count = 0;
        // 求next数组，多求一格位置
        int[] next = getNextArray(str2);
        // 利用next数组，求出来s1中所有与s2相等的子串数量
        while (x < str1.length) {
            // 如果两个位置相等，则两个字符串都向后移动一格位置，去比较下一个位置的字符相不相等。
            if (str1[x] == str2[y]) {
                x++;
                y++;
                // 如果发现str2已经遍历比较完了，说明此时就找到了一种str1子串和str2相等
                if (y == str2.length) {
                    // 将count加1
                    count++;
                    // 因为str2的next数组多求了一位，将y回到str2字符串最大匹配的前后缀的前缀部分的最后一个位置的下一个下标位置
                    // 这样就可以继续找新的能和str2匹配上的子串
                    y = next[y];
                }
                // 如果此时s2已经右推到最开头了，无法再右推了，但是此时还是没找到能够匹配上的子串，就说明此时以s1的x位置的字符不可能匹配出s2子串了，所以直接将s1向后右移一个位置，
                // 去检查以下一个位置为开头能不能匹配出s2。此时y也正好回到了下标0位置，也就是s2也从头开始匹配了。
            } else if (next[y] == -1) {
                x++;
                // 发现x和y位置的字符不相等，并且s2还可以右推，通过将y = next[y]与s1的x对齐来实现s2的右推
            } else {
                y = next[y];
            }
        }
        // 返回总个数
        return count;
    }

    // next数组多求一位
    // 比如：str2 = aaaa
    // 那么，next = -1,0,1,2,3
    // 最后一个3表示，终止位置之前的字符串最长前缀和最长后缀的匹配长度
    // 也就是next数组补一位
    public static int[] getNextArray(char[] str2) {
        // // s2长度为1，直接返回-1,0   多出来的0是因为要多求一位
        if (str2.length == 1) {
            return new int[] { -1, 0 };
        }
        // 创建next数组   多求一位，所以要加1
        int[] next = new int[str2.length + 1];
        // next数组的第一个和第二个位置的值都是固定的。
        next[0] = -1;
        next[1] = 0;
        // 目前在哪个位置上求next数组的值
        int i = 2;
        // 当前是哪个位置的值在和i-1位置的字符比较
        int cn = 0;
        // 开始求next数组信息
        while (i < next.length) {
            // 配成功的时候，此时cn的值也是i - 1的最长相等前缀的长度，也就是next[i - 1] = cn
            // 匹配成功之后next[i] = next[i - 1] + 1
            if (str2[i - 1] == str2[cn]) {
                next[i++] = ++cn;
                // 如果没有匹配上，就将cn继续前跳，跳到以前cn前面最长相等前缀的后面一个位置
            } else if (cn > 0) {
                cn = next[cn];
                // 如果cn已经等与-1了，说明此时cn已经跳到s2的第一个位置了，并且此时str2[i - 1]和str2[cn])还是没有匹配上字符
                // 说明此时i位置前面就没有相等的前缀和后缀了，直接将该位置的next信息设置为0
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

}
