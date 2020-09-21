package 排序;

import java.io.*;

public class 快速排序 {
    public static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
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

        quickSort(a, 0, index - 1);

        for (int i = 0; i < index; i++) {
            out.print(a[i] + " ");
        }


        out.flush();
    }

    /**
     * 驱动程序
     * @param arr
     * @param l
     * @param r
     */
    public static void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            int[] p = partition(arr, l, r);
            quickSort(arr, l, p[0] - 1);
            quickSort(arr, p[1] + 1, r);
        }
    }

    public static int[] partition(int[] arr, int l, int r) {
        int indexLeft = l - 1;
        int indexRight = r;

        while (l < indexRight) {
            if (arr[l] < arr[r]) {
                swap(arr, ++indexLeft, l++);
            } else if (arr[l] > arr[r]) {
                swap(arr, --indexRight, l);
            } else {
                l++;
            }
        }
        swap(arr, indexRight, r);

        return new int[]{indexLeft + 1, indexRight};
    }

    public static void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
