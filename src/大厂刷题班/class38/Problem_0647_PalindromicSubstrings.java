package 大厂刷题班.class38;
// Manacher算法
// https://leetcode.cn/problems/palindromic-substrings/
public class Problem_0647_PalindromicSubstrings {
    public int countSubstrings(String s) {
        // 空字符串直接返回0
        if (s == null || s.length() == 0) {
            return 0;
        }

        // 统计回文子串数量
        int ans = 0;
        // 记录以每个位置为中心的回文串半径是多少
        int[] pArr = manacher(s);
        for (int i = 0; i < pArr.length; i++) {
            // 每一个位置的半径除以2，就是以这个位置为中心的回文子串数量，之所以要除以2，是因为之前我们将原字符串中插入了特殊字符，所以这个里面的结果都要除以2才行
            // 特殊字符位置的数据也要加上，因为特殊位置的数据可能是偶数个数的回文子串的数据
            ans += (pArr[i] >> 1);
        }

        return ans;
    }

    public int[] manacher(String str) {
        // 生成处理后的字符串
        char[] s = getManacherString(str);
        int n = s.length;
        // 最右回文边界的中心位置，和R变量绑定在一起的
        int c = -1;
        // 最右的扩成功位置的再下一个位置
        int r = -1;
        // 回文半径的大小
        int[] pArr = new int[n];

        // 遍历处理后的字符串，开始算法流程
        // 尝试以每一个位置作为回文中心点去向外扩
        for (int i = 0; i < n; i++) {
            // R第一个违规的位置，i >= R
            // R > i表示i在R内，这种情况是可以存在优化的，就是分了我们之前讲过的三种情况，其中①和②两种情况时答案分别为pArr[2 * C - i]和R - i，比如如果pArr[2 * C - i]比R - i小，那么说明此时就是①情况，反之就是情况②。
            // R <= i表示i在R外，这种是没法优化的，这种情况就直接将回文半径先设置为1，后面再去外扩尝试。因为每一个位置的回文串最少也有1个，也就是它本身
            pArr[i] = i < r ? Math.min(pArr[2 * c - i], r - i) : 1;
            // 此时pArr[i]表示i位置外扩的区域至少是多大。
            // 该循环就是尝试外扩
            while (i + pArr[i] < n && i - pArr[i] >= 0) {
                // i + pArr[i]是此时回文范围右边界的后面一个字符位置，i - pArr[i]是此时回文范围左边界的前面一个字符位置
                // 如果这两个位置相等，说明此时要么是情况一，要么是情况二的③，这种就去外扩尝试即可，两个位置的字符串相等说明还可以继续外扩
                if (s[i + pArr[i]] == s[i - pArr[i]]) {
                    // 将当前位置的回文半径自增
                    pArr[i]++;
                    // 如果分支不成立，那么后续也肯定不可能能外扩了，直接跳出循环
                    // 其中如果是情况二的①和②进入到该while循环，那么一定直接就会进入到这个else分支跳出循环
                } else {
                    break;
                }
            }
            // 如果这个位置的回文区域的右边界比R更靠右，那么就继续更新R和C，作为新的最右回文边界
            if (i + pArr[i] > r) {
                c = i;
                r = i + pArr[i];
            }
        }
        // 返回每一个位置的回文半径的大小
        return pArr;
    }

    // 将原字符串进行处理，两两中间插入一个特殊字符，用来在manacher算法处理过程中减少情况的判断
    // #a#b#c#d#e#
    public char[] getManacherString(String str) {
        char[] s1 = str.toCharArray();
        char[] s2 = new char[2 * s1.length + 1];
        int index = 0;
        s2[index++] = '#';
        for (int i = 0; i < s1.length; i++) {
            s2[index++] = s1[i];
            s2[index++] = '#';
        }

        return s2;
    }
}
