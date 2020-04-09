package 字符串问题.滑动窗口.无重复字符的最长子串;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int sum = 0;
        // 这里使用HashSet这个数据结构作为滑动窗口
        HashSet<Character> set = new HashSet();
        // 滑动窗口的左端
        int left = 0;
        // 滑动窗口的右端
        int right = 0;

        /**
         * 思路就是
         * abcabcbb，进入这个队列（窗口）为 abc 满足题目要求，当再进入 a，队列变成了 abca，这时候不满足要求。所以，我们要移动这个队列！
         *
         * 如何移动？
         * 我们只要把队列的左边的元素移出就行了，直到满足题目要求！
         *
         * 一直维持这样的队列，找出队列出现最长的长度时候，求出解！
         *
         * 时间复杂度：O(n)
         */
        while (right < s.length()) {
            // 窗口中没有重复字符，直接将这个字符放入set中，滑动窗口扩大
            if (!set.contains(s.charAt(right))) {
                set.add(s.charAt(right));
                right++;

            // 窗口中有了重读字符，需要移动窗口，将左端移动一位并将左端的字符删去
            // 我们就是要从最左端开始，把每一个元素都有可能作为滑动窗口的开头，之所以要从最左端开始，因为这样才能保证求出的滑动窗口可能是最大的
            // 有了冲突直接删除最左端是不会有任何问题的
            } else {
                set.remove(s.charAt(left));
                left++;
            }

            // 记录最长的符合要求的字符串
            sum = Math.max(sum, right - left);
        }

        return sum;
    }
}
