package 大厂刷题班.class33;
// 字符串    哈希表
// https://leetcode.cn/problems/valid-anagram/
public class Problem_0242_ValidAnagram {
    public static boolean isAnagram(String s, String t) {
        // 必须要加这一句，保证后面的++操作和--操作次数是一样的，这样才能保证只要是没有出现--count[cha] < 0的情况，那么就一定是返回true
        if (s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        int[] count = new int[256];
        for (char cha : str1) {
            count[cha]++;
        }
        for (char cha : str2) {
            if (--count[cha] < 0) {
                return false;
            }
        }
        return true;
    }
}
