package 体系学习班.class19;
// https://leetcode.cn/problems/decode-ways/
// 直接用下面的动态规划代码提交到力扣就可以过
public class Code02_ConvertToLetterString {

    // 1、暴力递归
    // str只含有数字字符0~9
    // 返回多少种转化方案
    public static int number(String str) {
        // 判空
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    // str[0..i-1]转化无需过问
    // str[i.....]去转化，返回有多少种转化方法
    public static int process(char[] str, int i) {
        // 当遍历完了所有位置的数字时，并且还没有碰到无效情况，就说明已经找到了一种有效的转换方法，返回1
        if (i == str.length) {
            return 1;
        }
        // 如果碰到了某一个数字是0，这种数字没有对应的应为字母，所以这一分支的转换方法都是无效的，因为当前这个分支想要单独转换i这个位置的数字，但是这个数字0没有对应的英文字符，所以整个分支的转换都是无效的，直接返回0，表示有0个转化方法
        if (str[i] == '0') { // 之前的决定有问题
            return 0;
        }

        // 下面讨论两种情况：
        // 1、单独转换i位置的数字
        // 2、将i位置和i + 1位置的数字组合起来，去进行转换

        // 走到这里说明str[i] != '0'
        // 可能性一：i单转
        int ways = process(str, i + 1);
        // 可能性二：i和i + 1组合起来转换。这种转换需要有一定条件限制，首先i + 1位置不能越界，其次i和i + 1位置组合成的数字需要小于等于26，这样才能有对应的英文字母
        if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
            // 将两种情况的结果进行累加
            ways += process(str, i + 2);
        }
        // 返回
        return ways;
    }

    // 2、动态规划

    // 从右往左的动态规划
    // 就是上面方法的动态规划版本
    // dp[i]表示：str[i...]有多少种转化方式
    public static int dp1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // 将传入的字符串转换成字符数组
        char[] str = s.toCharArray();
        // 创建一维dp
        int N = str.length;
        int[] dp = new int[N + 1];
        // 赋初值
        dp[N] = 1;
        // 对整个dp数组进行赋值
        for (int i = N - 1; i >= 0; i--) {
            if (str[i] != '0') {
                // 	情况一
                int ways = dp[i + 1];
                // 情况二
                if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
                    ways += dp[i + 2];
                }
                // 将结果赋值给dp数组
                dp[i] = ways;
            }
        }
        // 返回最终结果
        return dp[0];
    }


    // 为了测试
    // 从左往右的动态规划
    // dp[i]表示：str[0...i]有多少种转化方式
    public static int dp2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        if (str[0] == '0') {
            return 0;
        }
        int[] dp = new int[N];
        dp[0] = 1;
        for (int i = 1; i < N; i++) {
            if (str[i] == '0') {
                // 如果此时str[i]=='0'，那么他是一定要拉前一个字符(i-1的字符)一起拼的，
                // 那么就要求前一个字符，不能也是‘0’，否则拼不了。
                // 前一个字符不是‘0’就够了嘛？不够，还得要求拼完了要么是10，要么是20，如果更大的话，拼不了。
                // 这就够了嘛？还不够，你们拼完了，还得要求str[0...i-2]真的可以被分解！
                // 如果str[0...i-2]都不存在分解方案，那i和i-1拼成了也不行，因为之前的搞定不了。
                if (str[i - 1] == '0' || str[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0)) {
                    return 0;
                } else {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                dp[i] = dp[i - 1];
                if (str[i - 1] != '0' && (str[i - 1] - '0') * 10 + str[i] - '0' <= 26) {
                    dp[i] += i - 2 >= 0 ? dp[i - 2] : 1;
                }
            }
        }
        return dp[N - 1];
    }

    // 为了测试
    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }

    // 为了测试
    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = randomString(len);
            int ans0 = number(s);
            int ans1 = dp1(s);
            int ans2 = dp2(s);
            if (ans0 != ans1 || ans0 != ans2) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }
}