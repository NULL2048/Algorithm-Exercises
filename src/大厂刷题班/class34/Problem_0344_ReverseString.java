package 大厂刷题班.class34;
// 字符串  数组
// https://leetcode.cn/problems/reverse-string/
public class Problem_0344_ReverseString {
    public void reverseString(char[] s) {
        int l = 0;
        int r = s.length - 1;
        while (l < r) {
            swap(s, l, r);
            l++;
            r--;
        }
    }

    public void swap(char[] s, int i, int j) {
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}
