package 大厂刷题班.class27;
// Manacher算法
// https://leetcode.cn/problems/longest-palindromic-substring/
public class Problem_0005_LongestPalindromicSubstring {
    public static String longestPalindrome(String s) {
        // 过滤无效参数
        if (s == null || s.length() == 0) {
            return null;
        }
        // 对字符串进行处理
        // "12132" -> "#1#2#1#3#2#"
        char[] str = manacherString(s);
        // 回文半径的大小
        int[] pArr = new int[str.length];
        // 最右回文边界的中心位置，和R变量绑定在一起的
        int C = -1;
        // 在讲解流程中：R代表最右的扩成功的位置
        // 在具体的代码实现中：最右的扩成功位置的再下一个位置，这样写是为了方便coding，能少些一些判断条件
        int R = -1;
        // 最大回文长度
        int max = Integer.MIN_VALUE;
        // 最大回文串的最后一个字符的下标位置(在原始数组中的下标)，它和max一起维护更新
        int end = -1;
        // 遍历处理后的字符串，开始算法流程
        // 尝试以每一个位置作为回文中心点去向外扩
        for (int i = 0; i < str.length; i++) {
            // R第一个违规的位置，i>= R
            // R > i表示i在R内，这种情况是可以存在优化的，就是分了我们之前讲过的三种情况，其中①和②两种情况时答案分别为pArr[2 * C - i]和R - i，比如如果pArr[2 * C - i]比R - i小，那么说明此时就是①情况，反之就是情况②。
            // R <= i表示i在R外，这种是没法优化的，这种情况就直接将回文半径先设置为1，后面再去外扩尝试。因为每一个位置的回文串最少也有1个，也就是它本身
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            // 此时pArr[i]表示i位置外扩的区域至少是多大。
            // 该循环就是尝试外扩
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                // i + pArr[i]是此时回文范围右边界的后面一个字符位置，i - pArr[i]是此时回文范围左边界的前面一个字符位置
                // 如果这两个位置相等，说明此时要么是情况一，要么是情况二的③，这种就去外扩尝试即可，两个位置的字符串相等说明还可以继续外扩
                if (str[i + pArr[i]] == str[i - pArr[i]])
                    // 将当前位置的回文半径自增
                    pArr[i]++;
                    // 如果分支不成立，那么后续也肯定不可能能外扩了，直接跳出循环
                    // 其中如果是情况二的①和②进入到该while循环，那么一定直接就会进入到这个else分支跳出循环
                else {
                    break;
                }
            }
            // 如果这个位置的回文区域的右边界比R更靠右，那么就继续更新R和C，作为新的最右回文边界
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            // 记录最长的回文半径
            //max = Math.max(max, pArr[i]);
            if (max < pArr[i]) {
                max = pArr[i];
                // 此时处理串中最长回文半径的回文串的右边界下标是i+pArr[i]
                // 然后我们再利用公式转换为原始串的下标即可
                end = (i + pArr[i] - 1) / 2;
            }
        }
        // 可以通过回文半径结合最右边界的下标计算出最左边界的下标，这压根就可以直接用substring截取要返回的最长回文串即可
        return s.substring(end - (max - 1), end);
    }
    // 对字符串进行处理
    // 开头、结尾以及每两个字符串之间都插入一个特殊字符，然后转换成字符数组返回
    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }
}
