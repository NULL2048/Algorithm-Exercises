package 大厂刷题班.class05;

// 样本对应模型  空间压缩
// 阉割版  https://leetcode.cn/problems/edit-distance/   这个力扣题就是把删除，替换，添加操作的代价都固定成了1
public class Code03_EditCost {
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) {
            return 0;
        }

        char[] s1 = word1.toCharArray();
        char[] s2 = word2.toCharArray();
        return minCost(s1, s2, 1, 1, 1);
    }

    /**
     * @param s1 字符串1
     * @param s2 字符串2
     * @param ac 添加操作的代价
     * @param rc 替换操作的代价
     * @param dc 删除操作的代价
     * @return
     */
    // for test
    public static int minCost(char[] s1, char[] s2, int ac, int rc, int dc) {
        // dp[i][j]：s1前缀取前i个字符，编辑成s2前缀取j个字符，将s1的前i个字符组成的前缀串如何用最少编辑代价转换成s2的前j个字符的前缀串，最少代价是多少。
        // dp[i][j] = -1表示当前状态下无法编辑成功
        // 这里一定要注意，i表示的是s1的前i个字符（下标0~下标i-1范围上的字符），不包括下标i位置的字符，也就是说i=0，表示的是下标0之前的字符，也就是空字符串，并不包括下标0位置的字符
        // j也是同理。所以这道题如果要表示s1和s2全部的字符，就应该是dp[s1.length][s2.length]，而不是dp[s1.length - 1][s2.length]，所以dp数组的长度要多加1个
        int[][] dp = new int[s1.length + 1][s2.length + 1];

        // 初始化dp数组  规定dp[0][0] = 0
        // 0列s2编辑成s1空串，只能删除
        for (int i = 1; i <= s1.length; i++) {
            dp[i][0] = dc * i;
        }
        // 0行s1空串编辑为s2，只能添加
        for (int j = 1; j <= s2.length; j++) {
            dp[0][j] = ac * j;
        }

        for (int i = 1; i <= s1.length; i++) {
            for (int j = 1; j <= s2.length; j++) {
                // 分情况讨论
                // 可能性1：s1的前i-1个字符（不包括下标i-1位置的字符）可以变成s2的前j个字符（不包括下标j位置的字符），即删掉s1的最后一个i位置的字符即可完成编辑，如果dp[i - 1][j] == -1，说明无法编辑成功，赋值为-1
                int p1 = dp[i - 1][j] != -1 ? dp[i - 1][j] + dc : -1;
                // 可能性2：s1的前i个字符先变成str2的前j-1个字符，后再加上s2的最后一个j位置的字符即可完成编辑。如果dp[i][j - 1] == -1，说明无法编辑成功，赋值为-1
                int p2 = dp[i][j - 1] != -1 ? dp[i][j - 1] + ac : -1;
                // 可能性3: s1, s2两个字符串最后一个字符串相等，如果str1前面i-1个字符可以编辑成s2前面j-1个字符，那么最后一个字符保留即可完成编辑。
                // 可能性4：s1, s2两个字符串最后一个字符串不相等，如果str1前面i-1个字符可以编辑成s2前面j-1个字符，那么最后一个字符替换即可完成编辑。
                int p3 = s1[i - 1] == s2[j - 1] ? (dp[i - 1][j - 1] != -1 ? dp[i - 1][j - 1] : -1) : (dp[i - 1][j - 1] != -1 ? dp[i - 1][j - 1] + rc : -1);

                // 取四种情况的最小值
                dp[i][j] = Math.min(p1, Math.min(p2, p3));
            }
        }
        // 返回答案
        return dp[s1.length][s2.length];
    }


    // for test
    public static int minCost2(String str1, String str2, int ic, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
        char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
        if (chs1.length < chs2.length) {
            int tmp = ic;
            ic = dc;
            dc = tmp;
        }
        int[] dp = new int[shorts.length + 1];
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = ic * i;
        }
        for (int i = 1; i <= longs.length; i++) {
            int pre = dp[0];
            dp[0] = dc * i;
            for (int j = 1; j <= shorts.length; j++) {
                int tmp = dp[j];
                if (longs[i - 1] == shorts[j - 1]) {
                    dp[j] = pre;
                } else {
                    dp[j] = pre + rc;
                }
                dp[j] = Math.min(dp[j], dp[j - 1] + ic);
                dp[j] = Math.min(dp[j], tmp + dc);
                pre = tmp;
            }
        }
        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String str1 = "ab12cd3";
        String str2 = "abcdf";
        System.out.println(minCost(str1.toCharArray(), str2.toCharArray(), 5, 3, 2));
        System.out.println(minCost2(str1, str2, 5, 3, 2));

        str1 = "abcdf";
        str2 = "ab12cd3";
        System.out.println(minCost(str1.toCharArray(), str2.toCharArray(), 3, 2, 4));
        System.out.println(minCost2(str1, str2, 3, 2, 4));

        str1 = "";
        str2 = "ab12cd3";
        System.out.println(minCost(str1.toCharArray(), str2.toCharArray(), 1, 7, 5));
        System.out.println(minCost2(str1, str2, 1, 7, 5));

        str1 = "abcdf";
        str2 = "";
        System.out.println(minCost(str1.toCharArray(), str2.toCharArray(), 2, 9, 8));
        System.out.println(minCost2(str1, str2, 2, 9, 8));

    }

}
