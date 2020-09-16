package 排序;

import java.io.*;

public class 选择排序 {
    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int[] a = new int[20];
        int index = 0;
        while (true) {
            in.nextToken();
            int num = (int) in.nval;
            if (num == -1) {
                break;
            }

            a[index++] = num;
        }

        // 每一次就会把最小的数移动到左端
        for (int i = 0; i < index; i++) {
            // 找到i下标之后的最小的数
            int minIndex = i;
            for (int j = i + 1; j < index; j++) {
                if (a[minIndex] > a[j]) {
                    minIndex = j;
                }
            }
            int temp = a[i];
            a[i] = a[minIndex];
            a[minIndex] = temp;
        }

        for (int i = 0; i < index; i++) {
            out.print(a[i] + " ");
        }

        out.flush();
    }
}
