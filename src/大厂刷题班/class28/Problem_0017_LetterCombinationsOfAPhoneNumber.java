package 大厂刷题班.class28;

import java.util.ArrayList;
import java.util.List;

// 深度优先遍历
// https://leetcode.cn/problems/letter-combinations-of-a-phone-number/
public class Problem_0017_LetterCombinationsOfAPhoneNumber {
    // 数字和字母的对应和关系表，一维下标是数字，二维是数字对应的字母表
    public static char[][] phone = {
            { 'a', 'b', 'c' }, // 2    0
            { 'd', 'e', 'f' }, // 3    1
            { 'g', 'h', 'i' }, // 4    2
            { 'j', 'k', 'l' }, // 5    3
            { 'm', 'n', 'o' }, // 6
            { 'p', 'q', 'r', 's' }, // 7
            { 't', 'u', 'v' },   // 8
            { 'w', 'x', 'y', 'z' }, // 9
    };

    public List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return ans;
        }

        char[] dig = digits.toCharArray();
        char[] path = new char[dig.length];

        dfs(dig, 0, path, 0, phone, ans);
        return ans;
    }


    public void dfs(char[] dig, int di, char[] path, int pi, char[][] phone, List<String> ans) {
        // 递归出口，已经将所有按键都遍历过了，收集一下沿途的答案
        if (di == dig.length) {
            // 将path加入到ans中
            ans.add(String.valueOf(path));
        } else {
            // 尝试这个按键所有可能的字符向下递归
            for (int i = 0; i < phone[dig[di] - '2'].length; i++) {
                // 将字符加入到path中
                path[pi] = phone[dig[di] - '2'][i];
                dfs(dig, di + 1, path, pi + 1, phone, ans);
            }
        }
    }
}
