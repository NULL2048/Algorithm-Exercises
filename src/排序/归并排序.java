package 排序;

import java.io.*;

public class 归并排序 {
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

        // 传入的是下标值0和index-1
        mergeSort(a, 0, index - 1);


        for (int i = 0; i < index; i++) {
            out.print(a[i] + " ");
        }

        out.flush();
    }

    /**
     *
     * 驱动方法
     * @param arr
     * @param left
     * @param right
     */
    public static void mergeSort(int[] arr, int left, int right) {
        // 因为传入的是下标值，所以left和right表示的是下标值
        // 当递归到还剩下两个元素的时候就可以返回了，所以left>=right就直接返回就行了，left + 1 = right的时候正好只剩下两个元素
        if (left < right) {
            //  防止溢出
            int mid = left + ((right - left) >> 1);
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        // 临时外部空间
        int[] tempArr1 = new int[20];
        int[] tempArr2 = new int[20];

        int index1 = 0;
        for (int i = left; i <= mid; i++) {
            tempArr1[index1++] = arr[i];
        }
        // 设置成极大值的作用就是为了防止后面像arr写入排序好的数据的时候，tempArr的指针只想最后面了，那个值是0，所以就把0全都写入到arr了
        tempArr1[index1] = 99999999;

        int index2 = 0;
        for (int i = mid + 1; i <= right; i++) {
            tempArr2[index2++] = arr[i];
        }
        tempArr2[index2] = 99999999;


        index1 = 0;
        index2 = 0;
        for (int i = left; i <= right; i++) {
            // 如果上面没有设置极大值，最后写入到arr数组的数据都会是0，这个很好理解，因为这里是取两个数最小的放入到arr，0肯定是最小的，但是0并不属于这个数据集，而是tempArr溢出部分的数据
            arr[i] = tempArr1[index1] < tempArr2[index2] ? tempArr1[index1++] : tempArr2[index2++];
        }
    }

}
