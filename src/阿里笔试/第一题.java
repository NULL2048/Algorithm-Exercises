package 阿里笔试;

import java.io.*;

public class 第一题 {
    public static void main(String[] args) throws IOException {
        StreamTokenizer scan = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        scan.nextToken();
        int n = (int) scan.nval;
        int[] a = new int[n + 5];
        for (int i = 0; i < n; i++) {
            scan.nextToken();
            a[i] = (int) scan.nval;
        }

        int lcm = a[1] * a[0] / gcd(a[1], a[0]);
        for (int i = 2; i < n; i++) {
            lcm = lcm * a[i] / gcd(lcm, a[i]);
        }

        out.println(lcm);
        out.flush();

        return;

//        int[] b = new int[n + 5];
//        for (int i = 0; i < n - 1; i++) {
//            int temp = a[i + 1] - a[i];
//            if (temp <= 0) {
//                out.println("-1");
//                return;
//            }
//            b[i] = a[i + 1] - a[i];
//        }
//
//        if (n == 2) {
//            out.println(b[0]);
//        }
//
//        int temp = gcd(b[0], b[1]);
//        for (int i = 2; i < n - 1; i++) {
//            temp = gcd(temp, b[i]);
//        }
//        out.println(temp);
//
//        out.flush();
    }

    public static int gcd(int a, int b) {
        if (a < b) {
            int temp = a;
            a = b;
            b = temp;
        }

        int c = a % b;
        while (c != 0) {
            a = b;
            b = c;
            c = a % b;
        }

        return b;
    }
}
