package 大厂刷题班.class05;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

// 【动态规划】【一个样本做行个样本做列】【样本对应模型】【字节面试题】【KMP】
// 题目：
// 给定两个字符串s1和s2，问s2最少删除多少字符可以成为s1的子串？
// 比如 s1 = "abcde"，s2 = "axbc"
// 返回 1
public class Code04_DeleteMinCost {
    // 解法一
    // 求出str2所有的子序列，然后按照长度排序，长度大的排在前面。
    // 然后考察哪个子序列字符串和s1的某个子串相等(KMP)，答案就出来了。
    // 分析：
    // 因为题目原本的样本数据中，有特别说明s2的长度很小。所以这么做也没有太大问题，也几乎不会超时。
    // 但是如果某一次考试给定的s2长度远大于s1，这么做就不合适了。
    public static int minCost1(String s1, String s2) {
        // 用来记录s2的全部子序列，相当于找到删除操作能让s2形成的所有可能的字符串
        List<String> s2Subs = new ArrayList<>();
        // 通过递归找到s2的所有子序列
        process(s2.toCharArray(), 0, "", s2Subs);
        // 按照字符串长度长度由大到小排序
        s2Subs.sort(new LenComp());
        // 将所有str2的子序列和str1做KMP，看哪一个str2的子序列是str1的子串
        // 我们从str2子序列长度由大到小一次判断，找到的第一个最大长度的子序列就是我们想要的答案，因为子序列长度最大能成为str1的子串，说明str2删除的字符最少
        for (String str : s2Subs) {
            // indexOf底层和KMP算法代价几乎一样，也可以用KMP代替
            if (s1.indexOf(str) != -1) {
                // 找到一个就直接返回
                return s2.length() - str.length();
            }
        }
        // 如果str2无法通过删除操作得到str1，直接返回str2的长度
        return s2.length();
    }

    // 每个字符只有两种选择，要或者不要，通过递归来将所有的子序列情况找到并记录到List中  时间复杂度就是O(2^M)，M是str2的长度
    public static void process(char[] str2, int index, String path, List<String> list) {
        // basecase  将当前分支形成的子序列存入到list中
        if (index == str2.length) {
            list.add(path);
            return;
        }
        process(str2, index + 1, path, list);
        process(str2, index + 1, path + str2[index], list);
    }

    public static class LenComp implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o2.length() - o1.length();
        }

    }



    // 解法二
    // 生成所有s1的子串
    // 然后考察每个子串和s2的编辑距离(假设编辑距离只有删除动作且删除一个字符的代价为1)
    // 如果s1的长度较小，s2长度较大，这个方法比较合适
    public static int minCost2(String s1, String s2) {
        // 过滤无效参数
        if (s1.length() == 0 || s2.length() == 0) {
            return s2.length();
        }
        // 取最小的编辑距离
        int ans = Integer.MAX_VALUE;
        char[] str2 = s2.toCharArray();
        // 通过一个二层循环遍历出s1的所有子串
        for (int start = 0; start < s1.length(); start++) {
            for (int end = start + 1; end <= s1.length(); end++) {
                // str1[start....end]
                // substring -> [ 0,1 )
                // 将sr2和str1当前找到的子串去做找编辑距离，看str2能否通过删除操作得到str1的当前子串
                ans = Math.min(ans, distance(str2, s1.substring(start, end).toCharArray()));
            }
        }
        // 如果ans不是系统最大值，说明有办法得到，则返回最小的编辑距离，如果是系统最大值，就返回str2的长度表示无法通过删除得到s1的子串
        return ans == Integer.MAX_VALUE ? s2.length() : ans;
    }


    // 求str2变到s1sub的编辑距离
    // 假设编辑距离只有删除动作且删除一个字符的代价为1，返回至少要删几个字符，如果变不成，返回Integer.Max
    public static int distance(char[] str2, char[] s1sub) {
        int row = str2.length;
        int col = s1sub.length;
        // 注意，这里的dp[i][j]含义和笔记中的那个代码不太一样，笔记中的dp[i][j]中的i和j表示前缀长度
        // 这个dp[i][j]中的i和j表示的是前缀的结尾下标，0...i和0...j，本质其实这两个是一样的
        int[][] dp = new int[row][col];
        // dp[i][j]的含义：
        // str2[0..i]仅通过删除行为变成s1sub[0..j]的最小代价
        // 可能性一：
        // str2[0..i]变的过程中，不保留最后一个字符(str2[i])，
        // 那么就是通过str2[0..i-1]变成s1sub[0..j]之后，再最后删掉str2[i]即可 -> dp[i][j] = dp[i-1][j] + 1
        // 可能性二：
        // str2[0..i]变的过程中，想保留最后一个字符(str2[i])，然后变成s1sub[0..j]，
        // 这要求str2[i] == s1sub[j]才有这种可能, 然后str2[0..i-1]变成s1sub[0..j-1]即可
        // 也就是str2[i] == s1sub[j] 的条件下，dp[i][j] = dp[i-1][j-1]
        dp[0][0] = str2[0] == s1sub[0] ? 0 : Integer.MAX_VALUE;

        // 先赋初值
        for (int j = 1; j < col; j++) {
            dp[0][j] = Integer.MAX_VALUE;
        }
        for (int i = 1; i < row; i++) {
            dp[i][0] = (dp[i - 1][0] != Integer.MAX_VALUE || str2[i] == s1sub[0]) ? i : Integer.MAX_VALUE;
        }

        // 复制普遍位置
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                // 先给赋值为系统最大
                dp[i][j] = Integer.MAX_VALUE;
                // 子啊分别讨论两种情况，求最小值
                if (dp[i - 1][j] != Integer.MAX_VALUE) {
                    dp[i][j] = dp[i - 1][j] + 1;
                }
                if (str2[i] == s1sub[j] && dp[i - 1][j - 1] != Integer.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
                }

            }
        }
        // 返回最终答案
        return dp[row - 1][col - 1];
    }

    // 这个是课程视频和笔记里将的方法二的代码版本，这个对于我来说可能更好理解一些，毕竟听课就是讲的这个代码
    // x字符串只通过删除的方式，变到y字符串
    // 返回至少要删几个字符
    // 如果变不成，返回Integer.Max
    public static int onlyDelete(char[] x, char[] y) {
        if (x.length < y.length) {
            return Integer.MAX_VALUE;
        }
        int N = x.length;
        int M = y.length;
        int[][] dp = new int[N + 1][M + 1];

        // 给所有位置初始设置为系统最大，其实这个赋值过程和上面的代码一样都一起并入到给普遍位置赋值的双循环的开头位置也是一样的，时间复杂度没有区别
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[0][0] = 0;
        // dp[i][j]表示前缀长度
        // 赋初值
        for (int i = 1; i <= N; i++) {
            dp[i][0] = i;
        }

        // 普遍位置赋值
        for (int xlen = 1; xlen <= N; xlen++) {
            for (int ylen = 1; ylen <= Math.min(M, xlen); ylen++) {
                // 只有两种情况
                // 情况一：如果str2的前xlen-1个字符已经能得到str1的前个ylen字符，那么直接将此时str2最后一个字符删掉即可
                if (dp[xlen - 1][ylen] != Integer.MAX_VALUE) {
                    dp[xlen][ylen] = dp[xlen - 1][ylen] + 1;
                }
                // 情况二：两个字符串最后两个字符相等，并且str2的前缀xlen-1可以删除得到str1的前缀ylen-1
                // 那么什么都不用改变，str2的前缀xlen就一定等于str1的前缀ylen了
                if (x[xlen - 1] == y[ylen - 1] && dp[xlen - 1][ylen - 1] != Integer.MAX_VALUE) {
                    dp[xlen][ylen] = Math.min(dp[xlen][ylen], dp[xlen - 1][ylen - 1]);
                }
            }
        }
        // 返回答案
        return dp[N][M];
    }


    // 解法三：解法二的优化
    public static int minCost3(String s1, String s2) {
        if (s1.length() == 0 || s2.length() == 0) {
            return s2.length();
        }
        char[] str2 = s2.toCharArray();
        char[] str1 = s1.toCharArray();
        int M = str2.length;
        int N = str1.length;
        int[][] dp = new int[M][N];
        int ans = M;
        // 开始的列数   只要是str1子串的开始列数相同，那么他们就公用同一个dp数组
        for (int start = 0; start < N; start++) {
            dp[0][start] = str2[0] == str1[start] ? 0 : M;
            for (int row = 1; row < M; row++) {
                dp[row][start] = (str2[row] == str1[start] || dp[row - 1][start] != M) ? row : M;
            }
            ans = Math.min(ans, dp[M - 1][start]);
            // 以上已经把start列所有的数据填好
            // 以下要把dp[...][start+1....N-1]的信息填好
            // start...end end - start +2
            for (int end = start + 1; end < N && end - start < M; end++) {
                // 0... first-1 行 不用管
                int first = end - start;
                dp[first][end] = (str2[first] == str1[end] && dp[first - 1][end - 1] == 0) ? 0 : M;
                for (int row = first + 1; row < M; row++) {
                    dp[row][end] = M;
                    if (dp[row - 1][end] != M) {
                        dp[row][end] = dp[row - 1][end] + 1;
                    }
                    if (dp[row - 1][end - 1] != M && str2[row] == str1[end]) {
                        dp[row][end] = Math.min(dp[row][end], dp[row - 1][end - 1]);
                    }
                }
                ans = Math.min(ans, dp[M - 1][end]);
            }
        }
        return ans;
    }

    // 解法四：来自学生的做法，时间复杂度O(N * M平方)
    // 复杂度和方法三一样，但是思路截然不同
    public static int minCost4(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        HashMap<Character, ArrayList<Integer>> map1 = new HashMap<>();
        for (int i = 0; i < str1.length; i++) {
            ArrayList<Integer> list = map1.getOrDefault(str1[i], new ArrayList<Integer>());
            list.add(i);
            map1.put(str1[i], list);
        }
        int ans = 0;
        // 假设删除后的str2必以i位置开头
        // 那么查找i位置在str1上一共有几个，并对str1上的每个位置开始遍历
        // 再次遍历str2一次，看存在对应str1中i后续连续子串可容纳的最长长度
        for (int i = 0; i < str2.length; i++) {
            if (map1.containsKey(str2[i])) {
                ArrayList<Integer> keyList = map1.get(str2[i]);
                for (int j = 0; j < keyList.size(); j++) {
                    int cur1 = keyList.get(j) + 1;
                    int cur2 = i + 1;
                    int count = 1;
                    for (int k = cur2; k < str2.length && cur1 < str1.length; k++) {
                        if (str2[k] == str1[cur1]) {
                            cur1++;
                            count++;
                        }
                    }
                    ans = Math.max(ans, count);
                }
            }
        }
        return s2.length() - ans;
    }

    // for test
    public static String generateRandomString(int l, int v) {
        int len = (int) (Math.random() * l);
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ('a' + (int) (Math.random() * v));
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {

        char[] x = { 'a', 'b', 'c', 'd' };
        char[] y = { 'a', 'd' };

        System.out.println(onlyDelete(x, y));

        int str1Len = 20;
        int str2Len = 10;
        int v = 5;
        int testTime = 10000;
        boolean pass = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String str1 = generateRandomString(str1Len, v);
            String str2 = generateRandomString(str2Len, v);
            int ans1 = minCost1(str1, str2);
            int ans2 = minCost2(str1, str2);
            int ans3 = minCost3(str1, str2);
            int ans4 = minCost4(str1, str2);
            if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                pass = false;
                System.out.println(str1);
                System.out.println(str2);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("test pass : " + pass);
    }

}
