package 体系学习班.class28;
// 力扣有一道题是要找一个字符串中最长回文子串是什么，下面的代码是返回最长回文子串的长度。可以直接在下面代码的基础上稍微修改一下就可以作为力扣这道题的解
// 测试链接：https://leetcode.cn/problems/longest-palindromic-substring/
public class Code01_Manacher {
    // Manacher算法主流程
    public static int manacher(String s) {
        // 过滤无效参数
        if (s == null || s.length() == 0) {
            return 0;
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
        // 遍历处理后的字符串，开始算法流程
        // 尝试以每一个位置作为回文中心点去向外扩
        for (int i = 0; i < str.length; i++) {
            // R第一个违规的位置，i>= R
            // R > i表示i在R内，这种情况是可以存在优化的，就是分了我们之前讲过的三种情况，其中①和②两种情况时答案分别为pArr[2 * C - i]和R - i，比如如果pArr[2 * C - i]比R - i小，那么说明此时就是①情况。
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
            max = Math.max(max, pArr[i]);
        }
        // 处理后的字符串的最长回文半径减1，就是原始字符串的最长回文串长度
        return max - 1;
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

    // for test
    public static int right(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = manacherString(s);
        int max = 0;
        for (int i = 0; i < str.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < str.length && str[L] == str[R]) {
                L--;
                R++;
            }
            max = Math.max(max, R - L - 1);
        }
        return max / 2;
    }

    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            if (manacher(str) != right(str)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
