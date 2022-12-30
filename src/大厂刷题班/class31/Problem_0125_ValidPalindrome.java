package 大厂刷题班.class31;
// 双指针   字符串   数组   回文串  模拟
// https://leetcode.cn/problems/valid-palindrome/
public class Problem_0125_ValidPalindrome {
    public boolean isPalindrome(String str) {
        // 题目要求空字符串认为是回文串
        if (str == null || str.length() == 0) {
            return true;
        }

        // 转为字符串数组
        char[] s = str.toCharArray();
        // 设置两边的双指针
        int l = 0;
        int r = s.length - 1;
        // 从两边开始向中间遍历，判断是否为回文串
        while (l <= r) {
            // 判断左指针是否为有效，如果无效就l++，去下一个位置去判断
            if (!validChar(s, l)) {
                l++;
                continue;
            }
            // 判断右指针是否为有效，如果无效就r--，去下一个位置去判断
            if (!validChar(s, r)) {
                r--;
                continue;
            }

            // 如果出现了l和r都是有效字符，但是不相等的情况，那么一定不是回文串，直接返回false
            if (s[l] != s[r]) {
                return false;
            }
            // 如果两个位置的字符相等，就继续向中间移动，判断下一组位置的字符是否有效且相等
            l++;
            r--;
        }

        // 如果完成了上面循环的判断，就说明字符串是回文串，返回true
        return true;
    }

    // 判断s数组i下标位置的字符是否为有效字符（大小写字母和数组就是有效字符），有效则返回true，无效则返回false
    // 如果是大写字母，将其转换为小写字母后再返回true
    public boolean validChar(char[] s, int i) {
        if (s[i] >= '0' && s[i] <= '9') {
            return true;
        }

        if (s[i] >= 'a' && s[i] <= 'z') {
            return true;
        }
        // 如果是大写字母，将其转换为小写字母后再返回true
        if (s[i] >= 'A' && s[i] <= 'Z') {
            s[i] = (char) (s[i] - 'A' + 'a');
            return true;
        }

        // 其余情况一律都是无效字符，返回false
        return false;
    }
}
