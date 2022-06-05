package 体系学习班.class05;

import java.io.*;

// 根据荷兰问题优化后的快速排序
public class Code03_QuickSort {
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
        // 传入的是下标值
        quickSort(a, 0, index - 1);
        for (int i = 0; i < index; i++) {
            out.print(a[i] + " ");
        }
        out.flush();
    }
    /**
     * 驱动程序
     * @param arr 要排序的数组
     * @param l 当前这一轮的左边界
     * @param r 当前这一轮的右边界
     */
    public static void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            // 快速排序和归并排序在这里就有一个很大的区别，归并排序是先进行向下递归，当递归到底的时候，再向上合并，进行排序。所以归并排序的驱动程序先调用mergeSort,然后再调用merge
            // 但是快速排序在向下递归的过程中就逐渐排列好了，当地递归到底的时候数组已经是排列好的数组了，没有向上合并的过程了，所以要比归并排序快一些。所以快速排序是先partition()，然后再quickSort().
            // 我们每次就将数组最右边界的数作为基准来进行排序
            // 数组p存储的是等于基准的区间的左右下标边界值
            int[] p = partition(arr, l, r);
            // 上一步已经分出来了三个区间了，其中中间的等于基准的区间不用比较了，再次话分左部区间和右部区间就行了
            quickSort(arr, l, p[0] - 1);
            quickSort(arr, p[1] + 1, r);
        }
    }

    /**
     * 执行排序过程
     * 这个方法我们对经典快速排序进行了优化
     * @param arr
     * @param l
     * @param r
     * @return 返回等于基准的区间范围
     */
    public static int[] partition(int[] arr, int l, int r) {
        // indexLeft下标(包括indexLeft本身)左边的数都比基准数据小，包括indexLeft指向的数自己   在还没有筛选出数来的时候indexLeft先向左移动一位，所以是l-1
        int indexLeft = l - 1;
        // indexRight下标(包括indexRight本身)右边的数都比基准数大   因为一开始r下标是基准数，所以区间是从r-1开始的，在还没有筛选出数来的时候需要先让indexRight向右走一位  所以是r-1+1
        int indexRight = r - 1 + 1;
        // 游标  指向正在判断的下标位置
        int cur = l;

        // 以当前区间最右边的数为基准，也就是下标r指向的数
        // cur左边的时已经排好的，indexRight右边的是已经排好的，所以cur游标不能超过indexRight
        while (cur < indexRight) {
            // 当游标指向的数小于基准的时候
            if (arr[cur] < arr[r]) {
                // 将小于基准的区间范围扩大   并且将新加入到小于基准区间的位置的数与之前cur下标位置的数（这个数是判断好的小于基准的）进行交换，然后再将游标向右移动一位
                swap(arr, ++indexLeft, cur++);
                // 当游标指向的数大于基准的时候
            } else if (arr[cur] > arr[r]) {
                // 将大于基准的区间范围扩大  并且将新加入到大于基准区间的位置的数与之前cur下表位置的数（这个数是判断好的大于基准的）进行交换，然后游标不变，等下一轮继续判断。这是因为上面小于基准的数进行交换的时候，换到cur的数不是等于基准的数，就是没有进行交换，所以数与基准的大小关系已经确定了，不用再判断了，才需要将cur游标进行右移。但是这里换到cur的数我们并不能确认与基准的大小关系，所以还是得继续判断
                swap(arr, --indexRight, cur);
                // 当游标指向的数等于基准的时候
            } else {
                // 直接将游标右移就行了，因为indexLeft与cur之间的数是等于基准的
                cur++;
            }
        }
        // 此时基准还在原位置，需要将他交换到等于基准的区间，所以r与indexRight交换，r指向基准，indexRight指向的是大于基准的数，两者交换一下正好
        swap(arr, indexRight, r);
        // 返回等于基准的区间范围，注意是闭区间，所以是[indexLeft + 1, indexRight]。indexRight是因为上一步交换了，所以它也指向基准了
        return new int[]{indexLeft + 1, indexRight};
    }

    /**
     * 交换方法
     * @param arr
     * @param index1
     * @param index2
     */
    public static void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }

}
