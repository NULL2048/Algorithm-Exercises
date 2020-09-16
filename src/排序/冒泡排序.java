package 排序;

import java.io.*;

public class 冒泡排序 {
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

        // 第一层循环只是用来控制要比较的轮数  只需要比较index-1轮，否则会越界，因为是两两比较
        for (int i = 0; i < index - 1; i++) {
            // 第二层循环才是比较过程中的下标，因为每一轮都会把最大的数移动到最右端，所以要减i
            for (int j = 0; j < index - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < index; i++) {
            out.print(a[i] + " ");
        }

        out.flush();
    }
}
