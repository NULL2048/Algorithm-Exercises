package 最长公共子串;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        StreamTokenizer scan = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        scan.nextToken();
        String str = scan.sval;
        int max = 0;
        int head = 0;
        int tail = 0;

        // 偶数情况
        for (int i = 0; i < str.length(); i++) {
            int j = i;
            int k = i + 1;
            int cnt = 0;

            while (j >= 0 && k < str.length()) {
                if (str.charAt(j) == str.charAt(k)) {
                    cnt += 2;
                    j--;
                    k++;
                } else {
                    break;
                }
            }
            if (cnt > max) {
                max = cnt;
                head = j + 1;
                tail = k - 1;
            }
        }

        // 奇数情况
        for (int i = 0; i < str.length(); i++) {
            int j = i - 1;
            int k = i + 1;
            int cnt = 1;

            while (j >= 0 && k < str.length()) {
                if (str.charAt(j) == str.charAt(k)) {
                    cnt += 2;
                    j--;
                    k++;
                } else {
                    break;
                }
            }
            if (cnt > max) {
                max = cnt;
                head = j + 1;
                tail = k - 1;
            }
        }

        System.out.println(str.substring(head, tail + 1));
    }
}

