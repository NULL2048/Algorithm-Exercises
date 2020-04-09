package 测试;


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

        int n = (int) scan.nval;
        int m = (int) scan.nval;
        int a = (int) scan.nval;
        int b = (int) scan.nval;

        out.flush();
    }

}
