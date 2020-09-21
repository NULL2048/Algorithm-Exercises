package 排序.小和问题;

import java.io.*;

public class Main {
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
        // 这里是递归出口，直接返回0就行了
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

//    // for test
//    public static int comparator(int[] arr) {
//        if (arr == null || arr.length < 2) {
//            return 0;
//        }
//        int res = 0;
//        for (int i = 1; i < arr.length; i++) {
//            for (int j = 0; j < i; j++) {
//                res += arr[j] < arr[i] ? arr[j] : 0;
//            }
//        }
//        return res;
//    }
//
//    // for test
//    public static int[] generateRandomArray(int maxSize, int maxValue) {
//        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
//        }
//        return arr;
//    }
//
//    // for test
//    public static int[] copyArray(int[] arr) {
//        if (arr == null) {
//            return null;
//        }
//        int[] res = new int[arr.length];
//        for (int i = 0; i < arr.length; i++) {
//            res[i] = arr[i];
//        }
//        return res;
//    }
//
//    // for test
//    public static boolean isEqual(int[] arr1, int[] arr2) {
//        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
//            return false;
//        }
//        if (arr1 == null && arr2 == null) {
//            return true;
//        }
//        if (arr1.length != arr2.length) {
//            return false;
//        }
//        for (int i = 0; i < arr1.length; i++) {
//            if (arr1[i] != arr2[i]) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    // for test
//    public static void printArray(int[] arr) {
//        if (arr == null) {
//            return;
//        }
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print(arr[i] + " ");
//        }
//        System.out.println();
//    }
//
//    public static int smallSum(int[] arr) {
//        if (arr == null || arr.length < 2) {
//            return 0;
//        }
//        return mergeSort(arr, 0, arr.length - 1);
//    }
//
//    // for test
//    public static void main(String[] args) {
//        int testTime = 500000;
//        int maxSize = 100;
//        int maxValue = 100;
//        boolean succeed = true;
//        for (int i = 0; i < testTime; i++) {
//            int[] arr1 = generateRandomArray(maxSize, maxValue);
//            int[] arr2 = copyArray(arr1);
//            if (smallSum(arr1) != comparator(arr2)) {
//                succeed = false;
//                printArray(arr1);
//                printArray(arr2);
//                break;
//            }
//        }
//        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
//    }
}
