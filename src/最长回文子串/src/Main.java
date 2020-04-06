package 最长回文子串.src;

import java.io.*;

/**
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba"也是一个有效答案。
 *
 * 输入: "cbbd"
 * 输出: "bb"
 */
public class Main {
    public static void main(String[] args) throws IOException {
        StreamTokenizer scan = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        scan.nextToken();
        String str = scan.sval;

//        if (str == null || str.isEmpty()) {
//            return "";
//        }
//
//        if (str.length() == 1) {
//            return str;
//        }
//
//        if (str.length() == 2 && str.charAt(0) != str.charAt(1)) {
//            return String.valueOf(str.charAt(0));
//        }

        int h = 0;
        int t = 0;

        // 偶数
        for (int i = 0; i < str.length(); i++) {
            int head = i;
            int tail = i + 1;
            boolean flag = false;

            while (head >= 0 && tail < str.length()) {
                if (str.charAt(head) == str.charAt(tail)) {
                    head--;
                    tail++;
                    flag = true;
                } else {
                    break;
                }
            }

            if (flag && ((tail - 1) - (head + 1) > t - h)) {
                h = head + 1;
                t = tail - 1;
            }
        }

        // 奇数
        for (int i = 1; i < str.length(); i++) {
            int head = i - 1;
            int tail = i + 1;
            boolean flag = false;

            while (head >= 0 && tail < str.length()) {
                if (str.charAt(head) == str.charAt(tail)) {
                    head--;
                    tail++;
                    flag = true;
                } else {
                    break;
                }
            }

            if (flag && ((tail - 1) - (head + 1)  > t - h)) {
                h = head + 1;
                t = tail - 1;
            }
        }

        out.println(str.substring(h, t + 1));
        out.flush();
    }
}
