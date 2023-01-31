package 大厂刷题班.class34;
// 字符串   数组   计数
// https://leetcode.cn/problems/first-unique-character-in-a-string/
public class Problem_0387_FirstUniqueCharacterInAString {
    public int firstUniqChar(String str) {
        int[] count = new int[26];
        char[] s = str.toCharArray();

        for (int i = 0; i < s.length; i++) {
            count[s[i] - 'a']++;
        }

        for (int i = 0; i < s.length; i++) {
            if (count[s[i] - 'a'] == 1) {
                return i;
            }
        }

        return -1;
    }
}
