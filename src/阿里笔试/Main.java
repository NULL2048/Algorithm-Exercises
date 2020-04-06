package 阿里笔试;


import java.io.*;


public class Main {
    private static int[] a = new int[1000005];
    private static int[] num = new int[1000005];
    private static int index = 0;
    private static int n;
    private static int count;
    private static int sum;

    public static void main(String[] args) throws IOException {
        StreamTokenizer scan = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        scan.nextToken();
        int n = (int) scan.nval;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

            }
        }
        out.flush();
    }

}
