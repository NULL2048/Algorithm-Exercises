package 阿里笔试;

import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.net.BindException;

public class Main {
    private static int[] a = new int[1000005];
    private static int[] num = new int[1000005];
    private static int index = 0;
    private static int n;
    private static int count;
    private static int sum;

    public static void main(String[] args) throws IOException {
        StreamTokenizer scan = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        scan.nextToken();
        n = (int) scan.nval;
        for (int i = 0; i < 3; i++) {
            scan.nextToken();
            a[i] = (int) scan.nval;
        }

        for (int i = 0; i < n; i++) {
            int index = 0;
            int[] num = new int[n + 5];
            take(i, num, index);
        }

    }

    public static void take(int l, int[] num, int index) {
        if (l >= n) {
            return;
        }
        num[index++] = a[l];
        if (a[l] > num[n]) {
            num[n] = a[l];
        }

        for (int i = 0; i < index; i++) {
            System.out.print(num[i] + " ");
        }
        System.out.println();

        take(++l, num, index);
    }

}
