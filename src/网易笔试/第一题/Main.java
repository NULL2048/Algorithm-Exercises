package 网易笔试.第一题;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        StreamTokenizer scan = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        scan.nextToken();
        int n = (int) scan.nval;

        for (int i = 0; i <n; i++) {

        }
        out.flush();
    }
}
