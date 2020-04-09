package 网易笔试.第二题;

import java.io.*;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) throws Exception{
        StreamTokenizer scan = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        scan.nextToken();
        int n = (int) scan.nval;
        scan.nextToken();
        int m = (int) scan.nval;
        scan.nextToken();
        int f = (int) scan.nval;

        // 存储
        HashSet<Integer> set = new HashSet();
        set.add(f);
        for (int i = 0; i < m; i++) {
            scan.nextToken();
            int q = (int) scan.nval;
            boolean flag = false;
            int[] a = new int [q + 5];
            for (int j = 0; j < q; j++) {
                scan.nextToken();
                a[j] = (int) scan.nval;

                if (set.contains(a[j])) {
                    flag = true;
                }
            }

            if (flag) {
                for (int j = 0; j < q; j++) {
                    set.add(a[j]);
                }
            }

        }

        out.println(set.size());
        out.println(set);

        out.flush();
    }
}
