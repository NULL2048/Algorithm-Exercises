package 大厂刷题班.class12;

import java.util.Arrays;

// 窗口
// 本题测试链接 : https://leetcode.cn/problems/permutation-in-string/
// 只有欠帐表中的每个字符的欠账大于0的时候，才需要改变all的值，如果每个字符的欠账修改变为小于0了，不管怎么变，all也不变，因为这是一次无效欠账或还款
public class Code01_ContainAllCharExactly {
    // 暴力解
    public static int containExactly1(String s, String a) {
        if (s == null || a == null || s.length() < a.length()) {
            return -1;
        }
        char[] aim = a.toCharArray();
        Arrays.sort(aim);
        String aimSort = String.valueOf(aim);
        for (int L = 0; L < s.length(); L++) {
            for (int R = L; R < s.length(); R++) {
                char[] cur = s.substring(L, R + 1).toCharArray();
                Arrays.sort(cur);
                String curSort = String.valueOf(cur);
                if (curSort.equals(aimSort)) {
                    return L;
                }
            }
        }
        return -1;
    }

    public static int containExactly2(String s, String a) {
        if (s == null || a == null || s.length() < a.length()) {
            return -1;
        }
        char[] str = s.toCharArray();
        char[] aim = a.toCharArray();
        for (int L = 0; L <= str.length - aim.length; L++) {
            if (isCountEqual(str, L, aim)) {
                return L;
            }
        }
        return -1;
    }

    public static boolean isCountEqual(char[] str, int L, char[] aim) {
        int[] count = new int[256];
        for (int i = 0; i < aim.length; i++) {
            count[aim[i]]++;
        }
        for (int i = 0; i < aim.length; i++) {
            if (count[str[L + i]]-- == 0) {
                return false;
            }
        }
        return true;
    }

    // 最优解
    public static int containExactly3(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return -1;
        }
        char[] str2 = s2.toCharArray();
        int M = str2.length;
        int[] count = new int[256];
        for (int i = 0; i < M; i++) {
            count[str2[i]]++;
        }
        int all = M;
        char[] str1 = s1.toCharArray();
        int R = 0;
        // 0~M-1
        for (; R < M; R++) { // 最早的M个字符，让其窗口初步形成
            // 只有欠帐表的欠账是大于0的，才可以修改all
            if (count[str1[R]]-- > 0) {
                all--;
            }
        }
        // 窗口初步形成了，并没有判断有效无效，决定下一个位置一上来判断
        // 接下来的过程，窗口右进一个，左吐一个。只需要维护一个指针即可，不用维护两个指针
        for (; R < str1.length; R++) {
            if (all == 0) { // R-1
                return R - M;
            }
            // 只有欠帐表的欠账是大于0的，才可以修改all
            if (count[str1[R]]-- > 0) {
                all--;
            }
            // 只有欠帐表的欠账是大于等于0的，才可以修改all
            if (count[str1[R - M]]++ >= 0) {
                all++;
            }
        }
        // 还要再判断一下最后一个窗口，判断all是否为0
        return all == 0 ? R - M : -1;
    }


    // LeetCode题解
    public boolean checkInclusion(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() > s2.length()) {
            return false;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int m = str1.length;
        int n = str2.length;
        // 欠帐表
        int[] count = new int[256];
        // 记录当前总共欠了多少个字符
        int all = 0;
        // 初始化欠帐表   将str1中0~m-1的所有字符都先加入到欠帐表中
        for (int i = 0; i < m; i++) {
            count[str1[i]]++;
            all++;
        }

        // 窗口左右边界
        int l = 0;
        int r = 0;
        // 最早的m个字符，让其窗口初步形成
        for (; r < m; r++) {
            // 只有欠帐表的欠账是大于0的，才可以修改all
            if ((count[str2[r]]--) > 0) {
                all--;
            }
        }

        // 窗口初步形成了，并没有判断有效无效，决定下一个位置一上来判断
        // 接下来的过程，窗口右进一个，左吐一个。其实这里可以优化为只需要维护一个指针即可，不用维护两个指针，因为左边界可以直接通过r计算出来
        for (; r < n; l++, r++) {
            // 如果all==0，说明找到了符合要求的子串
            if (all == 0) {
                // 如果是要返回子串的起始位置，这里就返回r-m
                return true;
            }
            // 只有欠帐表的欠账是大于0的，才可以修改all。因为可能有一些在str2中的字符，压根就不存在于str1中，那么这样的字符在欠帐表中肯定都是小于等于0，如果左边界划过了这样的字符，这个字符的count一定是小于0的
            // 这里是先赋值，再++，也就是先判断判断是否大于等于0的值是count[str2]这个值，而不是判断count[str2]++完了之后的值是否大于等于0
            if (count[str2[l]]++ >= 0) {
                all++;
            }
            // 只有欠帐表的欠账是大于等于0的，才可以修改all
            // 如果右边界滑过了str1没有的字符，那么它的count一定是小于等于0的，所以要先判断一下这是不是有效的还账或者欠帐
            if (count[str2[r]]-- > 0) {
                all--;
            }
        }

        // 还要再判断一下最后一个窗口，判断all是否为0
        return all == 0 ? true : false;
    }

    // for test
    public static String getRandomString(int possibilities, int maxSize) {
        char[] ans = new char[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strMaxSize = 20;
        int aimMaxSize = 10;
        int testTimes = 500000;
        System.out.println("test begin, test time : " + testTimes);
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strMaxSize);
            String aim = getRandomString(possibilities, aimMaxSize);
            int ans1 = containExactly1(str, aim);
            int ans2 = containExactly2(str, aim);
            int ans3 = containExactly3(str, aim);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(str);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("test finish");

    }
}
