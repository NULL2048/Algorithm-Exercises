package 大厂刷题班.class11;

import java.util.ArrayList;
import java.util.List;

// 从左向右的尝试模型    回溯构建结果
public class Code02_PalindromePartitioningII {

    // 下面的测试链接只测了本题的第一问，直接提交可以通过
    // 本题测试链接 : https://leetcode.cn/problems/palindrome-partitioning-ii/
    // 1、暴力递归代码
    class Solution1 {
        public int minCut(String str) {
            char[] s = str.toCharArray();

            // 这个方法返回的是最少回文串数，我们要求分割次数，所以要再减1
            return process(s, 0) - 1;
        }

        // 返回字符串s[index....s.length-1]最少的回文串数量
        public int process(char[] s, int index) {
            // basecase  index到了s.length位置，已经没有字符串了，直接返回0
            if (index == s.length) {
                return 0;
            }

            // 记录s[index....s.length-1]最少的回文串数量，初始化为系统最大值
            int min = Integer.MAX_VALUE;
            // 尝试从每一个位置分割
            for (int i = index; i < s.length; i++) {
                // 只有当s[index...i]字符串是一个回文串，才能往后面去分割，不然连这一段都不是一个回文串，就不能将它分割出来
                if (ispalindrome(s, index, i)) {
                    // 如果当前分割就是一个回文串，那么就再去下一段进行分割
                    // 取最小值
                    min = Math.min(min, process(s, i + 1));
                }
            }
            // 这里要加1，当字符串只有一个字符的时候，要算一个回文串，上面的流程并没有给算上
            return min + 1;
        }

        // 判断s[l...r]是不是回文串  就是一个暴力模拟
        public boolean ispalindrome(char[] s, int l, int r) {
            while (l < r) {
                if (s[l++] != s[r--]) {
                    return false;
                }
            }
            return true;
        }
    }

    // 2、记忆化搜索代码
    class Solution2 {
        public int minCut(String str) {
            char[] s = str.toCharArray();
            // dp[i]：字符串s[i....s.length-1]最少的回文串数量
            int[] dp = new int[s.length];
            process(s, 0, dp);
            // 这里存的的是最少回文串数，我们要求分割次数，所以要再减1
            return dp[0]- 1;
        }

        public int process(char[] s, int index, int[] dp) {
            if (index == s.length) {
                return 0;
            }

            // 如果重复值就直接返回
            if (dp[index] != 0) {
                return dp[index];
            }

            int min = Integer.MAX_VALUE;
            for (int i = index; i < s.length; i++) {
                if (ispalindrome(s, index, i)) {
                    min = Math.min(min, process(s, i + 1, dp));
                }
            }

            // 赋值
            dp[index] = min + 1;
            return dp[index];
        }

        public boolean ispalindrome(char[] s, int l, int r) {
            while (l < r) {
                if (s[l++] != s[r--]) {
                    return false;
                }
            }
            return true;
        }
    }

    // 3、优化回文串判断的记忆化搜索代码
    class Solution {
        public int minCut(String str) {
            char[] s = str.toCharArray();
            int[] dp = new int[s.length];
            // 构造回文串检查预处理结构
            boolean[][] check = createCheckMap(s, s.length);
            process(s, 0, dp, check);
            // 这个方法返回的是最少回文串数，我们要求分割次数，所以要再减1
            return dp[0]- 1;
        }

        public int process(char[] s, int index, int[] dp,  boolean[][] check) {
            if (index == s.length) {
                return 0;
            }

            if (dp[index] != 0) {
                return dp[index];
            }

            int min = Integer.MAX_VALUE;
            for (int i = index; i < s.length; i++) {
                if (check[index][i]) {
                    min = Math.min(min, process(s, i + 1, dp, check));
                }
            }

            dp[index] = min + 1;
            return dp[index];
        }

        // 构造回文串检查预处理结构   其实构造这个预处理结构的过程就是一个动态规划赋值的过程，利用到了动态规划状态转移的思想
        public boolean[][] createCheckMap(char[] str, int n) {
            // check[i][j]：i~j范围的字符串是回文串就为true，不是回文串就是false
            boolean[][] check = new boolean[n][n];
            // 先给对角线赋值赋值，只有一个字符肯定都是回文串
            for (int i = 0; i < n; i++) {
                check[i][i] = true;
            }
            // 再给第二条对角线赋值，两个字符，如果两个字符相等，那么就是回文串，如果两个字符不等，就不是回文串
            for (int i = 0; i < n - 1; i++) {
                check[i][i + 1] = str[i] == str[i + 1];
            }
            // 没有i > j的情况，dp数组左下半部分无效
            // 给普遍位置赋值，赋值方向通过下面的依赖关系式就能够看出来
            for (int i = n - 3; i >= 0; i--) {
                for (int j = i + 2; j < n; j++) {
                    // 当str[i] == str[j]，并且i+1~j-1范围还是一个回文串，就说明i~j范围是一个回文串
                    check[i][j] = str[i] == str[j] && check[i + 1][j - 1];
                }
            }
            return check;
        }
    }

    // 4、动态规划代码
    public int minCut(String s) {
        char[] str = s.toCharArray();
        int n = str.length;
        // 构造回文串检查预处理结构
        boolean[][] check = createCheckMap(str, n);

        // 从i出发（包括i位置）后面所有的字符串至少分成几个部分让每个部分都是回文。注意这里是存储的是分几部分，不是切几刀，求切几刀要再减1
        int[] dp = new int[n + 1];
        // 因为是从i到最后来设置的dp数组含义，所以i从n-1开始循环，i就表示从i出发尝试找到后面划分最少部分能保证都是回文串的分法。
        for (int i = n - 1; i >= 0; i--) {
            // 如果i~n-1范围整个就是一个回文串，就直接将dp[i]赋值为1
            if (check[i][n - 1]) {
                dp[i] = 1;
            // 如果i~n-1范围不是回文串，就要去找怎么划分能让这一部分切出来的部分都是回文串
            } else {
                // 记录最少分出来的回文串数量
                int next = Integer.MAX_VALUE;
                // 尝试在i~n-1范围上所有可能的划分情况，就从前缀开始划分，看最少能划分出来多少回文串
                // 此时dp[i+1...]肯定都已经求出来了，可以直接用
                for (int j = i; j < n; j++) {
                    // 如果i~j范围是一个回文串，那么i~j就算是一个部分的回文串（是i~n-1范围上的一个前缀串），然后再加上j+1~n-1范围上最少能划分出来的回文串个数，就找到了一种尝试划分的方法，记录下这个回文串数量，然后从所有的可尝试选择中选出最少回文串数量的划分
                    if (check[i][j]) {
                        // 找到最少的回文串划分数量
                        next = Math.min(next, dp[j + 1]);
                    }
                }
                // 上面循环肯定会找到一个划分点j，能使得所有划分出来的回文串最少
                // i~j肯定整个就是一个回文串，1个
                // next记录的是j后面的部分能划分出来的最少回文串数量
                // 所以i~n-1范围最少能划分出来的回文串数量就是next + 1
                dp[i] = 1 + next;
            }
        }
        // dp中存储的是最少回文串数，我们要求分割次数，所以要再减1
        return dp[0] - 1;
    }

    // 构造回文串检查预处理结构
    public static boolean[][] createCheckMap(char[] str, int n) {
        // check[i][j]：i~j范围的字符串是回文串就为true，不是回文串就是false
        boolean[][] check = new boolean[n][n];
        // 先给对角线赋值赋值，只有一个字符肯定都是回文串
        for (int i = 0; i < n; i++) {
            check[i][i] = true;
        }
        // 再给第二条对角线赋值，两个字符，如果两个字符相等，那么就是回文串，如果两个字符不等，就不是回文串
        for (int i = 0; i < n - 1; i++) {
            check[i][i + 1] = str[i] == str[i + 1];
        }

        // 没有i > j的情况，dp数组左下半部分无效
        // 给普遍位置赋值
        for (int i = n - 3; i >= 0; i--) {
            for (int j = i + 2; j < n; j++) {
                // 当str[i] == str[j]，并且i+1~j-1范围还是一个回文串，就说明i~j范围是一个回文串
                check[i][j] = str[i] == str[j] && check[i + 1][j - 1];
            }
        }

        return check;
    }


    // 本题第二问，返回其中一种结果
    public static List<String> minCutOneWay(String s) {
        // 记录一种答案，里面存着所有分割出来的回文串
        List<String> ans = new ArrayList<>();
        // 如果字符串只有一个字符，直接将其加入到答案中
        if (s == null || s.length() < 2) {
            ans.add(s);
        } else {
            // 先构造dp
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[][] checkMap = createCheckMap(str, N);
            int[] dp = new int[N + 1];
            dp[N] = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int next = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            next = Math.min(next, dp[j + 1]);
                        }
                    }
                    dp[i] = 1 + next;
                }
            }

            // 利用dp表回溯构造其中一种结果
            // dp[i]  (0....5) 回文！  dp[0] == dp[6] + 1
            //  (0....5)   6
            // 以0位置作起始位置，去尝试后面的每一个位置作为第一个划分出来的回文串结束位置j，i~j是划分出来的回文串，然后根据dp[i] = dp[h] + 1这个条件去回溯找出一种结果
            for (int i = 0, j = 1; j <= N; j++) {
                // 如果i~j-1是一个回文串，并且dp[i] == dp[j] + 1，就说明i~j-1就是一种划分出来的方法，i~j就是一个符合要求的划分出来的回文串
                if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                    // 将回文串加入到ans
                    ans.add(s.substring(i, j));
                    // 将i设置为j，然后接着去执行相同的流程，找到后续符合条件的划分，下一轮流程就是以现在的j作为起始点向后回溯
                    i = j;
                }
            }
        }
        // 返回结果
        return ans;
    }


    // 本题第三问，返回所有结果
    public static List<List<String>> minCutAllWays(String s) {
        // 双层List，用来记录所有的分割方案
        List<List<String>> ans = new ArrayList<>();
        // 如果字符串只有一个字符，直接将其加入到答案中
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
        } else {
            // 先构造dp表
            char[] str = s.toCharArray();
            int N = str.length;
            boolean[][] checkMap = createCheckMap(str, N);
            int[] dp = new int[N + 1];
            dp[N] = 0;
            for (int i = N - 1; i >= 0; i--) {
                if (checkMap[i][N - 1]) {
                    dp[i] = 1;
                } else {
                    int next = Integer.MAX_VALUE;
                    for (int j = i; j < N; j++) {
                        if (checkMap[i][j]) {
                            next = Math.min(next, dp[j + 1]);
                        }
                    }
                    dp[i] = 1 + next;
                }
            }

            // 递归回溯，找到所有的分割情况
            process(s, 0, 1, checkMap, dp, new ArrayList<>(), ans);
        }
        return ans;
    }

    // s[0....i-1]  存到path里去了
    // s[i..j-1]考察的分出来的第一份
    public static void process(String s, int i, int j, boolean[][] checkMap, int[] dp,
                               List<String> path,
                               List<List<String>> ans) {
        // s[i...N-1]   已经递归到结尾了
        if (j == s.length()) {
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                path.add(s.substring(i, j));
                ans.add(copyStringList(path));
                // 恢复现场
                path.remove(path.size() - 1);
            }
            // s[i...j-1]   还没有递归到结尾
        } else {
            // 判断能否回溯的条件
            if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
                // 找到一个可以向下走的分支,j和j+1
                path.add(s.substring(i, j));
                process(s, j, j + 1, checkMap, dp, path, ans);
                // 恢复现场
                path.remove(path.size() - 1);
            }
            // 向下递归，到下一个划分点,i和j+1
            process(s, i, j + 1, checkMap, dp, path, ans);
        }
    }

    // 将结果复制到新的List中返回
    public static List<String> copyStringList(List<String> list) {
        List<String> ans = new ArrayList<>();
        for (String str : list) {
            ans.add(str);
        }
        return ans;
    }


    // 本题第四问，返回切出来的子串都是回文串的所有方案，和问题三不同的是并不要求切分刀数最少，直接返回所有符合条件的切分方案。
    // 本题测试链接 : https://leetcode.cn/problems/palindrome-partitioning/
    public List<List<String>> partition(String s) {
        // 双层List，用来记录所有的分割方案
        List<List<String>> ans = new ArrayList<>();
        // 如果字符串只有一个字符，直接将其加入到答案中
        if (s == null || s.length() < 2) {
            List<String> cur = new ArrayList<>();
            cur.add(s);
            ans.add(cur);
        } else {
            char[] str = s.toCharArray();
            int N = str.length;
            // check[i][j]：i~j范围的字符串是回文串就为true，不是回文串就是false
            boolean[][] checkMap = createCheckMap(str, N);

            // 递归回溯，找到所有的分割情况
            process(s, 0, 1, checkMap, new ArrayList<>(), ans);
        }
        return ans;
    }

    // s[0....i-1]的划分方案都存到path里去了
    // 此时对s[i..j-1]进行考察，看s[i...j - 1]是否为一个回文串，如果是的话，就将i~j-1部分分割出来加入到path，然后再去尝试考察s[j...]如何切分
    public static void process(String s, int i, int j, boolean[][] checkMap, List<String> path, List<List<String>> ans) {
        // s[i...N-1]   已经递归到结尾了
        if (j == s.length()) {
            // 此时0~i-1的划分方案都已经放入到path了，如果此时i~j-1是一个回文串，并且j遍历完了整个字符串，就说明我们将i~j-1切分出来，加入到ans中，此时ans中就收集好了一种切分方案
            if (checkMap[i][j - 1] ) {
                // 将i~j-1切分出来加入的path中
                path.add(s.substring(i, j));
                // 将path加入到ans，至此ans收集到了一种切分方案
                ans.add(copyStringList(path));
                // 恢复现场，需要将path中的最后一个切分删掉，留给其他的尝试
                path.remove(path.size() - 1);
            }
            // s[i...j-1]   还没有递归到结尾
        } else {
            // 判断能否回溯的条件，如果此时i~j-1是回文串，所以i~j-1是可以切分出来的，加入到path中
            if (checkMap[i][j - 1]) {
                // 将i~j-1切分出来加入到path中
                path.add(s.substring(i, j));
                // 此时0~j-1都已经找到切分方案加入到path了
                // 下面我们需要去尝试在j~N-1范围内找到一个可以向下走的分支
                // 从判断j~j+1范围开始，看j~j+1是否为一个回文串可以且分出来
                process(s, j, j + 1, checkMap, path, ans);
                // 恢复现场
                path.remove(path.size() - 1);
            }
            // 执行到这里，0~i-1的切分方法都已经加入到path了，并且切分i~j-1的方案也已经尝试了（i~j-1有可能可以切分，也有可能无法切分，取决于checkMap[i][j - 1]）
            // 下面我们继续向下递归，到下一个划分点i和j+1，去判断i~j+1范围的字符串是否为回文串，看能不能将这一部分且分出来。
            // 这里不用去考虑是不是要在某些时候将i增加来尝试所有的可能性，因为将i加1进行尝试的前提是i前面已经找到了切分方法，而这个已经囊括在了上面代码的process(s, j, j + 1, checkMap, path, ans)中
            process(s, i, j + 1, checkMap,  path, ans);
        }
    }

    public static void main(String[] args) {
        String s = null;
        List<String> ans2 = null;
        List<List<String>> ans3 = null;

        System.out.println("本题第二问，返回其中一种结果测试开始");
        s = "abacbc";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabccbac";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();

        s = "aabaa";
        ans2 = minCutOneWay(s);
        for (String str : ans2) {
            System.out.print(str + " ");
        }
        System.out.println();
        System.out.println("本题第二问，返回其中一种结果测试结束");
        System.out.println();
        System.out.println("本题第三问，返回所有可能结果测试开始");
        s = "cbbbcbc";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

        s = "aaaaaa";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();

        s = "aab";
        ans3 = minCutAllWays(s);
        for (List<String> way : ans3) {
            for (String str : way) {
                System.out.print(str + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("本题第三问，返回所有可能结果测试结束");
    }

}
