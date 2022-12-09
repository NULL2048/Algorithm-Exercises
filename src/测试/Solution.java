package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class Solution {
    public static String removeDuplicateLetters1(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        int[] map = new int[256];
        for (int i = 0; i < str.length(); i++) {
            map[str.charAt(i)]++;
        }
        int minACSIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
            if (--map[str.charAt(i)] == 0) {
                break;
            }
        }
        // 0...break(之前) minACSIndex
        // str[minACSIndex] 剩下的字符串str[minACSIndex+1...] -> 去掉str[minACSIndex]字符 -> s'
        // s'...
        return String.valueOf(str.charAt(minACSIndex)) + removeDuplicateLetters1(
                str.substring(minACSIndex + 1).replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
    }


    public static String removeDuplicateLetters(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        if (str.length() == 1) {
            return str;
        }

        if (str.length() == 2) {
            return str.charAt(0) == str.charAt(1) ? str.substring(0, 1) : str;
        }

        char[] s = str.toCharArray();
        int[] map = new int[26];
        for (int i = 0; i < s.length; i++) {
            map[s[i] - 'a']++;
        }

        int l = 0;
        int r = 0;
        boolean[] isAdd = new boolean[26];
        boolean[] isVisited = new boolean[s.length];

        StringBuilder sb = new StringBuilder();
        while (r < s.length) {
            if (!isVisited[r] && ((map[s[r] - 'a'] == -1 || --map[s[r] - 'a'] > 0))) {
                isVisited[r] = true;
                r++;
            } else if (isVisited[r] && map[s[r] - 'a'] > 0) {
                isVisited[r] = true;
                r++;
            } else {
                isVisited[r] = true;

                int minCharacterIndex = -1;
                for (int i = l; i <= r; i++) {
                    if (map[s[i] - 'a'] != -1 && (minCharacterIndex == -1 || s[minCharacterIndex] > s[i])) {
                        minCharacterIndex = i;
                    }

                }

                sb.append(s[minCharacterIndex]);

                // for (int i = minCharacterIndex + 1; i <= r; i++) {
                //     if (map[s[i] - 'a'] != -1) {
                //         map[s[i] - 'a']++;
                //     }
                // }

                map[s[minCharacterIndex] - 'a'] = -1;
                l = minCharacterIndex + 1;
                r = l;

            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        int[][] grid = {{1, 1, -1}, {1, -1, 1}, {-1, 1, 1}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {1,2,3,6};
        int n = 3;

//        Arrays.sort(nums);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        //System.out.println(nums);

//        "ADOBECODEBANC"
//        "ABC"
        String str1 = "rusrbofeggbbkyuyjsrzornpdguwzizqszpbicdquakqws";
        String str2 = "aa";
        System.out.println(removeDuplicateLetters(str1));
        System.out.println("hesitxyplovdqfkz");
        System.out.println("hesitxyplovdqfkz".equals(removeDuplicateLetters(str1)));

//        int[] ans = maxSumOfThreeSubarrays(nums, n);
//        for (int a : ans) {
//            System.out.print(a + " ");
//        }
    }
}