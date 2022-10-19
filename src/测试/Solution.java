package 测试;

import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static String decodeString(String s) {
        String[] stack = new String[s.length()];
        int top = -1;



        StringBuilder sb = new StringBuilder();

//        for (int i = 0; stack[i] != null; i++) {
//            sb.append(stack[i]);
//        }
        sb = process(s, 0, top, stack).str;
        return sb.toString();
    }

    public static class Info {
        private StringBuilder str;
        private int end;
        private int top;

        public Info(StringBuilder str, int end, int top) {
            this.str = str;
            this.end = end;
            this.top = top;
        }
    }

    public static Info process(String s, int i, int top, String[] stack) {
        int cur = 0;
        StringBuilder sb = new StringBuilder();
        while (i < s.length() && s.charAt(i) != ']') {
            if (s.charAt(i) != '[') {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    cur = (cur * 10) + (s.charAt(i) - '0');
                } else {
                    sb.append(String.valueOf(s.charAt(i)));
                }
                i++;
            } else {
                Info nextInfo = process(s, i + 1, top, stack);
                i = nextInfo.end;
                StringBuilder sbNext = nextInfo.str;
                top = nextInfo.top;
                sbNext = printString(cur, sbNext);
                sb.append(sbNext);

                //stack[++top] = sb.toString();

                //sb.delete(0, sb.length());
                cur = 0;
            }
        }

        return new Info(sb, i + 1, top);
    }

    public static StringBuilder printString(int num, StringBuilder str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(str);
        }
        return sb;
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1,1},{1,0,1},{1,1,1}};
        int[] nums = {7,-9,15,-2};

        String str = "100[leetcode]";
        System.out.println(decodeString(str));
    }


}