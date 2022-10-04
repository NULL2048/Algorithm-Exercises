package 测试;

import java.util.HashMap;
class Solution {
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int[] preMap = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            preMap[i] = -1;
        }

        int[] temp = new int[256];
        for (int i = 0; i < 256; i++) {
            temp[i] = -1;
        }
        for (int i = 0; i < str.length; i++) {
            preMap[i] = temp[str[i]];
            temp[str[i]] = i;
        }

        int maxLen = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) {
            maxLen = Math.max(maxLen, process(str.length - 1, str, preMap));
        }
        return maxLen;
    }

    public static int process(int index, char[] str, int[] preMap) {
        if (index == 0) {
            return 1;
        }

        int p1 = preMap[index] + 1;
        int p2 = index - 1 - process(index - 1, str, preMap) + 1;

        return index - Math.max(p1, p2) + 1;

    }

    public static void main(String[] args) {
        int[] nums = {1,2,1};
        String str = "abcabcbb";
        System.out.println(lengthOfLongestSubstring(str));
    }


}