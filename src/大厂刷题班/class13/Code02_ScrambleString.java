package 大厂刷题班.class13;

// 样本对应模型
// 本题测试链接 : https://leetcode.cn/problems/scramble-string/
public class Code02_ScrambleString {
    // 大过滤  判断str2是否有可能是str1的旋变串
    public boolean isSameTypeSameNumber (char[] s1, char[] s2) {
        // 必须保证长度一样
        if (s1.length != s2.length) {
            return false;
        }

        // 再去判断字符组成和个数是否相同
        int[] cnt = new int[256];
        for (int i = 0; i < s1.length; i++) {
            cnt[s1[i]]++;
        }

        for (int i = 0; i < s2.length; i++) {
            cnt[s2[i]]--;
            if (cnt[s2[i]] < 0) {
                return false;
            }
        }

        return true;
    }


    // 0、暴力递归  四个可变参数
    public boolean isScramble0(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }

        if ((str1 == null && str2 != null) || (str1 != null && str2 == null)) {
            return false;
        }

        if (str1.equals(str2)) {
            return true;
        }

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        return isSameTypeSameNumber(s1, s2) && process0(s1, 0, s1.length - 1, s2, 0, s2.length - 1);
    }

    // str1[L1...R1] str2[L2...R2] 是否互为旋变串
    // 一定保证这两段是等长的
    public boolean process0(char[] s1, int l1, int r1, char[] s2, int l2, int r2) {
        // basecase
        // 当你l1到r1和l2到r2上分别只有一个字符，如果只有一个字符的情况下，str1上这个字符跟str2上这个字符相等就说明是旋变串，不相等就不是。
        if (l1 == r1) {
            return s1[l1] == s2[l2];
        }

        // 尝试所有的切分位置，遍历所有可能的情况。就以当前s1的范围，从l1~r1范围上尝试所有的切割位置
        for (int cut = l1; cut < r1; cut++) {
            // 如果1左对2左，并且1右对2右
            boolean p1 = process0(s1, l1, cut, s2, l2, l2 + (cut - l1)) && process0(s1, cut + 1, r1, s2, l2 + (cut - l1) + 1, r2);
            // 如果1左对2右，并且1右对2左
            boolean p2 = process0(s1, l1, cut, s2, r2 - (cut - l1), r2) && process0(s1, cut + 1, r1, s2, l2, r2 - (cut - l1) - 1);

            // 只要两种对应关系有一种为true，那么就说明是当前范围的字符串是旋变串关系
            if (p1 || p2) {
                return true;
            }
        }

        // 如果将所有可能都尝试了一遍，没有发现为true的，就说明当前范围的字符串不是旋变串关系
        return false;
    }


    // 1、暴力递归   三个可变参数
    public boolean isScramble1(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }

        if ((str1 == null && str2 != null) || (str1 != null && str2 == null)) {
            return false;
        }

        if (str1.equals(str2)) {
            return true;
        }

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        return isSameTypeSameNumber(s1, s2) && process1(s1, 0, s2, 0, s1.length);
    }

    // 返回str1[从L1开始往右长度为size的子串]和str2[从L2开始往右长度为size的子串]是否互为旋变字符串
    // 在str1中的这一段和str2中的这一段一定是等长的，所以只用一个参数size
    public boolean process1(char[] s1, int l1, char[] s2, int l2, int size) {
        if (size == 1) {
            return s1[l1] == s2[l2];
        }

        // 因为参数引入了长度size，所以为了方便写代码，这里尝试不同的分隔下标也改成尝试不同的分割长度
        // leftPartLen表示分割后左部分的长度
        // 枚举每一种分割左部分长度情况，有一个计算出互为旋变就返回true。都算不出来最后返回false
        for (int leftPartLen = 1; leftPartLen < size; leftPartLen++) {
            // 如果1左对2左，并且1右对2右
            boolean p1 = process1(s1, l1, s2, l2, leftPartLen) && process1(s1, l1 + leftPartLen, s2, l2 + leftPartLen, size - leftPartLen);
            // 如果1左对2右，并且1右对2左
            boolean p2 = process1(s1, l1, s2, l2 + size - 1 - leftPartLen + 1, leftPartLen) && process1(s1, l1 + leftPartLen, s2, l2, size - leftPartLen);
            // 只要两种对应关系有一种为true，那么就说明是当前范围的字符串是旋变串关系
            if (p1 || p2) {
                return true;
            }
        }
        // 如果将所有可能都尝试了一遍，没有发现为true的，就说明当前范围的字符串不是旋变串关系
        return false;
    }


    // 2、记忆化搜索   三维
    public boolean isScramble(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }

        if ((str1 == null && str2 != null) || (str1 != null && str2 == null)) {
            return false;
        }

        if (str1.equals(str2)) {
            return true;
        }

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        // dp[i][j][k] = 0 processDP(i,j,k)状态之前没有算过的
        // dp[i][j][k] = -1 processDP(i,j,k)状态之前算过的,返回值是false
        // dp[i][j][k] = 1 processDP(i,j,k)状态之前算过的,返回值是true
        int[][][] dp = new int[s1.length][s2.length][s1.length + 1];

        return isSameTypeSameNumber(s1, s2) && process(s1, 0, s2, 0, s1.length, dp);
    }

    // 这里返回值依旧是boolean
    public boolean process(char[] s1, int l1, char[] s2, int l2, int size, int[][][] dp) {
        // 查看缓存中是否有当前结果，如果有的话直接返回
        if (dp[l1][l2][size] != 0) {
            return dp[l1][l2][size] == 1;
        }
        // 这里要先手动赋一个值，不然编译器会报错，报ans未初始化
        boolean ans = false;
        // basecase
        if (size == 1) {
            ans = s1[l1] == s2[l2];
        } else {
            // 因为参数引入了长度size，所以为了方便写代码，这里尝试不同的分隔下标也改成尝试不同的分割长度
            // leftPartLen表示分割后左部分的长度
            // 枚举每一种分割左部分长度情况，有一个计算出互为旋变就返回true。都算不出来最后返回false
            for (int leftPartLen = 1; leftPartLen < size; leftPartLen++) {
                boolean p1 = process(s1, l1, s2, l2, leftPartLen, dp) && process(s1, l1 + leftPartLen, s2, l2 + leftPartLen, size - leftPartLen, dp);
                boolean p2 = process(s1, l1, s2, l2 + size - 1 - leftPartLen + 1, leftPartLen, dp) && process(s1, l1 + leftPartLen, s2, l2, size - leftPartLen, dp);

                if (p1 || p2) {
                    ans = true;
                    break;
                }
            }
        }

        // 在最后统一给dp赋值，并返回。9
        dp[l1][l2][size] = ans ? 1 : -1;
        return ans;
    }


    // 4、动态规划   三维
    public boolean isScramble3(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!isSameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = s1.length();
        boolean[][][] dp = new boolean[N][N][N + 1];
        for (int L1 = 0; L1 < N; L1++) {
            for (int L2 = 0; L2 < N; L2++) {
                dp[L1][L2][1] = str1[L1] == str2[L2];
            }
        }
        // 第一层for循环含义是：依次填size=2层、size=3层..size=N层，每一层都是一个二维平面
        // 第二、三层for循环含义是：在具体的一层，整个面都要填写，所以用两个for循环去填一个二维面
        // L1的取值氛围是[0,N-size]，因为从L1出发往右长度为size的子串，L1是不能从N-size+1出发的，这样往右就不够size个字符了
        // L2的取值范围同理
        // 第4层for循环完全是递归函数怎么写，这里就怎么改的
        for (int size = 2; size <= N; size++) {
            for (int L1 = 0; L1 <= N - size; L1++) {
                for (int L2 = 0; L2 <= N - size; L2++) {
                    for (int leftPart = 1; leftPart < size; leftPart++) {
                        if ((dp[L1][L2][leftPart] && dp[L1 + leftPart][L2 + leftPart][size - leftPart])
                                || (dp[L1][L2 + size - leftPart][leftPart] && dp[L1 + leftPart][L2][size - leftPart])) {
                            dp[L1][L2][size] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][0][N];
    }
}
