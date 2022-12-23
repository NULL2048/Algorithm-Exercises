package 大厂刷题班.class28;
// KMP算法
// https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/
public class Problem_0028_KMP {
    public int strStr(String haystack, String needle) {
        // 过滤无效参数
        if (haystack == null || haystack.length() == 0 || needle == null || needle.length() == 0) {
            return -1;
        }
        // 将字符串转换为字符数组
        char[] s1 = haystack.toCharArray();
        char[] s2 = needle.toCharArray();
        // x表示此时s1已经遍历比较到哪个位置了
        int x = 0;
        // y表示此时s2已经遍历比较到哪个位置了
        int y = 0;
        // 快速计算s2的next[]数组
        // O(M) m <= n
        int[] next = getNextArray(s2);

        /**
         * 开始比较匹配s1和s2，循环结束条件就是只要有一个字符串遍历完了就结束
         * y越界：s1的某一个开头撸过了所有s2，从x出发往左推y的长度。这就此时在s1中找到了一个和s2相等的子串。
         * x越界：说明尝试所有s1的开头，都没有搞出s2，说明s1中就不存在和s2相等的子串。
         */
        // O(N)
        while (x < s1.length && y < s2.length) {
            // 如果两个位置相等，则两个字符串都向后移动一格位置，去比较下一个位置的字符相不相等。
            if (s1[x] == s2[y]) {
                x++;
                y++;
                // 如果此时s2已经右推到最开头了，无法再右推了，但是此时还是没找到能够匹配上的子串，就说明此时以s1的x位置的字符不可能匹配出s2子串了，所以直接将s1向后右移一个位置，
                // 去检查以下一个位置为开头能不能匹配出s2。此时y也正好回到了下标0位置，也就是s2也从头开始匹配了。
            } else if (next[y] == -1){
                x++;
                // 发现x和y位置的字符不相等，并且s2还可以右推，通过将y = next[y]与s1的x对齐来实现s2的右推
            } else {
                y = next[y];
            }
        }

        // 如果是y越界，说明在s1中找到了一个和s2相等的子串。字串的第一个位置的下标就是x - y。x此时的含义是s1中能匹配到的s2子串的最后一个字符的下标，y表示s2子串的长度，所以x - y得到的就是s1中能匹配到的s2子串的第一个位置下标。
        // 如果是x越界，说明没有找到相等子串，直接返回-1
        return y == s2.length ? x - y : -1;
    }

    // 快速计算s2的next[]数组
    // 这个是利用前面位置已经求得的next信息来加速求得后面位置的next信息
    public int[] getNextArray(char[] s2) {
        // s2长度为1，直接返回-1
        if (s2.length == 1) {
            return new int[] {-1};
        }
        // 创建next数组
        int[] next = new int[s2.length];
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
            if (s2[i - 1] == s2[cn]) {
                next[i++] = ++cn;
                // 如果没有匹配上，就将cn继续前跳，跳到以前cn前面最长相等前缀的后面一个位置
            } else if (cn > 0) {
                cn = next[cn];
                // 如果cn已经等于-1了，说明此时cn已经跳到s2的第一个位置了，并且此时str2[i - 1]和str2[cn]还是没有匹配上字符
                // 说明此时i位置前面就没有相等的前缀和后缀了，直接将该位置的next信息设置为0
            } else {
                next[i++] = 0;
            }
        }

        return next;
    }
}
