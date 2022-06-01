package 体系学习班.class04;

import java.io.*;

public class Code02_SmallSum {
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
        out.println(mergeSort(a, 0, index - 1));
        for (int i = 0; i < index; i++) {
            out.print(a[i] + " ");
        }
        out.flush();
    }

    /**
     * 驱动方法，先进行递归的过程，讲大数组划分成小数组
     * @param arr
     * @param left
     * @param right
     * @return
     */
    public static int mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + ((right - left) >> 1);
            // 这里就把所有的递归调用的方法都加在一起然后return，因为这里需要返回小和，mergeSort和merge方法都有可能返回数值，都要算在里面，所以这种情况的递归放回就要这样写。
            return mergeSort(arr, left, mid) + mergeSort(arr, mid + 1, right) + merge(arr, left, mid, right);
        }
        // 这里是递归出口，直接返回0就行了。当递归到最后一层的视乎，没有最小和，所以一定是返回0
        return 0;
    }


    /**
     * 进行求小和，具体流程在笔记里已经画好了，过程和归并排序完全一样，只不过加了一个res的过程
     * @param arr
     * @param left
     * @param mid
     * @param right
     * @return
     */
    public static int merge(int[] arr, int left, int mid, int right) {
        int[] tempArr1 = new int[500];
        int[] tempArr2 = new int[500];
        int index1 = 0;
        int index2 = 0;
        for (int i = left; i <= mid; i++) {
            tempArr1[index1++] = arr[i];
        }
        tempArr1[index1] = 99999999;
        for (int i = mid + 1; i <= right; i++) {
            tempArr2[index2++] = arr[i];
        }
        tempArr2[index2] = 99999999;
        int length1 = index1;
        // 记录一下右半部分的数组的长度，在后面求右半部分中比左半部非某个数的大的数的个数时用的到
        int length2 = index2;
        index1 = 0;
        index2 = 0;

        int res = 0;
        for (int i = left; i <= right; i++) {
            // 计算小和   tempArr1[index1] * (length2 - index2)的意思是因为左部数组中的tempArr1[index1]比右部数组中的tempArr2[index2]小，所以求小和的时候就是tempArr1[index1]乘以右部所有比tempArr1[index1]大的数的个数。不用管左部比tempArr1[index1]大的个数，因为在之前已经计算完了
            res += (tempArr1[index1] < tempArr2[index2] ? tempArr1[index1] * (length2 - index2) : 0);
            arr[i] = tempArr1[index1] < tempArr2[index2] ? tempArr1[index1++] : tempArr2[index2++];
        }
        return res;
    }
}